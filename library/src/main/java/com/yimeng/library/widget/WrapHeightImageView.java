package com.yimeng.library.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 宽度设置好了之后，自动根据src计算高度，让宽高比与src图片的宽高比一样
 *
 * @author Administrator
 */
public class WrapHeightImageView extends ImageView {

    public WrapHeightImageView(Context context, AttributeSet attrs,
                               int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public WrapHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public WrapHeightImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // For simple implementation, or internal size is always 0.
        // We depend on the container to specify the layout size of
        // our view. We can't really know what it is since we will be
        // adding and removing different arbitrary views and do not
        // want the layout to change as this happens.
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

        // Children are just made to fill our space.
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = getMeasuredHeight();
        Drawable d = getDrawable();
        int dw = 0, dh = 0;
        if (d != null && d.getIntrinsicWidth() > 0 && d.getIntrinsicHeight() > 0) {
            childHeightSize = Math.round((1.0f * childWidthSize / d.getIntrinsicWidth() * d.getIntrinsicHeight()));
            dw = d.getIntrinsicWidth();
            dh = d.getIntrinsicHeight();
        } else if (defW > 0 && defH > 0) {
            childHeightSize = Math.round((1.0f * childWidthSize / defW * defH));
        }
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);

        // Log.d("test", "onMesure: "+defW+", "+defH+", "+dw+", "+dh+", "+childWidthSize+", "+childHeightSize+", "+widthMeasureSpec+", "+heightMeasureSpec+", "+d);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    int defW = 0, defH = 0;

    /**
     * 设置默认的宽高比
     *
     * @param w
     * @param h
     */
    public void setDefaultWH(int w, int h) {
        defW = w;
        defH = h;
    }

}
