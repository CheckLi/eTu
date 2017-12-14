package com.yitu.etu.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.yitu.etu.R
import com.yitu.etu.entity.OrderBean

/**
 * @className:OrderAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月14日 22:32
 */
class OrderAdapter(list: List<OrderBean>) : MyBaseAdapter<OrderBean>( list) {
    override fun getItemResource(pos: Int): Int = R.layout.adapter_item_order

    override fun getItemView(position: Int, convertView: View, holder: ViewHolder?, parent: ViewGroup?): View {
        return convertView
    }

    override fun getCount(): Int {
        return 10
    }
}
