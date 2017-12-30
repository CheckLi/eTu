package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yitu.etu.R;
import com.yitu.etu.util.Tools;

/**
 * @className:SceneService
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月30日 13:46
 */
public class SceneServiceAdapter extends BaseAdapter<String, SceneServiceAdapter.ViewHolder> {
    private final int px;

    public SceneServiceAdapter(Context context) {
        super(context);
        px = Tools.dp2px(context, 7);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public ViewHolder onCreateViewHolder(int position) {
        return new ViewHolder();
    }

    @Override
    public void initItemView(int position, View convertView, ViewHolder viewHolder) {
        viewHolder.tv_scene = (TextView) convertView.findViewById(R.id.tv_scene);
        viewHolder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
        viewHolder.tv_good = (TextView) convertView.findViewById(R.id.tv_good);
        viewHolder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
        viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
        viewHolder.tv_ts = (TextView) convertView.findViewById(R.id.tv_ts);
        viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
    }

    @Override
    public void bindData(int position, View convertView, ViewHolder viewHolder) {
        if (position == 0) {
            convertView.setPadding(px, px, px, px);
        } else {
            convertView.setPadding(px, 0, px, px);
        }
        viewHolder.tv_scene.setText("附近景区:" + "");
        viewHolder.tv_phone.setText("电话:" + "");
        viewHolder.tv_good.setText("点赞:" + "");
        viewHolder.tv_address.setText("地址:"+"");
        viewHolder.tv_price.setText("最低消费:" + "");
        viewHolder.tv_ts.setText("特殊:" + "");
        viewHolder.tv_title.setText("12:" + "");

    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        ImageView imageView;
        TextView tv_scene, tv_phone, tv_good, tv_address, tv_price, tv_ts, tv_title;

        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_scene_service;
        }
    }
}
