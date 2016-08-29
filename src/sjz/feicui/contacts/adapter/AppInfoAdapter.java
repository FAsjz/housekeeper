package sjz.feicui.contacts.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.base.adapter.BaseBaseAdapter;
import sjz.feicui.contacts.entity.AppInfo;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

/***
 * 
 * 应用程序列表适配器
 * 
 * @author Administrator
 * 
 */
public class AppInfoAdapter extends BaseBaseAdapter<AppInfo> {
//	private ArrayList<Integer> positions = new ArrayList<Integer>();
//	private HashMap<Integer, Boolean> state = new HashMap<Integer,Boolean>();
	private ViewHolder vh;
	
	public AppInfoAdapter(Context context) {
		super(context);
		
	}

	@Override
	public View getMyView(final int position, View convertView, ViewGroup parent) {
		vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = mInflater.inflate(R.layout.activity_softmanager_list_item,null);
			vh.checkBox = (CheckBox) convertView.findViewById(R.id.softmgr_list_item_ch);
			vh.iv = (ImageView) convertView.findViewById(R.id.softmgr_list_item_iv);
			vh.tv1 = (TextView) convertView.findViewById(R.id.softmgr_list_item_tv1);
			vh.tv2 = (TextView) convertView.findViewById(R.id.softmgr_list_item_tv2);
			vh.tv3 = (TextView) convertView.findViewById(R.id.softmgr_list_item_tv3);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		vh.checkBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!infos.get(position).isChecked) {
//					positions.add(position);
					infos.get(position).isChecked = true;
				}else {
					infos.get(position).isChecked = false;
//					positions.remove(positions.indexOf(positions));
				}
				
			}
		});
		
		/*vh.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (arg1) {
					 state.put(position, arg1);
				}else {
					 state.remove(position);
				}
			}
		});*/
//		vh.checkBox.setChecked(false);
//		if (positions.contains(positions)) {
//			vh.checkBox.setChecked(true);
//		}
		vh.checkBox.setChecked(infos.get(position).isChecked);
//		if (infos.get(position).isAllChecked) {
//			vh.checkBox.setChecked(true);
//		}else {
//			vh.checkBox.setChecked(false);
//		}
//		vh.checkBox.setChecked(state.get(position)==null? false : true);
		vh.iv.setImageDrawable(infos.get(position).icon);
		vh.tv1.setText(infos.get(position).title);
		vh.tv2.setText(infos.get(position).packageName);
		vh.tv3.setText(infos.get(position).version);
		return convertView;
	}
	class ViewHolder{
		CheckBox checkBox;
		ImageView iv;
		TextView tv1,tv2,tv3;
	}
}
