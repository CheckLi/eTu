package com.yitu.etu.ui.activity

import android.content.Context
import android.view.View
import com.yitu.etu.R
import com.yitu.etu.dialog.TipsDialog
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.post
import io.rong.imkit.RongIM
import io.rong.imkit.model.UIConversation
import io.rong.imlib.model.Conversation
import okhttp3.Call
import java.lang.Exception

class SubConversationListFragmentActivity : BaseActivity() {

    override fun getLayout(): Int =R.layout.activity_sub_conversation_list_fragment

    override fun initActionBar() {
        title="系统消息"
    }

    override fun initView() {

    }

    override fun getData() {

    }

    override fun onDestroy() {
        RongIM.setConversationListBehaviorListener(null)
        super.onDestroy()
    }

    override fun initListener() {
        RongIM.setConversationListBehaviorListener(listener)
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
            val dialog=TipsDialog(this@SubConversationListFragmentActivity,"添加好友")
            dialog.setMessage("是否接受${p2?.uiConversationTitle}的好友请求？")
            dialog.setLeftBtn("拒绝"){
                noFriend(p2?.conversationSenderId?:"",dialog,p2?.conversationType,p2?.conversationTargetId)
            }
            dialog.setRightBtn("接受"){
                agreeFriend(p2?.conversationSenderId?:"",dialog,p2?.conversationType,p2?.conversationTargetId)
            }
            dialog.showDialog()
            return true
        }

    }

    /**
     * 接收好友请求
     */
    fun agreeFriend(userid:String,dialog: TipsDialog,type:Conversation.ConversationType?,targetId:String?){
        showWaitDialog("接受中...")
        post(Urls.URL_AGREEFIEND, hashMapOf("user_id" to userid,"status" to "1"), object : GsonCallback<ObjectBaseEntity<Any>>() {
            override fun onError(call: Call?, e: Exception?, id: Int) {
                showToast("添加失败")
                hideWaitDialog()
            }

            override fun onResponse(response: ObjectBaseEntity<Any>, id: Int) {
                hideWaitDialog()
                if(response.success()){
                    showToast("添加成功")
                    dialog.dismiss()
                    RongIM.getInstance().removeConversation(type,targetId,null)
                }else{
                    showToast(response.message)
                }
            }

        })
    }

    /**
     * 拒绝好友请求
     */
    fun noFriend(userid:String,dialog: TipsDialog,type:Conversation.ConversationType?,targetId:String?){
        showWaitDialog("拒绝中...")
        post(Urls.URL_AGREEFIEND, hashMapOf("user_id" to userid,"status" to "-1"), object : GsonCallback<ObjectBaseEntity<Any>>() {
            override fun onError(call: Call?, e: Exception?, id: Int) {
                showToast("拒绝失败")
                hideWaitDialog()
            }

            override fun onResponse(response: ObjectBaseEntity<Any>, id: Int) {
                hideWaitDialog()
                if(response.success()){
                    showToast("已拒绝")
                    dialog.dismiss()
                    RongIM.getInstance().removeConversation(type,targetId,null)
                }else{
                    showToast(response.message)
                }
            }

        })
    }
}

