package com.yitu.etu.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.yitu.etu.R
import com.yitu.etu.entity.UserInfo
import com.yitu.etu.util.addHost
import com.yitu.etu.util.imageLoad.ImageLoadUtil
import kotlinx.android.synthetic.main.adapter_item_friend_list.view.*

/**
 * @className:OrderAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月14日 22:32
 */
class FriendListAdapter(list: List<UserInfo>) : MyBaseAdapter<UserInfo>( list) {
    override fun getItemResource(pos: Int): Int = R.layout.adapter_item_friend_list
    override fun getItemView(position: Int, convertView: View, holder: ViewHolder?, parent: ViewGroup?): View {
        convertView.run {
            with(getItem(position)){
                ImageLoadUtil.getInstance().loadImage(icon,header.addHost(),50,50)
                tv_name.text=name
                tv_des.text=intro
            }
        }
        return convertView
    }
}
