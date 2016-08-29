package sjz.feicui.contacts.activity;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.R.layout;
import sjz.feicui.contacts.R.menu;
import sjz.feicui.contacts.base.activity.ActionBarActivity;
import sjz.feicui.contacts.view.ActionBar;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

public class AboutActivity extends ActionBarActivity {
	private ActionBar ab;
	private OnClickListener on = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// 获得传递过来的数据包
			Bundle bundle = getIntent().getBundleExtra("bundle");
			String className = bundle.getString("className");
			if (className.equals(HomeActivity.class.getSimpleName())) {
				AboutActivity.this.finish();

			} else if (className.equals(SettingActivity.class.getSimpleName())) {
				AboutActivity.this.finish();
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about);
		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

	@Override
	protected void initView() {
		initActionBar(R.drawable.ic_arrows_left,
				getString(R.string.about_actionbar), ActionBar.ID_NULL, on);
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

}
