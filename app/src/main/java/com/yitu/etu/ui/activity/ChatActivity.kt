package com.yitu.etu.ui.activity

import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import com.huizhuang.zxsq.utils.nextActivity
import com.xiaozhi.firework_core.FireWorkView
import com.yitu.etu.R
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.UserInfo
import com.yitu.etu.eventBusItem.EventPlayYanHua
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.Tools
import com.yitu.etu.util.isLogin
import com.yitu.etu.util.post
import io.rong.imkit.fragment.ConversationFragment
import kotlinx.android.synthetic.main.actionbar_layout.view.*
import okhttp3.Call
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.lang.Exception


class ChatActivity : BaseActivity() {
    private lateinit var yanhua: FireWorkView
    override fun getLayout(): Int = R.layout.activity_chat

    override fun initActionBar() {
        title = intent.data.getQueryParameter("title")
        setRightClick(R.drawable.icon145) {
            val pop = Tools.getPopupWindow(this@ChatActivity, arrayOf("旅友圈", "加为好友"), object : AdapterView.OnItemClickListener {
                override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (!isLogin()) {
                        showToast("请先登录")
                    } else {
                        when (position) {
                            0 -> nextActivity<SearchResultUserActivity>(
                                    "user_id" to intent.data.getQueryParameter("targetId")
                            )
                            1 -> addFriend()
                            else -> ""
                        }
                    }
                }

            }, "right")
            pop.showAsDropDown(mActionBarView.iv_right, 20, 0)
        }
    }

    override fun initView() {
        EventBus.getDefault().register(this)
    }

    fun playAnimation() {
        /**
         * 添加烟花曾
         */
        yanhua = FireWorkView(this@ChatActivity, R.drawable.icon1)
        val params = FrameLayout.LayoutParams(-1, -1)
        (window.decorView as ViewGroup).addView(yanhua, params)
        yanhua.playAnim()
    }

    override fun getData() {
    }

    override fun initListener() {
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.conversation) as ConversationFragment
        if (!fragment.onBackPressed()) {
            finish()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 添加好友
     */
    fun addFriend() {
        showWaitDialog("添加中...")
        post(Urls.URL_ADD_FRIEND, hashMapOf("suser_id" to intent.data.getQueryParameter("targetId")), object : GsonCallback<ObjectBaseEntity<UserInfo>>() {
            override fun onError(call: Call?, e: Exception?, id: Int) {
                showToast("添加失败")
                hideWaitDialog()
            }

            override fun onResponse(response: ObjectBaseEntity<UserInfo>, id: Int) {
                hideWaitDialog()
                if (response.success() && response.data != null) {
                    showToast("添加成功")
                } else {
                    showToast(response.message)
                }
            }

        })
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        hand.removeCallbacks(run)
        super.onDestroy()
    }

    val hand = Handler()
    val run = Runnable {
        yanhua.stopAnim()
        (window.decorView as ViewGroup).removeView(yanhua)
    }

    @Subscribe
    fun onEventPlay(event: EventPlayYanHua) {
        if (event.play) {
            playAnimation()
            hand.postDelayed(run, 6000)
        } else {
            yanhua.stopAnim()
            (window.decorView as ViewGroup).removeView(yanhua)
        }
    }
}
