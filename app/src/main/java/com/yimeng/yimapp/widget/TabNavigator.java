package com.yimeng.yimapp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Jax on 2016/9/8.
 * Description :底部导航栏的封装
 * Version : V1.0.0
 */
public class TabNavigator extends LinearLayout {

    public TabNavigator(Context context) {
        super(context);
    }

    public TabNavigator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabNavigator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        setOrientation(HORIZONTAL);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (!(child instanceof TabNavigatorItem))
                throw new RuntimeException("child  of TabNavigator must be TabNavigatorItem");
        }
    }


}
