package com.yitu.etu.ui.activity

import android.view.MotionEvent
import android.view.View
import com.yitu.etu.R
import com.yitu.etu.entity.BuyCar
import com.yitu.etu.eventBusItem.EventRefresh
import com.yitu.etu.ui.adapter.BuyCarAdapter
import com.yitu.etu.util.BuyCarUtil
import com.yitu.etu.util.formatPrice
import com.yitu.etu.util.pay.BuyType
import com.yitu.etu.util.pay.PayUtil
import kotlinx.android.synthetic.main.activity_buy_car2.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*
import kotlin.concurrent.thread

class BuyCarActivity : BaseActivity() {
    lateinit var adapter: BuyCarAdapter
    private var totalPrice: Float = 0f
    override fun getLayout(): Int = R.layout.activity_buy_car2

    override fun initActionBar() {
        title = "购物车"
        setRightText("添加"){
            BuyCarUtil.addBuyCar(BuyCar(Random().nextInt(10)))
        }
    }

    override fun initView() {
        EventBus.getDefault().register(this)
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
        initTotalPrice()
    }

    override fun initListener() {
        /**
         * 添加和删除
         */
        adapter.setBuyCarListener({ select, del ->
            if (del) {
                if (adapter.count == 0) {
                    data_empty_view.visibility = View.VISIBLE
                }
                listview.slideBack()
            } else if (!select) {
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
            initTotalPrice()
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
                        initTotalPrice()
                    }
                }
            }
            true
        }
        /**
         * 去结算
         */
        to_settlement.setOnClickListener {
            val params = HashMap<String, String>()
            ids = adapter.getPutId()
            params.put("product_id", ids)
            params.put("count", adapter.getPutCount())
            PayUtil.getInstance(-1, totalPrice, "购物车购买", BuyType.TYPE_BUY_SHOP_PROJECT)
                    .toPayActivity(this, params)
            onEventClear(EventRefresh(className))
        }

    }

    var ids: String = ""
    private fun initTotalPrice() {
        tv_total_price.setSpanText("合计：%￥${totalPrice.formatPrice()}%")
    }

    override fun onPause() {
        thread {
            BuyCarUtil.saveBuyCar(adapter.getList())
        }
        super.onPause()
    }

    /**
     * 购买成功清空购物车
     */
    @Subscribe
    fun onEventClear(event: EventRefresh) {
        if (event.classname == className) {
            BuyCarUtil.removeCar(ids)
            ids=""
            initData()
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}
