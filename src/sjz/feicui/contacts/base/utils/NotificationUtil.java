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

		// getActivity用于在Activity之间切换

		Intent it = new Intent(context, HomeActivity.class);

		 PendingIntent pi = PendingIntent.getActivity(context, 1, it,
		 PendingIntent.FLAG_UPDATE_CURRENT);
		/*
		 * Bitmap bt = BitmapFactory.decodeResource(context.getResources(),
		 * R.drawable.ic_launcher);
		 */
		// 创建通知
		NotificationCompat.Builder nf = new NotificationCompat.Builder(context);
		Notification notification = nf
				.setTicker("新通知")

				.setContentTitle("仙剑奇侠")
				.setContentText("你的对手独孤求败在仙剑大会上把你击败了是否要复仇？")
				.setWhen(System.currentTimeMillis())
				.setVibrate(new long[] { 10, 60 })
				// 设置小图标
				.setSmallIcon(R.drawable.ic_launcher)
				/*
				 * Intent 意图pending Intent 延迟意图
				 */
				.setContentIntent(pi)
				.build();
		// 获取通知管理器
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// 发送通知
		nm.notify(NOTIFICATION_ID, notification);

	}

	public static void cancelNotification(Context context) {
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// 取消通知
		nm.cancel(NOTIFICATION_ID);
	}
}
