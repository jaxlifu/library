package com.yimeng.library.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 可设置能否滑动的ViewPager
 */
public class ScrollAvailableViewPager extends ViewPager {
    private boolean mScrollAvailable = false;

    public void setScrollAvailable(boolean available) {
        mScrollAvailable = available;
    }

    public ScrollAvailableViewPager(Context context) {
        super(context);
    }

    public ScrollAvailableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mScrollAvailable && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mScrollAvailable && super.onInterceptTouchEvent(ev);
    }
}
