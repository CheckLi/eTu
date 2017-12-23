package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.View;

import com.yitu.etu.R;
import com.yitu.etu.entity.CircleFirendEntity;
import com.yitu.etu.util.Tools;
import com.yitu.etu.widget.MgridView;

import java.util.ArrayList;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/21.
 */
public class CircleFirendAdapter extends BaseAdapter<CircleFirendEntity, CircleFirendAdapter.ViewHolder> {
    public CircleFirendAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(int position) {
        return new ViewHolder();
    }

    @Override
    public void initItemView(int position, View convertView, ViewHolder viewHolder) {
        viewHolder.imageGridView = (MgridView) convertView.findViewById(R.id.gridView);
        viewHolder.chartGridView = (MgridView) convertView.findViewById(R.id.chartGridView);

    }

    @Override
    public void bindData(int position, View convertView, ViewHolder viewHolder) {
        ArrayList<String> imageUrl = new ArrayList<String>();
        imageUrl.add("dsads");
        imageUrl.add("dsads");
        imageUrl.add("dsads");
        imageUrl.add("dsads");
        imageUrl.add("dsads");
        imageUrl.add("dsads");
        imageUrl.add("dsads");
        if (imageUrl.size() > 0) {
            viewHolder.imageGridView.setPadding(0, Tools.dp2px(getContext(), 5), 0, 0);
            viewHolder.imageGridView.setAdapter(new ImageAdapter(getContext(), imageUrl));
        }
        ArrayList<String> imageUrl2 = new ArrayList<String>();
        imageUrl2.add("dsads");
        imageUrl2.add("dsads");
        viewHolder.chartGridView.setAdapter(new ChartAdapter(getContext(), imageUrl2));
    }

    @Override
    public int getCount() {
        return 10;
    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        MgridView imageGridView;
        MgridView chartGridView;

        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_circle_firend;
        }
    }
}
