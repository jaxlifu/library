package com.yimeng.library.tabnavigator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yimeng.library.R;


/**
 * Created by Jax on 2016/9/8.
 * Description :TabNavigator对应的Item
 * Version : V1.0.0
 */
public class TabNavigatorItem extends FrameLayout {
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
    private float mTextSize;//字体大小
    private int mTextColor;//字体颜色
    private int mSelectedTextColor;//选中的状态的字体颜色
    private int mRenderIcon;//默认的图片资源
    private int mRenderSelectedIcon;//选中的图片资源
    private String mBadgeText;//是否需要展示角标
    private boolean mSelected;//当前是否选中
    private int mImageSize;//图片大小

    private ImageView m_ivImage;
    private TextView m_tvTitle;
    private TextView m_tvBadge;
    private View mRootView;


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
            mTextSize = a.getDimension(R.styleable.TabNavigatorItem_textSize, 12.0f);
            mTextColor = a.getColor(R.styleable.TabNavigatorItem_textColor, Color.parseColor("#636061"));
            mSelectedTextColor = a.getColor(R.styleable.TabNavigatorItem_selectedTextColor, Color.parseColor("#f94937"));
            mImageSize = a.getDimensionPixelSize(R.styleable.TabNavigatorItem_imageSize, (int) dp2px(20.0f));
            mRenderIcon = a.getResourceId(R.styleable.TabNavigatorItem_renderIcon, 0);
            mRenderSelectedIcon = a.getResourceId(R.styleable.TabNavigatorItem_renderSelectedIcon, 0);
            mBadgeText = a.getString(R.styleable.TabNavigatorItem_badgeText);
            mSelected = a.getBoolean(R.styleable.TabNavigatorItem_selected, false);
        } finally {
            a.recycle();
        }

        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_tab_navigator, null);
        m_ivImage = (ImageView) mRootView.findViewById(R.id.iv_navigator_img);
        m_tvTitle = (TextView) mRootView.findViewById(R.id.tv_navigator_title);
        m_tvBadge = (TextView) mRootView.findViewById(R.id.tv_navigator_badge);

        m_tvTitle.setText(mTitle);
        m_tvBadge.setText(mBadgeText);
        ViewGroup.LayoutParams params = m_ivImage.getLayoutParams();
        params.width = mImageSize;
        params.height = mImageSize;
        m_ivImage.setLayoutParams(params);
        m_tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);

        refreshNavigator();
        addView(mRootView);
    }


    private void refreshNavigator() {
        m_tvBadge.setVisibility(TextUtils.isEmpty(mBadgeText) ? GONE : VISIBLE);
        if (mSelected) {//选中状态
            m_tvTitle.setTextColor(mSelectedTextColor);
            m_ivImage.setImageResource(mRenderSelectedIcon);
        } else {//未选中状态
            m_tvTitle.setTextColor(mTextColor);
            m_ivImage.setImageResource(mRenderIcon);

        }
    }

    public float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        mSelected = selected;
        refreshNavigator();
    }

    public void setBadgeText(String badgeText) {
        mBadgeText = badgeText;
        m_tvBadge.setText(mBadgeText);
        refreshNavigator();
    }

    public void setBadgeText(int count) {
        String value;
        if (count > 99) value = "99+";
        else value = String.valueOf(count);
        this.setBadgeText(value);
    }
}
