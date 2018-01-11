package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.yitu.etu.Iinterface.ImageSelectListener;
import com.yitu.etu.R;
import com.yitu.etu.util.FileUtil;
import com.yitu.etu.util.Tools;
import com.yitu.etu.util.imageLoad.ImageLoadUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/21.
 */
public class ReleaseMyTravelsInputAdapter extends BaseAdapter<String, ReleaseMyTravelsInputAdapter.ViewHolder> {
    ImageSelectListener mListener;
    private Map<String, String> images;
    private boolean isShowAdd = true;

    ArrayList<String> str = new ArrayList<>();

    public ReleaseMyTravelsInputAdapter(Context context) {
        super(context);
        images = new HashMap<>();
        str.add("");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReleaseMyTravelsInputAdapter.ViewHolder viewHolder = onCreateViewHolder(position);
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
        return (data == null || data.size() == 0) ? 1 : super.getCount();
    }

    @Override
    public void addData(String data) {
        super.addData(data);
        str.add("");
    }

    @Override
    public void initItemView(int position, View convertView, ViewHolder viewHolder) {
        viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
        viewHolder.text = (EditText) convertView.findViewById(R.id.text);
    }

    @Override
    public void bindData(final int position, View convertView, final ViewHolder viewHolder) {
//        if (isShowAdd && position == getCount() - 1) {
//            viewHolder.imageView.setImageResource(R.drawable.icon57);
//            viewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            int px = Tools.dp2px(getContext(), 40);
//            convertView.setPadding(px, px, px, px);
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mListener != null) {
//                        mListener.select(position);
//                    }
//                }
//            });
//
//        } else {
//            convertView.setPadding(0, 0, 0, 0);
        int px = Tools.dp2px(getContext(), 0.5f);
        convertView.setPadding(px, px, px, px);
        if (data == null || data.size() == 0) {
            viewHolder.imageView.setVisibility(View.INVISIBLE);
        }
        else{
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

        }
        if(position==0){
            viewHolder.text.setHint("游记内容");
        }
        else{
            viewHolder.text.setHint("");
        }
        viewHolder.text.setText(str.get(position));
        viewHolder.text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                str.set(position, s.toString());
            }
        });
     //        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mListener != null) {
//                    mListener.select(position);
//                }
//            }
//        });
//        }
//        ((ImageView)convertView).setImageResource();
    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        ImageView imageView;
        EditText text;

        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_choice_img_2;
        }
    }

    public String getPutString() {
        StringBuffer buffer = new StringBuffer("");
        StringBuffer buffer2 = new StringBuffer("");
        if (data == null || data.size() == 0) {
            buffer.append(str.get(0));
            buffer2.append(str.get(0));
        } else {
            for (int i = 0; i < data.size(); i++) {
                String s = data.get(i);
                if (s.contains("https:") || s.contains("http:")) {
                    if (images.containsKey(s)) {
                        buffer.append(String.format(str.get(i) + "<img width=\"%s\" src=\"%s\" />","100%", images.get(s)));
                    }
                } else {
                    buffer.append(String.format(str.get(i) + "<img width=\"%s\" src=\"%s\"/>","100%", FileUtil.GetImageStr(s),"100%"));

                    buffer2.append(String.format(str.get(i) + "<img width=\"%s\" src=\"%s\"/>","100%", s));
                }
            }
        }
        Log.e("aa:",buffer2.toString());
//        String.format(str.get(0)+"<img  src=\"%s\"/>", image_select.getImagePutString())
        return buffer.length() > 0 ? buffer.toString() : "";
    }

    public void showAdd(boolean isShow) {
        isShowAdd = isShow;
        notifyDataSetChanged();
    }

}
