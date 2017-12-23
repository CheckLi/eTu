package com.yitu.etu.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.yitu.etu.R
import com.yitu.etu.entity.MyRouteBean
import com.yitu.etu.util.Empty
import com.yitu.etu.util.addHost
import com.yitu.etu.util.getTime
import com.yitu.etu.util.imageLoad.ImageLoadUtil
import kotlinx.android.synthetic.main.adapter_item_route.view.*

/**
 * @className:OrderAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月14日 22:32
 */
class RouteAdapter(list: List<MyRouteBean>) : MyBaseAdapter<MyRouteBean>( list) {
    override fun getItemResource(pos: Int): Int = R.layout.adapter_item_route

    override fun getItemView(position: Int, convertView: View, holder: ViewHolder?, parent: ViewGroup?): View {
        convertView.run {
            with(getItem(position)){
                tv_name.text=name.Empty()
                ImageLoadUtil.getInstance().loadImage(icon,image.addHost(),50,50)
                tv_time.text=startTime.getTime()
                tv_status.text=status.Empty()
            }
        }
        return convertView
    }

}
