package com.crowley.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

public class SingleInstanceActivity extends ActionBarActivity {
	private TextView edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.standard_main);
		edit = (TextView) findViewById(R.id.edit1);
		
		edit.setText("" + this + "\n" + this.getTaskId());
	}
	
	
	
	public void startStandard(View view) {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
	}

	public void startSingleTop(View view) {
		Intent intent = new Intent();
		intent.setClass(this, SingleTopActivity.class);
		startActivity(intent);
	}

	public void startSingleTask(View view) {
		Intent intent = new Intent();
		intent.setClass(this, SingleTaskActivity.class);
		startActivity(intent);
	}

	public void startSingleInstance(View view) {
		Intent intent = new Intent();
		intent.setClass(this, SingleInstanceActivity.class);
		startActivity(intent);
	}

	
}
