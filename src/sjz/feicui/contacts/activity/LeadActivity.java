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
		
		 /**ȫ�����ã����ش�������װ��**/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_lead);
		initView();
		// ����SettingActivity���洫�ݹ���������
		Bundle bundle = getIntent().getBundleExtra("bundle");
		if (bundle != null) {
			// ���û�л�ȡ�����򷵻�null
			className = bundle.getString("className");

		}

		super.onCreate(savedInstanceState);
		SharedPreferences shared = getSharedPreferences("first",
				Context.MODE_PRIVATE);
		boolean isFirst = shared.getBoolean("first", true);
		
		// �ж��Ƿ��Ǵ����ý�����ת������
		if (className != null
				&& className.equals(SettingActivity.class.getSimpleName())) {
			// ���ֵ���ת
			intent = new Intent(this, LeadService.class);
			startService(intent);
		} else {
			// �ж��Ƿ��ǵ�һ�δ�Ӧ�ó���
			if (!isFirst) {
				startActivity(LogoActivity.class);
				finish();
			} else {
				Editor et = shared.edit();
				et.putBoolean("first", false);
				et.commit();
				// ���ֵ���ת
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
		// ��ȡ����ImageViewԭ�����
		points[0] = (ImageView) findViewById(R.id.iv_lead_left);
		points[1] = (ImageView) findViewById(R.id.iv_lead_middle);
		points[2] = (ImageView) findViewById(R.id.iv_lead_right);
		// ��ȡ���ֶ���
		mTextView = (TextView) findViewById(R.id.tv_lead);
		mButton = (Button) findViewById(R.id.bt_lead);
	}

	@Override
	protected void setListener() {
		// ���������ü���
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
			// ��ҳ���л���ʱ��
			@Override
			public void onPageSelected(int position) {
				// ����ԭ��ͼƬ
				for (int i = 0; i < points.length; i++) {
					points[i].setImageResource(R.drawable.lead_rhq_a);

				}
				points[position].setImageResource(R.drawable.lead_rhq_b);
				//����������ʾ״̬
				if (position == points.length - 1) {
					mButton.setVisibility(View.VISIBLE);
					mTextView.setVisibility(View.INVISIBLE);
				} else {
					mButton.setVisibility(View.INVISIBLE);
					mTextView.setVisibility(View.VISIBLE);
				}
			}

			// ��ҳ�����ڹ�����ʱ���α�����
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			// ������״̬�л���ʱ�����
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
