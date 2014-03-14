package com.lmh.android.multiactivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void openActivity(View v){
		Intent intent = new Intent(this, OtherActivity.class);
		//1>intent.setClass(this, OtherActivity.class);
		//2>intent.setClassName(this, "com.lmh.android.multiactivity.OtherActivity");
		//3>intent.setClassName("com.lmh.android.multiactivity", "com.lmh.android.multiactivity.OtherActivity");
		//4>intent.setComponent(new ComponentName(this, OtherActivity.class));
		/*intent.putExtra("name", "Crowley");
		intent.putExtra("age", 22);*/
		Bundle bundle = new Bundle();
		bundle.putString("name", "Shumanchang");
		bundle.putInt("age", 22);
		intent.putExtras(bundle);
		//跳转到其他Activity
		//startActivity(intent);
		//这样跳转Activity，当新的Activity退出之后，会返回到这个Activity，然后调用onActivityResult(...)方法
		startActivityForResult(intent, 200);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String msg = data.getStringExtra("data");
		Toast.makeText(this, "requestCode:" + requestCode + "\nresultCode:" + resultCode + "\ndata:" + msg, Toast.LENGTH_SHORT).show();
	}
}
