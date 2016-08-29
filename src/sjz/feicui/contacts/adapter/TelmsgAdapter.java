package sjz.feicui.contacts.adapter;

import java.util.ArrayList;
import java.util.List;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.base.adapter.BaseBaseAdapter;
import sjz.feicui.contacts.biz.TelDBReadManager;
import sjz.feicui.contacts.entity.ClassListInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * TelmsgActivity中ListView用于显示的适配器
 * */
public class TelmsgAdapter extends BaseBaseAdapter<ClassListInfo> {
	public TelmsgAdapter(Context context) {
		super(context);
	}

	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = mInflater
					.inflate(R.layout.activity_telmsg_item, null);
			vh.tv = (TextView) convertView.findViewById(R.id.textview);
			convertView.setTag(vh);

		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.tv.setText(infos.get(position).name);

		return convertView;
	}

	class ViewHolder {
		TextView tv;
	}

}
