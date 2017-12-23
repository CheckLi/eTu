package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, E extends BaseAdapter.abstractViewHodler> extends android.widget.BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    public List<T> data;
    boolean showNoDataItem = false;//没有数据时是否显示没有数据的item
    int NODATA = -1;//vieWtype 没有数据
    public BaseAdapter(Context context) {
        this.context = context;
    }
    public BaseAdapter(Context context,
                       List<T> data) {
        this(context);
        this.data = data;
    }
    public Context getContext() {
        return context;
    }

    public LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(context);
    }

    public List<T> getData() {
        return data;
    }


    public void clearAll() {
        if (this.data == null) {
            return;
        } else {
            data.clear();
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> data) {
        if (data == null) {
            return;
        }
        initData();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void addToFirst(T data) {
        if (data == null) {
            return;
        }
        initData();
        this.data.add(0, data);
        notifyDataSetChanged();
    }
    public  void initData(){
        if (this.data == null) {
            this.data = new ArrayList<T>();
        }
    }

    public void removeByPosition(int position) {
        if (position < getCount()) {
            data.remove(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public T getItem(int position) {
        if (position < getCount()) {
            return data.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public int getCount() {
        int count = data == null ? 0 : data.size();
        if (showNoDataItem && count == 0) {
            count = 1;
        }
        return count;
    }

    public void isShowNoDataItem(boolean showNoDateItem) {
        this.showNoDataItem = showNoDateItem;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        int viewType = 1;
        int count =(data == null ? 0 : data.size());
        if (showNoDataItem && count == 0) {
            viewType = NODATA;
        }
        return viewType;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        E viewHolder;
        if(convertView==null){
            viewHolder=onCreateViewHolder(position);
            convertView=getLayoutInflater().inflate(viewHolder.getItemLayoutID(getItemViewType(position)),null);
            initItemView(position,convertView,viewHolder);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (E)convertView.getTag();
        }
        bindData(position,convertView,viewHolder);
        return convertView;
    }
    //创建ViewHolder
    abstract public E onCreateViewHolder(int position);

    //初始化view
    abstract public void initItemView(int position,View convertView,E e);

    //绑定数据
    abstract public void bindData(int position,View convertView,E viewHolder);

    abstract  class abstractViewHodler {
        abstract int  getItemLayoutID(int  type);
    }
}

