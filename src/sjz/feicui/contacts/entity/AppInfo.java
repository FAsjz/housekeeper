package sjz.feicui.contacts.entity;

import android.graphics.drawable.Drawable;

public class AppInfo {
	public Drawable icon;// 图标
	public String title;// 标题
	public String packageName;// 包名
	public String version;// 版本
	public boolean isChecked;// 多选框
	public boolean isAllChecked;// 全选框
	
	
	@Override
	public String toString() {
		return "AppInfo [icon=" + icon + ", title=" + title + ", packageName="
				+ packageName + ", version=" + version + ", isChecked="
				+ isChecked + "]";
	}
	public AppInfo() {
		super();
	}
	
}
