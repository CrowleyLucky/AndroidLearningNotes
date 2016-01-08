package com.crowley.dragandscale;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
//TODO http://blog.csdn.net/laiqun_ai/article/details/39367381
public class MainActivity extends Activity {
	private ImageView image;
	private TextView output;
	static int count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		image = (ImageView) findViewById(R.id.image);
		output = (TextView) findViewById(R.id.output);
		image.setOnTouchListener(new ImageTouchListener());
		
	}
	
	private final class ImageTouchListener implements OnTouchListener {
		PointF start = new PointF();
		Matrix matrix = new Matrix();
		Matrix currentMatrix = new Matrix();
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				output.append("\nACTION_UP" + ++count);
				break;
			case MotionEvent.ACTION_DOWN:
				currentMatrix.set(image.getImageMatrix());
				start.set(event.getX(), event.getY());
				output.append("\nACTION_DOWN" + ++count);
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				output.append("\nACTION_POINTER_DOWN" + ++count);
				break;
			case MotionEvent.ACTION_POINTER_UP:
				output.append("\nACTION_POINTER_UP" + ++count);
				break;
			case MotionEvent.ACTION_MOVE:
				matrix.set(currentMatrix);
				matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
				break;
			default:
				break;
			}
			image.setImageMatrix(matrix);
			return true;
		}
		
	}
}
