package sjz.feicui.contacts.activity;

import java.util.ArrayList;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.R.layout;
import sjz.feicui.contacts.R.menu;
import sjz.feicui.contacts.adapter.PhoneCkeckAdapter;
import sjz.feicui.contacts.base.activity.ActionBarActivity;
import sjz.feicui.contacts.base.activity.BaseActivity;
import sjz.feicui.contacts.biz.MemoryInfoManager;
import sjz.feicui.contacts.biz.TelPhoneInfoManager;
import sjz.feicui.contacts.entity.PhoneInfo;
import sjz.feicui.contacts.view.ActionBar;
import android.os.BatteryManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PhoneCheckActivity extends ActionBarActivity {
	private LinearLayout ll;
	private BattertReceiver br;
	private int totalBattery, currentBattery, temperature;
	private ProgressBar batteryPb, pb;
	private TextView tv;
	private ListView lv;
	private PhoneCkeckAdapter adapter;
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
		setContentView(R.layout.activity_phone_check);

		initView();
		getAdapterInfo();
		setListener();
	}

	public void getAdapterInfo() {
		new Thread() {
			public void run() {
				String deviceName = "设备名称："
						+ TelPhoneInfoManager.getPhoneBrand();
				String deviceVersion = "系统版本："
						+ TelPhoneInfoManager.getPhoneVersion();
				Drawable icon1 = getResources().getDrawable(
						R.drawable.phoneckeck1);
				final PhoneInfo info1 = new PhoneInfo(deviceName, deviceVersion,
						icon1);
				

				String totalRam = "全部运行内存："
						+ Formatter.formatFileSize(PhoneCheckActivity.this,
								MemoryInfoManager.getPhoneTotalRamMemory());
				String freeRam = "剩余运行内存："
						+ Formatter
								.formatFileSize(
										PhoneCheckActivity.this,
										MemoryInfoManager
												.getPhoneFreeRamMemory(PhoneCheckActivity.this));
				Drawable icon2 = getResources().getDrawable(
						R.drawable.phoneckeck2);
				final PhoneInfo info2 = new PhoneInfo(totalRam, freeRam, icon2);
				

				String cpuName = "cpu名称：" + TelPhoneInfoManager.getCpuInfo();
				String cpuNumber = "cpu数量："
						+ TelPhoneInfoManager.getCPUNumber();
				Drawable icon3 = getResources().getDrawable(
						R.drawable.phoneckeck3);
				final PhoneInfo info3 = new PhoneInfo(cpuName, cpuNumber, icon3);

				String diaplayMetrics = "手机分辨率："
						+ TelPhoneInfoManager.getDpi(PhoneCheckActivity.this);
				String cameraMetrics = "相机分辨率："
						+ TelPhoneInfoManager.getCameraMetrics();
				Drawable icon4 = getResources().getDrawable(
						R.drawable.phoneckeck4);
				final PhoneInfo info4 = new PhoneInfo(diaplayMetrics, cameraMetrics,
						icon4);

				String basebandVersion = "基带版本："
						+ TelPhoneInfoManager.getBasebandVersion();
				String isRoot = "是否ROOT："
						+ (TelPhoneInfoManager.isRoot() ? "是" : "否");
				Drawable icon5 = getResources().getDrawable(
						R.drawable.phoneckeck5);
				final PhoneInfo info5 = new PhoneInfo(basebandVersion, isRoot, icon5);
				
				// 更新ui
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						adapter.addDataToAdapter(info1);
						adapter.addDataToAdapter(info2);
						adapter.addDataToAdapter(info3);
						adapter.addDataToAdapter(info4);
						adapter.addDataToAdapter(info5);
						pb.setVisibility(View.INVISIBLE);
						lv.setVisibility(View.VISIBLE);
						// 更新UI
						adapter.notifyDataSetChanged();
					}
				});
			};
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.phone_check, menu);
		return true;
	}

	@Override
	protected void initView() {
		initActionBar(R.drawable.ic_arrows_left,
				getString(R.string.home_phonecheck), ActionBar.ID_NULL, on);
		ll = (LinearLayout) findViewById(R.id.phoneckeck_ll);
		tv = (TextView) findViewById(R.id.phoneckeck_power_tv);
		lv = (ListView) findViewById(R.id.phoneckeck_lv);
		adapter = new PhoneCkeckAdapter(this);
		pb = (ProgressBar) findViewById(R.id.phonecheck_progressBar);
		lv.setAdapter(adapter);
		br = new BattertReceiver();
		// 注册广播接收者并调整频道
		registerReceiver(br, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		batteryPb = (ProgressBar) findViewById(R.id.phonecheck_battery_pb);

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 解绑
		unregisterReceiver(br);
	}

	class BattertReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
				// 总电量
				totalBattery = intent
						.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
				// 当前电量
				currentBattery = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,
						0);
				// 手机温度
				temperature = intent.getIntExtra(
						BatteryManager.EXTRA_TEMPERATURE, 0);
				batteryPb.setMax(totalBattery);
				batteryPb.setProgress(currentBattery);
				tv.setText((int) ((float) currentBattery / totalBattery * 100)
						+ "%");
			}
		}

	}
	public void showDialong(View v){
		Dialog dialog = new AlertDialog.Builder(this)
		.setTitle("电池电量信息")
		.setItems(new String[]{"当前电量:"+currentBattery + "%","电池温度:"+temperature/10+"℃"}, null).show();
		dialog.setCanceledOnTouchOutside(true);
	}

	
}
