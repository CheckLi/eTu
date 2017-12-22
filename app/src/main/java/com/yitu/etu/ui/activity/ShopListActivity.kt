package com.yitu.etu.ui.activity

import android.app.ActionBar
import android.view.View
import android.widget.AbsListView
import com.yitu.etu.R
import com.yitu.etu.ui.adapter.BuyCarAdapter
import kotlinx.android.synthetic.main.activity_buy_car.*

class ShopListActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_buy_car

    override fun initActionBar() {
        title = "商品管理"
        setRightText("添加") {
            showToast("添加商品")
        }
    }

    override fun initView() {
        val params = AbsListView.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, (4 * resources.displayMetrics.density).toInt())
        val view2 = View(this)
        view2.layoutParams = params
        recyclerView.addFooterView(view2)
        recyclerView.adapter = BuyCarAdapter(listOf())


    }

    override fun getData() {
    }

    override fun initListener() {

    }

}
