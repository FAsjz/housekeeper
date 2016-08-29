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
 * ������ʾ��ӭ����
 * 
 * 
 * */
public class LogoActivity extends BaseActivity {
	private ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * ������������
		 * 
		 * 1.������ 2.������ʾ�����ļ���id
		 */
		 /**ȫ�����ã����ش�������װ��**/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /**����������View�ģ����Դ������е����β��ֱ����غ������Ȼ��Ч,��Ҫȥ������**/
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
		// ��ȡ��ǰ���沥�ŵĶ���
		iv = (ImageView) findViewById(R.id.img_logo);
	}

	@Override
	protected void setListener() {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
		// ���ü���
		anim.setAnimationListener(new AnimationListener() {
			// ����������ʱ���ô˺���
			@Override
			public void onAnimationStart(Animation animation) {
				LogUtil.d("Log", "onAnimationStart");
				// TODO Auto-generated method stub

			}

			// �������ظ�����ʱ���ô˺���
			@Override
			public void onAnimationRepeat(Animation animation) {
				LogUtil.d("Log", "onAnimationRepeat");
				// TODO Auto-generated method stub

			}

			// ���������Ž���ʱ���ô˺���
			@Override
			public void onAnimationEnd(Animation animation) {
				LogUtil.d("Log", "onAnimationEnd");

				 startActivity( HomeActivity.class);
				 finish();
//				startActivity(HomeActivity.class, R.anim.in, R.anim.exit);

			}
		});

		// ���ö���
		iv.setAnimation(anim);
	}

}
