package com.crowley.activitylifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("Crowley", "onCreate");
	}
	
	public void toPause(View view) {
		Intent intent = new Intent();
		intent.setClass(this, PauseActivity.class);
		startActivity(intent);
	}
	
	public void toStop(View view) {
		Intent intent = new Intent();
		intent.setClass(this, StopActivity.class);
		startActivity(intent);
	}
	
	public void toDestory(View view) {
		this.finish();
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.d("Crowley", "onStart");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d("Crowley", "onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("Crowley", "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d("Crowley", "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d("Crowley", "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("Crowley", "onDestory");
	}

}
