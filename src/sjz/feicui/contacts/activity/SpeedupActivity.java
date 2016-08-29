package sjz.feicui.contacts.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.adapter.ProgressInfoAdapter;
import sjz.feicui.contacts.base.activity.ActionBarActivity;
import sjz.feicui.contacts.base.utils.CommonUtil;
import sjz.feicui.contacts.base.utils.LogUtil;
import sjz.feicui.contacts.biz.AppInfoManager;
import sjz.feicui.contacts.biz.MemoryInfoManager;
import sjz.feicui.contacts.biz.TelPhoneInfoManager;
import sjz.feicui.contacts.entity.ProgressInfo;
import sjz.feicui.contacts.view.ActionBar;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SpeedupActivity extends ActionBarActivity {
	private TextView title, veision, memory;
	private Button bt1, bt2;// 一键清理，切换用户进程
	private ProgressBar pb,largePb;
	private ListView mListView;
	private CheckBox cb;
	private ProgressInfoAdapter adapter;
	private boolean isSystem;
	private Runnable r = new Runnable(){

		@Override
		public void run() {
			AppInfoManager am = new AppInfoManager(SpeedupActivity.this);
			HashMap<Integer, ArrayList<ProgressInfo>> map = am
					.getProgressInfos(SpeedupActivity.this);
			if (isSystem) {
				adapter.addDataToAdapter(map
						.get(AppInfoManager.RUNING_APP_TYPE_SYS));
			}else {
				adapter.addDataToAdapter(map
						.get(AppInfoManager.RUNING_APP_TYPE_USER));
			}
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					largePb.setVisibility(View.INVISIBLE);
					mListView.setVisibility(View.VISIBLE);
					// 更新UI
					adapter.notifyDataSetChanged();
				}
			});
			
		}
		
	};
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
		setContentView(R.layout.activity_speedup);

		initView();

		setListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.speedup, menu);
		return true;
	}

	@Override
	protected void initView() {
		initActionBar(R.drawable.ic_arrows_left,
				getString(R.string.home_speedup), ActionBar.ID_NULL, on);
		title = (TextView) findViewById(R.id.speedup_title);
		veision = (TextView) findViewById(R.id.speedup_veision);
		memory = (TextView) findViewById(R.id.speedup_memory);
		bt1 = (Button) findViewById(R.id.speedup_bt1);
		bt2 = (Button) findViewById(R.id.speedup_bt2);
		mListView = (ListView) findViewById(R.id.speedup_lv);
		cb = (CheckBox) findViewById(R.id.speedup_cb);
		pb = (ProgressBar) findViewById(R.id.speedup_pb);
		largePb =(ProgressBar) findViewById(R.id.speedup_progressBar);
		adapter = new ProgressInfoAdapter(this);
		mListView.setAdapter(adapter);

		// 设置手机品牌和型号
		title.setText(TelPhoneInfoManager.getPhoneBrand());
		veision.setText(TelPhoneInfoManager.getPhoneModel() + " android"
				+ TelPhoneInfoManager.getPhoneVersion());
		// 获取手机运存信息
		getMemoryAndProgress();

		// 显示正在运行应用
		new Thread(r).start();

	}

	public void getMemoryAndProgress() {
		double totalMem = CommonUtil.byteCastMB(MemoryInfoManager
				.getPhoneTotalRamMemory());
		double freeMem = CommonUtil.byteCastMB(MemoryInfoManager
				.getPhoneFreeRamMemory(this));
		memory.setText("已用内存：" + (int)(totalMem-freeMem)+ "M/" + totalMem + "M");
		// 设置进度条
		pb.setProgress((int) ( (totalMem-freeMem) / totalMem * 100));
	}
	
	@Override
	protected void setListener() {
		// 全选框设置
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				ArrayList<ProgressInfo> list = adapter.getDataFromAdapter();
				for (ProgressInfo progressInfo : list) {
					progressInfo.isChecked = isChecked;
				}
				adapter.notifyDataSetChanged();

			}
		});
		// 切换用户和系统进程按钮
		bt2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				adapter.clearDataTOAdapter();
				if (isSystem) {
					bt2.setText("只显示系统进程");
					cb.setChecked(false);
					isSystem = false;
				} else {
					cb.setChecked(false);
					bt2.setText("只显示用户进程");
					isSystem = true;
				}
				largePb.setVisibility(View.VISIBLE);
				mListView.setVisibility(View.INVISIBLE);
				new Thread(r).start();
			}
		});
		//一键清除
		bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//为获取包名添加活动管理器
				ActivityManager am = (ActivityManager) 
						SpeedupActivity.this.getSystemService(Context.ACTIVITY_SERVICE);
				
				ArrayList<ProgressInfo> list = adapter.getDataFromAdapter();
				for (ProgressInfo progressInfo : list) {
					if (progressInfo.isChecked) {
						am.killBackgroundProcesses(progressInfo.packageName);
					}
				}
				//更新适配器和运存信息
				getMemoryAndProgress();
				adapter.clearDataTOAdapter();
				largePb.setVisibility(View.VISIBLE);
				mListView.setVisibility(View.INVISIBLE);
				new Thread(r).start();
				//更新进度条和运存信息
			}
		});
		// 监听ListView的滚动状态
		mListView.setOnScrollListener(new OnScrollListener() {
			// 当滚动状态改变
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_FLING:// 快速滑动
					/*// LogUtil.d("onScrollStateChanged", "快速滑动中");
					adapter.clearDataTOAdapter();
					ArrayList<ProgressInfo> list = adapter.getDataFromAdapter();
					for (ProgressInfo progressInfo : list) {
						Drawable drawable = SpeedupActivity.this.getResources()
								.getDrawable(R.drawable.android_loding);
						progressInfo.icon = drawable;
						adapter.notifyDataSetChanged();
					}*/
					break;

				case OnScrollListener.SCROLL_STATE_IDLE:// 结束滚动
					// LogUtil.d("onScrollStateChanged", "结束滚动");
					/*adapter.clearDataTOAdapter();
					new Thread(r).start();*/
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 开始滚动
					// LogUtil.d("onScrollStateChanged", "开始滚动");
					break;
				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}
		});

	}

}
