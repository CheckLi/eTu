package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yitu.etu.R;
import com.yitu.etu.entity.CommentEntity;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.util.DateUtil;
import com.yitu.etu.util.imageLoad.ImageLoadUtil;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/27.
 */
public class CommentAdapter extends BaseAdapter<CommentEntity,CommentAdapter.ViewHolder>{
    public CommentAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(int position) {
        return new ViewHolder();
    }

    @Override
    public void initItemView(int position, View convertView, ViewHolder viewHolder) {
        viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        viewHolder.text = (TextView) convertView.findViewById(R.id.text);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.image);

    }

    @Override
    public void bindData(int position, View convertView, ViewHolder viewHolder) {
        CommentEntity data= getItem(position);
        viewHolder.tv_name.setText(data.getUser().getName());
        viewHolder.tv_time.setText(DateUtil.getTime(data.getText(),"yyyy-MM-dd HH:mm"));
        viewHolder.text.setText(data.getText());
        ImageLoadUtil.getInstance().loadImage(viewHolder.image, Urls.address + data.getUser().getHeader(), R.drawable.default_head, 200, 200);
    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        ImageView image;
        TextView tv_name, tv_time, text;

        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_comment;
        }
    }
}
