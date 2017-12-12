package com.yitu.etu.ui.fragment

import android.net.Uri
import com.yitu.etu.EtuApplication
import com.yitu.etu.R
import io.rong.imkit.fragment.ConversationListFragment
import io.rong.imlib.model.Conversation
import kotlinx.android.synthetic.main.fragment_ly_layout.*

/**
 * @className:LYFragment
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月09日 17:23
 */
class LYFragment : BaseFragment() {
    override fun getLayout(): Int = R.layout.fragment_ly_layout

    companion object {
        fun getInstance(): LYFragment {
            val ly = LYFragment()
            return ly
        }

    }

    override fun initView() {
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
        childFragmentManager.beginTransaction().replace(rl_content.id, fragment, "lyfragment").commitAllowingStateLoss()
    }

    override fun getData() {

    }

    override fun initListener() {

    }
}
