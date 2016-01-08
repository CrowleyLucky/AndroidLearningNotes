package com.crowley.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {
	private View buttons;
	private Camera camera;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置为全屏模式
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		buttons = (View) findViewById(R.id.buttons_view);
		
		SurfaceView sv = (SurfaceView) findViewById(R.id.surface_view);
		sv.getHolder().setFixedSize(400, 320);
		sv.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		sv.getHolder().setKeepScreenOn(true);
		sv.getHolder().addCallback(new MyCallBack());
		
	}

	private final class MyCallBack implements Callback {

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				camera = Camera.open();
				Camera.Parameters parameters = camera.getParameters();
				//System.out.println(parameters.flatten());
				parameters.setPreviewSize(800, 480);
				//parameters.setPreviewFrameRate(3);
				parameters.setPictureSize(1024, 768);
				parameters.setJpegQuality(80);
				//TODO 不知道为啥，重新设置参数了之后，打开相机会死机！！！！！
				//camera.setParameters(parameters);
				camera.setPreviewDisplay(holder);
				camera.startPreview();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			if(null != camera) {
				camera.release();
				camera = null;
			}
		}
		
	}
	
	public void takePhotos(View v) {
		switch (v.getId()) {
		case R.id.auto_focus:
			camera.autoFocus(null);
			break;

		case R.id.shot:
			camera.takePicture(null, null, new MyPictureCallback());
			break;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			buttons.setVisibility(ViewGroup.VISIBLE);
			return true;
		}
		return super.onTouchEvent(event);
	}
	
	private final class MyPictureCallback implements PictureCallback {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			try {
				File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
				FileOutputStream out = new FileOutputStream(file);
				out.write(data);
				out.flush();
				out.close();
				System.out.println("took a photo...");
				camera.startPreview();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
	}
}
