package com.yimeng.yimapp.home.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.yimeng.yimapp.R;
import com.yimeng.yimapp.adapter.ViewPageAdapter;
import com.yimeng.yimapp.base.BaseActivity;
import com.yimeng.yimapp.databinding.ActivityMainBinding;
import com.yimeng.yimapp.home.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private MainViewModel mViewModel;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mViewModel = new MainViewModel(this, mBinding);
        setViewPage(mBinding.vpHomeContent);
        mBinding.setViewModel(mViewModel);
    }

    private void setViewPage(ViewPager viewPage) {
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager(), this);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance("首页信息"));
        fragments.add(HomeFragment.newInstance("发现页面"));
        fragments.add(HomeFragment.newInstance("我的信息"));
        adapter.setDataList(fragments);
        viewPage.setAdapter(adapter);
    }


}
