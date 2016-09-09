package com.yimeng.yimapp.home.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;

import com.yimeng.library.recyclerview.RefreshRecyclerView;
import com.yimeng.yimapp.R;
import com.yimeng.yimapp.adapter.MineAdapter;
import com.yimeng.yimapp.base.BaseFragment;
import com.yimeng.yimapp.databinding.FragmentMineBinding;
import com.yimeng.yimapp.home.viewmodel.MineViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jax on 2016/9/1.
 * Description :
 * Version : V1.0.0
 */
public class MineFragment extends BaseFragment<FragmentMineBinding> {

    private MineViewModel mViewModel;
    private MineAdapter mAdapter;
    private List<String> mList = new ArrayList<>();
    private Handler mHandler = new Handler();

    public static MineFragment newInstance() {

        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mViewModel = new MineViewModel(mAct);
        mBinding.setViewModel(mViewModel);
        setUpRecyclerView(mBinding.rvMineList);
    }

    /**
     * 设置recyclerView
     */
    private void setUpRecyclerView(RefreshRecyclerView view) {
        view.setLayoutManager(new LinearLayoutManager(mAct, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MineAdapter(mAct);
        view.setAdapter(mAdapter);
        initData();
        mAdapter.addAll(mList);
        view.showNoMore();
        view.setRefreshListener(() -> mHandler.postDelayed(view::dismissSwipeRefresh, 1900));
        view.setLoadMoreListener(() -> mHandler.postDelayed(view::showNoMore, 1900));
    }

    private void initData() {
        mList.clear();
        mList.add("多图选择");
        mList.add("Picker");
        mList.add("ActionSheet");
        mList.add("CircleImagView");
        mList.add("TagsGroup");
        mList.add("WrapWidthImageView");
        mList.add("WrapHeightImageView");
    }
}
