package sjz.feicui.contacts.activity;

import java.util.ArrayList;
import java.util.List;

import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.R.layout;
import sjz.feicui.contacts.R.menu;
import sjz.feicui.contacts.base.activity.ActionBarActivity;
import sjz.feicui.contacts.base.activity.BaseActivity;
import sjz.feicui.contacts.base.utils.CommonUtil;
import sjz.feicui.contacts.base.utils.LogUtil;
import sjz.feicui.contacts.base.utils.ToastUtil;
import sjz.feicui.contacts.biz.MemoryInfoManager;
import sjz.feicui.contacts.entity.ProgressInfo;
import sjz.feicui.contacts.view.ActionBar;
import sjz.feicui.contacts.view.HomePieCharts;
import android.os.Bundle;
import android.os.Debug;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class HomeActivity extends ActionBarActivity {
	private float firstSweep,secondSweep;
	private ActionBar ab;
	private long waitTime = 1000;// �ȴ�ʱ��
	private long time = 0;// ��¼ʱ��
	private View mView;
	private TextView tv1,tv3;
	private HomePieCharts hpc;
	private OnClickListener on = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_actionbar_left:
				Bundle bundle = new Bundle();
				Class cls = HomeActivity.class;
				String name = cls.getSimpleName();
				bundle.putString("className", name);
				startActivity(AboutActivity.class, bundle);
				break;

			case R.id.iv_actionbar_right:
				startActivity(SettingActivity.class);
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		initView();
		hpc.setSweep(360, getpoint(360));
		setListener();
	}
	// �����豸����
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// ˫���˳�Ӧ�ó���
			// 1����˫��
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				long curTime = System.currentTimeMillis();
				if (curTime - time <= waitTime) {
					// ˫���˳�
					HomeActivity.this.finish();
				} else {
					// ��ʾ
					ToastUtil.show(this, "�ٰ�һ���˳�", 0);
				}
				time = curTime;
				return true;
			}
			// super.onKeyDown(keyCode, event)��ʾ���ø�����Ĭ�ϵİ�����Ϊ
			return super.onKeyDown(keyCode, event);
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	protected void initView() {
		initActionBar(R.drawable.ic_launcher, getString(R.string.app_name),
				R.drawable.setting_logo, on);
		mView = findViewById(R.id.home_clean);
		tv3 = (TextView) findViewById(R.id.home_tv3);
		tv1 = (TextView) findViewById(R.id.home_tv1);
		hpc = (HomePieCharts) findViewById(R.id.homepiecharts);
		tv1.setText(Math.round(getpoint(100)) +"");
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	// ����ͼƬ�ĵ��
	public void dump(View view) {
		switch (view.getId()) {
		case R.id.txt_speedup:
			startActivity(SpeedupActivity.class);
			break;
		case R.id.txt_clean:
			startActivity(CleanActivity.class);
			break;
		case R.id.txt_filemgr:
			startActivity(FileManagerActivity.class);
			break;
		case R.id.txt_phonecheck:
			startActivity(PhoneCheckActivity.class);
			break;
		case R.id.txt_softmgr:
			startActivity(SoftManagerActivity.class);
			break;
		case R.id.txt_telbook:
			startActivity(TelmsgActivity.class);
			break;
		}
	}
	public void clean(View v){
		
		firstSweep = getpoint(360);
		PackageManager pm = HomeActivity.this.getPackageManager();
		ActivityManager am = (ActivityManager) 
				HomeActivity.this.getSystemService(Context.ACTIVITY_SERVICE);
		/*ArrayList<RunningAppProcessInfo> runningApp = (ArrayList<RunningAppProcessInfo>) am
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcessInfo : runningApp) {
			String packageName = appProcessInfo.processName;// �������г� �������
			int importance = appProcessInfo.importance; // �������г������ ����
			// ������̣��������������½���
			if (importance >= RunningAppProcessInfo.IMPORTANCE_SERVICE) {
					am.killBackgroundProcesses(packageName);
			}
		}*/
		 List<AndroidAppProcess> processInfos = ProcessManager.getRunningAppProcesses();
		 for (AndroidAppProcess processInfo : processInfos) {
				String packageName = processInfo.name;// �������г� �������
				try {
	                // ��ȡӦ�ó�����Ϣ
	                ApplicationInfo applicationInfo = pm.getApplicationInfo(
	                		packageName, 0);
	              
	                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
	                    // �û�����
	                	am.killBackgroundProcesses(packageName);
	                } 
				}catch (NameNotFoundException e) {
					 e.printStackTrace();
				}
		 }
		tv1.setText(Math.round(getpoint(100)) +"");
		secondSweep =getpoint(360);		
		hpc.setSweep(firstSweep, secondSweep);
	}
	public float getpoint(int sweep){
		double totalMem = CommonUtil.byteCastMB(MemoryInfoManager
				.getPhoneTotalRamMemory());
		double freeMem = CommonUtil.byteCastMB(MemoryInfoManager
				.getPhoneFreeRamMemory(this));
		return (float) ((totalMem-freeMem)/totalMem * sweep);
	}

}
