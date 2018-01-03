package com.yitu.etu.ui.fragment

import android.net.Uri
import com.huizhuang.zxsq.utils.nextCheckLoginActivity
import com.yitu.etu.EtuApplication
import com.yitu.etu.R
import com.yitu.etu.ui.activity.CircleFirendActivity
import io.rong.imkit.fragment.ConversationListFragment
import io.rong.imlib.model.Conversation
import kotlinx.android.synthetic.main.ly_head.*
import org.jetbrains.anko.bundleOf

/**
 * @className:LYFragment
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月09日 17:23
 */
class LYFragment : BaseFragment() {
    var type = 0//0不带分享，1带分享
    override fun getLayout(): Int = R.layout.fragment_ly_layout

    companion object {
        fun getInstance(): LYFragment {
            val ly = LYFragment()
            return ly
        }

        fun getInstance(type: Int): LYFragment {
            val ly = LYFragment()
            ly.arguments = bundleOf("type" to type)
            return ly
        }

    }

    override fun initView() {
        if(arguments!=null) {
            type = arguments.getInt("type")
        }
        addFragment()
    }

    private fun addFragment() {
        val fragment = ConversationListFragment()
        val uri = Uri.parse("rong://" + EtuApplication.getInstance().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.PUSH_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.CUSTOMER_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.CHATROOM.getName(), "false")
                .build()
        fragment.uri = uri

        childFragmentManager.beginTransaction().replace(R.id.rl_content, fragment, "lyfragment").commitAllowingStateLoss()
    }

    override fun getData() {

    }

    override fun initListener() {
        ll_ly.setOnClickListener {
            nextCheckLoginActivity<CircleFirendActivity>()
        }
    }
}
