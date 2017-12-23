package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.yitu.etu.R;
import com.yitu.etu.util.Tools;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/21.
 */
public class ChooseImageAdapter extends BaseAdapter<String, ChooseImageAdapter.ViewHolder> {
    public ChooseImageAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(int position) {
        return new ViewHolder();
    }

    @Override
    public int getCount() {
        return 1 + 10;
    }

    @Override
    public void initItemView(int position, View convertView, ViewHolder viewHolder) {
        viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
    }

    @Override
    public void bindData(int position, View convertView, ViewHolder viewHolder) {
        if (position == 0) {
            viewHolder.imageView.setImageResource(R.drawable.icon57);
            int px = Tools.dp2px(getContext(), 20);
            convertView.setPadding(px, px, px, px);
        } else {
//            convertView.setPadding(0, 0, 0, 0);
            int px = Tools.dp2px(getContext(), 0.5f);
            convertView.setPadding(px, px, px, px);

            viewHolder.imageView.setImageResource(R.drawable.etu_default2);

        }
//        ((ImageView)convertView).setImageResource();
    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        ImageView imageView;

        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_choice_img;
        }
    }
}
