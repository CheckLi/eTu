package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.yitu.etu.R;
import com.yitu.etu.entity.SceneEntity;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.widget.GlideApp;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/21.
 */
public class YjImageAdapter extends BaseAdapter<SceneEntity.TitlelistBean, YjImageAdapter.ViewHolder> {
    public YjImageAdapter(Context context) {
        super(context);
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

        GlideApp.with(getContext())
                .load(Urls.address + getItem(0).getUser().getHeader())
                .placeholder(R.drawable.default_head)
                .error(R.drawable.default_head)
                .into(viewHolder.imageView);

    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        ImageView imageView;

        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_circle_imageview;
        }
    }
}
