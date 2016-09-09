package com.yimeng.yimapp.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yimeng.library.recyclerview.adapter.BaseViewHolder;
import com.yimeng.library.recyclerview.adapter.RecyclerAdapter;
import com.yimeng.yimapp.R;

/**
 * Created by Jax on 2016/9/9.
 * Description :
 * Version : V1.0.0
 */
public class MineAdapter extends RecyclerAdapter<String> {
    public MineAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder<String> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new StringViewHolder(parent);
    }

    private class StringViewHolder extends BaseViewHolder<String> {
        private TextView m_tvTitle;

        public StringViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_mine_description);
        }

        @Override
        public void onInitializeView() {
            super.onInitializeView();
            m_tvTitle = findViewById(R.id.tv_function_title);
        }

        @Override
        public void setData(String item) {
            super.setData(item);
            m_tvTitle.setText(item);
        }
    }
}
