package crowley.crowley.gusture;

import java.util.List;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	private GestureLibrary library = null;
	private GestureOverlayView gestureView = null;
	private Gesture ges = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gestureView = (GestureOverlayView) this.findViewById(R.id.gesture);
		library = GestureLibraries.fromRawResource(this, R.raw.gestures);
		library.load();
		//识别单笔手势
		//gestureView.addOnGesturePerformedListener(new MyGesturePerformedListener());
		gestureView.addOnGestureListener(new MyGestureListener());
	}

	private class MyGestureListener implements OnGestureListener {

		@Override
		public void onGesture(GestureOverlayView overlay, MotionEvent event) {
			System.out.println("onGesture");
		}

		@Override
		public void onGestureCancelled(GestureOverlayView overlay,
				MotionEvent event) {
			System.out.println("onGestureCancelled");			
		}

		@Override
		public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
			System.out.println("onGestureEnded");	
			ges = overlay.getGesture();
			
		}

		@Override
		public void onGestureStarted(GestureOverlayView overlay,
				MotionEvent event) {
			System.out.println("onGestureStarted");			
		}
		
	}
	
	private class MyGesturePerformedListener implements OnGesturePerformedListener {

		@Override
		public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
			recognize(gesture);
		}
		
	} 
	
	public void find(View view) {
		recognize(ges);
		gestureView.clear(true);
	}
	
	public void recognize(Gesture gesture) {
		List<Prediction> predictions = library.recognize(gesture);
		if(!predictions.isEmpty()) {
			Prediction prediction = predictions.get(0);
			double score = prediction.score;
			if(score <= 6) {
				Toast.makeText(MainActivity.this.getApplicationContext(), "Low match:" + prediction.name + "\nscore:" + score, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(MainActivity.this.getApplicationContext(), "High match:" + prediction.name + "\nscore:" + score, Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(MainActivity.this.getApplicationContext(), "No match", Toast.LENGTH_SHORT).show();
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
		}
	}
	
}
