package com.yitu.etu.ui.fragment

import com.huizhuang.zxsq.utils.nextActivityFromFragment
import com.yitu.etu.R
import com.yitu.etu.ui.activity.*
import io.rong.imkit.RongIM
import io.rong.imlib.model.CSCustomServiceInfo
import kotlinx.android.synthetic.main.fragment_account_layout.*



/**
 * @className:LYFragment
 * @description:`
 * @author: JIAMING.LI
 * @date:2017年12月09日 17:23
 */
class AccountFragment : BaseFragment() {
    //首先需要构造使用客服者的用户信息
    val csBuilder = CSCustomServiceInfo.Builder()
    val csInfo = csBuilder.nickName("e途").build()

    override fun getLayout(): Int = R.layout.fragment_account_layout

    companion object {
        fun getInstance(): AccountFragment {
            return AccountFragment()
        }

    }

    override fun initView() {

    }

    override fun getData() {

    }

    override fun initListener() {
        /**
         * 我的钱包
         */
        tv_my_wallet.setOnClickListener {
            nextActivityFromFragment<MyWalletActivity>()
        }

        /**
         * 我的订单
         */
        tv_my_order.setOnClickListener {
            nextActivityFromFragment<MyOrderActivity>()
        }

        /**
         * 购物车
         */
        tv_buy_car.setOnClickListener {
            nextActivityFromFragment<BuyCarActivity>()
        }

        /**
         * 我的店铺
         */
        tv_my_shop.setOnClickListener {
            nextActivityFromFragment<MyShopActivity>()
        }

        /**
         * 我的行程
         */
        tv_my_route.setOnClickListener {
            nextActivityFromFragment<MyRouteActivity>()
        }

        /**
         * 景点推荐
         */
        tv_view_recomment.setOnClickListener {
            nextActivityFromFragment<ViewRecommentActivity>()
        }

        /**
         * 我的游记
         */
        tv_my_travels.setOnClickListener {
            nextActivityFromFragment<MyTravelsActivity>()
        }

        /**
         * 我的收藏
         */
        tv_my_collect.setOnClickListener {
            nextActivityFromFragment<MyCollectActivity>()
        }

        /**
         * 账号管理
         */
        tv_account_manage.setOnClickListener {
            nextActivityFromFragment<AccountManageActivity>()
        }

        /**
         * 福利中心
         */
        tv_boon_center.setOnClickListener {
            nextActivityFromFragment<BoonActivity>()
        }

        /**
         * 联系客服
         */
        tv_call_service.setOnClickListener {

            /**
             * 启动客户服聊天界面。
             *
             * @param context           应用上下文。
             * @param customerServiceId 要与之聊天的客服 Id。
             * @param title             聊天的标题，开发者可以在聊天界面通过 intent.getData().getQueryParameter("title") 获取该值, 再手动设置为标题。
             * @param customServiceInfo 当前使用客服者的用户信息。{@link io.rong.imlib.model.CSCustomServiceInfo}
             *              * KEFU151041166014716上线客服id
             */
            RongIM.getInstance().startCustomerServiceChat(activity, "KEFU151304509872992", "在线客服",csInfo)
        }
        /**
         * 软件分享
         */
        tv_software_share.setOnClickListener {
            nextActivityFromFragment<SoftWareShareActivity>()
        }
    }
}