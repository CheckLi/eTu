package com.yitu.etu.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public abstract class MyBaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
	protected List<T> data;
	protected T info;
	private int lastPosition = -1;
	private boolean isAnimation = true;

	public MyBaseRecyclerAdapter( List<T> data) {
		// TODO Auto-generated constructor stub
		this.data = data == null ? new ArrayList<T>() : data;
	}
	public MyBaseRecyclerAdapter( T data) {
		// TODO Auto-generated constructor stub
		this.info=data;
	}
	public void closeAnimation() {
		isAnimation = false;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	/**
	 * 使用该getItemView方法替换原来的getView方法，需要子类实现
	 *
	 * @param position
	 * @param holder
	 * @return
	 */
	public abstract void getItemView(int position, RecyclerViewHolder holder);

	public abstract int getItemResource(int type);

	public int getItemTypeCount() {
		return 1;
	};

	@Override
	public void onBindViewHolder(RecyclerViewHolder arg0, int position) {
		// TODO Auto-generated method stub
		getItemView(position, arg0);
	}

	@Override
	public RecyclerViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		if (getItemTypeCount() <= 1) {
			return new RecyclerViewHolder(View.inflate(arg0.getContext(), getItemResource(arg1), null));
		} else {
			return new RecyclerViewHolder(View.inflate(arg0.getContext(), arg1, null));
		}
	}

	public void add(T elem) {
		data.add(elem);
		notifyDataSetChanged();
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

	public void replace(int position, T elem) {
		data.set(position, elem);
		notifyDataSetChanged();
	}

	public void replaceAll(List<T> elem) {
		data.clear();
		data.addAll(elem);
		notifyDataSetChanged();
	}

	@Override
	public void onViewDetachedFromWindow(RecyclerViewHolder holder) {
		// TODO Auto-generated method stub
		super.onViewDetachedFromWindow(holder);
		holder.itemView.clearAnimation();
	}
}
