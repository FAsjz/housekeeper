package sjz.feicui.contacts.base.activity;

import sjz.feicui.contacts.activity.LogoActivity;

import sjz.feicui.contacts.activity.TelmsgListActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * ����Activity�ĸ��� ��������ת�ĺ���
 * 
 * */
public abstract class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}

	/**
	 * ��һ��������ת����һ������
	 * 
	 * @param act
	 *            ���ĸ����濪ʼ��ת
	 * @param cls
	 *            ��ת���ĸ�����
	 * */
	protected void startActivity(Class<?> cls) {
		// ��ת��������
		startActivity(new Intent(this, cls));
		// TODO Auto-generated method stub
	}

	/**
	 * ��һ��������ת��ϵͳ����
	 * 
	 * @param action
	 *            ��ת���ĸ�ϵͳ����
	 * */
	protected void startActivity(String action) {
		Intent it = new Intent(action);
		startActivity(it);
	}

	/**
	 * ��һ��������ת����һ�����沢��Я������
	 * 
	 * @param act
	 *            ���ĸ����濪ʼ��ת
	 * @param cls
	 *            ��ת���ĸ�����
	 * */
	protected void startActivity(Class<?> cls, Bundle bundle) {

		// ��ת��TelmsgListActivity
		Intent it = new Intent(this, cls);
		it.putExtra("bundle", bundle);
		startActivity(it);
	}

	/**
	 * ��һ��������ת����һ�����沢�Ҵ�����Ч��
	 * 
	 * */
	protected void startActivity(Class<?> cls, int in, int out) {

		Intent it = new Intent(this, cls);
		startActivity(it);
		this.finish();
		// �������ת�����������startActivity(it)����act.finish();֮��
		overridePendingTransition(in, out);
	}

	protected abstract void initView();

	protected abstract void setListener();
}
