package com.air.mvvmdemo;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Air on 15/9/11.
 */
public class MyRecyclerAdapter<T> extends RecyclerView.Adapter {
    private List<T> items;
    public MyRecyclerAdapter(List<T> items,@LayoutRes int layout) {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
