package com.yimeng.library.utils;

import android.os.Build;

import java.lang.reflect.Method;

/**
 * Created by Jax on 2016/6/21.
 * Description :
 * Version : V1.0.0
 */
public class OsUtils {

    public static boolean isFlyme() {
        try {
            // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null||Build.MANUFACTURER.equals("Meizu");
        } catch (final Exception e) {
            return false;
        }
    }
}
