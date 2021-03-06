package com.yitu.etu.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.yitu.etu.R
import com.yitu.etu.entity.MyBoonBean
import kotlinx.android.synthetic.main.adapter_item_boon.view.*

/**
 * @className:OrderAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月14日 22:32
 */
class BoonAdapter(list: List<MyBoonBean>) : MyBaseAdapter<MyBoonBean>(list) {
    override fun getItemResource(pos: Int): Int = R.layout.adapter_item_boon
    override fun getItemView(position: Int, convertView: View, holder: ViewHolder?, parent: ViewGroup?): View {
        convertView.run {
            with(getItem(position)) {
                tv_name.text = name
                tv_price.text = "￥$price"
                tv_price_old.text = "市场价：$oldprice"
            }
        }
        return convertView
    }
}
