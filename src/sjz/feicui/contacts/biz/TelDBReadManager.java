package sjz.feicui.contacts.biz;

import java.io.File;
import java.util.ArrayList;

import sjz.feicui.contacts.activity.TelmsgActivity;
import sjz.feicui.contacts.base.utils.ToastUtil;
import sjz.feicui.contacts.entity.ClassListInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * ��ȡcommonnum.db���ݿ���
 * 
 * 
 */
public class TelDBReadManager {
	private static File file = new File(TelmsgActivity.TOFILEPATH);

	public static boolean isExits() {
		if (file.exists() && file.length() > 0) {
			return true;
		}
		return false;

	}

	/**
	 * ��ȡClassList���ݿ���е�����
	 * 
	 * @return ������TelActivity�����ListView����ʾ������Դ
	 */
	public static ArrayList<ClassListInfo> getClassListInfo(Context context) {
		ArrayList<ClassListInfo> list = new ArrayList<ClassListInfo>();
		// ��׿�Դ���С�����ݿ�SQLiteDatabase
		/*
		 * openOrCreateDatabase�������ڴ򿪻���һ�����ݿ� ���� Ҫ�򿪵�����·�� �α깤����һ�㲻��ֱ��дnull
		 */
		if (isExits()) {
			// ��ȡ���ݿ����
			SQLiteDatabase data = SQLiteDatabase.openOrCreateDatabase(file,
					null);
			// ��ȡ����
			Cursor cursor = data.rawQuery("select * from classlist", null);// ִ��SQL���
			// ѭ������ȥ������
			while (cursor.moveToNext()) {// moveToNext�����ж��Ƿ�����һ�����ݣ������ƶ�ָ��
				String name = cursor.getString(cursor.getColumnIndex("name"));
				ClassListInfo info = new ClassListInfo(name);
				list.add(info);
			}
			// �ر�Cursor����
			cursor.close();
			// �ر����ݿ���Դ
			data.close();
			return list;
		} else {
			ToastUtil.show(context, "���ݿ�û�ҵ�", Toast.LENGTH_SHORT);
			return null;
		}

	}

	/**
	 * ��ȡClassList���ݿ���е�����
	 * 
	 * @param context
	 *            ������
	 * @return ������TelActivity�����ListView����ʾ������Դ
	 */
	public static ArrayList<ClassListInfo> getgetClassListItemInfo(
			Context context, String tablename) {
		ArrayList<ClassListInfo> list = new ArrayList<ClassListInfo>();
		if (isExits()) {
			SQLiteDatabase data = SQLiteDatabase.openOrCreateDatabase(file,
					null);
			Cursor cursor = data.query(tablename, new String[] { "_id", "name",
					"number" }, null, null, null, null, "_id asc", null);
			while (cursor.moveToNext()) {// moveToNext�����ж��Ƿ�����һ�����ݣ������ƶ�ָ��
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String number = cursor.getString(cursor
						.getColumnIndex("number"));
				int id = cursor.getInt(cursor.getColumnIndex("_id"));
				ClassListInfo info = new ClassListInfo(name, id, number);
				list.add(info);
			}
		} else {
			Toast.makeText(context, "���ݿ�û�ҵ�", Toast.LENGTH_SHORT).show();
			return null;
		}
		return list;

	}
}

