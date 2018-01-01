package com.yitu.etu.ui.adapter

import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.yitu.etu.Iinterface.IBuyCarListener
import com.yitu.etu.R
import com.yitu.etu.entity.BuyCar
import com.yitu.etu.util.Empty
import kotlinx.android.synthetic.main.buy_car_item.view.*

/**
 * @className:BuyCarAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月14日 23:23
 */
class BuyCarAdapter : MyBaseAdapter<BuyCar> {
    private var buyCarListener: IBuyCarListener? = null
    override fun getItemResource(pos: Int): Int = R.layout.buy_car_item

    override fun getItemView(position: Int, convertView: View, holder: ViewHolder?, parent: ViewGroup?): View {
        convertView.run {
            with(getItem(position)) {
                cb_check.isChecked = isCheck
                tv_count.text = count.toString()
                tv_desc.text = des.Empty()
                tv_name.text = name.Empty()
                tv_price.text = price.toString()
                iv_add.setOnClickListener {
                    count++
                    tv_count.text = count.toString()
                    buyCarListener?.price(true, price)
                }

                iv_cut.setOnClickListener {
                    if (count > 1) {
                        count--
                        tv_count.text = count.toString()
                        buyCarListener?.price(false, price)
                    }
                }

                cb_check.setOnTouchListener { v, event ->
                    when (event.action) {

                        MotionEvent.ACTION_UP -> {
                            isCheck = !isCheck
                            cb_check.isChecked = isCheck
                            buyCarListener?.price(isCheck, count * price)
                            buyCarListener?.select(isCheck, false)
                        }
                    }
                    true
                }
            }

            /**
             * 删除购物车商品
             */
            tvDelete.setOnClickListener {
                remove(position)
                buyCarListener?.select(false, true)
                notifyDataSetChanged()
            }
        }
        return convertView
    }

    /**
     * 点击事件
     */
    fun setBuyCarListener(onSelect: (select: Boolean, isDel: Boolean) -> Unit, onClick: (isAdd: Boolean, price: Float) -> Unit) {
        buyCarListener = object : IBuyCarListener {
            override fun select(select: Boolean, del: Boolean) {
                onSelect(select, del)
            }

            override fun price(isAdd: Boolean, price: Float) {
                onClick(isAdd, price)
            }

        }
    }

    constructor(data: List<BuyCar>) : super(data) {}

    fun getTotalPrice(): Float {
        var totalPrice = 0f
        data.forEach {
            if (it.isCheck) {
                totalPrice += it.count * it.price
            }
        }
        return totalPrice
    }

    fun checkSelect(): Boolean {
        var selectAll = true
        data.forEach {
            if (!it.isCheck) {
                selectAll = false
                return@forEach
            }
        }
        return selectAll
    }

    fun setSelectAll(isSelect: Boolean) {
        data.forEach {
            it.isCheck = isSelect
        }
        notifyDataSetChanged()
    }

    fun getList(): MutableList<BuyCar>? {
        return data
    }

    fun addItem(buycar: BuyCar) {
        if (data.size == 0) {
            data = arrayListOf(buycar)
        } else {
            data.add(buycar)
        }
        notifyDataSetChanged()
    }

    /**
     * 获取选中id
     */
    fun getPutId(): String {
        val ids = StringBuffer()
        data.forEach {
            if (it.isCheck) {
                ids.append("${it.id},")
            }
        }
        return if (ids.length > 1) ids.toString().substring(0, ids.length - 1) else ids.toString()
    }
    /**
     * 获取选中id
     */
    fun getPutCount(): String {
        val ids = StringBuffer()
        data.forEach {
            if (it.isCheck) {
                ids.append("${it.count},")
            }
        }
        return if (ids.length > 1) ids.toString().substring(0, ids.length - 1) else ids.toString()
    }
}
