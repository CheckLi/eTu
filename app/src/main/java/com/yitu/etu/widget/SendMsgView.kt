package com.yitu.etu.widget

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import com.yitu.etu.R
import com.yitu.etu.entity.EmojiChildBean
import com.yitu.etu.ui.fragment.EmojiFragment
import com.yitu.etu.util.JsonUtil
import com.yitu.etu.util.TextUtils
import com.yitu.etu.util.ToastUtil
import kotlinx.android.synthetic.main.emoji_layout.view.*
import kotlinx.android.synthetic.main.layout_send_msg.view.*
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

/**
 *
 *
 * Created by deng meng on 2017/12/27.
 */
class SendMsgView : FrameLayout {
    var isEmoji = false
    var emojiView: View? = null
    var viewpager:ViewPager?=null
    internal var sendMsg: SendMsgListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private lateinit var editText: EditText

    internal fun init() {
        val v = LayoutInflater.from(context).inflate(R.layout.layout_send_msg, null)
        addView(v)
        editText = v.findViewById(R.id.text) as EditText
        editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if (sendMsg != null) {
                    val text = v.text.toString()
                    if (text == "") {
                        ToastUtil.showMessage("请输入文字")
                    } else {
                        v.text = ""
                        sendMsg!!.send(text)

                    }
                }
            }
            false
        }

        iv_emoji.setOnClickListener {
            isEmoji = !isEmoji
            iv_emoji.isSelected = isEmoji
            if (emojiView == null) {
                emojiView = view_emoji_stub.inflate()
                viewpager=emojiView?.findViewById(R.id.viewpager) as ViewPager
                initData()
            }
            if (isEmoji) {
                emojiView?.visibility = View.VISIBLE
            } else {
                emojiView?.visibility = View.GONE
            }
        }
    }

    private fun initData() {
        val jsonString = readAssetsTxt(context, "emoji.json")
        if (!TextUtils.isEmpty(jsonString)) {
            val json = JSONObject(jsonString)
            val it = json.keys()
            val list = mutableListOf<EmojiChildBean>()
            while (it.hasNext()) {

                val key = it.next() as String
                val array = JsonUtil.getInstance().fromJsonList(json.optString(key), String::class.java)
                addBean(key, "people", "人物", R.id.rb_emoji_rw, array, list)
                addBean(key, "flower", "自然", R.id.rb_emoji_zr, array, list)
                addBean(key, "bell", "日常", R.id.rb_emoji_rc, array, list)
                addBean(key, "vehicle", "交通", R.id.rb_emoji_jt, array, list)
                addBean(key, "number", "符号", R.id.rb_emoji_fh, array, list)
            }
            val context=context
            if(context is AppCompatActivity){
                val activity=context
                viewpager?.adapter = MyPagerAdapter(editText,list, activity.supportFragmentManager)
                viewpager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {

                    }

                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                    }

                    override fun onPageSelected(position: Int) {
                        tv_title.text = viewpager?.adapter?.getPageTitle(position)
                        rg_emoji.check(list[position].id)
                    }

                })
            }

        }

    }

    private fun addBean(key: String, toKey: String, name: String, id: Int, array: ArrayList<String>, list: MutableList<EmojiChildBean>) {
        if (key == toKey) {
            val size = array.size
            for (i in 0 until size step 30) {
                val bean = EmojiChildBean()
                bean.name = name
                bean.id = id
                if (i + 30 <= size) {
                    bean.list = array.subList(i, i + 30)
                } else {
                    bean.list = array.subList(i, size)
                }
                list.add(bean)
            }

        }
    }

    fun setSendMsg(sendMsg: SendMsgListener) {
        this.sendMsg = sendMsg
    }

    interface SendMsgListener {
        fun send(text: String)
    }


    /**
     * 读取assets下的txt文件，返回utf-8 String
     * @param context
     * @param fileName 不包括后缀
     * @return
     */
    fun readAssetsTxt(context: Context, fileName: String): String {
        try {
            //Return an AssetManager instance for your application's package
            val `is` = context.assets.open(fileName)
            val size = `is`.available()
            // Read the entire asset into a local byte buffer.
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            // Convert the buffer into a string.
            // Finally stick the string into the text view.
            return String(buffer, Charset.defaultCharset())
        } catch (e: IOException) {
            // Should never happen!
            //            throw new RuntimeException(e);
            e.printStackTrace()
        }

        return ""
    }
}

private class MyPagerAdapter(var editText: EditText, var list: MutableList<EmojiChildBean>, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return list[position].name
    }

    override fun getItem(position: Int): Fragment {
        val fragment = EmojiFragment.getInstance(list[position])
        fragment.setOnItemClickListener {
            editText.append(it)
        }
        return fragment
    }
}
