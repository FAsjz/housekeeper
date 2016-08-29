package sjz.feicui.contacts.biz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

import sjz.feicui.contacts.base.utils.LogUtil;
import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build;
import android.util.DisplayMetrics;

/**
 * 
 * 获取手机信息
 * 
 * @author Administrator
 * 
 */
public class TelPhoneInfoManager {
	//是否root
	public static boolean isRoot(){
		boolean bool = false;
		if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
			bool =false;
		}else{
			bool =true;
		}
		return bool;
		
	}
	//获取相机分辨率
	public static String getCameraMetrics(){
		Camera camera = Camera.open();
		if (camera == null) {
			return "无后置摄像头";
		}
		Parameters param = camera.getParameters();
		List<Size> sizes = param.getSupportedPictureSizes();
		Size size = sizes.get(0);
		for (Size s : sizes) {
			if (size.height * size.width < s.height * s.width) {
				size = s;
			}
		}
		camera.release();
		camera = null;
		return size.width + "*" + size.height;
		
	}
	
	
	//获取手机cpu数量
	public static int getCPUNumber(){
		File file = new File("sys/devices/system/cpu/");
		File[] cpus = file.listFiles(new FileFilter(){

			@Override
			public boolean accept(File arg0) {
				if (Pattern.matches("cpu[0-9]", arg0.getName())) {
					return true;
					 }
				return false;
			}
			
		});
		return cpus.length;
	}
	// 获取手机cpu名称
	public static String getCpuInfo() {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader("/proc/cpuinfo");
			br = new BufferedReader(fr);
			String text = br.readLine();
			String[] array = text.split(":\\s+", 2);
			for (int i = 0; i < array.length; i++) {
			}
			return array[1];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fr != null)
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;
	}

	// 获取手机基带版本
	public static String getBasebandVersion() {

		try {
			Class cl = Class.forName("android.os.SystemProperties");
			Object invoker = cl.newInstance();
			Method m = cl.getMethod("get", new Class[] { String.class,
					String.class });
			Object result = m.invoke(invoker, new Object[] {
					"gsm.version.baseband", "no message" });

			return (String) result;
		} catch (Exception e) {

			LogUtil.d("basebandVersion", "11111");
		}
		return null;

	}

	// 获取手机分辨率信息
	public static String getDpi(Activity act) {
		DisplayMetrics dm = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(dm);

		int width = dm.widthPixels;
		int height = dm.heightPixels;
		return width + "*" + height;
	}

	/*
	 * 获取手机品牌
	 */
	public static String getPhoneBrand() {
		return Build.BRAND;
	}

	/*
	 * 获取手机型号
	 */
	public static String getPhoneModel() {
		return Build.MODEL;
	}

	/*
	 * 获取手机版本号
	 */
	public static String getPhoneVersion() {
		return Build.VERSION.RELEASE;
	}

}
