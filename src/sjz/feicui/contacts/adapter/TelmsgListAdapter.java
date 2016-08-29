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
public class TelmsgListAdapter extends BaseBaseAdapter<ClassListInfo> {
	public TelmsgListAdapter(Context context) {
		super(context);
	}

	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = mInflater.inflate(R.layout.activity_telmsg_list_item,
					null);
			vh.tv1 = (TextView) convertView.findViewById(R.id.tv_name);
			vh.tv2 = (TextView) convertView.findViewById(R.id.tv_number);
			convertView.setTag(vh);

		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.tv1.setText(infos.get(position).name);
		vh.tv2.setText(infos.get(position).number);

		return convertView;
	}

	class ViewHolder {
		TextView tv1, tv2;
	}

}
