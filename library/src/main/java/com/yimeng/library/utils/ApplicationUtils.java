package com.yimeng.library.utils;

import android.content.Context;

/**
 * Created by Jax on 2016/8/31.
 * Description :
 * Version : V1.0.0
 */
public class ApplicationUtils {

    private Context mApplication;
    private static ApplicationUtils mInstance;

    public static synchronized ApplicationUtils getInstance() {
        if (mInstance == null)
            mInstance = new ApplicationUtils();
        return mInstance;
    }

    private ApplicationUtils() {

    }

    public static Context getContext() {
        return ApplicationUtils.getInstance().mApplication;
    }

    public static void initialize(Context context) {
        ApplicationUtils.getInstance().mApplication = context;
    }

}
