package com.crowley.tween;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private ImageView iv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iv = (ImageView) findViewById(R.id.hfy);
	}
	
	public void alpha(View view) {
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
		iv.startAnimation(animation);
	}
	
	public void translate(View view) {
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
		iv.startAnimation(animation);
	}
	
	public void scale(View view) {
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
		iv.startAnimation(animation);
	}
	
	public void rotate(View view) {
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
		iv.startAnimation(animation);
	}
}
