 package sjz.feicui.contacts.activity;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.R.layout;
import sjz.feicui.contacts.R.menu;
import sjz.feicui.contacts.base.activity.ActionBarActivity;
import sjz.feicui.contacts.base.utils.CommonUtil;
import sjz.feicui.contacts.biz.MemoryInfoManager;
import sjz.feicui.contacts.biz.MemorySize;
import sjz.feicui.contacts.view.ActionBar;
import sjz.feicui.contacts.view.PieCharts;
import android.os.Bundle;
import android.app.Activity;
import android.app.SearchManager.OnCancelListener;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SoftManagerActivity extends ActionBarActivity {
	private ProgressBar phonePb, cardPb, outCardPb;
	private TextView phoneTv, cardTv, outCardTv;
	private PieCharts pieCharts;
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
		setContentView(R.layout.activity_soft_manager);
		initView();
		// 设置内存文字信息
		setMemoryInfo();
		setListener();
	}

	private void setMemoryInfo() {
		// 手机内存
//		double phoneSelfFreeMemory = MemoryInfoManager.getPhoneSelfFreeMemory();
//		double phoneSelfAllMemory = MemoryInfoManager.getPhoneSelfAllMemory();
		double phoneSelfFreeMemory = MemorySize.getAvailableInternalMemorySize();
		double phoneSelfAllMemory = MemorySize.getTotalInternalMemorySize();
		// SD卡
//		double sdCardAllMemory = MemoryInfoManager.getSDCardAllMemory(this);
//		double sdCardFreeMemory = MemoryInfoManager.getSDCardFreeMemory(this);
		double sdCardAllMemory = MemorySize.getTotalExternalMemorySize();
		double sdCardFreeMemory = MemorySize.getAvailableExternalMemorySize();
		// 转换单位
		phoneSelfFreeMemory = CommonUtil.byteCastMB((long) phoneSelfFreeMemory);
		phoneSelfAllMemory = CommonUtil.byteCastMB((long) phoneSelfAllMemory);
		sdCardAllMemory = CommonUtil.byteCastMB((long) sdCardAllMemory);
		sdCardFreeMemory = CommonUtil.byteCastMB((long) sdCardFreeMemory);
		// 设置文字
		phoneTv.setText("可用" + phoneSelfFreeMemory + "M/" + phoneSelfAllMemory
				+ "M");
		cardTv.setText("可用" + sdCardFreeMemory + "M/" + sdCardAllMemory + "M");
		//设置饼状图百分比
		float phoneSweep = (float) ((phoneSelfAllMemory-phoneSelfFreeMemory) / (phoneSelfAllMemory
				+ sdCardAllMemory) *360);
		float SDSweep = (float) ((sdCardAllMemory-sdCardFreeMemory) / (phoneSelfAllMemory
				+ sdCardAllMemory) * 360);
		//设置角度
		pieCharts.setSweep(phoneSweep, SDSweep);
		// 计算进度条的百分比
		int phonepoint = (int) (phoneSelfFreeMemory
				/ phoneSelfAllMemory * 100);
		int cardpoint = (int) (sdCardFreeMemory
				/ sdCardAllMemory * 100);
		// 设置进度条
		phonePb.setProgress(phonepoint);
		cardPb.setProgress(cardpoint);
/*		// 获取外置SD的存储空间
		double outSDCardAllMemory = MemoryInfoManager.getOutSDCardAllMemory(this);
		double outSDCardFreeMemory = MemoryInfoManager.getOutSDCardFreeMemory(this);
		// 转换单位
		outSDCardAllMemory = CommonUtil.byteCastMB((long) outSDCardAllMemory);
		outSDCardFreeMemory = CommonUtil.byteCastMB((long) outSDCardFreeMemory);
		// 设置文字
		outCardTv.setText("可用" + outSDCardFreeMemory + "/" + outSDCardAllMemory
				+ "M");
		// 计算进度条的百分比
		int outCardpoint = (int) ((outSDCardAllMemory - outSDCardFreeMemory)
				/ outSDCardAllMemory * 100);
		// 设置进度条
		outCardPb.setProgress(outCardpoint);*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.soft_manager, menu);
		return true;
	}

	@Override
	protected void initView() {
		initActionBar(R.drawable.ic_arrows_left,
				getString(R.string.softmgr_title), ActionBar.ID_NULL, on);
		// 获取两个进度条
		phonePb = (ProgressBar) findViewById(R.id.softmgr_pb1);
		cardPb = (ProgressBar) findViewById(R.id.softmgr_pb2);
		
		// 用于显示的的文字
		phoneTv = (TextView) findViewById(R.id.softmgr_tv1);
		cardTv = (TextView) findViewById(R.id.softmgr_tv2);
		pieCharts = (PieCharts) findViewById(R.id.softmgr_circle);
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	public void allApp(View v) {
		Bundle bundle = new Bundle();
		bundle.putString("actionbartitle", "所有软件");
		startActivity(SoftManagerListActivity.class, bundle);
	}

	public void userApp(View v) {
		Bundle bundle = new Bundle();
		bundle.putString("actionbartitle", "用户软件");
		startActivity(SoftManagerListActivity.class, bundle);
	}

	public void systemApp(View v) {
		Bundle bundle = new Bundle();
		bundle.putString("actionbartitle", "系统软件");
		startActivity(SoftManagerListActivity.class, bundle);
	}

}
