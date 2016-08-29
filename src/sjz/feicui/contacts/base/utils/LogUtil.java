package sjz.feicui.contacts.base.utils;
/*
 * 用来管理Log日志
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
