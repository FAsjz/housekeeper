package sjz.feicui.contacts.biz;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import sjz.feicui.contacts.base.utils.LogUtil;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

/**
 * 管理Astsets下的数据库
 * */
public class AssetsDBManager {
	/**
	 * 复制本地Assets中的db文件到指定目录中
	 * 
	 * @param context
	 * @param path
	 *            Assets内db文件路径
	 * @param File
	 *            to 目标位置 throws IOException
	 */
	public static final String TGA = "AssetsDBManager";

	public static void copyAssetDBFileToFile(Context context, String from,
			String path) throws IOException {
		AssetManager am = context.getAssets();
		// 输入流(用来读取当前项目的Assets内的db文本)
		BufferedInputStream bis = null;
		// 输出流(用来将读取到的db信息写到指定目录文件to中去)
		BufferedOutputStream bos = null;
		try {
			LogUtil.d("bis", from); 
			InputStream is = am.open("db/commonnum.db");
			 LogUtil.d("bis", from); 
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(new FileOutputStream(path));
			int x;
			byte[] b = new byte[2 * 1024];
			while ((x = bis.read(b)) != -1) {
				bos.write(b, 0, x);
			}
			bos.flush();

		} catch (IOException e) {
			throw e;
		} finally {

			bis.close();
			bos.close();
		}
	}
}
