package sjz.feicui.contacts.adapter;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.adapter.AppInfoAdapter.ViewHolder;
import sjz.feicui.contacts.base.adapter.BaseBaseAdapter;
import sjz.feicui.contacts.entity.PhoneInfo;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.TextView;

public class PhoneCkeckAdapter extends BaseBaseAdapter<PhoneInfo> {
	public PhoneCkeckAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		viewHolder vh = null;
		if (convertView == null) {
			vh = new viewHolder();
			convertView = mInflater.inflate(R.layout.activity_phonecheck_list,null);
			vh.iv = (ImageView) convertView.findViewById(R.id.phone_ckeck_iv);
			vh.tv1 = (TextView) convertView.findViewById(R.id.phone_ckeck_tv1);
			vh.tv2 = (TextView) convertView.findViewById(R.id.phone_ckeck_tv2);
			convertView.setTag(vh);
		}else {
			vh = (viewHolder) convertView.getTag();
		}
		vh.iv.setImageDrawable(infos.get(position).getImage());
		vh.tv1.setText(infos.get(position).getTitle());
		vh.tv2.setText(infos.get(position).getText());
		return convertView;
	}
	class viewHolder{
		private ImageView iv;
		private TextView tv1,tv2;
	}
}
