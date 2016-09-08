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
    private OnTabItemClick mTabItemClick;
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


    public void setTabItemClick(OnTabItemClick tabItemClick) {
        mTabItemClick = tabItemClick;
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
            int index = i;
            //首先确保每个子View都是TabNavigatorItem
            if (!(child instanceof TabNavigatorItem)) {
                throw new RuntimeException("child  of TabNavigator must be TabNavigatorItem");
            }

            TabNavigatorItem item = (TabNavigatorItem) child;
            item.setOnClickListener(view -> {
                mCurrentIndex = index;
                for (int position = 0; position < getChildCount(); position++) {
                    getChildAt(position).setSelected(false);
                }
                item.setSelected(true);
                if (mTabItemClick != null) {
                    mTabItemClick.onTabClick(item, mCurrentIndex);
                }
            });
        }
    }

    public interface OnTabItemClick {
        void onTabClick(View view, int position);
    }


}
