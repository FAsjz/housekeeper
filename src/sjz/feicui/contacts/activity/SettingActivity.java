package sjz.feicui.contacts.activity;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.R.layout;
import sjz.feicui.contacts.R.menu;
import sjz.feicui.contacts.base.activity.ActionBarActivity;
import sjz.feicui.contacts.base.activity.BaseActivity;
import sjz.feicui.contacts.base.utils.NotificationUtil;
import sjz.feicui.contacts.view.ActionBar;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class SettingActivity extends ActionBarActivity {
	private ToggleButton mTb,startup,messagepush;
	private OnClickListener on = new OnClickListener() {

		@Override
		public void onClick(View v) {
			SettingActivity.this.finish();

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);
		initView();
		setListener();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}

	@Override
	protected void initView() {
		initActionBar(R.drawable.ic_arrows_left,
				getString(R.string.setting_actionbar), ActionBar.ID_NULL, on);
		mTb = (ToggleButton) findViewById(R.id.notification);
		messagepush = (ToggleButton) findViewById(R.id.messagepush);
		startup = (ToggleButton) findViewById(R.id.startup);
	}

	@Override
	protected void setListener() {
		super.setListener();
		mTb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			// isChecked 是否被打开
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// 发送通知
					NotificationUtil.showNotification(SettingActivity.this);
				} else {
					// 取消通知
					NotificationUtil.cancelNotification(SettingActivity.this);
				}
			}
		});
	}

	// Item的点击事件
	public void doClick(View v) {
		switch (v.getId()) {
		// 关于
		case R.id.setting_layout_about:
			Bundle bundle = new Bundle();
			bundle.putString("className", SettingActivity.class.getSimpleName());
			startActivity(AboutActivity.class, bundle);
			break;
		// 帮助
		case R.id.setting_layout_help:
			// 跳转到引导界面
			Bundle bundle2 = new Bundle();
			bundle2.putString("className",
					SettingActivity.class.getSimpleName());
			startActivity(LeadActivity.class, bundle2);
			break;
		}
	}

}
