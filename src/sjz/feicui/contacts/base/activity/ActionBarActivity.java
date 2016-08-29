package sjz.feicui.contacts.base.activity;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.view.ActionBar;
import android.os.Bundle;
import android.view.View.OnClickListener;

public class ActionBarActivity extends BaseActivity {
	protected ActionBar ab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void initActionBar(int left, String text, int right,
			OnClickListener on) {
		ab = (ActionBar) findViewById(R.id.actionbar);
		ab.initActionbar(left, text, right, on);
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

}
