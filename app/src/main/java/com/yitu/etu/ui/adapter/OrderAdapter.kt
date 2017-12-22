package com.yitu.etu.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.yitu.etu.R
import com.yitu.etu.entity.OrderList
import com.yitu.etu.util.Empty
import com.yitu.etu.util.addHost
import com.yitu.etu.util.getTime
import com.yitu.etu.util.imageLoad.ImageLoadUtil
import kotlinx.android.synthetic.main.adapter_item_order.view.*

/**
 * @className:OrderAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月14日 22:32
 */
class OrderAdapter(list: List<OrderList>) : MyBaseAdapter<OrderList>(list) {
    override fun getItemResource(pos: Int): Int = R.layout.adapter_item_order

    override fun getItemView(position: Int, convertView: View, holder: ViewHolder?, parent: ViewGroup?): View {
        convertView?.run {
            with(getItem(position)) {
                with(product) {
                    ImageLoadUtil.getInstance().loadImage(icon, image.addHost(), 60, 60)
                    tv_name.text = name.Empty()
                }
                tv_pay_type.text = paytype.Empty()
                tv_status.text = status.Empty()
                tv_time.text = created.getTime()
            }
        }
        return convertView
    }
}
