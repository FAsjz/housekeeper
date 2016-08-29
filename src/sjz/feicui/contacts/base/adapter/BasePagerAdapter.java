package sjz.feicui.contacts.base.adapter;

import java.util.ArrayList;

import sjz.feicui.contacts.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class BasePagerAdapter extends PagerAdapter {
	private ArrayList<View> views = new ArrayList<View>();
	private LayoutInflater inflater;
	private Context context;
	int[] pics = { R.drawable.adware_style_applist, R.drawable.adware_style_banner,
			R.drawable.adware_style_creditswall };

	public BasePagerAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		// 将三个视图加载成View对象、
		// 将三张视图加载成View对象，并设置图片
		for (int i = 0; i < pics.length; i++) {
			View v = inflater.inflate(R.layout.activity_lead_viewpager_item, null);
			ImageView iv = (ImageView) v
					.findViewById(R.id.iv_lead_viewpager_item);
			iv.setImageResource(pics[i]);
			// 将视图加载近适配器
			addViewToAdapter(v);
		}
	}

	/**
	 * 添加视图对象到适配器
	 * 
	 * @param v
	 *            视图对象
	 * */
	public void addViewToAdapter(View v) {
		views.add(v);
	}

	/**
	 * 返回数据源集合
	 * */
	public ArrayList<View> gerViews() {
		return views;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {

		return arg0 == arg1;
	}

	// 将视图添加进视图组
	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		View v = views.get(position);
		container.addView(v);
		return v;
	}

	// 将视图移出视图组
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));
	}
}
