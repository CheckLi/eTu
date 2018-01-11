package com.yitu.etu.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.yitu.etu.R
import com.yitu.etu.entity.OrderActionInfo
import com.yitu.etu.util.Empty
import com.yitu.etu.util.addHost
import com.yitu.etu.util.imageLoad.ImageLoadUtil
import kotlinx.android.synthetic.main.adapter_item_action_order.view.*

/**
 * @className:OrderAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月14日 22:32
 */
class OrderActionAdapter(list: List<OrderActionInfo>) : MyBaseAdapter<OrderActionInfo>(list) {
    override fun getItemResource(pos: Int): Int = R.layout.adapter_item_action_order

    override fun getItemView(position: Int, convertView: View, holder: ViewHolder?, parent: ViewGroup?): View {
        convertView?.run {
            with(getItem(position)) {
                with(user) {
                    ImageLoadUtil.getInstance().loadImage(icon, header.addHost(), 60, 60)
                    tv_name.text = name.Empty()
                }
                tv_status.text = status.Empty()
                tv_time.text = createdTime.Empty()
            }
        }
        return convertView
    }
}
