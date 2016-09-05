package com.yimeng.yimapp.base;

import android.app.Application;

import com.karumi.dexter.Dexter;
import com.yimeng.library.utils.ApplicationUtils;

/**
 * Created by Jax on 2016/9/1.
 * Description :
 * Version : V1.0.0
 */
public class YiMApplication extends Application {


    private static YiMApplication mApplication;

    public static synchronized YiMApplication getInstance() {
        if (mApplication == null)
            mApplication = new YiMApplication();
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        Dexter.initialize(this);
        ApplicationUtils.initialize(this);
    }
}
