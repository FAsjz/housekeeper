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
 * 自定义控件，用于导航条
 * 
 * 
 * */
public class ActionBar extends LinearLayout {
	private ImageView leftImageView;
	private ImageView rightImageView;
	private TextView middleTextView;
	// 如果传递的是-1，表示不显示图片，否则显示图片
	public static final int ID_NULL = -1;

	// 代码
	public ActionBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	// XML文件
	public ActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 加载子视图
		View.inflate(context, R.layout.activity_home_actionbar, this);
		leftImageView = (ImageView) this.findViewById(R.id.iv_actionbar_left);
		middleTextView = (TextView) this.findViewById(R.id.tv_actionbar_middle);
		rightImageView = (ImageView) this.findViewById(R.id.iv_actionbar_right);
	}

	/**
	 * 初始化ActionBar,设置图片和文字
	 * */
	public void initActionbar(int left, String text, int right,
			OnClickListener on) {
		if (left == ID_NULL) {
			leftImageView.setVisibility(View.INVISIBLE);
		} else {
			leftImageView.setImageResource(left);
			// 设置点击事件
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
