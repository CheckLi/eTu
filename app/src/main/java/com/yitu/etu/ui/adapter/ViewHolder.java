package com.yitu.etu.ui.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * @className:ViewHolder
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月14日 22:30
 */
public class ViewHolder {
    private SparseArray<View> views = new SparseArray<View>();
    private View convertView;

    public ViewHolder(View convertView) {
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
