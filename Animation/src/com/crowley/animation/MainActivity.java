package com.crowley.animation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void switchActivity(View view) {
		Intent intent = new Intent(this, OtherActivity.class);
		this.startActivity(intent);
		this.overridePendingTransition(R.anim.enter_anim3, R.anim.exit_anim);
		//this.overridePendingTransition(R.anim.enter_anim2, R.anim.exit_anim);
		//this.overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
		//this.overridePendingTransition(R.anim.exit_anim, 0);
	}
}
