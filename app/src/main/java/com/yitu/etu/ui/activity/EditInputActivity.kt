package com.yitu.etu.ui.activity

import android.app.Activity
import android.content.Intent
import com.yitu.etu.R
import com.yitu.etu.entity.AppConstant
import kotlinx.android.synthetic.main.activity_edit_input.*

class EditInputActivity : BaseActivity() {
    lateinit var name: String
    override fun getLayout(): Int = R.layout.activity_edit_input

    override fun getIntentExtra(intent: Intent?) {
        name = intent?.getStringExtra(AppConstant.PARAM_RESULT_STRING) ?: ""
    }

    override fun initActionBar() {
        title=intent?.getStringExtra(AppConstant.PARAM_TITLE)?:"修改"
        setRightText("保存") {
            if (name != et_chang.text.toString()) {
                name = et_chang.text.toString()
                val intent = Intent()
                intent.putExtra(AppConstant.PARAM_RESULT_STRING, name)
                setResult(Activity.RESULT_OK,intent)
            }
            finish()
        }
    }

    override fun initView() {
        et_chang.hint = "修改$title"
        et_chang.setText(name)
        et_chang.setSelection(0,name.length)
    }

    override fun getData() {

    }

    override fun initListener() {

    }

}
