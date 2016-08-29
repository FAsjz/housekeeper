package sjz.feicui.contacts.activity;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.R.layout;
import sjz.feicui.contacts.R.menu;
import sjz.feicui.contacts.base.activity.BaseActivity;
import sjz.feicui.contacts.base.adapter.BasePagerAdapter;
import sjz.feicui.contacts.service.LeadService;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LeadActivity extends BaseActivity {
	private ViewPager mViewPager;
	private BasePagerAdapter mBasePagerAdapter;
	private ImageView[] points = new ImageView[3];
	private TextView mTextView;
	private Button mButton;
	private String className;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		 /**全屏设置，隐藏窗口所有装饰**/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_lead);
		initView();
		// 接受SettingActivity界面传递过来的数据
		Bundle bundle = getIntent().getBundleExtra("bundle");
		if (bundle != null) {
			// 如果没有获取数据则返回null
			className = bundle.getString("className");

		}

		super.onCreate(savedInstanceState);
		SharedPreferences shared = getSharedPreferences("first",
				Context.MODE_PRIVATE);
		boolean isFirst = shared.getBoolean("first", true);
		
		// 判断是否是从设置界面跳转过来的
		if (className != null
				&& className.equals(SettingActivity.class.getSimpleName())) {
			// 音乐的跳转
			intent = new Intent(this, LeadService.class);
			startService(intent);
		} else {
			// 判断是否是第一次打开应用程序
			if (!isFirst) {
				startActivity(LogoActivity.class);
				finish();
			} else {
				Editor et = shared.edit();
				et.putBoolean("first", false);
				et.commit();
				// 音乐的跳转
				intent = new Intent(this, LeadService.class);
				startService(intent);

			}

		}

		setListener();
	}

	@Override
	protected void initView() {
		mViewPager = (ViewPager) findViewById(R.id.vp_lead);
		mBasePagerAdapter = new BasePagerAdapter(this);
		mViewPager.setAdapter(mBasePagerAdapter);
		// 获取三个ImageView原点对象
		points[0] = (ImageView) findViewById(R.id.iv_lead_left);
		points[1] = (ImageView) findViewById(R.id.iv_lead_middle);
		points[2] = (ImageView) findViewById(R.id.iv_lead_right);
		// 获取文字对象
		mTextView = (TextView) findViewById(R.id.tv_lead);
		mButton = (Button) findViewById(R.id.bt_lead);
	}

	@Override
	protected void setListener() {
		// 给文字设置监听
		mTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 stopService(intent);
				if (className != null
						&& className.equals(SettingActivity.class
								.getSimpleName())) {
				} else {
					startActivity(LogoActivity.class);
				}
				finish();
			}
		});
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopService(intent);
				if (className != null
						&& className.equals(SettingActivity.class
								.getSimpleName())) {
				} else {
					startActivity(LogoActivity.class);
				}
				finish();
			}
		});
		

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			// 当页面切换的时候
			@Override
			public void onPageSelected(int position) {
				// 设置原点图片
				for (int i = 0; i < points.length; i++) {
					points[i].setImageResource(R.drawable.lead_rhq_a);

				}
				points[position].setImageResource(R.drawable.lead_rhq_b);
				//设置文字显示状态
				if (position == points.length - 1) {
					mButton.setVisibility(View.VISIBLE);
					mTextView.setVisibility(View.INVISIBLE);
				} else {
					mButton.setVisibility(View.INVISIBLE);
					mTextView.setVisibility(View.VISIBLE);
				}
			}

			// 当页面正在滚动的时候多次被调用
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			// 当滚动状态切换的时候调用
			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lead, menu);
		return true;
	}
}
