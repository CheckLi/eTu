package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.yitu.etu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/24.
 */
public class PoiSearchAdapter extends BaseAdapter<PoiItem, PoiSearchAdapter.ViewHolder> {
    boolean isEmpty = true;

    public PoiSearchAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(int position) {
        return new ViewHolder();
    }

    @Override
    public PoiItem getItem(int position) {
        return isEmpty?null:super.getItem(position);
    }

    @Override
    public void initItemView(int position, View convertView, ViewHolder viewHolder) {
        viewHolder.text = (TextView) convertView.findViewById(R.id.text);
    }

    @Override
    public int getCount() {
        return isEmpty ? 20 : super.getCount();
    }

    @Override
    public void addData(List<PoiItem> data) {
        if (this.data != null) {
            this.data.clear();
        }
        if (data == null || data.size() == 0) {
            isEmpty = true;
            data=new ArrayList<>();
        }
        else{
            isEmpty = false;
        }
        super.addData(data);
    }


    @Override
    public void bindData(int position, View convertView, ViewHolder viewHolder) {
        if (getItem(position)==null) {
            viewHolder.text.setText("     ");
        } else {
            viewHolder.text.setText(getItem(position).getSnippet());
        }
    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        TextView text;

        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_choice_area;
        }
    }
}
