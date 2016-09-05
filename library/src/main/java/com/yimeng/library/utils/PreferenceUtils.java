package com.yimeng.library.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;


/**
 * SharedPreferences处理的一些工具类
 */
public class PreferenceUtils {

    public static boolean getBoolean(String key, boolean defValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(ApplicationUtils.getContext());
        return settings.getBoolean(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ApplicationUtils.getContext()).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static int getInt(String key, int defValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(ApplicationUtils.getContext());
        return settings.getInt(key, defValue);
    }

    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ApplicationUtils.getContext()).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static long getLong(String key, long defValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(ApplicationUtils.getContext());
        return settings.getLong(key, defValue);
    }

    public static void putLong(String key, long value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ApplicationUtils.getContext()).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static String getString(String key, String defValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(ApplicationUtils.getContext());
        return settings.getString(key, defValue);
    }

    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ApplicationUtils.getContext()).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void remove(String key) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ApplicationUtils.getContext()).edit();
        editor.remove(key);
        editor.apply();
    }

    public static void removeAll() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ApplicationUtils.getContext()).edit();
        editor.clear();
        editor.apply();
    }

    public static void registerOnPrefChangeListener(OnSharedPreferenceChangeListener listener) {
        try {
            PreferenceManager.getDefaultSharedPreferences(ApplicationUtils.getContext()).registerOnSharedPreferenceChangeListener(listener);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void unregisterOnPrefChangeListener(OnSharedPreferenceChangeListener listener) {
        try {
            PreferenceManager.getDefaultSharedPreferences(ApplicationUtils.getContext()).unregisterOnSharedPreferenceChangeListener(listener);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
