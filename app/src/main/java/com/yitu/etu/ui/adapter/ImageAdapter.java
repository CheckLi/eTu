package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.yitu.etu.R;
import com.yitu.etu.util.Tools;

import java.util.List;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/21.
 */
public class ImageAdapter extends BaseAdapter<String, ImageAdapter.ViewHolder> {
    public ImageAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public ViewHolder onCreateViewHolder(int position) {
        return new ViewHolder();
    }

    @Override
    public void initItemView(int position, View convertView, ViewHolder viewHolder) {
        viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
    }

    @Override
    public void bindData(int position, View convertView, final ViewHolder viewHolder) {
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.showImage(getContext(), "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4166721891,1503444760&fm=27&gp=0.jpg", viewHolder.imageView);
            }
        });
    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        ImageView imageView;

        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_imageview;
        }
    }
}
