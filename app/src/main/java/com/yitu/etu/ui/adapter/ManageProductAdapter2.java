package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yitu.etu.R;
import com.yitu.etu.entity.ShopProductEntity;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.util.Tools;
import com.yitu.etu.widget.GlideApp;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/29.
 */
public class ManageProductAdapter2 extends ManageProductAdapter{
    public ManageProductAdapter2(Context context) {
        super(context);
    }

    @Override
    public void bindData(int position, View convertView, ViewHolder viewHolder) {
        super.bindData(position, convertView, viewHolder);
        viewHolder.li_content.setPadding(px, 0, px, px);
        LinearLayout.LayoutParams liParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        liParams.leftMargin=px;
        liParams.rightMargin=px;  viewHolder.li_content.setLayoutParams(liParams);
    }
}
