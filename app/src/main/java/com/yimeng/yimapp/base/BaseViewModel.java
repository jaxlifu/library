package com.yimeng.yimapp.base;

import android.content.Context;
import android.databinding.BaseObservable;

/**
 * Created by Jax on 2016/9/1.
 * Description :
 * Version : V1.0.0
 */
public abstract class BaseViewModel extends BaseObservable {
    protected String TAG = getClass().getSimpleName();
    protected Context mContext;

    public BaseViewModel(Context context) {
        mContext = context;
    }
}
