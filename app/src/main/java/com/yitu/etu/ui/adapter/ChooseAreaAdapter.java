package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yitu.etu.R;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/24.
 */
public class ChooseAreaAdapter extends BaseAdapter<String,ChooseAreaAdapter.ViewHolder>{

    public ChooseAreaAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(int position) {
        return new ViewHolder();
    }

    @Override
    public void initItemView(int position, View convertView, ViewHolder viewHolder) {
        viewHolder.text=(TextView) convertView.findViewById(R.id.text);
    }

    @Override
    public void bindData(int position, View convertView, ViewHolder viewHolder) {
        viewHolder.text.setText("dsad"+position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        TextView text;

        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_choice_area;
        }
    }
}
