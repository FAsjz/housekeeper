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
		// ��ʼ���ؼ�
		initView();

		// ��ѯ����
		if (TelDBReadManager.isExits()) {
			ArrayList<ClassListInfo> infos = TelDBReadManager
					.getgetClassListItemInfo(this, "table" + position);
			// ��������ӵ���������
			mAdapter.addDataToAdapter(infos);
		} else {
			ToastUtil.show(this, "���ݿ�û���ҵ��򲻴���", Toast.LENGTH_SHORT);
		}
		mListView.setAdapter(mAdapter);
		setListener();
	}

	@Override
	protected void setListener() {
		mListView.setOnItemClickListener(this);
	}

	// �����ťˢ��ListView
	public void refresh(View view) {
		ArrayList<ClassListInfo> infos = TelDBReadManager
				.getgetClassListItemInfo(this, "table" + position);
		mAdapter.addDataToAdapter(infos);
		// ֪ͨ���������½���
		mAdapter.notifyDataSetChanged();
	}

	@Override
	protected void initView() {
		mListView = (ListView) findViewById(R.id.listview_lv);
		mAdapter = new TelmsgListAdapter(this);
		// ��TelmsgActivity���ݹ���������
		Bundle bundle = getIntent().getBundleExtra("bundle");
		position = bundle.getInt("position");
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		DialongUtil.show(this, "����", "�Ƿ�ʼ����"
				+ mAdapter.getDataFromAdapter().get(position).name
				+ "�绰��\n\nTEL:"
				+ mAdapter.getDataFromAdapter().get(position).number, mAdapter,
				position);
	}
}
