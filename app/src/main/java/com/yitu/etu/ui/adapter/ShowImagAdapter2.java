package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.yitu.etu.Iinterface.ImageSelectListener;
import com.yitu.etu.R;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.util.FileUtil;
import com.yitu.etu.util.Tools;
import com.yitu.etu.util.imageLoad.ImageLoadUtil;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/21.
 */
public class ShowImagAdapter2 extends BaseAdapter<String, ShowImagAdapter2.ViewHolder> {
    ImageSelectListener mListener;

    public ShowImagAdapter2(Context context) {
        super(context);
    }
    @Override
    public ViewHolder onCreateViewHolder(int position) {
        return new ViewHolder();
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public void initItemView(int position, View convertView, ViewHolder viewHolder) {
        viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
    }

    @Override
    public void bindData(final int position, View convertView, ViewHolder viewHolder) {
        int px = Tools.dp2px(getContext(), 0.5f);
        convertView.setPadding(px, px, px, px);
        viewHolder.imageView.setImageResource(R.drawable.etu_default);
        ImageLoadUtil.getInstance().loadImage(viewHolder.imageView, Urls.address+getItem(position), 100, 100);
    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        ImageView imageView;

        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_choice_img;
        }
    }

    public String getPutString() {
        StringBuffer buffer = new StringBuffer("");
        for (String s : data) {
            buffer.append(FileUtil.GetImageStr(s) + "|");
        }
        return buffer.length() > 0 ? buffer.toString().substring(0, buffer.length() - 1) : "";
    }
}
