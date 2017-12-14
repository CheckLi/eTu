package com.yitu.etu.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * @className:RecyclerViewHolder
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月14日 22:31
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views = new SparseArray<View>();
    private View convertView;

    public RecyclerViewHolder(View convertView) {
        super(convertView);
        this.convertView = convertView;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int resId) {
        View v = views.get(resId);
        if (null == v) {
            v = convertView.findViewById(resId);
            views.put(resId, v);
        }
        return (T) v;
    }
}
