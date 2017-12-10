package com.yitu.etu.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.yitu.etu.R
import kotlinx.android.synthetic.main.ly_chat_item.view.*
import java.util.*

/**
 * @className:LYAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月10日 14:57
 */
class LYAdapter(context: Context, data: List<String>) : MyBaseAdapter<String>(context, data) {
    val names = arrayOf("e店小二", "刘小姐", "孙大娘", "武大狼", "西门庆", "武松")
    val contents = arrayOf("抱歉,人工客服不在线", "武大郎不想跟你说话", "武松打死西门庆", "刘小姐下海", "呵呵呵呵呵", "哈哈哈哈")
    val random = Random()
    override fun getItemResource(pos: Int): Int = R.layout.ly_chat_item

    override fun getItemView(position: Int, convertView: View, holder: MyBaseAdapter<String>.ViewHolder, parent: ViewGroup): View? {
        convertView.run {
            tv_name.tag = if (tv_name.tag == null) {
                names[random.nextInt(count)]
            } else {
                tv_name.tag
            }
            tv_content.tag = if (tv_content.tag == null) {
                contents[random.nextInt(count)]
            } else {
                tv_content.tag
            }
            tv_name.text = tv_name.tag.toString()
            tv_content.text = tv_content.tag.toString()
        }
        return convertView
    }

    override fun getCount(): Int {
        return names.size
    }
}
