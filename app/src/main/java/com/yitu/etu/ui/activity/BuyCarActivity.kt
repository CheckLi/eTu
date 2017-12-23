package com.yitu.etu.ui.activity

import android.view.MotionEvent
import android.view.View
import com.yitu.etu.R
import com.yitu.etu.entity.BuyCar
import com.yitu.etu.ui.adapter.BuyCarAdapter
import com.yitu.etu.util.BuyCarUtil
import kotlinx.android.synthetic.main.activity_buy_car2.*
import kotlin.concurrent.thread

class BuyCarActivity : BaseActivity() {
    lateinit var adapter: BuyCarAdapter
    private var totalPrice: Float = 0f
    override fun getLayout(): Int = R.layout.activity_buy_car2

    override fun initActionBar() {
        title = "购物车"
        setRightText("商品添加模拟"){
            val by=BuyCar()
            BuyCarUtil.addBuyCar(by)
            adapter.addItem(by)
            if(adapter.count==0){
                data_empty_view.visibility = View.VISIBLE
            }else{
                data_empty_view.visibility = View.GONE
            }
            listview.slideBack()
        }
    }

    override fun initView() {
        adapter = BuyCarAdapter(listOf())
        listview.adapter = adapter
    }

    override fun getData() {
        showWaitDialog("获取中...")
        initData()

    }

    private fun initData() {
        thread {
            val list = BuyCarUtil.getAllBuyCar()
            runOnUiThread {
                if (list == null || list.size == 0) {
                    data_empty_view.visibility = View.VISIBLE
                } else {
                    data_empty_view.visibility = View.GONE
                    adapter.replaceAll(list)
                    initInfo()
                }
                hideWaitDialog()

            }
        }
    }

    private fun initInfo() {
        totalPrice = adapter.getTotalPrice()
        cb_select_all.isChecked = adapter.checkSelect()
        tv_total_price.setSpanText("合计：%￥$totalPrice%")
    }

    override fun initListener() {
        /**
         * 添加和删除
         */
        adapter.setBuyCarListener({ select,del ->
            if(del){
                if(adapter.count==0){
                    data_empty_view.visibility = View.VISIBLE
                }
                listview.slideBack()
            }else if (!select) {
                cb_select_all.isChecked = false
            } else {
                cb_select_all.isChecked = adapter.checkSelect()
            }
        }, { isAdd, price ->
            totalPrice = if (isAdd) {
                totalPrice.plus(price)
            } else {
                totalPrice.minus(price)
            }
            tv_total_price.setSpanText("合计：%￥$totalPrice%")
        })

        /**
         * 全选按钮
         */
        cb_select_all.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    cb_select_all.isChecked = !cb_select_all.isChecked
                    adapter.setSelectAll(cb_select_all.isChecked)
                    if (cb_select_all.isChecked) {
                        initInfo()
                    } else {
                        totalPrice = 0.00f
                        tv_total_price.setSpanText("合计：%￥$totalPrice%")
                    }
                }
            }
            true
        }
    }

    override fun onPause() {
        thread {
            BuyCarUtil.saveBuyCar(adapter.getList())
        }
        super.onPause()
    }

}
