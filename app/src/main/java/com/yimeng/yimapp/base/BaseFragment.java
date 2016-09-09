package com.yimeng.yimapp.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by Jax on 2016/9/1.
 * Description :
 * Version : V1.0.0
 */
public abstract class BaseFragment<VB extends ViewDataBinding> extends Fragment {
    protected String TAG = getClass().getSimpleName();
    protected View mRootView;
    protected VB mBinding;
    protected Activity mAct;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, setLayoutResID(), container, false);
        mAct = getActivity();
        if (mRootView == null) {
            mRootView = mBinding.getRoot();
        } else {
            ViewParent parent = mRootView.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(mRootView);
            }
        }
        init(savedInstanceState);
        return mRootView;
    }

    @LayoutRes
    protected abstract int setLayoutResID();

    protected abstract void init(Bundle savedInstanceState);

}

