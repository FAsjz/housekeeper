package sjz.feicui.contacts.activity;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.base.activity.BaseActivity;
import sjz.feicui.contacts.base.utils.LogUtil;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * 用于显示欢迎界面
 * 
 * 
 * */
public class LogoActivity extends BaseActivity {
	private ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * 创建动画对象
		 * 
		 * 1.上下文 2.用于显示布局文件的id
		 */
		 /**全屏设置，隐藏窗口所有装饰**/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题**/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_logo);
		initView();
		setListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logo, menu);
		return true;
	}

	@Override
	protected void initView() {
		// 获取当前界面播放的动画
		iv = (ImageView) findViewById(R.id.img_logo);
	}

	@Override
	protected void setListener() {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
		// 设置监听
		anim.setAnimationListener(new AnimationListener() {
			// 当动画播放时调用此函数
			@Override
			public void onAnimationStart(Animation animation) {
				LogUtil.d("Log", "onAnimationStart");
				// TODO Auto-generated method stub

			}

			// 当动画重复播放时调用此函数
			@Override
			public void onAnimationRepeat(Animation animation) {
				LogUtil.d("Log", "onAnimationRepeat");
				// TODO Auto-generated method stub

			}

			// 当动画播放结束时调用此函数
			@Override
			public void onAnimationEnd(Animation animation) {
				LogUtil.d("Log", "onAnimationEnd");

				 startActivity( HomeActivity.class);
				 finish();
//				startActivity(HomeActivity.class, R.anim.in, R.anim.exit);

			}
		});

		// 设置动画
		iv.setAnimation(anim);
	}

}
