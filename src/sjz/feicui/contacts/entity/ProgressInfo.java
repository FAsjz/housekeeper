package sjz.feicui.contacts.entity;

import android.graphics.drawable.Drawable;

public class ProgressInfo {
	public Drawable icon;//ͼ��
	public String title;//����
	public String packageName;//����
	public boolean isSystem;//��ʾ�Ƿ���ϵͳ����
	public boolean isChecked;//��ѡ���Ƿ�ѡ��
	public double memory;//�ڴ�
	
	public ProgressInfo() {
		super();
	}
	@Override
	public String toString() {
		return "ProgressInfo [icon=" + icon + ", title=" + title
				+ ", isChecked=" + isChecked + ", memory=" + memory + "]";
	}
}
