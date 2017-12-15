package com.yitu.etu.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.yitu.etu.R
import com.yitu.etu.entity.BuyCarBean

/**
 * @className:BuyCarAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月14日 23:23
 */
class BuyCarAdapter : MyBaseAdapter<BuyCarBean> {
    override fun getItemResource(pos: Int): Int = R.layout.adapter_item_buy_car

    override fun getItemView(position: Int, convertView: View, holder: ViewHolder?, parent: ViewGroup?): View {
        return convertView
    }

    constructor( data: List<BuyCarBean>) : super(data) {}

    override fun getCount(): Int {
        return 5
    }

}
