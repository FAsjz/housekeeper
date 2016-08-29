package sjz.feicui.contacts.view;

import java.util.Timer;
import java.util.TimerTask;

import sjz.feicui.contacts.R;
import sjz.feicui.contacts.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class PieCharts extends View {
	private Paint p;
	private float phoneSweep,SDSweep,x,y;
	private int wight,height,violet,yellow;
	private RectF rectF;
	public PieCharts(Context context, AttributeSet attrs) {
		super(context, attrs);
		p = new Paint();
		violet = color.Fuchsia;
		yellow = color.yellow;
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//获取空间宽度和高度
		wight = MeasureSpec.getSize(widthMeasureSpec);
		height = MeasureSpec.getSize(heightMeasureSpec);
		rectF = new RectF(0,0,wight,height);
		setMeasuredDimension(wight, height);
		
	}
	public void setSweep(float phoneSweep,float SDSweep){
		this.phoneSweep = phoneSweep;
		this.SDSweep = SDSweep;
		invalidate();
		new Thread(){
			public void run() {
				final Timer timer = new Timer();
				TimerTask task  =new TimerTask() {
					
					@Override
					public void run() {
						if (x <= PieCharts.this.phoneSweep) {
							x++;
						}
						if (y <= PieCharts.this.SDSweep) {
							y++;
						}
						if (x == PieCharts.this.phoneSweep && y == PieCharts.this.SDSweep) {
							//清除计时器
							timer.cancel();
						}
						
						postInvalidate();
					}
				};
				/*
				 * 执行任务
				 * 		TimerTask函数
				 * 		延迟多少时间执行
				 * 		多长时间执行一次
				 */
				timer.schedule(task, 100, 30);
			};
		}.start();
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		//总背景色
		p.setColor(getResources().getColor(R.color.orange));
		canvas.drawArc(rectF, -90, 360, true, p);
		//手机内存所占面积
		p.setColor(getResources().getColor(R.color.BlueViolet));
		canvas.drawArc(rectF, -90, x, true, p);
		//sd卡占用面积
		p.setColor(getResources().getColor(R.color.green));
		canvas.drawArc(rectF, phoneSweep-90, y, true, p);
	}
}
