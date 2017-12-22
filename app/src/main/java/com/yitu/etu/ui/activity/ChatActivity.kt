package com.yitu.etu.ui.activity

import android.view.KeyEvent
import com.yitu.etu.R
import io.rong.imkit.fragment.ConversationFragment



class ChatActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_chat

    override fun initActionBar() {
        title=intent.data.getQueryParameter("title")
    }

    override fun initView() {
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
}
