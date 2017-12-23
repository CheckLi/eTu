package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.View;

import com.yitu.etu.R;

import java.util.List;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/21.
 */
public class ImageAdapter extends BaseAdapter<String,ImageAdapter.ViewHolder>{
    public ImageAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public ViewHolder onCreateViewHolder(int position) {
        return new ViewHolder();
    }

    @Override
    public void initItemView(int position, View convertView, ViewHolder viewHolder) {

    }

    @Override
    public void bindData(int position, View convertView, ViewHolder viewHolder) {
//        ((ImageView)convertView).setImageResource();
    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_imageview;
        }
    }
}
