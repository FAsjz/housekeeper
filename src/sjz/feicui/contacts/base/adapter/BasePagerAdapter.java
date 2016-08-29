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
		// ��������ͼ���س�View����
		// ��������ͼ���س�View���󣬲�����ͼƬ
		for (int i = 0; i < pics.length; i++) {
			View v = inflater.inflate(R.layout.activity_lead_viewpager_item, null);
			ImageView iv = (ImageView) v
					.findViewById(R.id.iv_lead_viewpager_item);
			iv.setImageResource(pics[i]);
			// ����ͼ���ؽ�������
			addViewToAdapter(v);
		}
	}

	/**
	 * �����ͼ����������
	 * 
	 * @param v
	 *            ��ͼ����
	 * */
	public void addViewToAdapter(View v) {
		views.add(v);
	}

	/**
	 * ��������Դ����
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

	// ����ͼ��ӽ���ͼ��
	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		View v = views.get(position);
		container.addView(v);
		return v;
	}

	// ����ͼ�Ƴ���ͼ��
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));
	}
}
