package sjz.feicui.contacts.service;

import sjz.feicui.contacts.R;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
/**
 * 
 * service
 * 为lead界面添加音乐
 * @author Administrator
 *
 */
public class LeadService extends Service {
	private MediaPlayer media;

	@Override
	public IBinder onBind(Intent arg0) {

		return null;
	}

	@Override
	public void onCreate() {
		if (media == null) {
			media = MediaPlayer.create(this, R.raw.muc);
			media.start();
		}
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (media != null) {
			media.stop();
			media.release();
			media = null;
		}
	}

}
