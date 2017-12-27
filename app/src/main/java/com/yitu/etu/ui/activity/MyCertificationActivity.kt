package com.yitu.etu.ui.activity

import android.widget.ImageView
import com.yitu.etu.R
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.RenZBean
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Http
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.FileUtil
import com.yitu.etu.util.addHost
import com.yitu.etu.util.imageLoad.ImageLoadUtil
import com.yitu.etu.util.post
import kotlinx.android.synthetic.main.activity_my_certification.*
import okhttp3.Call
import java.util.*
import kotlin.concurrent.thread

/**
 *
 * @className:认证界面
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月21日下午04:47:55
 */

class MyCertificationActivity : BaseActivity() {
    var position = 0
    var postData1 = ""
    var postData2 = ""
    var postData3 = ""
    override fun getLayout(): Int = R.layout.activity_my_certification
    override fun initActionBar() {
        title = "认证中心"
    }

    override fun initView() {
    }

    override fun getData() {
        showWaitDialog("获取认证信息...")
        post(Urls.getRenz, hashMapOf(), object : GsonCallback<ObjectBaseEntity<RenZBean>>() {
            override fun onResponse(response: ObjectBaseEntity<RenZBean>, id: Int) {
                hideWaitDialog()
                if (response.success()) {
                    with(response.data) {
                        ImageLoadUtil.getInstance().loadImage(iv_id_card_front, sfzzm.addHost(), 350, 350)
                        ImageLoadUtil.getInstance().loadImage(iv_id_card_verso, sfzfm.addHost(), 350, 350)
                        ImageLoadUtil.getInstance().loadImage(iv_id_card_hand, czz.addHost(), 350, 350)
                        showToast(when (status) {
                            -1 -> "审核未通过"
                            0 -> "等待审核"
                            else -> "审核通过"
                        })
                    }
                } else {
                    showToast(response.message)
                    setListener()
                }

            }

            override fun onError(call: Call?, e: java.lang.Exception?, id: Int) {
                hideWaitDialog()
                showToast("请上传认证信息")
                setListener()
            }

        })
    }

    fun setListener() {
        /**
         * 身份证正面照
         */
        fl_card_front.setOnClickListener {
            position = 0
            Single(false)
        }

        /**
         * 身份证反面照
         */
        fl_card_verso.setOnClickListener {
            position = 1
            Single(false)
        }

        /**
         * 身份证手持照
         */
        fl_card_hand.setOnClickListener {
            position = 2
            Single(false)
        }
        setRightText("完成") {
            putInfo()
        }
    }

    override fun initListener() {

    }

    override fun selectSuccess(path: String?) {
        path?.run {
            init(this)
        }
    }

    override fun selectSuccess(pathList: MutableList<String>?) {
        pathList?.forEach {
            init(it)
        }
    }

    fun init(path: String) {
        when (position) {
            0 -> {
                initDisplay(iv_id_card_front, path)
                postData1 = FileUtil.GetImageStr(path)
            }
            1 -> {
                initDisplay(iv_id_card_verso, path)
                postData2 = FileUtil.GetImageStr(path)
            }
            2 -> {
                initDisplay(iv_id_card_hand, path)
                postData3 = FileUtil.GetImageStr(path)
            }
        }

    }

    private fun initDisplay(image: ImageView, path: String) {
        ImageLoadUtil.getInstance().loadImage(image, path, 350, 350)
    }

    fun putInfo() {
        if (postData1.isNullOrBlank()) {
            showToast("请上传身份证正面照")
            return
        }
        if (postData2.isNullOrBlank()) {
            showToast("请上传身份证反面照")
            return
        }
        if (postData3.isNullOrBlank()) {
            showToast("请上传身份证手持照")
            return
        }
        val params = HashMap<String, String>()
        params.put("sfzzm", postData1)
        params.put("sfzfm", postData2)
        params.put("czz", postData3)
        showWaitDialog("上传中...")
        thread {
            Http.post(Urls.putRenz, params, object : GsonCallback<ObjectBaseEntity<Any>>() {
                override fun onError(call: Call, e: Exception, id: Int) {
                    showToast("上传失败")
                    hideWaitDialog()
                }

                override fun onResponse(response: ObjectBaseEntity<Any>, id: Int) {
                    hideWaitDialog()
                    if (response.success()) {
                        showToast("上传成功")
                    } else {
                        showToast(response.message)
                    }
                }
            })
        }

    }
}
