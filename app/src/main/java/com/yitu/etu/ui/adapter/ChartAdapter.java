package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.View;

import com.huizhuang.zxsq.widget.textview.SpanTextView;
import com.yitu.etu.R;

import java.util.List;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/21.
 */
public class ChartAdapter extends BaseAdapter<String, ChartAdapter.ViewHolder> {
    public ChartAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public ViewHolder onCreateViewHolder(int position) {
        return new ViewHolder();
    }

    @Override
    public void initItemView(int position, View convertView, ViewHolder viewHolder) {
        viewHolder.text = (SpanTextView) convertView.findViewById(R.id.text);
    }

    @Override
    public void bindData(int position, View convertView, ViewHolder viewHolder) {
        viewHolder.text.setSpanText("%1321321:%我们");
    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        SpanTextView text;

        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_chart;
        }
    }
}
