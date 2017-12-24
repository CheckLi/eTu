package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.yitu.etu.Iinterface.ImageSelectListener;
import com.yitu.etu.R;
import com.yitu.etu.util.Tools;
import com.yitu.etu.util.imageLoad.ImageLoadUtil;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/21.
 */
public class ChooseImageAdapter extends BaseAdapter<String, ChooseImageAdapter.ViewHolder> {
    ImageSelectListener mListener;

    public ChooseImageAdapter(Context context) {
        super(context);
    }

    public ImageSelectListener getListener() {
        return mListener;
    }

    public void setListener(ImageSelectListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(int position) {
        return new ViewHolder();
    }

    @Override
    public int getCount() {
        return 1 + super.getCount();
    }

    @Override
    public void initItemView(int position, View convertView, ViewHolder viewHolder) {
        viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
    }

    @Override
    public void bindData(final int position, View convertView, ViewHolder viewHolder) {
        if (position == getCount() - 1) {
            viewHolder.imageView.setImageResource(R.drawable.icon57);
            viewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            int px = Tools.dp2px(getContext(), 40);
            convertView.setPadding(px, px, px, px);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.select(position);
                    }
                }
            });

        } else {
//            convertView.setPadding(0, 0, 0, 0);
            int px = Tools.dp2px(getContext(), 0.5f);
            convertView.setPadding(px, px, px, px);

            viewHolder.imageView.setImageResource(R.drawable.etu_default2);
            ImageLoadUtil.getInstance().loadImage(viewHolder.imageView, getItem(position), 100, 100);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.select(position);
                    }
                }
            });
        }
//        ((ImageView)convertView).setImageResource();
    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        ImageView imageView;

        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_choice_img;
        }
    }


}
