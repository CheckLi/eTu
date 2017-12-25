package com.yitu.etu.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.yitu.etu.R
import com.yitu.etu.entity.HistoryPanBean
import com.yitu.etu.util.Empty
import com.yitu.etu.util.addHost
import com.yitu.etu.util.imageLoad.ImageLoadUtil
import kotlinx.android.synthetic.main.adapter_item_history_p_an.view.*

/**
 * @className:OrderAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月14日 22:32
 */
class HistoryPAnAdapter(list: List<HistoryPanBean>, val type: Int) : MyBaseAdapter<HistoryPanBean>(list) {
    override fun getItemResource(pos: Int): Int = R.layout.adapter_item_history_p_an

    override fun getItemView(position: Int, convertView: View, holder: ViewHolder?, parent: ViewGroup?): View {
        convertView?.run {
            with(getItem(position)) {
                ImageLoadUtil.getInstance().loadImage(icon, image.addHost(), 60, 60)
                tv_name.text = if (type == 0) {
                    "${name.Empty()} 平安符"
                } else name.Empty()
                tv_pay_type.text = desc
                tv_status.text = status.Empty("成功")
                tv_time.text = created.Empty()
            }
        }
        return convertView
    }
}