package com.yimeng.library.tabnavigator;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Jax on 2016/9/8.
 * Description :底部导航栏的封装
 * Version : V1.0.0
 */
public class TabNavigator extends LinearLayout {
    private OnTabItemClickListener mTabItemClick;
    private int mCurrentIndex;

    public TabNavigator(Context context) {
        this(context, null);
    }

    public TabNavigator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabNavigator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTabItemClick(OnTabItemClickListener tabItemClick) {
        mTabItemClick = tabItemClick;
    }

    @BindingAdapter("onTabClick")
    public static void setOnTabClickListener(TabNavigator view, OnTabItemClickListener listener) {
        if (view != null && listener != null) {
            view.setTabItemClick(listener);
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        if (getChildCount() == 0) return;
        getChildAt(0).setSelected(true);//默认选中第0个元素
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            final int index = i;
            //首先确保每个子View都是TabNavigatorItem
            if (!(child instanceof TabNavigatorItem)) {
                throw new RuntimeException("child  of TabNavigator must be TabNavigatorItem");
            }

            final TabNavigatorItem item = (TabNavigatorItem) child;
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentIndex = index;
                    for (int position = 0; position < getChildCount(); position++) {
                        getChildAt(position).setSelected(false);
                    }
                    item.setSelected(true);
                    if (mTabItemClick != null) {
                        mTabItemClick.onTabClick(item, mCurrentIndex);
                    }
                }
            });
        }
    }

    public interface OnTabItemClickListener {
        void onTabClick(View view, int position);
    }


}
