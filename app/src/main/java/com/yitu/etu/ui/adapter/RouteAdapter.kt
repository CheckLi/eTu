package com.yitu.etu.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.yitu.etu.R
import com.yitu.etu.entity.MyRouteBean
import com.yitu.etu.util.Empty
import com.yitu.etu.util.addHost
import com.yitu.etu.util.imageLoad.ImageLoadUtil
import com.yitu.etu.widget.ListSlideView
import kotlinx.android.synthetic.main.adapter_item_route.view.*

/**
 * @className:OrderAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月14日 22:32
 */
class RouteAdapter(val type: Int, list: List<MyRouteBean>) : MyBaseAdapter<MyRouteBean>(list) {
    override fun getItemResource(pos: Int): Int = if (type == 2) R.layout.adapter_item_route else R.layout.adapter_item_order

    override fun getItemView(position: Int, convertView: View, holder: ViewHolder, parent: ViewGroup?): View {
        if (type == 2) {
            convertView.run {
                with(getItem(position)) {
                    tv_name.text = name.Empty(actionName)
                    ImageLoadUtil.getInstance().loadImage(icon, image.addHost(), 50, 50)
                    tv_time.text = created
                    tv_status.text = status.Empty()
                    tvDelete.setOnClickListener {
                        val view = convertView.parent
                        if (view is ListSlideView) {
                            view.slideBack()
                        }
                        delListener?.del(position, getItem(position))
                    }

                }
            }
        } else {
            val name = holder.getView<TextView>(R.id.tv_name)
            val time = holder.getView<TextView>(R.id.tv_time)
            val payType = holder.getView<TextView>(R.id.tv_pay_type)
            val status1 = holder.getView<TextView>(R.id.tv_status)
            val icon = holder.getView<ImageView>(R.id.icon)
            with(getItem(position)) {
                name.text = actionName.Empty()
                ImageLoadUtil.getInstance().loadImage(icon, image.addHost(), 50, 50)
                time.text = created
                status1.text = status.Empty()
                payType.text = paytype.Empty()
            }
        }
        return convertView
    }

}
