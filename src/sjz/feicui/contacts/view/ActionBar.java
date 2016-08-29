package sjz.feicui.contacts.view;

import sjz.feicui.contacts.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * �Զ���ؼ������ڵ�����
 * 
 * 
 * */
public class ActionBar extends LinearLayout {
	private ImageView leftImageView;
	private ImageView rightImageView;
	private TextView middleTextView;
	// ������ݵ���-1����ʾ����ʾͼƬ��������ʾͼƬ
	public static final int ID_NULL = -1;

	// ����
	public ActionBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	// XML�ļ�
	public ActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// ��������ͼ
		View.inflate(context, R.layout.activity_home_actionbar, this);
		leftImageView = (ImageView) this.findViewById(R.id.iv_actionbar_left);
		middleTextView = (TextView) this.findViewById(R.id.tv_actionbar_middle);
		rightImageView = (ImageView) this.findViewById(R.id.iv_actionbar_right);
	}

	/**
	 * ��ʼ��ActionBar,����ͼƬ������
	 * */
	public void initActionbar(int left, String text, int right,
			OnClickListener on) {
		if (left == ID_NULL) {
			leftImageView.setVisibility(View.INVISIBLE);
		} else {
			leftImageView.setImageResource(left);
			// ���õ���¼�
			leftImageView.setOnClickListener(on);
		}
		if (right == ID_NULL) {
			rightImageView.setVisibility(View.INVISIBLE);
		} else {
			rightImageView.setImageResource(right);
			rightImageView.setOnClickListener(on);
		}
		middleTextView.setText(text);
	}

}
