package com.yitu.etu.ui.activity

import android.content.Context
import android.view.View
import android.widget.AdapterView
import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.R
import com.yitu.etu.dialog.TipsDialog
import com.yitu.etu.ui.fragment.LYFragment
import com.yitu.etu.util.Tools
import com.yitu.etu.util.activityUtil
import com.yitu.etu.util.isLogin
import com.yitu.etu.widget.chat.ShareMessage
import io.rong.imkit.RongIM
import io.rong.imkit.model.UIConversation
import io.rong.imlib.model.Conversation
import io.rong.message.ImageMessage
import kotlinx.android.synthetic.main.actionbar_layout.view.*
import kotlinx.android.synthetic.main.activity_share_friend.*
import org.jetbrains.anko.bundleOf


class ShareFriendActivity : BaseActivity() {
    var message: ShareMessage? = null
    var messageImage: ImageMessage? = null

    companion object {
        @JvmStatic
        fun startActivity(context: Context, message: ShareMessage) {
            activityUtil.nextActivity(context, ShareFriendActivity::class.java, bundleOf("message" to message), false)
        }
        @JvmStatic
        fun startActivity(context: Context, message: ImageMessage) {
            activityUtil.nextActivity(context, ShareFriendActivity::class.java, bundleOf("messageImage" to message), false)
        }
    }

    override fun getLayout(): Int = R.layout.activity_share_friend

    override fun initActionBar() {
        mActionBarView.setRightImage(R.drawable.icon56) {
            val pop = Tools.getPopupWindow(this@ShareFriendActivity, arrayOf("添加好友", "好友列表", "发起群聊"), object : AdapterView.OnItemClickListener {
                override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (!isLogin()) {
                        showToast("请先登录")
                    } else {
                        when (position) {
                            0 -> nextActivity<SearchFriendActivity>()
                            1 -> nextActivity<FriendListActivity>()
                            2 -> Tools.createChagGroup(this@ShareFriendActivity)
                            else -> ""
                        }
                    }
                }

            }, "right")
            pop.showAsDropDown(mActionBarView.iv_right, 20, 0)
        }
    }

    override fun initView() {
        message = intent.getParcelableExtra("message")
        messageImage = intent.getParcelableExtra("messageImage")
        supportFragmentManager.beginTransaction().replace(content.id, LYFragment.getInstance(1)).commitAllowingStateLoss()
    }

    override fun getData() {

    }

    override fun initListener() {
        /**
         * 设置会话列表界面操作的监听器。
         */
        RongIM.setConversationListBehaviorListener(listener)
    }

    override fun onDestroy() {
        RongIM.setConversationListBehaviorListener(null)
        super.onDestroy()
    }

    val listener=object : RongIM.ConversationListBehaviorListener {
        override fun onConversationPortraitClick(p0: Context?, p1: Conversation.ConversationType?, p2: String?): Boolean {
            return true
        }

        override fun onConversationPortraitLongClick(p0: Context?, p1: Conversation.ConversationType?, p2: String?): Boolean {
            return true
        }

        override fun onConversationLongClick(p0: Context?, p1: View?, p2: UIConversation?): Boolean {
            return true
        }

        override fun onConversationClick(p0: Context?, p1: View?, p2: UIConversation?): Boolean {
            val dialog = TipsDialog(this@ShareFriendActivity, "温馨提示")
            dialog.setMessage("是否分享到聊天")
            dialog.setRightBtn("确认") {
                if(messageImage!=null){
                    Tools.sendImageMessage(messageImage,p2?.conversationType,p2?.conversationTargetId,this@ShareFriendActivity)
                }else {
                    message?.userId = p2?.conversationTargetId
                    Tools.sendMessage(message, p2?.conversationType, this@ShareFriendActivity)
                }
                dialog.dismiss()
            }
            dialog.showDialog()
            return true
        }

    }
}
