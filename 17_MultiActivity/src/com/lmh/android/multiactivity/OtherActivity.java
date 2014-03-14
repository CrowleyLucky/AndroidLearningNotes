package com.lmh.android.multiactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class OtherActivity extends Activity {
	private TextView content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other);
		content = (TextView) findViewById(R.id.content);
		Intent intent = getIntent();
		/*String name = intent.getStringExtra("name");
		int age = intent.getIntExtra("age", 20);*/
		Bundle bundle = intent.getExtras();
		String name = bundle.getString("name");
		int age = bundle.getInt("age");
		content.setText("–’√˚£∫" + name + ", ƒÍ¡‰£∫" + age);
	}
	
	public void closeActivity(View v){
		//Toast.makeText(this, "close activity", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();
		intent.putExtra("data", "hello crowley~");
		setResult(232, intent);
		//Call this when your activity is done and should be closed. The ActivityResult is propagated back to whoever launched you via onActivityResult().
		finish();
	}
}
