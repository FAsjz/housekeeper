package sjz.feicui.contacts.entity;

import android.graphics.drawable.Drawable;

public class AppInfo {
	public Drawable icon;// ͼ��
	public String title;// ����
	public String packageName;// ����
	public String version;// �汾
	public boolean isChecked;// ��ѡ��
	public boolean isAllChecked;// ȫѡ��
	
	
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
