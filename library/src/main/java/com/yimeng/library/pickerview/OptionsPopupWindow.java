package com.yimeng.library.pickerview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.yimeng.library.R;
import com.yimeng.library.pickerview.lib.ScreenInfo;
import com.yimeng.library.pickerview.lib.WheelOptions;

import java.util.ArrayList;

/**
 * 选项选择器，可支持一二三级联动选择 / 2016-05-22 修改没有联动 ！！！！！！！！！！！！！1//
 *
 * @author Sai
 */
@SuppressWarnings("deprecation")
public class OptionsPopupWindow extends PopupWindow implements OnClickListener {
    private View rootView; // 总的布局
    WheelOptions wheelOptions;
    private View btnSubmit, btnCancel;
    private OnOptionsSelectListener optionsSelectListener;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    public OptionsPopupWindow(Context context) {
        super(context);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new BitmapDrawable());// 这样设置才能点击屏幕外dismiss窗口
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.ActionSheetDialogAnimation);

        LayoutInflater mLayoutInflater = LayoutInflater.from(context);
        rootView = mLayoutInflater.inflate(R.layout.pw_options, null);
        // -----确定和取消按钮
        btnSubmit = rootView.findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = rootView.findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        // ----转轮
        final View optionsPicker = rootView.findViewById(R.id.optionspicker);
        ScreenInfo screenInfo = new ScreenInfo((Activity) context);
        wheelOptions = new WheelOptions(optionsPicker);

        wheelOptions.screenheight = screenInfo.getHeight();

        setContentView(rootView);
    }

    public void setPicker(ArrayList<String> optionsItems) {
        wheelOptions.setPicker(optionsItems, null, null, null, false);
    }

    public void setPicker(ArrayList<String> options1Items, ArrayList<String> options2Items, boolean linkage) {
        wheelOptions.setPicker(options1Items, options2Items, null, null, linkage);
    }

    public void setPicker(ArrayList<String> options1Items, ArrayList<String> options2Items, ArrayList<String> options3Items, ArrayList<String> options4Items, boolean linkage) {
        wheelOptions.setPicker(options1Items, options2Items, options3Items, options4Items, linkage);
    }

    /**
     * 设置选中的item位置
     *
     * @param option1
     */
    public void setSelectOptions(int option1) {
        wheelOptions.setCurrentItems(option1, 0, 0, 0);
    }

    /**
     * 设置选中的item位置
     *
     * @param option1
     * @param option2
     */
    public void setSelectOptions(int option1, int option2) {
        wheelOptions.setCurrentItems(option1, option2, 0, 0);
    }

    /**
     * 设置选中的item位置
     *
     * @param option1
     * @param option2
     * @param option3
     */
    public void setSelectOptions(int option1, int option2, int option3) {
        wheelOptions.setCurrentItems(option1, option2, option3, 0);
    }

    /**
     * 设置选中的item位置
     *
     * @param option1
     * @param option2
     * @param option3
     */
    public void setSelectOptions(int option1, int option2, int option3, int option4) {
        wheelOptions.setCurrentItems(option1, option2, option3, option4);
    }


    /**
     * 设置选项的单位
     *
     * @param label1
     */
    public void setLabels(String label1) {
        wheelOptions.setLabels(label1, null, null, null);
    }

    /**
     * 设置选项的单位
     *
     * @param label1
     * @param label2
     */
    public void setLabels(String label1, String label2) {
        wheelOptions.setLabels(label1, label2, null, null);
    }

    /**
     * 设置选项的单位
     *
     * @param label1
     * @param label2
     * @param label3
     */
    public void setLabels(String label1, String label2, String label3) {
        wheelOptions.setLabels(label1, label2, label3, null);
    }

    /**
     * 设置选项的单位
     *
     * @param label1
     * @param label2
     * @param label3
     */
    public void setLabels(String label1, String label2, String label3, String label4) {
        wheelOptions.setLabels(label1, label2, label3, label4);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wheelOptions.setCyclic(cyclic);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
        } else {
            if (optionsSelectListener != null) {
                int[] optionsCurrentItems = wheelOptions.getCurrentItems();
                optionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2], optionsCurrentItems[3]);
            }
            dismiss();
        }
    }

    public interface OnOptionsSelectListener {
        void onOptionsSelect(int options1, int option2, int options3, int options4);
    }

    public void setOnOptionsSelectListener(OnOptionsSelectListener optionsSelectListener) {
        this.optionsSelectListener = optionsSelectListener;
    }
}
