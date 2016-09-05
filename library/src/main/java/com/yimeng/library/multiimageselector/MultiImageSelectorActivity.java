package com.yimeng.library.multiimageselector;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.orhanobut.logger.Logger;
import com.yimeng.library.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 多图选择
 * Created by Nereo on 2015/4/7.
 * Updated by nereo on 2016/1/19.
 */
public class MultiImageSelectorActivity extends FragmentActivity implements MultiImageSelectorFragment.Callback {
    private String TAG = getClass().getSimpleName();
    /**
     * 最大图片选择次数，int类型，默认9
     */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /**
     * 图片选择模式，默认多选
     */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    /**
     * 是否显示相机，默认显示
     */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    /**
     * 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合
     */
    public static final String EXTRA_RESULT = "select_result";
    /**
     * 默认选择集
     */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";

    /**
     * 单选
     */
    public static final int MODE_SINGLE = 0;
    /**
     * 多选
     */
    public static final int MODE_MULTI = 1;

    private ArrayList<String> resultList = new ArrayList<>();
    private Button mSubmitButton;
    private int mDefaultCount;
    private Context mContext;


    public static Intent getStartActIntent(Activity from, boolean showCamera, int maxCount) {
        Intent intent = new Intent(from, MultiImageSelectorActivity.class);
        // whether show camera
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, showCamera);
        // max select image amount
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxCount);
        // select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, maxCount > 1 ? MODE_MULTI : MODE_SINGLE);
        // default select images (support array list)
        //intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, defaultDataArray);
        // startActivityForResult(intent, REQUEST_IMAGE);
        return intent;
    }

    public static Intent getStartActIntent(Activity from, boolean showCamera, int maxCount, ArrayList<String> list) {
        Intent intent = new Intent(from, MultiImageSelectorActivity.class);
        // whether show camera
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, showCamera);
        // max select image amount
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxCount);
        // select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        // default select images (support array list)
        intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, list);
        // startActivityForResult(intent, REQUEST_IMAGE);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_default);
        mContext = this;
        Dexter.continuePendingRequestsIfPossible(mMultiplePermissionsListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { /*申请权限,在23之后的手机上要处理*/
            if (Dexter.isRequestOngoing()) return;
            Dexter.checkPermissions(mMultiplePermissionsListener, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE);
        } else init();
    }

    // ### fix 2016/7/11  解决 java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState ###

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        // super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

    private MultiplePermissionsListener mMultiplePermissionsListener = new MultiplePermissionsListener() {
        @Override
        public void onPermissionsChecked(MultiplePermissionsReport report) {
            Logger.d(TAG, "onPermissionRationaleShouldBeShown has run ===>");
            //用户拒绝了部分权限
            if (report.getDeniedPermissionResponses().size() != 0) {
                Toast.makeText(mContext, "用户拒绝了访问权限!", Toast.LENGTH_LONG).show();
                finish();
                return;
            }
            init();
        }

        @Override
        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
            Logger.d(TAG, "onPermissionRationaleShouldBeShown has run ===>");
            token.continuePermissionRequest();
        }
    };

    private void init() {
        Intent intent = getIntent();
        mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, 9);
        int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
        boolean isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        if (mode == MODE_MULTI && intent.hasExtra(EXTRA_DEFAULT_SELECTED_LIST)) {
            resultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        }

        Bundle bundle = new Bundle();
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, mDefaultCount);
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, mode);
        bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShow);
        bundle.putStringArrayList(MultiImageSelectorFragment.EXTRA_DEFAULT_SELECTED_LIST, resultList);

        getSupportFragmentManager().beginTransaction().add(R.id.image_grid, Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle)).commit();

        // 返回按钮
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        // 完成按钮
        mSubmitButton = (Button) findViewById(R.id.commit);
        if (resultList == null || resultList.size() <= 0) {
            mSubmitButton.setText(R.string.action_done);
            mSubmitButton.setEnabled(false);
        } else {
            updateDoneText();
            mSubmitButton.setEnabled(true);
        }
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resultList != null && resultList.size() > 0) {
                    // 返回已选择的图片数据
                    Intent data = new Intent();
                    data.putStringArrayListExtra(EXTRA_RESULT, resultList);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }

    private void updateDoneText() {
        mSubmitButton.setText(String.format(Locale.getDefault(), "%s(%d/%d)", getString(R.string.action_done), resultList.size(), mDefaultCount));
    }

    @Override
    public void onSingleImageSelected(String path) {
        Intent data = new Intent();
        resultList.add(path);
        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onImageSelected(String path) {
        if (!resultList.contains(path)) {
            resultList.add(path);
        }
        // 有图片之后，改变按钮状态
        if (resultList.size() > 0) {
            updateDoneText();
            if (!mSubmitButton.isEnabled()) {
                mSubmitButton.setEnabled(true);
            }
        }
    }

    @Override
    public void onImageUnselected(String path) {
        if (resultList.contains(path)) {
            resultList.remove(path);
        }
        updateDoneText();
        // 当为选择图片时候的状态
        if (resultList.size() == 0) {
            mSubmitButton.setText(R.string.action_done);
            mSubmitButton.setEnabled(false);
        }
    }

    @Override
    public void onCameraShot(File imageFile) {
        if (imageFile != null) {

            // notify system
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));

            Intent data = new Intent();
            resultList.add(imageFile.getAbsolutePath());
            data.putStringArrayListExtra(EXTRA_RESULT, resultList);
            setResult(RESULT_OK, data);
            finish();
        }
    }

}
