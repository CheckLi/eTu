package com.yitu.etu.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.yitu.etu.R
import com.yitu.etu.entity.MyBoonListBean
import com.yitu.etu.util.getTime
import kotlinx.android.synthetic.main.adapter_item_my_boon.view.*

/**
 * @className:OrderAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月14日 22:32
 */
class MyBoonAdapter(list: List<MyBoonListBean>) : MyBaseAdapter<MyBoonListBean>(list) {
    override fun getItemResource(pos: Int): Int = R.layout.adapter_item_my_boon
    override fun getItemView(position: Int, convertView: View, holder: ViewHolder?, parent: ViewGroup?): View {
        convertView.run {
            with(getItem(position)) {
                tv_name.text = ticket.name
                tv_time.text = created.getTime()
                tv_status.text = status
            }
        }
        return convertView
    }
}