package sjz.feicui.contacts.base.utils;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.activity.HomeActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

public final class NotificationUtil {
	public static final int NOTIFICATION_ID = 1;

	public static void showNotification(Context context) {

		// getActivity������Activity֮���л�

		Intent it = new Intent(context, HomeActivity.class);

		 PendingIntent pi = PendingIntent.getActivity(context, 1, it,
		 PendingIntent.FLAG_UPDATE_CURRENT);
		/*
		 * Bitmap bt = BitmapFactory.decodeResource(context.getResources(),
		 * R.drawable.ic_launcher);
		 */
		// ����֪ͨ
		NotificationCompat.Builder nf = new NotificationCompat.Builder(context);
		Notification notification = nf
				.setTicker("��֪ͨ")

				.setContentTitle("�ɽ�����")
				.setContentText("��Ķ��ֶ���������ɽ�����ϰ���������Ƿ�Ҫ����")
				.setWhen(System.currentTimeMillis())
				.setVibrate(new long[] { 10, 60 })
				// ����Сͼ��
				.setSmallIcon(R.drawable.ic_launcher)
				/*
				 * Intent ��ͼpending Intent �ӳ���ͼ
				 */
				.setContentIntent(pi)
				.build();
		// ��ȡ֪ͨ������
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// ����֪ͨ
		nm.notify(NOTIFICATION_ID, notification);

	}

	public static void cancelNotification(Context context) {
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// ȡ��֪ͨ
		nm.cancel(NOTIFICATION_ID);
	}
}
