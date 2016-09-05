package com.yimeng.library.multiimageselector.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Jax on 15/11/10.
 * Description :
 * Version : V1.0.0
 */
public class SquareFrameLayout extends FrameLayout {
    public SquareFrameLayout(Context context) {
        super(context);
    }

    public SquareFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
