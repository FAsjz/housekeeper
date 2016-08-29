package sjz.feicui.contacts.biz;

import java.io.File;
import java.math.BigDecimal;

import sjz.feicui.contacts.base.utils.FileTypeUtil;
import sjz.feicui.contacts.biz.FileManager.MyOnClickListener;
import android.content.Context;
import android.os.Environment;
import android.text.format.Formatter;
import android.text.style.ForegroundColorSpan;

public class RubbishManager {
	/*private MyOnClickListener myListener;
	public interface MyOnClickListener {
		void searchEnd();
	}

	public void setOnClickListener(MyOnClickListener on) {
		this.myListener = on;
	}*/
private static long cacheSize;
private static long apkSize;
private static long bigFileSize;
private static long imageSize;
	// 计算手机缓存大小
	public static String getTotalCacheSize(Context context) throws Exception {
		cacheSize = getFolderSize(context.getCacheDir());
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			cacheSize += getFolderSize(context.getExternalCacheDir());
		}
		return getFormatSize(cacheSize);
	}

	// 清除缓存
	public static void clearAllCache(Context context) {
		deleteDir(context.getCacheDir());
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			deleteDir(context.getExternalCacheDir());
		}
	}

	private static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	// 获取文件
	// Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/
	// 目录，一般放一些长时间保存的数据
	// Context.getExternalCacheDir() -->
	// SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
	public static long getFolderSize(File file) throws Exception {
		long size = 0;
		try {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				// 如果下面还有文件
				if (fileList[i].isDirectory()) {
					size = size + getFolderSize(fileList[i]);
				} else {
					size = size + fileList[i].length();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * 格式化单位
	 * 
	 * @param size
	 * @return
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			// return size + "Byte";
			return "0K";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
				+ "TB";
	}

	// 获取安装包
	// File file = new File(Environment.getExternalStorageDirectory().getPath());
	

	public static String getAPK(File file) {
		
		if (file == null) {
			return null;
		}
		File[] files = file.listFiles();
		if (files == null) {
			return null;
		}
		for (int i = 0; i < files.length; i++ ) {
			File file2 = files[i];
			if (file2 == null) {
				continue;
			}
			if (file2.isDirectory()) {
				getAPK(file2);
			} else {
				String end = file2.getName().substring(
						file2.getName().lastIndexOf(".") + 1,
						file2.getName().length());
				String[] iconAndType = FileTypeUtil.getFileType(end);
				if (iconAndType[1].equals(FileTypeUtil.TYPE_APK)) {
					apkSize += file2.length();
				}
			}
		}
		return getFormatSize(apkSize);
	}
	
	public static void clearApk(File file){
	
		File[] files = file.listFiles();
		if (files == null) {
			return;
		}
		for (File file2 : files) {
			if (file2 == null) {
				continue;
			}
			if (file2.isDirectory()) {
				clearApk(file2);
			} else {
				String end = file2.getName().substring(
						file2.getName().lastIndexOf(".") + 1,
						file2.getName().length());
				String[] iconAndType = FileTypeUtil.getFileType(end);
				if (iconAndType[1].equals(FileTypeUtil.TYPE_APK) && file2 != null) {
					file2.delete();
				}
			}
		}
	}
	public static String getBigFile(File file) {
		
		long length = 0;
		File[] files = file.listFiles();
		if (files == null) {
			return null;
		}
		for (File file2 : files) {
			if (file2 == null) {
				continue;
			}
			length = file2.length();
			if (file2.isDirectory()) {
				getBigFile(file2);
			} else {
				
				if (length/1024/1024 >= 10) {
					bigFileSize += file2.length();
				}
			}
		}
		
		return getFormatSize(bigFileSize);
	}
	
	public static String getfluousPictureSize(File file) {
	
		File[] files = file.listFiles();
		if (files == null) {
			return null;
		}
		for (int i = 0; i < files.length; i++ ) {
			File file2 = files[i];
			if (file2 == null) {
				continue;
			}
			if (file2.isDirectory()) {
				getfluousPictureSize(file2);
			} else {
				if (file2.getName().equals(".thumbnails")) {
					imageSize += file2.length();
				}
			}
		}
		return getFormatSize(imageSize);
	}

	public static String getAllSize(File file){
//		long allSize = imageSize + apkSize + bigFileSize + cacheSize;
		long allSize = imageSize + apkSize +cacheSize;
		return getFormatSize(allSize);
		
	}
	
}
