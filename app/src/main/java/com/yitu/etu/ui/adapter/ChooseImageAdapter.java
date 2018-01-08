package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.yitu.etu.Iinterface.ImageSelectListener;
import com.yitu.etu.R;
import com.yitu.etu.util.FileUtil;
import com.yitu.etu.util.Tools;
import com.yitu.etu.util.imageLoad.ImageLoadUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/21.
 */
public class ChooseImageAdapter extends BaseAdapter<String, ChooseImageAdapter.ViewHolder> {
    ImageSelectListener mListener;
    private Map<String, String> images;
    private boolean isShowAdd = true;

    public ChooseImageAdapter(Context context) {
        super(context);
        images = new HashMap<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChooseImageAdapter.ViewHolder viewHolder = onCreateViewHolder(position);
        convertView = getLayoutInflater().inflate(viewHolder.getItemLayoutID(getItemViewType(position)), null);
        initItemView(position, convertView, viewHolder);
        bindData(position, convertView, viewHolder);
        return convertView;
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
        return isShowAdd ? 1 + super.getCount() : super.getCount();
    }

    @Override
    public void initItemView(int position, View convertView, ViewHolder viewHolder) {
        viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
        viewHolder.btn_delete = (ImageView) convertView.findViewById(R.id.btn_delete);

    }
    SparseArray<String> delArray=new SparseArray<String>();

    @Override
    public void bindData(final int position, View convertView, final ViewHolder viewHolder) {
        if (isShowAdd && position == getCount() - 1) {
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
            viewHolder.btn_delete.setVisibility(View.GONE);
            convertView.setOnLongClickListener(null);

        } else {
//            convertView.setPadding(0, 0, 0, 0);
            int px = Tools.dp2px(getContext(), 0.5f);
            convertView.setPadding(px, px, px, px);

            viewHolder.imageView.setImageResource(R.drawable.etu_default);
            ImageLoadUtil.getInstance().loadImage(viewHolder.imageView, getItem(position), R.drawable.etu_default, 100, 100
                    , new Callback() {
                        @Override
                        public void onSuccess() {
                            if (getItem(position).contains("http:") || getItem(position).contains("https:")) {
                                if (!images.containsKey(getItem(position))) {
                                    images.put(getItem(position), FileUtil.GetImageStr(viewHolder.imageView.getDrawable()));
                                }
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.select(position);
                    }
                }
            });

                viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        images.remove(getItem(position));
                        removeByPosition(position);
                    }
                });
                if(delArray.indexOfKey(position)>-1){
                    viewHolder.btn_delete.setVisibility(View.VISIBLE);
                }

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    delArray.put(position,position+"");
                    notifyDataSetChanged();
                    return true;
                }
            });
        }
//        ((ImageView)convertView).setImageResource();
    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        ImageView imageView;
        ImageView btn_delete;

        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_choice_img;
        }
    }

    public String getPutString() {
        if (data != null) {
            StringBuffer buffer = new StringBuffer("");
            for (String s : data) {
                if (s.contains("https:") || s.contains("http:")) {
                    if (images.size() > 0 && images.containsKey(s)) {
                        buffer.append(images.get(s) + "|");
                    }
                } else {
                    buffer.append(FileUtil.GetImageStr(s) + "|");
                }
            }
            return buffer.length() > 0 ? buffer.toString().substring(0, buffer.length() - 1) : "";
        }
        return "";
    }

    public void showAdd(boolean isShow) {
        isShowAdd = isShow;
        notifyDataSetChanged();
    }

}
