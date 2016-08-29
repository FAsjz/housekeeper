package sjz.feicui.contacts.activity;

import java.io.File;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.R.layout;
import sjz.feicui.contacts.R.menu;
import sjz.feicui.contacts.base.activity.ActionBarActivity;
import sjz.feicui.contacts.base.activity.BaseActivity;
import sjz.feicui.contacts.base.utils.FileTypeUtil;
import sjz.feicui.contacts.biz.FileManager;
import sjz.feicui.contacts.view.ActionBar;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FileManagerActivity extends ActionBarActivity {
	private FileManager fm;
	private String sdPath = Environment.getExternalStorageDirectory().getPath();
	private TextView fileAllSize;
	private TextView fileSize1;
	private TextView fileSize2;
	private TextView fileSize3;
	private TextView fileSize4;
	private TextView fileSize5;
	private TextView fileSize6;
	private TextView allSize;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			allSize.setText(fm.getAllSize());
			fileAllSize.setText(fm.getAllSize());
			fileSize1.setText(fm.getTextSize());
			fileSize2.setText(fm.getVideoSize());
			fileSize3.setText(fm.getAudioSize());
			fileSize4.setText(fm.getImageSize());
			fileSize5.setText(fm.getZipSize());
			fileSize6.setText(fm.getApkSize());
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
		setContentView(R.layout.activity_file_manager);
		initView();
		
		setListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.file_manager, menu);
		return true;
	}

	@Override
	protected void initView() {
		initActionBar(R.drawable.ic_arrows_left,
				getString(R.string.home_filemgr), ActionBar.ID_NULL, on);
		allSize =(TextView) findViewById(R.id.filemgr_item_allsize);
		fileAllSize =(TextView) findViewById(R.id.filemgr_item_sizeall);
		fileSize1 = (TextView) findViewById(R.id.filemgr_item_size1);
		fileSize2 = (TextView) findViewById(R.id.filemgr_item_size2);
		fileSize3 = (TextView) findViewById(R.id.filemgr_item_size3);
		fileSize4 = (TextView) findViewById(R.id.filemgr_item_size4);
		fileSize5 = (TextView) findViewById(R.id.filemgr_item_size5);
		fileSize6 = (TextView) findViewById(R.id.filemgr_item_size6);
		
	}
	FileManager.MyOnClickListener myListener = new FileManager.MyOnClickListener() {
		
		@Override
		public void searchEnd() {
			handler.sendEmptyMessage(0);
		}
	};
	@Override
	protected void setListener() {
		fm =FileManager.getInstance();
		fm.setOnClickListener(myListener);
		new Thread(){
			public void run() {
				File file = new File(sdPath);
				fm.searchSDFile(file,FileManagerActivity.this);
			};
		}.start();
		
	}
	public void toNextActivity(View v){
		Intent it = new Intent(this, FileManagerShowActivity.class);
		switch (v.getId()) {
		case R.id.filemgr_item1:
			it.putExtra("date", FileTypeUtil.TYPE_ANY);
			break;
		case R.id.filemgr_item2:
			it.putExtra("date", FileTypeUtil.TYPE_TXT);
			break;
		case R.id.filemgr_item3:
			it.putExtra("date", FileTypeUtil.TYPE_VIDEO);
			break;
		case R.id.filemgr_item4:
			it.putExtra("date", FileTypeUtil.TYPE_AUDIO);
			break;
		case R.id.filemgr_item5:
			it.putExtra("date", FileTypeUtil.TYPE_IMAGE);
			break;
		case R.id.filemgr_item6:
			it.putExtra("date", FileTypeUtil.TYPE_ZIP);
			break;
		case R.id.filemgr_item7:
			it.putExtra("date", FileTypeUtil.TYPE_APK);
			break;
		}
		startActivityForResult(it, 0);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		allSize.setText(fm.getAllSize());
		fileAllSize.setText(fm.getAllSize());
		fileSize1.setText(fm.getTextSize());
		fileSize2.setText(fm.getVideoSize());
		fileSize3.setText(fm.getAudioSize());
		fileSize4.setText(fm.getImageSize());
		fileSize5.setText(fm.getZipSize());
		fileSize6.setText(fm.getApkSize());
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		fm.destroyDate();
	}
}
