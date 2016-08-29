package sjz.feicui.contacts.activity;

import java.io.File;
import java.io.IOException;
import sjz.feicui.contacts.R;
import sjz.feicui.contacts.adapter.TelmsgAdapter;
import sjz.feicui.contacts.base.activity.ActionBarActivity;
import sjz.feicui.contacts.base.activity.BaseActivity;
import sjz.feicui.contacts.base.utils.ToastUtil;
import sjz.feicui.contacts.biz.AssetsDBManager;
import sjz.feicui.contacts.biz.TelDBReadManager;
import sjz.feicui.contacts.entity.ClassListInfo;
import sjz.feicui.contacts.view.ActionBar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class TelmsgActivity extends ActionBarActivity implements OnItemClickListener {
	public static final String ASSET_DBFILE = "db/commonnum.db";
	public static final String TOFILEPATH = "data/data/sjz.feicui.contacts/commonnum.db";
	private ListView mListView;
	private TelmsgAdapter mAdapter;
//	private long waitTime = 1000;// 等待时间
//	private long time = 0;// 记录时间
	private OnClickListener on = new OnClickListener() {

		@Override
		public void onClick(View v) {
			finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_telmsg);

		// 初始化数据库信息
		initDBInfo();

		// 初始化控件
		initView();
		// 设置点击的监听事件
		setListener();
	}

	@Override
	protected void setListener() {
		// 设置点击事件
		mListView.setOnItemClickListener(this);
		mListView.setAdapter(mAdapter);

	}

	/*// 监听设备按键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 双击退出应用程序
		// 1秒内双击
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			long curTime = System.currentTimeMillis();
			if (curTime - time <= waitTime) {
				// 双击退出
				TelmsgActivity.this.finish();
			} else {
				// 提示
				ToastUtil.show(this, "再按一次退出", 0);
			}
			time = curTime;
			return true;
		}
		// super.onKeyDown(keyCode, event)表示调用父类中默认的按键行为
		return super.onKeyDown(keyCode, event);
	}*/

	@Override
	protected void initView() {
		mListView = (ListView) findViewById(R.id.listview);
		mAdapter = new TelmsgAdapter(this);
		mAdapter.addDataToAdapter(TelDBReadManager.getClassListInfo(this));
		// 手动添加一条数据，加载到索引为0的位置

		mAdapter.addDataToAdapter(new ClassListInfo("本地电话"), 0);
		initActionBar(R.drawable.ic_arrows_left,
				getString(R.string.home_telbook), ActionBar.ID_NULL, on);
	}

	private void initDBInfo() {
		try {
			if (!new File(TOFILEPATH).exists()) {

				AssetsDBManager.copyAssetDBFileToFile(this, ASSET_DBFILE,
						TOFILEPATH);

			}
		} catch (IOException e) {
			ToastUtil.show(this, "数据库文件拷贝失败...", Toast.LENGTH_SHORT);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.telmsg, menu);
		return true;
	}

	// 当点击了ListView中某个Item时调用
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 判断点击的索引是否为0
		if (position == 0) {
			startActivity(Intent.ACTION_DIAL);
		} else {
			Bundle bundle = new Bundle();
			bundle.putInt("position", position);
			startActivity(TelmsgListActivity.class, bundle);
		}
	}

}