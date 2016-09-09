package com.yimeng.yimapp.home.viewmodel;

import android.content.Context;
import android.view.View;

import com.yimeng.library.utils.ToastUtils;
import com.yimeng.library.widget.ScrollAvailableViewPager;
import com.yimeng.yimapp.base.BaseViewModel;
import com.yimeng.yimapp.databinding.ActivityMainBinding;

/**
 * Created by Jax on 2016/9/1.
 * Description :
 * Version : V1.0.0
 */
public class MainViewModel extends BaseViewModel {

    public static final int TAB_INDEX_HOME = 0x000;
    public static final int TAB_INDEX_PERSONAL = 0x001;
    public static final int TAB_INDEX_FITTING = 0x002;
    public static final int TAB_INDEX_DISCOVER = 0x003;
    public static final int TAB_INDEX_MINE = 0x004;

    private ActivityMainBinding mBinding;
    private ScrollAvailableViewPager mViewPager;

    public MainViewModel(Context context, ActivityMainBinding binding) {
        super(context);
        mBinding = binding;
        mViewPager = mBinding.vpHomeContent;
    }


    public void onTabClick(View view, int position) {
        switch (position) {
            case TAB_INDEX_HOME:
                mViewPager.setCurrentItem(0, false);
                break;
            case TAB_INDEX_PERSONAL:
            case TAB_INDEX_FITTING:
                ToastUtils.show("这个是3D试衣的界面!");
                break;
            case TAB_INDEX_DISCOVER:
                mViewPager.setCurrentItem(1, false);
                break;
            case TAB_INDEX_MINE:
                mViewPager.setCurrentItem(2, false);
                break;
        }
    }

}
