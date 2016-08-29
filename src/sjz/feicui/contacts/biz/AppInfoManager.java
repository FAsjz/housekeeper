package sjz.feicui.contacts.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import sjz.feicui.contacts.base.utils.CommonUtil;
import sjz.feicui.contacts.base.utils.LogUtil;
import sjz.feicui.contacts.entity.AppInfo;
import sjz.feicui.contacts.entity.ProgressInfo;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Debug;

/**
 * 应用信息管理类 获取所有应用信息
 */
public class AppInfoManager {

	private PackageManager pm;
	private ArrayList<AppInfo> allPackageInfos = new ArrayList<AppInfo>();
	private ArrayList<AppInfo> sysPackageInfos = new ArrayList<AppInfo>();
	private ArrayList<AppInfo> userPackageInfos = new ArrayList<AppInfo>();
	public static final int RUNING_APP_TYPE_SYS = 0;
	public static final int RUNING_APP_TYPE_USER = 1;
	private static ActivityManager am;

	public AppInfoManager(Context context) {
		if (pm == null) {
			// 获取包管理器
			pm = context.getPackageManager();
		}
		if (am == null) {
			// 获取包管理器
			am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		}
	}

	public PackageManager getPm() {
		return pm;
	}

	public static ActivityManager getAm() {
		return am;
	}

	public ArrayList<AppInfo> getUserPackageInfos() {
		return userPackageInfos;
	}

	/*
	 * 
	 * 获取正在运行的应用程序信息集合
	 */
	public  HashMap<Integer, ArrayList<ProgressInfo>> getProgressInfos(
			Context context) {
		HashMap<Integer, ArrayList<ProgressInfo>> map = new HashMap<Integer, ArrayList<ProgressInfo>>();
		ArrayList<ProgressInfo> sysapp = new ArrayList<ProgressInfo>();
		ArrayList<ProgressInfo> userapp = new ArrayList<ProgressInfo>();
		// 获取进程信息需要用到ActivityManager
		am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
//		ArrayList<RunningAppProcessInfo> runningApp = (ArrayList<RunningAppProcessInfo>) am
//				.getRunningAppProcesses();
		 List<AndroidAppProcess> processInfos = ProcessManager.getRunningAppProcesses();
//		for (RunningAppProcessInfo appProcessInfo : runningApp) {
//			String packageName = appProcessInfo.processName;// 正在运行程 序进程名
//			int pid = appProcessInfo.pid; // 正在运行程序进程ID
//			int importance = appProcessInfo.importance; // 正在运行程序进程 级别
			for (AndroidAppProcess processInfo : processInfos) {
				// 创建对象存放数据
				ProgressInfo pInfo = new ProgressInfo();
				String packageName = processInfo.name;// 正在运行程 序进程名
				// 获取应用程序的内存 信息
	            android.os.Debug.MemoryInfo[] memoryInfos = am
	                    .getProcessMemoryInfo(new int[] { processInfo.pid });
				long memsize = memoryInfos[0].getTotalPrivateDirty() * 1024L;
				pInfo.memory =  CommonUtil.byteCastMB(memsize);
				pInfo.packageName = packageName;
	            try {
	                // 获取应用程序信息
	                ApplicationInfo applicationInfo = pm.getApplicationInfo(
	                		packageName, 0);
	                Drawable icon = applicationInfo.loadIcon(pm);
	                pInfo.icon = icon;
	                String name = applicationInfo.loadLabel(pm).toString();
	                pInfo.title = name;
	                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
	                    // 用户进程
	                	pInfo.isSystem = false;
	                	userapp.add(pInfo);
	                } else {
	                    // 系统进程
	                	pInfo.isSystem = true;
	                	sysapp.add(pInfo);
	                }
	                map.put(RUNING_APP_TYPE_SYS, sysapp);
					map.put(RUNING_APP_TYPE_USER, userapp);
	            } catch (NameNotFoundException e) {
	                // TODO Auto-generated catch block
	            	LogUtil.d("NameNotFoundException","获取运存信息异常");
	                e.printStackTrace();
	                // 系统内核进程 没有名称
//	                pInfo.setName(packname);
//	                Drawable icon = context.getResources().getDrawable(
//	                        R.drawable.default_icon);
//	                pInfo.setIcon(icon);
	            }
//	            if (pInfo != null) {
//	                pInfos.add(pInfo);
//	            }
//				
			// 服务进程（包括）级别以下进程
			/*if (importance >= RunningAppProcessInfo.IMPORTANCE_SERVICE) {
				// 创建对象存放数据
				ProgressInfo pInfo = new ProgressInfo();
				// 获取应用信息对象
				 List<AndroidAppProcess> processInfos = ProcessManager.getRunningAppProcesses();
				try {
					ApplicationInfo appInfo = pm.getApplicationInfo(packageName,
							PackageManager.GET_META_DATA);
					// 获取图标
					Drawable icon = pm.getApplicationIcon(packageName);
					// 获取应用名
					String label = pm.getApplicationLabel(appInfo).toString();
					// 获取内存大小
					Debug.MemoryInfo[] dm = am
							.getProcessMemoryInfo(new int[] { pid });
					Debug.MemoryInfo i = dm[0];
					double size = CommonUtil.byteCastMB(i
							.getTotalPrivateDirty() * 1024);
					LogUtil.d("getTotalPrivateDirty",icon+","+size);
					pInfo.packageName = packageName;
					pInfo.icon = icon;
					pInfo.title = label;
					pInfo.memory = size;
					// 判断是系统应用还是用户应用
					if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
						pInfo.isSystem = true;
						sysapp.add(pInfo);
					} else {
						pInfo.isSystem = false;
						userapp.add(pInfo);
					}
					map.put(RUNING_APP_TYPE_SYS, sysapp);
					map.put(RUNING_APP_TYPE_USER, userapp);
				} catch (NameNotFoundException e) {
					LogUtil.d("NameNotFoundException","获取运存信息异常");
					e.printStackTrace();
				}
			}*/
		}
		return map;
	}

	public void addAllPackageInfos() {
		List<PackageInfo> packageInfos = pm
				.getInstalledPackages(PackageManager.GET_ACTIVITIES
						| PackageManager.GET_UNINSTALLED_PACKAGES);

		// 获取应用信息
		for (int i = 0; i < packageInfos.size(); i++) {
			AppInfo info = new AppInfo();
			PackageInfo packageInfo = packageInfos.get(i);

			// 获取ApplicationInfo
			ApplicationInfo appInfo = packageInfo.applicationInfo;

			info.title = appInfo.loadLabel(pm).toString();
			info.icon = appInfo.loadIcon(pm);
			info.version = packageInfo.versionName;
			info.packageName = packageInfo.packageName;
			// 判断是系统应用还是用户应用
			if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
				sysPackageInfos.add(info);

			} else {
				userPackageInfos.add(info);
			}
			allPackageInfos.add(info);
		}
	}

	public ArrayList<AppInfo> getAllPackageInfos() {
		return allPackageInfos;
	}

	public ArrayList<AppInfo> getSysPackageInfos() {
		return sysPackageInfos;
	}

}
