package sjz.feicui.contacts.entity;

import android.graphics.drawable.Drawable;

public class ProgressInfo {
	public Drawable icon;//图标
	public String title;//标题
	public String packageName;//包名
	public boolean isSystem;//显示是否是系统进程
	public boolean isChecked;//多选框是否选中
	public double memory;//内存
	
	public ProgressInfo() {
		super();
	}
	@Override
	public String toString() {
		return "ProgressInfo [icon=" + icon + ", title=" + title
				+ ", isChecked=" + isChecked + ", memory=" + memory + "]";
	}
}
