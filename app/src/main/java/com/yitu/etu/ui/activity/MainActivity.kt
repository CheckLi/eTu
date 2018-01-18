package com.yitu.etu.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import android.widget.AdapterView
import com.allenliu.versionchecklib.core.AllenChecker
import com.allenliu.versionchecklib.core.VersionParams
import com.bumptech.glide.Glide
import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.BuildConfig
import com.yitu.etu.EtuApplication
import com.yitu.etu.R
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.UserInfo
import com.yitu.etu.entity.VersionBean
import com.yitu.etu.eventBusItem.EventRefresh
import com.yitu.etu.eventBusItem.EventRefreshLocation
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.MyActivityManager
import com.yitu.etu.tools.Urls
import com.yitu.etu.ui.fragment.AccountFragment
import com.yitu.etu.ui.fragment.LYFragment
import com.yitu.etu.ui.fragment.MapsFragment
import com.yitu.etu.util.*
import com.yitu.etu.widget.tablayout.OnTabSelectListener
import io.rong.common.FileUtils
import io.rong.imkit.RongIM
import io.rong.imkit.manager.IUnReadMessageObserver
import io.rong.imlib.model.Conversation
import kotlinx.android.synthetic.main.actionbar_layout.view.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Call
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.io.File
import java.lang.Exception


class MainActivity : BaseActivity() {
    private var defaultPosition = 1
    override fun getLayout(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initActionBar() {
        mActionBarView.hideLeftImage()
    }

    override fun getIntentExtra(intent: Intent?) {
        defaultPosition = intent?.getIntExtra("defaultPosition", 1) ?: 1
        super.getIntentExtra(intent)
    }

    override fun initView() {
        registReciver()
        EventBus.getDefault().register(this)
        viewPager.adapter = MyPagerAdapter(supportFragmentManager)
        tab_select.setViewPager(viewPager, intArrayOf(R.drawable.icon136, -1, R.drawable.icon130))
        viewPager.offscreenPageLimit = 2
        tab_select.currentTab = defaultPosition
        select(defaultPosition)
        if (BuildConfig.BUILD_TYPE == "etuTest") {
            //超过20天没交钱，此APK无法被打开
            val time = Tools.getTimeDifference("2018-1-9 00:00", DateUtil.getTime("${System.currentTimeMillis() / 1000}", "yyyy-MM-dd HH:mm"))
            if (time >= 20 * 24) {
                val unInstall = Intent()
                unInstall.action = Intent.ACTION_DELETE
                unInstall.data = Uri.parse("package:" + packageName)
                startActivity(unInstall)
                // 执行这句会杀死进程(优点：会把整个应用的静态变量全部释放)
                MyActivityManager.getInstance().finishAllActivity()
                android.os.Process.killProcess(android.os.Process.myPid())
                System.exit(0)
                System.gc()
            }
        }
    }

    val reciver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                if (intent.action == "closeMainActivity") {
                    finish()
                }
            }
        }

    }

    fun registReciver() {
        val filter=IntentFilter()
        filter.addAction("closeMainActivity")
        registerReceiver(reciver,filter)
    }

    override fun getData() {
        checkVersion()
        EtuApplication.getInstance().connectChat()//连接聊天服务器
        val buff = StringBuffer("")
        buff.append("缓存目录1$cacheDir\n")
        buff.append("缓存目录2$filesDir\n")

        buff.append("缓存目录3${getExternalFilesDir(Environment.DIRECTORY_PICTURES)}\n")
        buff.append("缓存目录4$externalCacheDir\n")
        buff.append("缓存目录5${FileUtils.getCachePath(this)}\n")
        val file = cacheDir
        getsize(file)
        buff.append("缓存目录1$cacheDir 大小 ${file.length() / 1024.0f}kb ${lenght}kb\n")
        LogUtil.e("cache", buff.toString())
        refreshMyInfo()
    }

    var lenght = 0.0f
    fun getsize(file: File): Float {
        val files = file.listFiles()
        files.forEach {
            if (it.isDirectory) {
                getsize(it)
            } else {
                lenght += file.length() / 1024.0f
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
                tab_select.hideMsg(0)
                RongIM.getInstance().removeUnReadMessageCountChangedObserver(unReadListener)
                mActionBarView.setTitle("旅 友")
                mActionBarView.hideLeftImage()
                mActionBarView.setRightImage(R.drawable.icon56) {
                    val pop = Tools.getPopupWindow(this@MainActivity, arrayOf("添加好友", "好友列表", "发起群聊"), object : AdapterView.OnItemClickListener {
                        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            if (!isLogin()) {
                                showToast("请先登录")
                            } else {
                                when (position) {
                                    0 -> nextActivity<SearchFriendActivity>()
                                    1 -> nextActivity<FriendListActivity>()
                                    2 -> Tools.createChagGroup(this@MainActivity)
                                    else -> ""
                                }
                            }
                        }

                    }, "right")
                    pop.showAsDropDown(mActionBarView.iv_right, 20, 0)
                }
            }
            1 -> {
                registUnRead()
                mActionBarView.setLeftImage(R.drawable.icon114) {
                    val fragments = supportFragmentManager.fragments
                    fragments?.forEach {
                        if (it is MapsFragment) {
                            it.search()
                            return@setLeftImage
                        }
                    }
                }
                mActionBarView.setTitle("e 途")
                mActionBarView.setRightImage(R.drawable.icon87) {
                    nextActivity<BoonActivity>("type" to 0)
                }
            }
            2 -> {
                registUnRead()
                EventBus.getDefault().post(EventRefreshLocation())
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

    private fun registUnRead() {
        /**
         * 未读消息监听
         */
        RongIM.getInstance().addUnReadMessageCountChangedObserver(unReadListener, Conversation.ConversationType.PRIVATE, Conversation.ConversationType.GROUP, Conversation.ConversationType.SYSTEM,
                Conversation.ConversationType.APP_PUBLIC_SERVICE, Conversation.ConversationType.PUSH_SERVICE, Conversation.ConversationType.DISCUSSION,
                Conversation.ConversationType.CUSTOMER_SERVICE, Conversation.ConversationType.CHATROOM)
    }

    private val unReadListener = IUnReadMessageObserver { count ->
        if (count < 1) {
            tab_select.hideMsg(0)
        } else {
            tab_select.showMsg(0, if (count > 99) 99 else count)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        Glide.get(this).clearMemory()
        RongIM.getInstance().disconnect()
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(unReadListener)
        MyActivityManager.getInstance().finishAllActivity()
        AllenChecker.cancelMission()
        unregisterReceiver(reciver)
        super.onDestroy()
    }


    fun refreshMyInfo() {
        post(Urls.URL_GET_USER_INFO, hashMapOf(), object : GsonCallback<ObjectBaseEntity<UserInfo>>() {
            override fun onResponse(response: ObjectBaseEntity<UserInfo>, id: Int) {
                if (response.success() && response.data != null && response.data.id != 0) {
                    with(response.data) {
                        EtuApplication.getInstance().userInfo.setUserinfo(response.data)
                    }
                }
            }

            override fun onError(call: Call?, e: Exception?, id: Int) {
            }

        })
    }

    /**
     * 检查版本更新
     */
    fun checkVersion() {
        post(Urls.URL_VERSION, hashMapOf("version" to packageManager.getPackageInfo(packageName, 0).versionName), object : GsonCallback<ObjectBaseEntity<VersionBean>>() {
            override fun onResponse(response: ObjectBaseEntity<VersionBean>, id: Int) {
                if (!response.success() && response.data != null) {
                    response.data?.run {
                        val builder = VersionParams.Builder().setOnlyDownload(true)
                                .setDownloadUrl(url)
                                .setOnlayUpdata(mustUpdate==1)
                                .setTitle("检测到新版本")
                                .setUpdateMsg(des)
                        AllenChecker.startVersionCheck(EtuApplication.getInstance(), builder.build())
                    }

                }
            }

            override fun onError(call: Call?, e: Exception?, id: Int) {
            }

        })
    }

    /**
     * 刷新用户数据
     */
    @Subscribe
    fun onEventRefreshMyInfo(event: EventRefresh) {
        if (event.classname == className) {
            refreshMyInfo()
        }
    }

    var time = 0L

    override fun onBackPressed() {
        if (System.currentTimeMillis() - time > 2000) {
            showToast("再按一次返回退出程序")
            time = System.currentTimeMillis()
        } else {
            super.onBackPressed()
        }
    }

    override fun finish() {

        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(0)
        System.gc()
        super.finish()
        overridePendingTransition(-1, R.anim.abc_fade_out)
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