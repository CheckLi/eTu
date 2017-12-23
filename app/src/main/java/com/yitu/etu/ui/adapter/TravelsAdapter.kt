package com.yitu.etu.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.yitu.etu.R
import com.yitu.etu.entity.MyTravels
import com.yitu.etu.util.Empty
import kotlinx.android.synthetic.main.adapter_item_travels.view.*

/**
 * @className:OrderAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月14日 22:32
 */
class TravelsAdapter(list: List<MyTravels>) : MyBaseAdapter<MyTravels>( list) {
    override fun getItemResource(pos: Int): Int = R.layout.adapter_item_travels
    override fun getItemView(position: Int, convertView: View, holder: ViewHolder?, parent: ViewGroup?): View {
        convertView.run {
            with(getItem(position)){
//                ImageLoadUtil.getInstance().loadImage(icon,image.addHost(),50,50)
                tv_name.text=title.Empty()
                tv_des.text="申请中"
            }
        }
        return convertView
    }

}
