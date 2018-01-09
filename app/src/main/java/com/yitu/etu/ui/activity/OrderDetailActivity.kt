package com.yitu.etu.ui.activity

import android.content.Intent
import android.widget.TableRow
import android.widget.TextView
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Poi
import com.yitu.etu.R
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.OrderDetail
import com.yitu.etu.entity.OrderList
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.Tools
import com.yitu.etu.util.getTime
import com.yitu.etu.util.post
import kotlinx.android.synthetic.main.activity_order_detail.*
import okhttp3.Call
import java.lang.Exception


class OrderDetailActivity : BaseActivity() {
    lateinit var order: OrderList
    override fun getLayout(): Int = R.layout.activity_order_detail

    override fun getIntentExtra(intent: Intent?) {
        order = intent?.getSerializableExtra("order_detail") as OrderList
    }

    override fun initActionBar() {
        title = "订单详情"
    }

    override fun initView() {
      /*  with(order) {
            tr_order.run {
                for (pos in 0 until childCount) {
                    val text = (getChildAt(pos) as TextView)
                    when (pos) {
                        0 -> text.text = product?.name.Empty()
                        1 -> text.text = this@with.id.toString()
                        2 -> text.text = product?.price.Empty()
                        3 -> text.text = created.getTime()
                    }
                }

            }
        }*/
    }

    override fun getData() {
        showWaitDialog("获取中...")
        post(Urls.URL_ORDER_DETAIL, hashMapOf("id" to order.id.toString()), object : GsonCallback<ObjectBaseEntity<OrderDetail>>() {
            override fun onError(call: Call?, e: Exception?, id: Int) {
                hideWaitDialog()
                showToast("数据获取失败")
            }

            override fun onResponse(response: ObjectBaseEntity<OrderDetail>, id: Int) {
                hideWaitDialog()
                if (response.success()) {
                    with(response.data.shop) {
                        tv_address.text = "地址：$address"
                        tv_phone.text = "电话：$phone"
                    }
                    response.data?.run{
                        product?.forEach {
                            val tableRow = layoutInflater.inflate(R.layout.item_table_row, null) as TableRow
                            for (i in 0 until tableRow.childCount){
                                val text=tableRow.getChildAt(i) as TextView
                                when(i){
                                    0->text.text=it.name
                                    1->text.text=this.id.toString()
                                    2->text.text=this.price
                                    3->text.text=this.created.getTime()

                                }
                            }
                            tableLayout.addView(tableRow,tableLayout.childCount-1)
                        }
                        tv_number.text = "兑换编码：$checkSn"
                        tv_address.setOnClickListener {
                            val end = Poi(shop.address, LatLng(shop.addressLat, shop.addressLng), "")
                            Tools.navi(context, end)
                        }
                    }
                } else {
                    showToast(response.message)
                }
            }

        })
    }

    override fun initListener() {

    }

}
