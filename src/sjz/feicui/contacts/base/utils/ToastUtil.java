package sjz.feicui.contacts.base.utils;

import android.content.Context;
import android.widget.Toast;

public final class ToastUtil {
	private static Toast toast;

	public static void show(Context context, String text, int duration) {
		if (toast == null) {
			toast = Toast.makeText(context, text, duration);
		}

		// �޸�����
		toast.setText(text);
		toast.setDuration(duration);
		// ��ʾ����
		toast.show();
	}
}
