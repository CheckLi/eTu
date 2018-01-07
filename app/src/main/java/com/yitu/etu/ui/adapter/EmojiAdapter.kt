package com.yitu.etu.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.yitu.etu.R
import kotlinx.android.synthetic.main.emoji_adapter_item.view.*

/**
 * @className:EmojiAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2018年01月07日 17:16
 */
class EmojiAdapter(data: List<String>) : MyBaseAdapter<String>(data) {

    override fun getItemResource(pos: Int): Int= R.layout.emoji_adapter_item

    override fun getItemView(position: Int, convertView: View, holder: ViewHolder, parent: ViewGroup): View? {
        convertView.run {
            tv_emoji_name.text=getItem(position)
        }
        return convertView
    }
}
