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
	 * ��ȡ�ֻ����˴�
	 */
	private static ActivityManager am;
	public static long getPhoneTotalRamMemory()  {
		try {
			BufferedReader br = new BufferedReader(new FileReader("/proc/meminfo"));
			String text = br.readLine();
			String[] array = text.split(" +");
			return Long.valueOf(array[1]) * 1024;//kbת��Ϊb
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
		
	}
	/*
	 * ��ȡʣ���˴�
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
	 * ��ȡ�ֻ��Դ����ܿռ�
	 * 
	 */
	public static long getPhoneSelfAllMemory() {

		File root = Environment.getRootDirectory();
		StatFs rootState = new StatFs(root.getPath());
		// ��ȡ��Ӧ��·�����ڴ��ĸ����Լ��ڴ��Ĵ�С
		long rootMemory = rootState.getBlockCount() * rootState.getBlockSize();

		File data = Environment.getDataDirectory();
		StatFs dataState = new StatFs(data.getPath());
		// ��ȡ��Ӧ��·�����ڴ��ĸ����Լ��ڴ��Ĵ�С
		long dataMemory = dataState.getBlockCount() * dataState.getBlockSize();

		File cache = Environment.getDownloadCacheDirectory();
		StatFs cacheState = new StatFs(cache.getPath());
		// ��ȡ��Ӧ��·�����ڴ��ĸ����Լ��ڴ��Ĵ�С
		long cacheMemory = cacheState.getBlockCount()
				* cacheState.getBlockSize();

		return rootMemory + dataMemory + cacheMemory;
	}

	/**
	 * 
	 * ��ȡ�ֻ��Դ���ʣ��ռ�
	 */
	public static long getPhoneSelfFreeMemory() {
		File root = Environment.getRootDirectory();
		StatFs rootState = new StatFs(root.getPath());
		// ��ȡ��Ӧ��·�����ڴ��ĸ����Լ��ڴ��Ĵ�С
		long rootMemory = rootState.getAvailableBlocks()
				* rootState.getBlockSize();

		File data = Environment.getDataDirectory();
		StatFs dataState = new StatFs(data.getPath());
		// ��ȡ��Ӧ��·�����ڴ��ĸ����Լ��ڴ��Ĵ�С
		long dataMemory = dataState.getAvailableBlocks()
				* dataState.getBlockSize();

		File cache = Environment.getDownloadCacheDirectory();
		StatFs cacheState = new StatFs(cache.getPath());
		// ��ȡ��Ӧ��·�����ڴ��ĸ����Լ��ڴ��Ĵ�С
		long cacheMemory = cacheState.getAvailableBlocks()
				* cacheState.getBlockSize();

		return rootMemory + dataMemory + cacheMemory;
	}

	/**
	 * ����SD���ܿռ�
	 * 
	 */
	public static long getSDCardAllMemory(Context context) {
		if (isMountedSDCard(context)) {

			File root = Environment.getExternalStorageDirectory();
			StatFs rootState = new StatFs(root.getPath());
			// ��ȡ��Ӧ��·�����ڴ��ĸ����Լ��ڴ��Ĵ�С
			long rootMemory = rootState.getBlockCount()
					* rootState.getBlockSize();
			return rootMemory;
		} else {
			return 0;
		}
	}

	/**
	 * ����SD���Ŀ��ÿռ�
	 * 
	 */
	public static long getSDCardFreeMemory(Context context) {
		if (isMountedSDCard(context)) {
			File root = Environment.getExternalStorageDirectory();
			StatFs rootState = new StatFs(root.getPath());
			// ��ȡ��Ӧ��·�����ڴ��ĸ����Լ��ڴ��Ĵ�С
			long rootMemory = rootState.getAvailableBlocks()
					* rootState.getBlockSize();
			return rootMemory;

		} else {
			return 0;
		}

	}

	/**
	 * �ж�SD�Ƿ����
	 * 
	 */
	public static boolean isMountedSDCard(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			ToastUtil.show(context, "����SD��û���ҵ�", 0);
			return false;
		}

	}

	/**
	 * �ж�����SD���Ƿ����
	 * 
	 */
	public static String isMountedOutSDCard(Context context) {
		// ��ȡ��׿ϵͳ�Ļ�������
		Map<String, String> map = System.getenv();
		// �ж��Ƿ����õ�SD������
		if (map.containsKey("SECONDARY_STORAGE")) {
			String value = map.get("SECONDARY_STORAGE");
			String path = value.split(":")[0];
			return path;
		} else {
			ToastUtil.show(context, "����SD��û���ҵ�", 0);
			return null;
		}
	}

	/***
	 * ��ȡ����SD�����ܿռ�
	 * 
	 */
	public static long getOutSDCardAllMemory(Context context) {
		String path = isMountedOutSDCard(context);
		if (path != null) {

			File root = new File(path);
			StatFs rootState = new StatFs(root.getPath());
			// ��ȡ��Ӧ��·�����ڴ��ĸ����Լ��ڴ��Ĵ�С
			long rootMemory = rootState.getBlockCount()
					* rootState.getBlockSize();
			return rootMemory;
		} else {
			return 0;
		}
	}

	/***
	 * ��ȡ����SD���Ŀ��ÿռ�
	 * 
	 */
	public static long getOutSDCardFreeMemory(Context context) {
		String path = isMountedOutSDCard(context);
		if (path != null) {

			File root = new File(path);
			StatFs rootState = new StatFs(root.getPath());
			// ��ȡ��Ӧ��·�����ڴ��ĸ����Լ��ڴ��Ĵ�С
			long rootMemory = rootState.getAvailableBlocks()
					* rootState.getBlockSize();
			return rootMemory;
		} else {
			return 0;
		}
	}
}
