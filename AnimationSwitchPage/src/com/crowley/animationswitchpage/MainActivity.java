package com.crowley.animationswitchpage;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {
	private ViewFlipper flipper;
	private float posX;
	private float posY;
	private Animation inLeftToRight;
	private Animation outLeftToRight;
	private Animation inRightToLeft;
	private Animation outRightToLeft;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		flipper = (ViewFlipper) findViewById(R.id.view_flipper);
		inLeftToRight = AnimationUtils.loadAnimation(this, R.anim.in_left_to_right);
		outLeftToRight = AnimationUtils.loadAnimation(this, R.anim.out_left_to_right);
		inRightToLeft = AnimationUtils.loadAnimation(this, R.anim.in_right_to_left);
		outRightToLeft = AnimationUtils.loadAnimation(this, R.anim.out_right_to_left);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			posX = event.getX();
			posY = event.getY();
		} else if(event.getAction() == MotionEvent.ACTION_UP) {
			if(event.getX() - posX > 50 && Math.abs(event.getY() - posY) < 30) {
				//slip from left to right
				flipper.setInAnimation(inLeftToRight);
				flipper.setOutAnimation(outLeftToRight);
				flipper.showPrevious();
			} else if(posX - event.getX() > 50 && Math.abs(event.getY() - posY) < 30) {
				//slip from right to left
				flipper.setInAnimation(inRightToLeft);
				flipper.setOutAnimation(outRightToLeft);
				flipper.showNext();
			}
		}
		
		
		return super.onTouchEvent(event);
	}
	
	
	
}
