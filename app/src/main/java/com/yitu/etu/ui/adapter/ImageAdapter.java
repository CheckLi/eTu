package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.yitu.etu.R;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.util.Tools;
import com.yitu.etu.widget.GlideApp;

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
    public void bindData(final int position, View convertView, final ViewHolder viewHolder) {
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.showImage(getContext(), Urls.address+getItem(position), viewHolder.imageView);
            }
        });
        GlideApp.with(getContext())
                .load(Urls.address+getItem(position))
                .placeholder(R.drawable.ic_default_image)
                .error(R.drawable.ic_default_image)
                .into(viewHolder.imageView);

    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        ImageView imageView;

        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_imageview;
        }
    }
}
