package com.yimeng.yimapp.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Jax on 2016/9/1.
 * Description :
 * Version : V1.0.0
 */
public abstract class BaseActivity<VB extends ViewDataBinding> extends FragmentActivity {
    protected String TAG = getClass().getSimpleName();
    protected VB mBinding;
    protected Activity mAct;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAct = this;
        mBinding = DataBindingUtil.setContentView(mAct, setLayoutResID());
        init(savedInstanceState);
    }

    @LayoutRes
    protected abstract int setLayoutResID();

    protected abstract void init(Bundle savedInstanceState);
}
