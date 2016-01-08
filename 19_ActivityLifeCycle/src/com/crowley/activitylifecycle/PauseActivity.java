package com.crowley.activitylifecycle;

import android.app.Activity;
import android.os.Bundle;

public class PauseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pause_activity);
	}
	
}
