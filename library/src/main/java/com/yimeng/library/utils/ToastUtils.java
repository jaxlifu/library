package com.yimeng.library.utils;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yimeng.library.R;


/**
 * ToastUtils
 */
public class ToastUtils {

    private ToastUtils() {
        throw new AssertionError();
    }

    public static void show(int resId) {
        show(ApplicationUtils.getContext().getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration) {
        show(ApplicationUtils.getContext().getResources().getText(resId), duration);
    }

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void toast(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }


    public static void show(CharSequence text, int duration) {
        View view = LayoutInflater.from(ApplicationUtils.getContext()).inflate(R.layout.layout_toast_center, null);
        TextView messageTv = (TextView) view.findViewById(R.id.tv_title_toast);
        messageTv.setText(text);
        Toast toast = new Toast(ApplicationUtils.getContext());
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(duration);
        toast.setView(view);
        toast.show();
    }

    public static void show(int resId, Object... args) {
        show(String.format(ApplicationUtils.getContext().getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(String format, Object... args) {
        show(String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration, Object... args) {
        show(String.format(ApplicationUtils.getContext().getResources().getString(resId), args), duration);
    }

    public static void show(String format, int duration, Object... args) {
        show(String.format(format, args), duration);
    }

    public static void showSnack(View view, String message, String action, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(action, listener);
        ((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#ffffff"));
        snackbar.show();
    }

    public static void showSnack(View view, String message) {
        showSnack(view, message, null, null);
    }
}
