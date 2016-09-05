package com.yimeng.library.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yimeng.library.R;
import com.yimeng.library.recyclerview.adapter.Action;
import com.yimeng.library.recyclerview.adapter.RecyclerAdapter;


/**
 * Created by linlongxin on 2016/1/24.
 */
public class RefreshRecyclerView extends FrameLayout {

    private final String TAG = "RefreshRecyclerView";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private boolean refreshAble;
    private boolean loadMoreAble;

    public RefreshRecyclerView(Context context) {
        this(context, null);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(context, R.layout.view_refresh_recycler, this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.$_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.$_refresh_layout);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RefreshRecyclerView);
        refreshAble = typedArray.getBoolean(R.styleable.RefreshRecyclerView_refresh_able, true);
        loadMoreAble = typedArray.getBoolean(R.styleable.RefreshRecyclerView_load_more_able, true);
        if (!refreshAble) {
            mSwipeRefreshLayout.setEnabled(false);
        }
        typedArray.recycle();
    }

    public void setAdapter(RecyclerAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        this.mAdapter = adapter;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void setRefreshAction(final Action action) {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.isRefreshing = true;
                action.onAction();
            }
        });
    }

    public void setLoadMoreAction(final Action action) {
        Log.i(TAG, "setLoadMoreAction");
        if (mAdapter.isShowNoMore || !loadMoreAble) {
            return;
        }
        mAdapter.loadMoreAble = true;
        mAdapter.setLoadMoreAction(action);
    }

    public void showNoMore() {
        if (mAdapter == null) return;
        mAdapter.showNoMore();
    }

    public void setItemSpace(int left, int top, int right, int bottom) {
        if (mRecyclerView == null) return;
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(left, top, right, bottom));
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        if (mRecyclerView == null) return;
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public TextView getNoMoreView() {
        if (mAdapter == null) return null;
        return mAdapter.mNoMoreView;
    }

    public void setSwipeRefreshColorsFromRes(@ColorRes int... colors) {
        if (mSwipeRefreshLayout == null) return;
        mSwipeRefreshLayout.setColorSchemeResources(colors);
    }

    /**
     * 8位16进制数 ARGB
     */
    public void setSwipeRefreshColors(@ColorInt int... colors) {
        if (mSwipeRefreshLayout == null) return;
        mSwipeRefreshLayout.setColorSchemeColors(colors);
    }

    public void showSwipeRefresh() {
        if (mSwipeRefreshLayout == null) return;
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void dismissSwipeRefresh() {
        if (mSwipeRefreshLayout == null) return;
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
