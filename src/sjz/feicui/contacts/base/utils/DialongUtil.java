package sjz.feicui.contacts.base.utils;

import sjz.feicui.contacts.adapter.TelmsgListAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.util.Log;

/**
 * �Ի��򹤾���
 * */
public final class DialongUtil {

	public static void show(final Context context, String title,
			String message, final TelmsgListAdapter mAdapter, final int position) {
		// �����Ի���
		new AlertDialog.Builder(context).setTitle(title).setMessage(message)
				.setPositiveButton("����", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent it = new Intent(Intent.ACTION_CALL);
						it.setData(Uri
								.parse("tel:"
										+ mAdapter.getDataFromAdapter().get(
												position).number));
						Log.i("tag", mAdapter.getDataFromAdapter().get(
								position).number+"");
						context.startActivity(it);
					}
				}).setNegativeButton("ȡ��", null).show();
	}
}
