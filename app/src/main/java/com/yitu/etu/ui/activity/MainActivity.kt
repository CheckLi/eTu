package com.yitu.etu.ui.activity

import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.bumptech.glide.Glide
import com.yitu.etu.EtuApplication
import com.yitu.etu.R
import com.yitu.etu.ui.fragment.AccountFragment
import com.yitu.etu.ui.fragment.LYFragment
import com.yitu.etu.ui.fragment.MapsFragment
import com.yitu.etu.util.LogUtil
import com.yitu.etu.util.isLogin
import com.yitu.etu.util.userInfo
import com.yitu.etu.widget.tablayout.OnTabSelectListener
import io.rong.common.FileUtils
import io.rong.imkit.RongIM
import io.rong.imkit.RongIM.UserInfoProvider
import io.rong.imkit.utils.SystemUtils
import io.rong.imlib.RongIMClient
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initActionBar() {
        mActionBarView.hideLeftImage()
    }

    override fun initView() {
        viewPager.adapter = MyPagerAdapter(supportFragmentManager)
        tab_select.setViewPager(viewPager, intArrayOf(R.drawable.icon136, -1, R.drawable.icon130))
        viewPager.offscreenPageLimit=2
        tab_select.currentTab = 1
        select(1)
    }


    override fun getData() {
        if(isLogin()) {
            connect(userInfo().token)
        }
        val buff=StringBuffer("")
        buff.append("缓存目录1$cacheDir\n")
        buff.append("缓存目录2$filesDir\n")

        buff.append("缓存目录3${getExternalFilesDir(Environment.DIRECTORY_PICTURES)}\n")
        buff.append("缓存目录4$externalCacheDir\n")
        buff.append("缓存目录5${FileUtils.getCachePath(this)}\n")
        val file=cacheDir

        buff.append("缓存目录1$cacheDir 大小 ${file.length()/1024.0f}kb ${getsize(file)}kb\n")
        LogUtil.e("cache",buff.toString())
    }

    fun getsize(file:File):Float{
        val files=file.listFiles()
        var lenght=0.0f
        files.forEach {
            if(it.isDirectory){
                getsize(it)
            }else{
                lenght+=file.length()/1024.0f
            }
        }
        return lenght
    }

    /**
     * 设置标题的显示与隐藏
     */
    fun select(position: Int) {
        when (position) {
            0 -> {
                mActionBarView.setTitle("旅 友")
                mActionBarView.hideLeftImage()
                mActionBarView.setRightImage(R.drawable.icon56) {
                    showToast("添加")
                }
            }
            1 -> {
                mActionBarView.setLeftImage(R.drawable.icon114) {
                    val fragments=supportFragmentManager.fragments
                    fragments?.forEach {
                        if(it is MapsFragment){
                            it.search()
                            return@setLeftImage
                        }
                    }
                }
                mActionBarView.setTitle("e 途")
                mActionBarView.setRightImage(R.drawable.icon87) {
                    showToast("红包")
                }
            }
            2 -> {
                mActionBarView.setTitle("个人中心")
                mActionBarView.hideLeftImage()
                mActionBarView.hideRightImage()
            }
        }
    }

    override fun initListener() {
        /**
         * viewpager点击事件
         */
        tab_select.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                select(position)
            }

            override fun onTabReselect(position: Int) {
            }
        })

        /**
         * 中间按钮点击事件
         */
        iv_middle.setOnClickListener {
            tab_select.currentTab = 1
            select(1)
        }
    }

    /**
     *
     * 连接服务器，在整个应用程序全局，只需要调用一次，需在 [.init] 之后调用。
     *
     * 如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。
     *
     * @param token    从服务端获取的用户身份令牌（Token）。
     * @param callback 连接回调。
     * @return RongIM  客户端核心类的实例。
     */
     open fun connect(token: String) {

        if (EtuApplication.getInstance().applicationInfo.packageName == SystemUtils.getCurProcessName(EtuApplication.getInstance().applicationContext)) {

            RongIM.connect(token, object : RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                override fun onTokenIncorrect() {

                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                override fun onSuccess(userid: String) {
                    LogUtil.e("LoginActivity", "--onSuccess$userid")
                    showToast("聊天服务器已连接")
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                override fun onError(errorCode: RongIMClient.ErrorCode) {

                }
            })
        }
        /**
         * 设置用户信息的提供者，供 RongIM 调用获取用户名称和头像信息。
         *
         * @param userInfoProvider 用户信息提供者。
         * @param isCacheUserInfo  设置是否由 IMKit 来缓存用户信息。<br>
         *                         如果 App 提供的 UserInfoProvider
         *                         每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
         *                         此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
         * @see UserInfoProvider
         */
       /* RongIM.setUserInfoProvider({ userId ->
            UserInfo(userId,"小王子", Uri.parse("http://rongcloud-web.qiniudn.com/docs_demo_rongcloud_logo.png"))
        }, true)*/
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        Glide.get(this).clearMemory()
        RongIM.getInstance().disconnect()
        super.onDestroy()
    }
}

private class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "旅友"
            1 -> ""
            2 -> "个人"
            else -> ""
        }
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> MapsFragment()
            2 -> AccountFragment.getInstance()
            else -> {
                LYFragment.getInstance()
            }
        }
    }

}