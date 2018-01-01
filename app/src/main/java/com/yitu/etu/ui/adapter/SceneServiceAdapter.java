package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yitu.etu.R;
import com.yitu.etu.entity.SceneServiceEntity;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.util.Tools;
import com.yitu.etu.util.imageLoad.ImageLoadUtil;
import com.yitu.etu.widget.image.RoundImageView;

/**
 * @className:SceneService
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月30日 13:46
 */
public class SceneServiceAdapter extends BaseAdapter<SceneServiceEntity.ListBean, SceneServiceAdapter.ViewHolder> {
    private final int px;
    String name;
    public SceneServiceAdapter(Context context,String name) {
        super(context);
        px = Tools.dp2px(context, 7);
        this.name=name;
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
        viewHolder.imageView = (RoundImageView) convertView.findViewById(R.id.imageView);
        viewHolder.li_content=(LinearLayout) convertView.findViewById(R.id.li_content);
    }

    @Override
    public void bindData(int position, View convertView, ViewHolder viewHolder) {
        if (position == 0) {
            viewHolder.li_content.setPadding(px, px, px, px);
        } else {
            viewHolder.li_content.setPadding(px, 0, px, px);
        }
        SceneServiceEntity.ListBean data=getItem(position);
        viewHolder.tv_scene.setText("附近景区:" + name);
        viewHolder.tv_phone.setText("电话:" +data.getPhone());
        viewHolder.tv_good.setText("点赞:" +data.getGood());
        viewHolder.tv_address.setText("地址:"+data.getAddress());
        viewHolder.tv_price.setText("最低消费:￥" + data.getPrice());
        viewHolder.tv_ts.setText("特色:" + data.getTese());
        viewHolder.tv_title.setText(data.getName());
        ImageLoadUtil.getInstance().loadImage( viewHolder.imageView , Urls.address+data.getImage(),-1,-1);

    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        RoundImageView imageView;
        LinearLayout li_content;
        TextView tv_scene, tv_phone, tv_good, tv_address, tv_price, tv_ts, tv_title;

        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_scene_service;
        }
    }
}
