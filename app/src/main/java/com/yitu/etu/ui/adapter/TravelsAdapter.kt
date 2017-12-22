package com.yitu.etu.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.yitu.etu.R

/**
 * @className:OrderAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月14日 22:32
 */
class TravelsAdapter(list: List<String>) : MyBaseAdapter<String>( list) {
    override fun getItemResource(pos: Int): Int = R.layout.adapter_item_travels
    override fun getItemView(position: Int, convertView: View, holder: ViewHolder?, parent: ViewGroup?): View {
        return convertView
    }

    override fun getCount(): Int {
        return 10
    }
}
