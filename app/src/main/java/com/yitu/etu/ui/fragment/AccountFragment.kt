package com.yitu.etu.ui.fragment

import android.view.View
import com.huizhuang.zxsq.utils.nextActivityFromFragment
import com.huizhuang.zxsq.utils.nextCheckLoginActivity
import com.yitu.etu.EtuApplication
import com.yitu.etu.R
import com.yitu.etu.entity.UserInfo
import com.yitu.etu.eventBusItem.EventRefreshLocation
import com.yitu.etu.eventBusItem.LoginSuccessEvent
import com.yitu.etu.ui.activity.*
import com.yitu.etu.util.Empty
import com.yitu.etu.util.addHost
import com.yitu.etu.util.imageLoad.ImageLoadUtil
import com.yitu.etu.util.isLogin
import com.yitu.etu.util.location.LocationUtil
import com.yitu.etu.util.userInfo
import io.rong.imkit.RongIM
import io.rong.imlib.model.CSCustomServiceInfo
import kotlinx.android.synthetic.main.fragment_account_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


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

        EventBus.getDefault().register(this)
        initUserInfo(EtuApplication.getInstance().userInfo)
        LocationUtil.getInstance().startLocation {
            tv_location.text = it?.city.Empty("定位失败")
        }
    }

    @Subscribe
    fun onEventRefreshLocation(event:EventRefreshLocation){
        if (tv_location.text == "定位失败") {
            LocationUtil.getInstance().startLocation {
                tv_location.text = it?.city.Empty("定位失败")
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {

        super.onHiddenChanged(hidden)
    }

    override fun onDestroyView() {
        LocationUtil.getInstance().onDestory()
        super.onDestroyView()
    }

    override fun getData() {

    }

    override fun initListener() {
        /**
         * 登录和个人资料入口
         */
        ll_header.setOnClickListener {
            nextCheckLoginActivity<AccountDataActivity>()

        }
        /**
         * 我的钱包
         */
        tv_my_wallet.setOnClickListener {
            nextCheckLoginActivity<MyWalletActivity>()
        }

        /**
         * 我的订单
         */
        tv_my_order.setOnClickListener {
            nextCheckLoginActivity<MyOrderActivity>()
        }

        /**
         * 购物车
         */
        tv_buy_car.setOnClickListener {
            nextCheckLoginActivity<BuyCarActivity>()
        }

        /**
         * 我的店铺
         */
        tv_my_shop.setOnClickListener {
            if (isLogin() && userInfo().usertype != 0) {
                nextCheckLoginActivity<MyShopActivity>()
            } else {
                showToast("您还不是商家")
            }
        }

        /**
         * 我的行程
         */
        tv_my_route.setOnClickListener {
            nextCheckLoginActivity<MyRouteActivity>()
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
            nextCheckLoginActivity<MyTravelsActivity>()
        }

        /**
         * 我的收藏
         */
        tv_my_collect.setOnClickListener {
            nextCheckLoginActivity<MyCollectActivity>()
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
            nextCheckLoginActivity<MyBoonActivity>()
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
            RongIM.getInstance().startCustomerServiceChat(activity, "KEFU151041166014716", "在线客服", csInfo)
        }
        /**
         * 软件分享
         */
        tv_software_share.setOnClickListener {
            nextActivityFromFragment<SoftWareShareActivity>()
        }
    }

    /**
     * 登录成功回调
     */
    @Subscribe
    fun onEventLoginSuccess(event: LoginSuccessEvent?) {
        //等于空代表退出登录
        initUserInfo(event?.userInfo)
    }

    /**
     * 初始化个人信息
     */
    fun AccountFragment.initUserInfo(userInfo: UserInfo?) {
        if (userInfo == null) {
            tv_login.visibility = View.VISIBLE
            tv_username.text = ""
            tv_username.visibility = View.GONE
            ImageLoadUtil.getInstance().loadImage(iv_head, "drawable://", R.drawable.default_head, 100, 100)
        } else {
            with(userInfo) {
                tv_login.visibility = View.GONE
                tv_username.text = name.Empty()
                tv_username.visibility = View.VISIBLE
                ImageLoadUtil.getInstance().loadImage(iv_head, header.addHost()
                        , R.drawable.default_head, 100, 100)
                val drawable = resources.getDrawable(when (sex) {
                    0 -> R.drawable.icon2
                    else -> R.drawable.icon0
                })
                drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                tv_username.setCompoundDrawables(null, null, drawable, null)
            }
        }
    }


    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}