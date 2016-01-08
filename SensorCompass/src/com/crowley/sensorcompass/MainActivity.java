package com.crowley.sensorcompass;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private SensorManager manager ;
	private MySensorEventListener listener = new MySensorEventListener();
	private TextView param;
	private ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		manager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE); 
		param = (TextView) findViewById(R.id.param);
		imageView = (ImageView) findViewById(R.id.image);
	}

	@Override
	protected void onResume() {
		Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);
		super.onResume();
	}

	@Override
	protected void onPause() {
		manager.unregisterListener(listener);
		super.onPause();
	}
	
	private final class MySensorEventListener implements SensorEventListener {
		float preDegree = 0;
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			float degree = event.values[0];
			RotateAnimation animation = new RotateAnimation(preDegree, -degree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			animation.setDuration(100);
			imageView.startAnimation(animation);
			preDegree = -degree;
			param.setText("x:" + event.values[0] + "    " + "y:" + event.values[1] + "    " + "z:" + event.values[2]);
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			System.out.println("onAccuracyChanged()  accuracy=" + accuracy);
		}
	}
	
}
