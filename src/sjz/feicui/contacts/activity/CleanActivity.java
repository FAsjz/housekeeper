package sjz.feicui.contacts.activity;

import java.io.File;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.base.activity.ActionBarActivity;
import sjz.feicui.contacts.biz.RubbishManager;
import sjz.feicui.contacts.view.ActionBar;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CleanActivity extends ActionBarActivity {
	private TextView allSize,size1,size2,size3,size4;
	private CheckBox cb1,cb2,cb3,cb4;
	private ProgressBar pb;
	private Button bt;
	private File file = new File(Environment.getExternalStorageDirectory().getPath());
	private String cacheSize;
	private String imageSize;  
	private String apkSize;    
	private String BigFileSize;
	private String all;
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			size1.setText(imageSize);
			size3.setText(apkSize);
//			size4.setText(BigFileSize);
			size2.setText(cacheSize);
			allSize.setText(all);
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
		setContentView(R.layout.activity_clean);
		initView();
		new Thread(){
			public void run() {
//				size1.setVisibility(View.INVISIBLE);
//				pb.setVisibility(View.VISIBLE);
				 imageSize = RubbishManager.getfluousPictureSize(file);
				 apkSize = RubbishManager.getAPK(file);
//				 BigFileSize = RubbishManager.getBigFile(file);
				try {
					cacheSize = RubbishManager.getTotalCacheSize(CleanActivity.this);
				} catch (Exception e) {
					e.printStackTrace();
				}
				all = RubbishManager.getAllSize(file);
				handler.sendEmptyMessage(0);
			};
			
		}.start();
		setListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.clean, menu);
		return true;
	}

	@Override
	protected void initView() {
		initActionBar(R.drawable.ic_arrows_left,
				getString(R.string.home_clean), ActionBar.ID_NULL, on);
		allSize = (TextView) findViewById(R.id.clean_item_allsize);
		size1 = (TextView) findViewById(R.id.clean_item_size1);
		size2 = (TextView) findViewById(R.id.clean_item_size2);
		size3 = (TextView) findViewById(R.id.clean_item_size3);
		size4 = (TextView) findViewById(R.id.clean_item_size4);
		cb1 =(CheckBox) findViewById(R.id.clean_item_cb1);
		cb2 =(CheckBox) findViewById(R.id.clean_item_cb2);
		cb3 =(CheckBox) findViewById(R.id.clean_item_cb3);
		cb4 =(CheckBox) findViewById(R.id.clean_item_cb4);
		bt =(Button) findViewById(R.id.clean_bt);
//		pb = (ProgressBar) findViewById(R.id.clean_progressBar);
	}

	@Override
	protected void setListener() {
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (cb2.isChecked()) {
					try {
						RubbishManager.clearAllCache(CleanActivity.this);
						size2.setText(RubbishManager.getTotalCacheSize(CleanActivity.this));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
				
				
			}
		});
		
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	}
	public void toNextActivity(View v){
		
		switch (v.getId()) {
		case R.id.clean_item1:
			
			break;
		case R.id.clean_item2:
			
			break;
		case R.id.clean_item3:
		
			break;
		case R.id.clean_item4:
			
			break;
		
		}
		
	}
}
