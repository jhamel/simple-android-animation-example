package com.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;

public class MyView extends View {

	/** Triggers refresh of view */
	class RefreshHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			MyView.this.update();
			MyView.this.invalidate();
		}

		public void sleep(long delayMillis) {
			this.removeMessages(0);
			sendMessageDelayed(obtainMessage(0), delayMillis);
		}
	}

	private static final int NUMBER_OF_PIXELS_PER_MOVEMENT = 4;

	private static final int REFRESH_RATE_FOR_VIEW_IN_MILLISECS = 5;

	ShapeDrawable ball;
	int ballHeight = 50;
	int ballWidth = 50;
	int ballXPos = 10;
	int ballYPos = 10;

	ShapeDrawable rectangle;

	private final RefreshHandler refreshHandler = new RefreshHandler();

	public MyView(Context context) {
		super(context);

		setFocusable(true); // must be set, otherwise key events won't be
							// triggered

		ball = new ShapeDrawable(new OvalShape());
		ball.getPaint().setColor(0xff777777);

		rectangle = new ShapeDrawable(new RectShape());

		update();

	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {

		case KeyEvent.KEYCODE_DPAD_UP:
			ballYPos = ballYPos - NUMBER_OF_PIXELS_PER_MOVEMENT;
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			ballYPos = ballYPos + NUMBER_OF_PIXELS_PER_MOVEMENT;
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			ballXPos = ballXPos - NUMBER_OF_PIXELS_PER_MOVEMENT;
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			ballXPos = ballXPos + NUMBER_OF_PIXELS_PER_MOVEMENT;
			break;

		default:
			return super.onKeyDown(keyCode, event);
		}

		update();
		return true;

	}

	public void update() {

		ball.setBounds(ballXPos, ballYPos, ballXPos + ballWidth, ballYPos
				+ ballHeight);
		rectangle.setBounds(10, 20, 30, 40);

		// change rectangle color on collison with ball

		if (Rect.intersects(ball.getBounds(), rectangle.getBounds())) {
			rectangle.getPaint().setColor(0xff0099ff);
		} else {
			rectangle.getPaint().setColor(0xffff9900);
		}

		refreshHandler.sleep(REFRESH_RATE_FOR_VIEW_IN_MILLISECS);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		ball.draw(canvas);
		rectangle.draw(canvas);
	}
}
