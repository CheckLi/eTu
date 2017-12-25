package com.yitu.etu.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.yitu.etu.R
import com.yitu.etu.entity.ProductListBean
import com.yitu.etu.util.addHost
import com.yitu.etu.util.imageLoad.ImageLoadUtil
import kotlinx.android.synthetic.main.adapter_product_item.view.*

/**
 * @className:OrderAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月14日 22:32
 */
class ProductAdapter(list: List<ProductListBean>) : MyBaseAdapter<ProductListBean>(list) {
    override fun getItemResource(pos: Int): Int = R.layout.adapter_product_item
    override fun getItemView(position: Int, convertView: View, holder: ViewHolder?, parent: ViewGroup?): View {
        convertView.run {
            with(getItem(position)) {
                ImageLoadUtil.getInstance().loadImage(iv_icon, image.addHost(), 100, 100)
                tv_name.text = name
                tv_number.text = "剩余数量：$count"
                tv_desc.text = "$price 平安符"
            }
        }
        return convertView
    }
}
