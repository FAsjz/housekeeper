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
 * ����Astsets�µ����ݿ�
 * */
public class AssetsDBManager {
	/**
	 * ���Ʊ���Assets�е�db�ļ���ָ��Ŀ¼��
	 * 
	 * @param context
	 * @param path
	 *            Assets��db�ļ�·��
	 * @param File
	 *            to Ŀ��λ�� throws IOException
	 */
	public static final String TGA = "AssetsDBManager";

	public static void copyAssetDBFileToFile(Context context, String from,
			String path) throws IOException {
		AssetManager am = context.getAssets();
		// ������(������ȡ��ǰ��Ŀ��Assets�ڵ�db�ı�)
		BufferedInputStream bis = null;
		// �����(��������ȡ����db��Ϣд��ָ��Ŀ¼�ļ�to��ȥ)
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
