package sjz.feicui.contacts.activity;

import java.util.ArrayList;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.adapter.TelmsgListAdapter;
import sjz.feicui.contacts.base.activity.BaseActivity;
import sjz.feicui.contacts.base.utils.DialongUtil;
import sjz.feicui.contacts.base.utils.ToastUtil;
import sjz.feicui.contacts.biz.TelDBReadManager;
import sjz.feicui.contacts.entity.ClassListInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class TelmsgListActivity extends BaseActivity implements
		OnItemClickListener {
	private ListView mListView;
	private TelmsgListAdapter mAdapter;
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_telmsg_list);
		// 初始化控件
		initView();

		// 查询数据
		if (TelDBReadManager.isExits()) {
			ArrayList<ClassListInfo> infos = TelDBReadManager
					.getgetClassListItemInfo(this, "table" + position);
			// 将数据添加到适配器中
			mAdapter.addDataToAdapter(infos);
		} else {
			ToastUtil.show(this, "数据库没有找到或不存在", Toast.LENGTH_SHORT);
		}
		mListView.setAdapter(mAdapter);
		setListener();
	}

	@Override
	protected void setListener() {
		mListView.setOnItemClickListener(this);
	}

	// 点击按钮刷新ListView
	public void refresh(View view) {
		ArrayList<ClassListInfo> infos = TelDBReadManager
				.getgetClassListItemInfo(this, "table" + position);
		mAdapter.addDataToAdapter(infos);
		// 通知适配器更新界面
		mAdapter.notifyDataSetChanged();
	}

	@Override
	protected void initView() {
		mListView = (ListView) findViewById(R.id.listview_lv);
		mAdapter = new TelmsgListAdapter(this);
		// 从TelmsgActivity传递过来的索引
		Bundle bundle = getIntent().getBundleExtra("bundle");
		position = bundle.getInt("position");
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		DialongUtil.show(this, "警告", "是否开始拨打"
				+ mAdapter.getDataFromAdapter().get(position).name
				+ "电话？\n\nTEL:"
				+ mAdapter.getDataFromAdapter().get(position).number, mAdapter,
				position);
	}
}
