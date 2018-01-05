package com.yitu.etu.ui.activity

import com.yitu.etu.R
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Http
import com.yitu.etu.tools.Urls
import com.yitu.etu.ui.fragment.Chat.mTargetId
import com.yitu.etu.ui.fragment.Chat.sendMessage
import com.yitu.etu.ui.fragment.RealTimeMapFragment
import com.yitu.etu.util.Empty
import com.yitu.etu.widget.chat.RealTimeLocationEndMessage
import io.rong.imlib.model.Conversation
import io.rong.imlib.model.Message
import kotlinx.android.synthetic.main.activity_amap_real_time.*
import okhttp3.Call
import org.jetbrains.anko.bundleOf
import java.lang.Exception


class AMapRealTimeActivity : BaseActivity() {
    var type: Message.MessageDirection? = null
    var chatId: String? = null
    override fun getLayout(): Int = R.layout.activity_amap_real_time

    override fun initActionBar() {
        setRightClick(R.drawable.icon146, resources.getColor(R.color.white)) {
            finish()
        }
    }

    override fun initView() {
        type = intent.getSerializableExtra("type") as Message.MessageDirection?
        chatId = intent.getStringExtra("chat_id")
        val fragment = RealTimeMapFragment()
        fragment.arguments = bundleOf("chat_id" to chatId)
        supportFragmentManager.beginTransaction().replace(content.id, fragment).commitAllowingStateLoss()
    }

    override fun getData() {

    }

    override fun initListener() {

    }

    override fun onDestroy() {
        if (type == Message.MessageDirection.SEND) {
            val message1 = RealTimeLocationEndMessage.obtain("位置共享结束")
            val message = Message.obtain(mTargetId, Conversation.ConversationType.PRIVATE, message1)
            sendMessage(message)
            cancel(Urls.URL_CANCEL_LOCATION, hashMapOf("chat_id" to chatId.Empty()))
        } else {
            cancel(Urls.URL_CANCEL_LOCATION, hashMapOf("chat_id" to chatId.Empty()))
        }
        super.onDestroy()
    }

    private fun cancel(url: String, params: HashMap<String, String>) {
        Http.post(url, params, object : GsonCallback<ObjectBaseEntity<Any>>() {
            override fun onResponse(response: ObjectBaseEntity<Any>, id: Int) {

            }

            override fun onError(call: Call?, e: Exception?, id: Int) {

            }

        })
    }
}