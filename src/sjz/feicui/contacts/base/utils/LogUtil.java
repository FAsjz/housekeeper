package sjz.feicui.contacts.base.utils;
/*
 * ��������Log��־
 * */
import android.util.Log;

public final class LogUtil {
	public static boolean isOpenDebug = true;
	

	public static void d(String tag, String msg) {
		if (isOpenDebug) {
			Log.d(tag, msg);
		}
	}

	
}
