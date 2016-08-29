package sjz.feicui.contacts.activity;

import java.io.File;
import java.util.ArrayList;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.adapter.FileManagerShowAdapter;
import sjz.feicui.contacts.application.Myapplication;
import sjz.feicui.contacts.base.activity.ActionBarActivity;
import sjz.feicui.contacts.base.utils.FileTypeUtil;
import sjz.feicui.contacts.biz.FileManager;
import sjz.feicui.contacts.entity.FileInfo;
import sjz.feicui.contacts.view.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

public class FileManagerShowActivity extends ActionBarActivity {
	private ArrayList<FileInfo> files = new ArrayList<FileInfo>();
	private String size,title;
	private TextView tv1,tv2;
	private ListView lv;
	private CheckBox cb;
	private Button bt;
	private FileManagerShowAdapter adapter;
	private File file;
	Myapplication ma ;
	FileManager fm = FileManager.getInstance();
	String value;
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
		setContentView(R.layout.activity_file_manager_show);
		getIntentDate();
		
		initView();
		
		setListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.file_manager_show, menu);
		return true;
	}
	@Override
	protected void initView() {
		super.initView();
		initActionBar(R.drawable.ic_arrows_left,
				title, ActionBar.ID_NULL, on);
		tv1 = (TextView) findViewById(R.id.filemgr_show_fileNumber);
		tv2 = (TextView) findViewById(R.id.filemgr_show_fileSize);
		bt = (Button) findViewById(R.id.filemgr_show_bt);
		cb = (CheckBox) findViewById(R.id.filemgr_show_cb);
		tv1.setText(files.size()+"个");
		tv2.setText(size);
		lv = (ListView) findViewById(R.id.filemgr_show_lv);
		adapter = new FileManagerShowAdapter(this);
		adapter.addDataToAdapter(files);
		lv.setAdapter(adapter);
		
	}
	public void getIntentDate(){
		ma = (Myapplication) this.getApplication();
		value = getIntent().getStringExtra("date");
		if (value.equals(FileTypeUtil.TYPE_ANY)) {
			files = ma.getAllFiles();
			title = "全部";
			size = fm.getAllSize();
		}else if (value.equals(FileTypeUtil.TYPE_TXT)) {
			files = ma.getTextFiles();
			title = "文档";
			size = fm.getTextSize();
		}else if (value.equals(FileTypeUtil.TYPE_VIDEO)) {
			files = ma.getVideoFiles();
			title = "视频";
			size = fm.getVideoSize();
		}else if (value.equals(FileTypeUtil.TYPE_AUDIO)) {
			files = ma.getAudioFiles();
			title = "音频";
			size = fm.getAudioSize();
		}else if (value.equals(FileTypeUtil.TYPE_IMAGE)) {
			files = ma.getImageFiles();
			title = "图片";
			size = fm.getImageSize();
		}else if (value.equals(FileTypeUtil.TYPE_ZIP)) {
			files = ma.getZipFiles();
			title = "压缩包";
			size = fm.getZipSize();
		}else if (value.equals(FileTypeUtil.TYPE_APK)) {
			files = ma.getApkFiles();
			title = "安装包";
			size = fm.getApkSize();
		}
	}
	@Override
	protected void setListener() {
		super.setListener();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String mime = null;
				FileInfo infos = adapter.getDataFromAdapter().get(arg2);
				String name = infos.name;
				int index = name.lastIndexOf(".");
				if(index == -1){//没有后缀名
					return;
				}
				String end = name.substring(index + 1, name.length());
				for (int i = 0; i < FileTypeUtil.MIME_Table.length; i++) {
					if (end.equals(FileTypeUtil.MIME_Table[i][0])) {
						mime = FileTypeUtil.MIME_Table[i][1];
						break;
					}/*else{
						mime = "text/plain";//
					}*/
				}
				Intent it = new Intent(Intent.ACTION_VIEW);
				it.setDataAndType(Uri.parse("file://" + infos.path), mime);
				startActivity(it);
			}
		});
		//设置全选框
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				ArrayList<FileInfo> list = adapter.getDataFromAdapter();
				for (FileInfo lists : list) {
					lists.isCheck = arg1;
				}
				adapter.notifyDataSetChanged();
			}
		});
		
		//设置按钮监听
		bt.setOnClickListener(new OnClickListener() {
			/*OnClickListener listener = new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (file.exists()) {
						file.delete();
					}
				}
				
			};*/
			@Override
			public void onClick(View arg0) {
				String newSize = null;
				long sizeNew;
				ArrayList<FileInfo> list = adapter.getDataFromAdapter();
				for (int x = list.size()-1; x >= 0;x--) {
					FileInfo fileInfo = list.get(x);
					if (fileInfo.isCheck) {
						file = new File(fileInfo.path);
						Long fileSize = file.length();
						list.remove(x);
						if (file != null) {
							file.delete();
						}
						if (value.equals(FileTypeUtil.TYPE_ANY)) {
							fm.getAllFile().remove(fileInfo);
							sizeNew = fm.getAll()-fileSize;
							newSize = Formatter.formatFileSize(FileManagerShowActivity.this, sizeNew);
							fm.setAll(sizeNew);
							fm.setAllSize(newSize);
						}else if (value.equals(FileTypeUtil.TYPE_TXT)) {
							fm.getTextFile().remove(fileInfo);
							sizeNew = fm.getText()-fileSize;
							newSize = Formatter.formatFileSize(FileManagerShowActivity.this, sizeNew);
							fm.setText(sizeNew);
							fm.setTextSize(newSize);
						}else if (value.equals(FileTypeUtil.TYPE_VIDEO)) {
							fm.getVideoFile().remove(fileInfo);
							sizeNew = fm.getVideo()-fileSize;
							newSize = Formatter.formatFileSize(FileManagerShowActivity.this, sizeNew);
							fm.setVideo(sizeNew);
							fm.setVideoSize(newSize);
						}else if (value.equals(FileTypeUtil.TYPE_AUDIO)) {
							fm.getAudioFile().remove(fileInfo);
							sizeNew = fm.getAudio()-fileSize;
							newSize = Formatter.formatFileSize(FileManagerShowActivity.this, sizeNew);
							fm.setAudio(sizeNew);
							fm.setAudioSize(newSize);
						}else if (value.equals(FileTypeUtil.TYPE_IMAGE)) {
							fm.getImageFile().remove(fileInfo);
							sizeNew = fm.getImage()-fileSize;
							newSize = Formatter.formatFileSize(FileManagerShowActivity.this, sizeNew);
							fm.setImage(sizeNew);
							fm.setImageSize(newSize);
						}else if (value.equals(FileTypeUtil.TYPE_ZIP)) {
							fm.getZipFile().remove(fileInfo);
							sizeNew = fm.getZip()-fileSize;
							newSize = Formatter.formatFileSize(FileManagerShowActivity.this, sizeNew);
							fm.setZip(sizeNew);
							fm.setZipSize(newSize);
						}else if (value.equals(FileTypeUtil.TYPE_APK)) {
							fm.getApkFile().remove(fileInfo);
							sizeNew = fm.getApk()-fileSize;
							newSize = Formatter.formatFileSize(FileManagerShowActivity.this, sizeNew);
							fm.setApk(sizeNew);
							fm.setApkSize(newSize);
						}
					}
				}
				tv1.setText(files.size()+"个");
				tv2.setText(newSize);
				adapter.notifyDataSetChanged();
			}
		});
		
	}
}
