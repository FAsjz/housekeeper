package sjz.feicui.contacts.adapter;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.adapter.ProgressInfoAdapter.ViewHolder;
import sjz.feicui.contacts.base.adapter.BaseBaseAdapter;
import sjz.feicui.contacts.base.utils.FileTypeUtil;
import sjz.feicui.contacts.base.utils.LogUtil;
import sjz.feicui.contacts.entity.FileInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class FileManagerShowAdapter extends BaseBaseAdapter<FileInfo> {
	private LruCache<String, Bitmap> cache;

	public FileManagerShowAdapter(Context context) {
		super(context);
		LogUtil.d("FileManagerShowAdapter", (int) Runtime.getRuntime()
				.maxMemory() / 4 + "");
		cache = new LruCache<String, Bitmap>((int) Runtime.getRuntime()
				.maxMemory() / 4) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getHeight() * value.getRowBytes();
			}
		};
	}

	@Override
	public View getMyView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.activity_file_manager_show_list, null);
			vh.checkBox = (CheckBox) convertView
					.findViewById(R.id.filemgr_show_list_item_ch);
			vh.iv = (ImageView) convertView
					.findViewById(R.id.filemgr_show_list_item_iv);
			vh.tv1 = (TextView) convertView
					.findViewById(R.id.filemgr_show_list_item_tv1);
			vh.tv2 = (TextView) convertView
					.findViewById(R.id.filemgr_show_list_item_tv2);
			vh.tv3 = (TextView) convertView
					.findViewById(R.id.filemgr_show_list_item_tv3);
			convertView.setTag(vh);
//			vh.checkBox.setTag(position);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		/*
		 * vh.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
		 * {
		 * 
		 * @Override public void onCheckedChanged(CompoundButton arg0, boolean
		 * arg1) { infos.get((Integer) arg0.getTag()).isCheck = arg1; } });
		 */
		vh.checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!infos.get(position).isCheck) {
					infos.get(position).isCheck = true;
				} else {
					infos.get(position).isCheck = false;
				}

			}
		});

		vh.checkBox.setChecked(infos.get(position).isCheck);
		// 如果是图片返回图片本身；否则返回drawable图片
//		Bitmap bitmap;
		if (infos.get(position).type.equals(FileTypeUtil.TYPE_IMAGE)) {
			Bitmap b = cache.get(infos.get(position).path);
			if (b == null) {
				b = BitmapFactory.decodeFile(infos.get(position).path);
				cache.put(infos.get(position).path, b);
			}

			vh.iv.setImageBitmap(b);
		} else {
			int icon = mContext.getResources().getIdentifier(
					infos.get(position).iconName, "drawable",
					mContext.getPackageName());
			vh.iv.setImageResource(icon);
		}

		vh.tv1.setText(infos.get(position).name);
		vh.tv2.setText(infos.get(position).lastTime);
		vh.tv3.setText(infos.get(position).size);
		return convertView;
	}

	class ViewHolder {
		CheckBox checkBox;
		ImageView iv;
		TextView tv1, tv2, tv3;
	}

}
