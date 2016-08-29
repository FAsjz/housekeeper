package sjz.feicui.contacts.base.adapter;

import java.util.ArrayList;

import sjz.feicui.contacts.entity.ClassListInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BaseBaseAdapter<T> extends BaseAdapter {
	protected ArrayList<T> infos = new ArrayList<T>();
	protected Context mContext;
	protected LayoutInflater mInflater;
/**
 * 所有适配器的父类，提供删除添加数据的函数，子类必须重写getMyView函数
 * @param<T>   数据的类型
 * */
	public BaseBaseAdapter(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	// 添加数据到当前适配器集合
	public void addDataToAdapter(T e) {
		if (e != null) {
			infos.add(e);
		}
	}

	// 添加数据到当前适配器指定的位置
	public void addDataToAdapter(T e, int index) {
		if (e != null) {
			infos.add(index, e);
		}
	}

	// 添加数据到当前适配器中的集合
	public void addDataToAdapter(ArrayList<T> infos) {
		if (infos != null) {
			this.infos.addAll(infos);
		}
	}

	// 获取数据
	public ArrayList<T> getDataFromAdapter() {
		return infos;
	}

	// 删除当前适配器内容
	public void clearDataTOAdapter() {
		infos.clear();
	}

	@Override
	public int getCount() {
		return infos.size();
	}

	@Override
	public Object getItem(int position) {
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		return getMyView(position, convertView, parent);
	}

	public abstract View getMyView(int position, View convertView,
			ViewGroup parent);
}
