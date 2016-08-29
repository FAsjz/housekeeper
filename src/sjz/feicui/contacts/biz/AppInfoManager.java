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
 * Ӧ����Ϣ������ ��ȡ����Ӧ����Ϣ
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
			// ��ȡ��������
			pm = context.getPackageManager();
		}
		if (am == null) {
			// ��ȡ��������
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
	 * ��ȡ�������е�Ӧ�ó�����Ϣ����
	 */
	public  HashMap<Integer, ArrayList<ProgressInfo>> getProgressInfos(
			Context context) {
		HashMap<Integer, ArrayList<ProgressInfo>> map = new HashMap<Integer, ArrayList<ProgressInfo>>();
		ArrayList<ProgressInfo> sysapp = new ArrayList<ProgressInfo>();
		ArrayList<ProgressInfo> userapp = new ArrayList<ProgressInfo>();
		// ��ȡ������Ϣ��Ҫ�õ�ActivityManager
		am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
//		ArrayList<RunningAppProcessInfo> runningApp = (ArrayList<RunningAppProcessInfo>) am
//				.getRunningAppProcesses();
		 List<AndroidAppProcess> processInfos = ProcessManager.getRunningAppProcesses();
//		for (RunningAppProcessInfo appProcessInfo : runningApp) {
//			String packageName = appProcessInfo.processName;// �������г� �������
//			int pid = appProcessInfo.pid; // �������г������ID
//			int importance = appProcessInfo.importance; // �������г������ ����
			for (AndroidAppProcess processInfo : processInfos) {
				// ��������������
				ProgressInfo pInfo = new ProgressInfo();
				String packageName = processInfo.name;// �������г� �������
				// ��ȡӦ�ó�����ڴ� ��Ϣ
	            android.os.Debug.MemoryInfo[] memoryInfos = am
	                    .getProcessMemoryInfo(new int[] { processInfo.pid });
				long memsize = memoryInfos[0].getTotalPrivateDirty() * 1024L;
				pInfo.memory =  CommonUtil.byteCastMB(memsize);
				pInfo.packageName = packageName;
	            try {
	                // ��ȡӦ�ó�����Ϣ
	                ApplicationInfo applicationInfo = pm.getApplicationInfo(
	                		packageName, 0);
	                Drawable icon = applicationInfo.loadIcon(pm);
	                pInfo.icon = icon;
	                String name = applicationInfo.loadLabel(pm).toString();
	                pInfo.title = name;
	                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
	                    // �û�����
	                	pInfo.isSystem = false;
	                	userapp.add(pInfo);
	                } else {
	                    // ϵͳ����
	                	pInfo.isSystem = true;
	                	sysapp.add(pInfo);
	                }
	                map.put(RUNING_APP_TYPE_SYS, sysapp);
					map.put(RUNING_APP_TYPE_USER, userapp);
	            } catch (NameNotFoundException e) {
	                // TODO Auto-generated catch block
	            	LogUtil.d("NameNotFoundException","��ȡ�˴���Ϣ�쳣");
	                e.printStackTrace();
	                // ϵͳ�ں˽��� û������
//	                pInfo.setName(packname);
//	                Drawable icon = context.getResources().getDrawable(
//	                        R.drawable.default_icon);
//	                pInfo.setIcon(icon);
	            }
//	            if (pInfo != null) {
//	                pInfos.add(pInfo);
//	            }
//				
			// ������̣��������������½���
			/*if (importance >= RunningAppProcessInfo.IMPORTANCE_SERVICE) {
				// ��������������
				ProgressInfo pInfo = new ProgressInfo();
				// ��ȡӦ����Ϣ����
				 List<AndroidAppProcess> processInfos = ProcessManager.getRunningAppProcesses();
				try {
					ApplicationInfo appInfo = pm.getApplicationInfo(packageName,
							PackageManager.GET_META_DATA);
					// ��ȡͼ��
					Drawable icon = pm.getApplicationIcon(packageName);
					// ��ȡӦ����
					String label = pm.getApplicationLabel(appInfo).toString();
					// ��ȡ�ڴ��С
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
					// �ж���ϵͳӦ�û����û�Ӧ��
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
					LogUtil.d("NameNotFoundException","��ȡ�˴���Ϣ�쳣");
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

		// ��ȡӦ����Ϣ
		for (int i = 0; i < packageInfos.size(); i++) {
			AppInfo info = new AppInfo();
			PackageInfo packageInfo = packageInfos.get(i);

			// ��ȡApplicationInfo
			ApplicationInfo appInfo = packageInfo.applicationInfo;

			info.title = appInfo.loadLabel(pm).toString();
			info.icon = appInfo.loadIcon(pm);
			info.version = packageInfo.versionName;
			info.packageName = packageInfo.packageName;
			// �ж���ϵͳӦ�û����û�Ӧ��
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
