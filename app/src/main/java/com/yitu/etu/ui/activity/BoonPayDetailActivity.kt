package com.yitu.etu.ui.activity

import com.yitu.etu.R
import com.yitu.etu.entity.MyBoonBean
import com.yitu.etu.util.pay.BuyType
import com.yitu.etu.util.pay.PayUtil
import kotlinx.android.synthetic.main.activity_boon_pay_detail.*

class BoonPayDetailActivity : BaseActivity() {
    lateinit var bean:MyBoonBean
    override fun getLayout(): Int = R.layout.activity_boon_pay_detail

    override fun initActionBar() {
        title="福利详情"
    }

    override fun initView() {
        bean=intent.getSerializableExtra("detail") as MyBoonBean
    }

    override fun getData() {
        tv_content.text=bean.des
    }

    override fun initListener() {
        tv_cancel.setOnClickListener {
            finish()
        }

        tv_pay.setOnClickListener {
            PayUtil.getInstance(bean.id,bean.price.toFloat(),"购买门票：${bean.name}",BuyType.TYPE_BUY_TICKET)
                    .toPayActivity(this)
        }
    }

}
