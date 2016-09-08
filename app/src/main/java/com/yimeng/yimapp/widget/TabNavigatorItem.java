package com.yimeng.yimapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yimeng.yimapp.R;

/**
 * Created by Jax on 2016/9/8.
 * Description :TabNavigator对应的Item
 * Version : V1.0.0
 */
public class TabNavigatorItem extends RelativeLayout {
    /**
     * renderIcon: PropTypes.func,
     * renderSelectedIcon: PropTypes.func,
     * badgeText: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
     * renderBadge: PropTypes.func,
     * title: PropTypes.string,
     * titleStyle: Text.propTypes.style,
     * selectedTitleStyle: Text.propTypes.style,
     * tabStyle: View.propTypes.style,
     * selected: PropTypes.bool,
     * onPress: PropTypes.func,
     * allowFontScaling: PropTypes.bool,
     */
    private String mTitle;//导航的标题
    private int mTextSize;//字体大小
    private int mTextColor;//字体颜色
    private int mSelectedTextColor;//选中的状态的字体颜色
    private int mRenderIcon;//默认的图片资源
    private int mRenderSelectedIcon;//选中的图片资源
    private String mBadgeText;//是否需要展示角标
    private boolean mSelected;//当前是否选中
    private int mImageSize;//图片大小

    private ImageView mImageView;
    private TextView mTitleView;
    private TextView mBadgeView;


    public TabNavigatorItem(Context context) {
        this(context, null);
    }

    public TabNavigatorItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabNavigatorItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // Load styled attributes.
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabNavigatorItem);
        try {
            mTitle = a.getString(R.styleable.TabNavigatorItem_title);
            mRenderIcon = a.getResourceId(R.styleable.TabNavigatorItem_renderIcon, R.mipmap.ic_launcher);
            mRenderSelectedIcon = a.getResourceId(R.styleable.TabNavigatorItem_renderSelectedIcon, R.mipmap.ic_launcher);
            mBadgeText = a.getString(R.styleable.TabNavigatorItem_badgeText);
        } finally {
            a.recycle();
        }
    }

}
