package com.yimeng.yimapp.home.viewmodel;

import android.content.Context;
import android.databinding.Bindable;
import android.view.View;

import com.yimeng.library.utils.ToastUtils;
import com.yimeng.library.widget.ScrollAvailableViewPager;
import com.yimeng.yimapp.BR;
import com.yimeng.yimapp.R;
import com.yimeng.yimapp.base.BaseViewModel;
import com.yimeng.yimapp.databinding.ActivityMainBinding;

/**
 * Created by Jax on 2016/9/1.
 * Description :
 * Version : V1.0.0
 */
public class MainViewModel extends BaseViewModel {

    public static final int TAB_INDEX_HOME = 0x001;
    public static final int TAB_INDEX_PERSONAL = 0x002;
    public static final int TAB_INDEX_DISCOVER = 0x003;
    public static final int TAB_INDEX_MINE = 0x004;

    private ActivityMainBinding mBinding;
    private ScrollAvailableViewPager mViewPager;
    private int currentIndex = TAB_INDEX_HOME;

    public MainViewModel(Context context, ActivityMainBinding binding) {
        super(context);
        mBinding = binding;
        mViewPager = mBinding.vpHomeContent;
    }

    @Bindable
    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
        notifyPropertyChanged(BR.currentIndex);
    }

    public void onTabClick(View view) {
        switch (view.getId()) {
            case R.id.rl_tab_home:
                mViewPager.setCurrentItem(0, false);
                setCurrentIndex(TAB_INDEX_HOME);
                break;
            case R.id.rl_tab_fitting:
            case R.id.rl_tab_personal:
                ToastUtils.show("这个是3D试衣的界面!");
                break;
            case R.id.rl_tab_discover:
                mViewPager.setCurrentItem(1, false);
                setCurrentIndex(TAB_INDEX_DISCOVER);
                break;
            case R.id.rl_tab_mine:
                mViewPager.setCurrentItem(2, false);
                setCurrentIndex(TAB_INDEX_MINE);
                break;
        }
    }
}
