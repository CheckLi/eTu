package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yitu.etu.R;
import com.yitu.etu.entity.MapSceneEntity;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.widget.GlideApp;

import java.util.List;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/19.
 */
public class MapSceneSearchAdapter extends BaseAdapter<MapSceneEntity, MapSceneSearchAdapter.ViewHolder> {


    public MapSceneSearchAdapter(Context context, List<MapSceneEntity> data) {
        super(context, data);
    }

    @Override
    public ViewHolder onCreateViewHolder(int position) {
        return new ViewHolder();
    }

    @Override
    public void initItemView(int position, View convertView, ViewHolder viewHolder) {
        viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        viewHolder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
        viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);


    }

    @Override
    public void bindData(int position, View convertView, ViewHolder viewHolder) {
//        if (type == MapsFragment.type_scene) {
        MapSceneEntity data = getItem(position);
        viewHolder.tv_title.setText(data.title);
        viewHolder.tv_address.setText(data.address);
        GlideApp.with(getContext())
                .load(Urls.address + data.getImage())
                .centerCrop()
                .placeholder(R.drawable.icon17).into(viewHolder.image);
//        } else if (type == MapsFragment.type_friend) {
//            MapFirendEntity mapFirendEntity = (MapFirendEntity) getItem(position);
//            viewHolder.tv_title.setText(mapFirendEntity.title);
//
//            viewHolder.tv_address.setText(mapFirendEntity.address);
//        } else if (type == MapsFragment.type_order_scene) {
//            MapOrderSceneEntity mapOrderSceneEntity = (MapOrderSceneEntity) getItem(position);
//            viewHolder.tv_title.setText(mapOrderSceneEntity.title);
//            viewHolder.tv_address.setText(mapOrderSceneEntity.address);
//        }
    }


    class ViewHolder extends BaseAdapter.abstractViewHodler {
        TextView tv_title, tv_address,tv_price;
        ImageView image;
        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_scene_search_result;
        }
    }
}