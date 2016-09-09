package com.yimeng.yimapp.home.viewmodel;

import android.content.Context;

import com.yimeng.yimapp.base.BaseViewModel;

/**
 * Created by Jax on 2016/9/1.
 * Description :
 * Version : V1.0.0
 */
public class HomeViewModel extends BaseViewModel {
    private String message;

    public HomeViewModel(Context context, String message) {
        super(context);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
