package com.yimeng.yimapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Jax on 2016/9/1.
 * Description :
 * Version : V1.0.0
 */
public class ViewPageAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private List<Fragment> mList;

    public ViewPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    public void setDataList(List<Fragment> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList != null && !mList.isEmpty() ? mList.size() : 0;
    }
}
