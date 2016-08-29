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
 * 读取commonnum.db数据库类
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
	 * 获取ClassList数据库表中的数据
	 * 
	 * @return 用于在TelActivity界面的ListView中显示的数据源
	 */
	public static ArrayList<ClassListInfo> getClassListInfo(Context context) {
		ArrayList<ClassListInfo> list = new ArrayList<ClassListInfo>();
		// 安卓自带的小型数据库SQLiteDatabase
		/*
		 * openOrCreateDatabase函数用于打开或创造一个数据库 参数 要打开的数据路径 游标工厂，一般不用直接写null
		 */
		if (isExits()) {
			// 获取数据库对象
			SQLiteDatabase data = SQLiteDatabase.openOrCreateDatabase(file,
					null);
			// 读取数据
			Cursor cursor = data.rawQuery("select * from classlist", null);// 执行SQL语句
			// 循环集合去除数据
			while (cursor.moveToNext()) {// moveToNext函数判断是否还有下一个数据，并且移动指针
				String name = cursor.getString(cursor.getColumnIndex("name"));
				ClassListInfo info = new ClassListInfo(name);
				list.add(info);
			}
			// 关闭Cursor对象
			cursor.close();
			// 关闭数据库资源
			data.close();
			return list;
		} else {
			ToastUtil.show(context, "数据库没找到", Toast.LENGTH_SHORT);
			return null;
		}

	}

	/**
	 * 获取ClassList数据库表中的数据
	 * 
	 * @param context
	 *            上下文
	 * @return 用语在TelActivity界面的ListView中显示的数据源
	 */
	public static ArrayList<ClassListInfo> getgetClassListItemInfo(
			Context context, String tablename) {
		ArrayList<ClassListInfo> list = new ArrayList<ClassListInfo>();
		if (isExits()) {
			SQLiteDatabase data = SQLiteDatabase.openOrCreateDatabase(file,
					null);
			Cursor cursor = data.query(tablename, new String[] { "_id", "name",
					"number" }, null, null, null, null, "_id asc", null);
			while (cursor.moveToNext()) {// moveToNext函数判断是否还有下一个数据，并且移动指针
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String number = cursor.getString(cursor
						.getColumnIndex("number"));
				int id = cursor.getInt(cursor.getColumnIndex("_id"));
				ClassListInfo info = new ClassListInfo(name, id, number);
				list.add(info);
			}
		} else {
			Toast.makeText(context, "数据库没找到", Toast.LENGTH_SHORT).show();
			return null;
		}
		return list;

	}
}

