package wl.android;

import java.io.Console;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class activity030 extends Activity {
	Button btnSimpleDraw, btnTimerDraw;
	SurfaceView sfv;
	SurfaceHolder sfh;
	private Timer mTimer;
	private MyTimerTask mTimerTask;
	int Y_axis[],// �������Ҳ���Y ���ϵĵ�
		centerY,// ������
		oldX, oldY,// ��һ��XY ��
		currentX;// ��ǰ���Ƶ���X ���ϵĵ�

	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnSimpleDraw = (Button) this.findViewById(R.id.Button1);
		btnTimerDraw = (Button) this.findViewById(R.id.Button2);
		btnSimpleDraw.setOnClickListener(new ClickEvent());
		btnTimerDraw.setOnClickListener(new ClickEvent());
		sfv = (SurfaceView) this.findViewById(R.id.SurfaceView01);
		sfh = sfv.getHolder();
		// ��̬�������Ҳ��Ķ�ʱ��
		mTimer = new Timer();
		mTimerTask = new MyTimerTask();
		// ��ʼ��y ������
		centerY = (getWindowManager().getDefaultDisplay().getHeight() - sfv
				.getTop()) / 2;
		
		Y_axis = new int[getWindowManager().getDefaultDisplay().getWidth()];

		for (int i = 1; i < Y_axis.length; i++) {// �������Ҳ�
			Y_axis[i - 1] = centerY
					- (int) (100 * Math.sin(i * 2 * Math.PI / 180));
		}
	}

	class ClickEvent implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			if (v == btnSimpleDraw) {
				SimpleDraw(Y_axis.length - 1);// ֱ�ӻ������Ҳ�
			} else if (v == btnTimerDraw) {
				oldY = centerY;
				mTimer.schedule(mTimerTask, 0, 1);// ��̬�������Ҳ�
			}
		}
	}

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			SimpleDraw(currentX);
			currentX++;// ��ǰ��
			if (currentX == Y_axis.length - 1) {// ��������յ㣬����������
				ClearDraw();
				currentX = 0;
				oldY = centerY;
			}
		}
	}

	/*
	 * ����ָ������
	 */
	void SimpleDraw(int length) {
		if (length == 0)
			oldX = 0;
//		Canvas canvas = sfh.lockCanvas(new Rect(oldX, 0, oldX + length,
		Canvas canvas = sfh.lockCanvas(new Rect(oldX, 0, oldX + 2,
				getWindowManager().getDefaultDisplay().getHeight()));
		// �ؼ�:��ȡ����
		Log.i("Canvas:",
				String.valueOf(oldX) + "," + String.valueOf(oldX + length));

		Paint mPaint = new Paint();
		mPaint.setColor(Color.GREEN);// ����Ϊ��ɫ
		mPaint.setStrokeWidth(5);// ���û��ʴ�ϸ
		int y;
		for (int i = oldX + 1; i < length; i++) {// �滭���Ҳ�
			y = Y_axis[i - 1];
			canvas.drawLine(oldX, oldY, i, y, mPaint);
			oldX = i;
			oldY = y;
		}
		Log.i("Canvas2:", String.valueOf(oldX)+	"," + String.valueOf(length));

		sfh.unlockCanvasAndPost(canvas);// �����������ύ���õ�ͼ��
	}

	void ClearDraw() {
		Canvas canvas = sfh.lockCanvas(null);
		canvas.drawColor(Color.BLACK);// �������
		sfh.unlockCanvasAndPost(canvas);
	}

}