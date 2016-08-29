package sjz.feicui.contacts.activity;

import java.util.ArrayList;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.adapter.AppInfoAdapter;
import sjz.feicui.contacts.base.activity.ActionBarActivity;
import sjz.feicui.contacts.base.utils.LogUtil;
import sjz.feicui.contacts.biz.AppInfoManager;
import sjz.feicui.contacts.entity.AppInfo;
import sjz.feicui.contacts.view.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.ProgressBar;

public class SoftManagerListActivity extends ActionBarActivity {
	private AppInfoManager am;
	private ProgressBar rotatePb;
	private ListView mListView;
	private AppInfoAdapter appAdapter;
	private String title;
	private CheckBox cb;
	private Button mButton;
	private Runnable r = new Runnable(){

		@Override
		public void run() {
			am = new AppInfoManager(SoftManagerListActivity.this);
			// ��ȡӦ�ó�����Ϣ
			am.addAllPackageInfos();

			handler.sendEmptyMessage(1);
		}};
	private OnClickListener on = new OnClickListener() {

		@Override
		public void onClick(View v) {
			SoftManagerListActivity.this.finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_soft_manager_list);

		initView();
		new Thread(r).start();
		setListener();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.soft_manager_list, menu);
		return true;
	}

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				rotatePb.setVisibility(View.INVISIBLE);
				// ��mListView��ʾ
				mListView.setVisibility(View.VISIBLE);
				// ����������������
				if (title.equalsIgnoreCase("�������")) {
					appAdapter.addDataToAdapter(am.getAllPackageInfos());
				} else if (title.equalsIgnoreCase("ϵͳ���")) {
					appAdapter.addDataToAdapter(am.getSysPackageInfos());
				} else if(title.equalsIgnoreCase("�û����")){
					appAdapter.addDataToAdapter(am.getUserPackageInfos());
				}
				// ����������
				mListView.setAdapter(appAdapter);
			}
		}
	};
	
	@Override
	protected void initView() {
		rotatePb =(ProgressBar) findViewById(R.id.progressBar1);
		Bundle bundle = getIntent().getBundleExtra("bundle");
		title = bundle.getString("actionbartitle");
		initActionBar(R.drawable.ic_arrows_left, title, ActionBar.ID_NULL, on);
		mListView = (ListView) findViewById(R.id.softmgr_list_lv);
		appAdapter = new AppInfoAdapter(this);
		cb = (CheckBox) findViewById(R.id.softmgr_list_cb);
		mButton = (Button) findViewById(R.id.softmgr_list_bt);
	}

	@Override
	protected void setListener() {
		super.setListener();
		//ȫѡ��ѡ������
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//��ȡ����������
				
//				AppInfoAdapter appInfoAdapter = new AppInfoAdapter(SoftManagerListActivity.this);
				ArrayList<AppInfo> infos = appAdapter.getDataFromAdapter();
				for (AppInfo appInfo : infos) {
					
					appInfo.isChecked = isChecked;
				}
				appAdapter.notifyDataSetChanged();
			}
		});
		//ɾ����ť����
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<AppInfo> infos = appAdapter.getDataFromAdapter();
				
				for (AppInfo appInfo : infos) {
					if (appInfo.isChecked) {
						Intent it = new Intent(Intent.ACTION_DELETE);
						//����Ҫɾ��������
						it.setData(Uri.parse("package:" + appInfo.packageName));
						startActivity(it);
					}
				}
/*				//����ui
				appAdapter.clearDataTOAdapter();
				rotatePb.setVisibility(View.VISIBLE);
				mListView.setVisibility(View.INVISIBLE);
				new Thread(r).start();*/
				
				
			}
		});
	}

}
