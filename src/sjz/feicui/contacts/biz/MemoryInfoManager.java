package sjz.feicui.contacts.biz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sjz.feicui.contacts.base.utils.ToastUtil;
import sjz.feicui.contacts.entity.ProgressInfo;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;

public class MemoryInfoManager {
	/*
	 * 获取手机总运存
	 */
	private static ActivityManager am;
	public static long getPhoneTotalRamMemory()  {
		try {
			BufferedReader br = new BufferedReader(new FileReader("/proc/meminfo"));
			String text = br.readLine();
			String[] array = text.split(" +");
			return Long.valueOf(array[1]) * 1024;//kb转化为b
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
		
	}
	/*
	 * 获取剩余运存
	 */
	public static long getPhoneFreeRamMemory(Context context)  {
		MemoryInfo info = new MemoryInfo(); 
		if (am == null) {
			am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE); 
		}
		am.getMemoryInfo(info); 
		return info.availMem;
		
	}
	
	
	/**
	 * 获取手机自带的总空间
	 * 
	 */
	public static long getPhoneSelfAllMemory() {

		File root = Environment.getRootDirectory();
		StatFs rootState = new StatFs(root.getPath());
		// 获取对应的路径下内存块的个数以及内存块的大小
		long rootMemory = rootState.getBlockCount() * rootState.getBlockSize();

		File data = Environment.getDataDirectory();
		StatFs dataState = new StatFs(data.getPath());
		// 获取对应的路径下内存块的个数以及内存块的大小
		long dataMemory = dataState.getBlockCount() * dataState.getBlockSize();

		File cache = Environment.getDownloadCacheDirectory();
		StatFs cacheState = new StatFs(cache.getPath());
		// 获取对应的路径下内存块的个数以及内存块的大小
		long cacheMemory = cacheState.getBlockCount()
				* cacheState.getBlockSize();

		return rootMemory + dataMemory + cacheMemory;
	}

	/**
	 * 
	 * 获取手机自带的剩余空间
	 */
	public static long getPhoneSelfFreeMemory() {
		File root = Environment.getRootDirectory();
		StatFs rootState = new StatFs(root.getPath());
		// 获取对应的路径下内存块的个数以及内存块的大小
		long rootMemory = rootState.getAvailableBlocks()
				* rootState.getBlockSize();

		File data = Environment.getDataDirectory();
		StatFs dataState = new StatFs(data.getPath());
		// 获取对应的路径下内存块的个数以及内存块的大小
		long dataMemory = dataState.getAvailableBlocks()
				* dataState.getBlockSize();

		File cache = Environment.getDownloadCacheDirectory();
		StatFs cacheState = new StatFs(cache.getPath());
		// 获取对应的路径下内存块的个数以及内存块的大小
		long cacheMemory = cacheState.getAvailableBlocks()
				* cacheState.getBlockSize();

		return rootMemory + dataMemory + cacheMemory;
	}

	/**
	 * 内置SD卡总空间
	 * 
	 */
	public static long getSDCardAllMemory(Context context) {
		if (isMountedSDCard(context)) {

			File root = Environment.getExternalStorageDirectory();
			StatFs rootState = new StatFs(root.getPath());
			// 获取对应的路径下内存块的个数以及内存块的大小
			long rootMemory = rootState.getBlockCount()
					* rootState.getBlockSize();
			return rootMemory;
		} else {
			return 0;
		}
	}

	/**
	 * 内置SD卡的可用空间
	 * 
	 */
	public static long getSDCardFreeMemory(Context context) {
		if (isMountedSDCard(context)) {
			File root = Environment.getExternalStorageDirectory();
			StatFs rootState = new StatFs(root.getPath());
			// 获取对应的路径下内存块的个数以及内存块的大小
			long rootMemory = rootState.getAvailableBlocks()
					* rootState.getBlockSize();
			return rootMemory;

		} else {
			return 0;
		}

	}

	/**
	 * 判断SD是否存在
	 * 
	 */
	public static boolean isMountedSDCard(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			ToastUtil.show(context, "内置SD卡没有找到", 0);
			return false;
		}

	}

	/**
	 * 判断外置SD卡是否存在
	 * 
	 */
	public static String isMountedOutSDCard(Context context) {
		// 获取安卓系统的环境变量
		Map<String, String> map = System.getenv();
		// 判断是否含外置的SD卡环境
		if (map.containsKey("SECONDARY_STORAGE")) {
			String value = map.get("SECONDARY_STORAGE");
			String path = value.split(":")[0];
			return path;
		} else {
			ToastUtil.show(context, "外置SD卡没有找到", 0);
			return null;
		}
	}

	/***
	 * 获取外置SD卡的总空间
	 * 
	 */
	public static long getOutSDCardAllMemory(Context context) {
		String path = isMountedOutSDCard(context);
		if (path != null) {

			File root = new File(path);
			StatFs rootState = new StatFs(root.getPath());
			// 获取对应的路径下内存块的个数以及内存块的大小
			long rootMemory = rootState.getBlockCount()
					* rootState.getBlockSize();
			return rootMemory;
		} else {
			return 0;
		}
	}

	/***
	 * 获取外置SD卡的可用空间
	 * 
	 */
	public static long getOutSDCardFreeMemory(Context context) {
		String path = isMountedOutSDCard(context);
		if (path != null) {

			File root = new File(path);
			StatFs rootState = new StatFs(root.getPath());
			// 获取对应的路径下内存块的个数以及内存块的大小
			long rootMemory = rootState.getAvailableBlocks()
					* rootState.getBlockSize();
			return rootMemory;
		} else {
			return 0;
		}
	}
}
