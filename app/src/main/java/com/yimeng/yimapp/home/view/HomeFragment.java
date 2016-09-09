package com.yimeng.yimapp.home.view;

import android.os.Bundle;

import com.yimeng.yimapp.R;
import com.yimeng.yimapp.base.BaseFragment;
import com.yimeng.yimapp.databinding.FragmentHomeBinding;
import com.yimeng.yimapp.home.viewmodel.HomeViewModel;

/**
 * Created by Jax on 2016/9/1.
 * Description :
 * Version : V1.0.0
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    private HomeViewModel mViewModel;

    public static HomeFragment newInstance(String message) {
        Bundle bundle = new Bundle();
        HomeFragment fragment = new HomeFragment();
        bundle.putString("message", message);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mViewModel = new HomeViewModel(mAct, getArguments().getString("message"));
            mBinding.setViewModel(mViewModel);
        }
    }

}
