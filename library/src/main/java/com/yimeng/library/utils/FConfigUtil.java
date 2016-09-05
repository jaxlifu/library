package com.yimeng.library.utils;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 一些配置数据，保存在文件中
 *
 * @author Administrator
 */
public class FConfigUtil {
    static final String TAG = "FConfigUtil";

    synchronized public static int loadInt(final String key, int defVal) {
        Integer cf = defVal;
        String cfStr = load(key);
        try {
            cf = Integer.parseInt(cfStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cf;
    }

    synchronized public static boolean loadBoolean(final String key, boolean defVal) {
        Boolean cf = defVal;
        String cfStr = load(key);
        try {
            if (!TextUtils.isEmpty(cfStr)) {
                cf = Boolean.parseBoolean(cfStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cf;
    }

    synchronized public static String load(final String key) {
        String cf = null;
        if (ApplicationUtils.getContext() == null) {
            return null;
        }
        try {
            File dir = ApplicationUtils.getContext().getFilesDir();
            File f = new File(dir, key);
            FileInputStream in = new FileInputStream(f);
            byte[] buf = new byte[(int) f.length()];
            in.read(buf);
            cf = new String(buf);
        } catch (Exception e) {
            Logger.e(TAG, e.getMessage());
        }
        return cf;
    }

    synchronized public static void save(final String key, final String val) {
        if (ApplicationUtils.getContext() == null || val == null) {
            return;
        }
        try {
            File dir = ApplicationUtils.getContext().getFilesDir();
            File f = new File(dir, key);
            FileOutputStream out = new FileOutputStream(f);
            out.write(val.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            Logger.e(TAG, e.getMessage());
        }

    }

    synchronized public static void clear(final String key) {
        try {
            File dir = ApplicationUtils.getContext().getFilesDir();
            File f = new File(dir, key);
            if (f.isFile() && f.exists()) {
                f.delete();
            }
        } catch (Exception e) {
            Logger.e(TAG, e.getMessage());
        }
    }
}
