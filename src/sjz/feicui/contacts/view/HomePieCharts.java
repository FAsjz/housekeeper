package sjz.feicui.contacts.view;

import java.util.Timer;
import java.util.TimerTask;

import sjz.feicui.contacts.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class HomePieCharts extends View {
	private Paint p;
	private float firstSweep,secondSweep,x;
	private int wight,height,backIndex;
	private int[] speed = {2,2,4,4,6,6,6,8,8,8};
	private RectF rectF;
	private int falg = 0;
	public HomePieCharts(Context context, AttributeSet attrs) {
		super(context, attrs);
		p = new Paint();
	
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//��ȡ�ռ��Ⱥ͸߶�
		wight = MeasureSpec.getSize(widthMeasureSpec);
		height = MeasureSpec.getSize(heightMeasureSpec);
		rectF = new RectF(0,0,wight,height);
		setMeasuredDimension(wight, height);
		
	}
	public void setSweep(final float firstSweep,final float secondSweep){
		this.firstSweep = firstSweep;
		this.secondSweep = secondSweep;
		x = firstSweep;
		falg = 0;
		new Thread(){
			public void run() {
				final Timer timer = new Timer();
				TimerTask task  =new TimerTask() {
					@Override
					public void run() {
						switch (falg) {
						case 0:
							
							x -= speed[backIndex];
							backIndex++;
							if (backIndex >= speed.length - 1) {
								backIndex = speed.length - 1;
							}
							if (x <= 8 ) {
								falg = 1;
							}
							postInvalidate();
							break;

						case 1:
							x += speed[backIndex];
							backIndex++;
							if (backIndex >= speed.length - 1) {
								backIndex = speed.length - 1;
							}
							if (x >= secondSweep ) {
								//�����ʱ��
								timer.cancel();
							}
							postInvalidate();
							break;
						}
						
						
					}
				};
				/*
				 * ִ������
				 * 		TimerTask����
				 * 		�ӳٶ���ʱ��ִ��
				 * 		�೤ʱ��ִ��һ��
				 */
				timer.schedule(task, 100, 40);
			};
		}.start();
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		p.setColor(Color.BLUE);
		canvas.drawArc(rectF, -90, x, true, p);
		
	}
}
