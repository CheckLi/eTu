package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yitu.etu.R;
import com.yitu.etu.entity.ShopProductEntity;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.util.Tools;
import com.yitu.etu.widget.GlideApp;

import java.util.List;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/29.
 */
public class ManageProductAdapter extends BaseAdapter<ShopProductEntity,ManageProductAdapter.ViewHolder>{

    private final int px;

    public ManageProductAdapter(Context context) {
        super(context);
        px = Tools.dp2px(context, 7);

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
        if (position == 0) {
            convertView.setPadding(px, px, px, px);
        } else {
            convertView.setPadding(px, 0, px, px);
        }
        ShopProductEntity data= getItem(position);
        viewHolder.tv_title.setText(data.getName());

        viewHolder.tv_price.setText("价格:"+data.getPrice()+"元");

        viewHolder.tv_address.setText(data.getDes());
        GlideApp.with(getContext())
                .load(Urls.address + data.getImage())
                .centerCrop()
                .error(R.drawable.etu_default)
                .placeholder(R.drawable.etu_default).into(viewHolder.image);
    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        TextView tv_title, tv_address, tv_price;
        ImageView image;

        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_scene_search_result;
        }
    }
}
