package sjz.feicui.contacts.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import sjz.feicui.contacts.R;
import sjz.feicui.contacts.adapter.AppInfoAdapter.ViewHolder;
import sjz.feicui.contacts.base.adapter.BaseBaseAdapter;
import sjz.feicui.contacts.base.utils.LogUtil;
import sjz.feicui.contacts.entity.ProgressInfo;

public class ProgressInfoAdapter extends BaseBaseAdapter<ProgressInfo> {

	public ProgressInfoAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getMyView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = mInflater.inflate(R.layout.activity_speedup_list_item,null);
			vh.checkBox = (CheckBox) convertView.findViewById(R.id.speedup_list_item_ch);
			vh.iv = (ImageView) convertView.findViewById(R.id.speedup_list_item_iv);
			vh.tv1 = (TextView) convertView.findViewById(R.id.speedup_list_item_tv1);
			vh.tv2 = (TextView) convertView.findViewById(R.id.speedup_list_item_tv2);
			vh.tv3 = (TextView) convertView.findViewById(R.id.speedup_list_item_tv3);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		vh.checkBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!infos.get(position).isChecked) {
					infos.get(position).isChecked = true;
				}else{
					infos.get(position).isChecked = false;
				}
				
			}
		});
		if (infos.get(position).isSystem) {
			vh.tv3.setVisibility(View.VISIBLE);
		}else{
			vh.tv3.setVisibility(View.INVISIBLE);
		}
		vh.checkBox.setChecked(infos.get(position).isChecked);
		vh.iv.setImageDrawable(infos.get(position).icon);
		vh.tv1.setText(infos.get(position).title);
		vh.tv2.setText("内存：" + infos.get(position).memory + "M");
		vh.tv3.setText("系统进程");
		return convertView;
	}
	class ViewHolder{
		CheckBox checkBox;
		ImageView iv;
		TextView tv1,tv2,tv3;
	}
}
