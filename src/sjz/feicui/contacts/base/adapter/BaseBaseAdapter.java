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
 * �����������ĸ��࣬�ṩɾ��������ݵĺ��������������дgetMyView����
 * @param<T>   ���ݵ�����
 * */
	public BaseBaseAdapter(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	// ������ݵ���ǰ����������
	public void addDataToAdapter(T e) {
		if (e != null) {
			infos.add(e);
		}
	}

	// ������ݵ���ǰ������ָ����λ��
	public void addDataToAdapter(T e, int index) {
		if (e != null) {
			infos.add(index, e);
		}
	}

	// ������ݵ���ǰ�������еļ���
	public void addDataToAdapter(ArrayList<T> infos) {
		if (infos != null) {
			this.infos.addAll(infos);
		}
	}

	// ��ȡ����
	public ArrayList<T> getDataFromAdapter() {
		return infos;
	}

	// ɾ����ǰ����������
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
