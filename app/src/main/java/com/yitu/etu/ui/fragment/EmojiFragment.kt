package com.yitu.etu.ui.fragment

import android.widget.AdapterView
import com.yitu.etu.R
import com.yitu.etu.entity.EmojiChildBean
import com.yitu.etu.ui.adapter.EmojiAdapter
import kotlinx.android.synthetic.main.emoji_fragment_layout.*
import org.jetbrains.anko.bundleOf

/**
 * @className:EmojiFragment
 * @description:
 * @author: JIAMING.LI
 * @date:2018年01月07日 17:13
 */
class EmojiFragment : BaseFragment() {
    var data: EmojiChildBean? = null

    companion object {
        @JvmStatic
        fun getInstance(data: EmojiChildBean): EmojiFragment {
            val fragment = EmojiFragment()
            fragment.arguments = bundleOf("list" to data)
            return fragment
        }
    }

    override fun getLayout(): Int = R.layout.emoji_fragment_layout

    override fun initView() {
        data = arguments.getSerializable("list") as EmojiChildBean
        if (data != null) {
            gridView.adapter = EmojiAdapter(data?.list ?: mutableListOf())
        }
    }

    override fun getData() {

    }

    override fun initListener() {
        gridView.setOnItemClickListener { parent, view, position, id ->
            listener?.onItemClick(parent, view, position, id)
        }
    }

    var listener: AdapterView.OnItemClickListener? = null
    fun setOnItemClickListener(listener: (position: String) -> Unit) {
        this.listener = AdapterView.OnItemClickListener { parent, view, position, id ->
            listener(parent.adapter.getItem(position).toString())
        }

    }
}
