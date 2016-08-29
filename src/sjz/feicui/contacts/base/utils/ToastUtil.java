package sjz.feicui.contacts.base.utils;

import android.content.Context;
import android.widget.Toast;

public final class ToastUtil {
	private static Toast toast;

	public static void show(Context context, String text, int duration) {
		if (toast == null) {
			toast = Toast.makeText(context, text, duration);
		}

		// 修改内容
		toast.setText(text);
		toast.setDuration(duration);
		// 显示内容
		toast.show();
	}
}
