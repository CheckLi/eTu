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
class FriendListAdapter(list: List<UserInfo>,val select:Boolean) : MyBaseAdapter<UserInfo>(list) {
    override fun getItemResource(pos: Int): Int = R.layout.adapter_item_friend_list
    override fun getItemView(position: Int, convertView: View, holder: ViewHolder?, parent: ViewGroup?): View {
        convertView.run {
            with(getItem(position)) {
                ImageLoadUtil.getInstance().loadImage(icon, header.addHost(), 50, 50)
                tv_name.text = name
                tv_des.text = intro
                if (isCheck) {
                    iv_gou.visibility = View.VISIBLE
                } else {
                    iv_gou.visibility = View.GONE
                }
            }
        }
        if(select){
            convertView.setOnClickListener {
                select(position)
            }
        }
        return convertView
    }

    fun select(position:Int){
        if(!data[position].isCheck) {
            data.forEachIndexed { index, userInfo ->
                userInfo.isCheck = index == position
            }
            notifyDataSetChanged()
        }
    }

    fun getIds():MutableList<String>{
        val list= mutableListOf<String>()
        data.forEach {
            if(it.isCheck){
                list.add(it.id.toString())
            }
        }
        return list
    }

    fun getNames():String{
        val buff=StringBuffer("群聊信息:")
        data.forEach {
            if(it.isCheck){
                buff.append("${it.name},")
            }
        }
        return if(buff.length>1) buff.substring(0,buff.length-1) else buff.toString()
    }
}
