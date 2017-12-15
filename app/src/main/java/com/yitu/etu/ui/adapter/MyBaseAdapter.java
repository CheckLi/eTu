package com.yitu.etu.ui.adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class MyBaseAdapter<T> extends BaseAdapter {

	protected List<T> data;
	private boolean isAnimation = true;

	public MyBaseAdapter(Context context, List<T> data) {
		this.data = data == null ? new ArrayList<T>() : data;
	}

	public MyBaseAdapter( List<T> data) {
		this.data = data == null ? new ArrayList<T>() : data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		if (position >= data.size())
			return null;
		return data.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	public void closeAnimation() {
		isAnimation = false;
	}
	/**
	 * 该方法需要子类实现，需要返回item布局的resource id
	 *
	 * @return
	 */
	public abstract int getItemResource(int pos);

	/**
	 * 使用该getItemView方法替换原来的getView方法，需要子类实现
	 *
	 * @param position
	 * @param parent
	 * @param holder
	 * @return
	 */
	public abstract View getItemView(int position, View convertView, ViewHolder holder,ViewGroup parent);

	@SuppressWarnings("unchecked")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (null == convertView) {
			convertView = View.inflate(parent.getContext(), getItemResource(position), null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		return getItemView(position, convertView, holder,parent);
	}

	public void addAll(List<T> elem) {
		data.addAll(elem);
		notifyDataSetChanged();
	}
	public void remove(T elem) {
		data.remove(elem);
		notifyDataSetChanged();
	}

	public void remove(int index) {
		data.remove(index);
		notifyDataSetChanged();
	}

	public void replaceAll(List<T> elem) {
		data.clear();
		data.addAll(elem);
		notifyDataSetChanged();
	}
}