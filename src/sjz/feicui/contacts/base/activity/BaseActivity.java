package sjz.feicui.contacts.base.activity;

import sjz.feicui.contacts.activity.LogoActivity;

import sjz.feicui.contacts.activity.TelmsgListActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * 所有Activity的父类 包含了跳转的函数
 * 
 * */
public abstract class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}

	/**
	 * 从一个界面跳转到另一个界面
	 * 
	 * @param act
	 *            从哪个界面开始跳转
	 * @param cls
	 *            跳转到哪个界面
	 * */
	protected void startActivity(Class<?> cls) {
		// 跳转到主界面
		startActivity(new Intent(this, cls));
		// TODO Auto-generated method stub
	}

	/**
	 * 从一个界面跳转到系统界面
	 * 
	 * @param action
	 *            跳转到哪个系统界面
	 * */
	protected void startActivity(String action) {
		Intent it = new Intent(action);
		startActivity(it);
	}

	/**
	 * 从一个界面跳转到另一个界面并且携带数据
	 * 
	 * @param act
	 *            从哪个界面开始跳转
	 * @param cls
	 *            跳转到哪个界面
	 * */
	protected void startActivity(Class<?> cls, Bundle bundle) {

		// 跳转到TelmsgListActivity
		Intent it = new Intent(this, cls);
		it.putExtra("bundle", bundle);
		startActivity(it);
	}

	/**
	 * 从一个界面跳转到另一个界面并且带动画效果
	 * 
	 * */
	protected void startActivity(Class<?> cls, int in, int out) {

		Intent it = new Intent(this, cls);
		startActivity(it);
		this.finish();
		// 界面的跳转动画必须加载startActivity(it)或者act.finish();之后
		overridePendingTransition(in, out);
	}

	protected abstract void initView();

	protected abstract void setListener();
}
