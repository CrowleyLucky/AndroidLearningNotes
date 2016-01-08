package com.crowley.asynctaskandhandler;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressAsyncTask extends AsyncTask<Integer, Integer, String> {
	private ProgressBar asyncTaskPB;
	private TextView asyncText;
	
	
	public ProgressAsyncTask(ProgressBar asyncTaskPB, TextView asyncText) {
		this.asyncTaskPB = asyncTaskPB;
		this.asyncText = asyncText;
	}

	@Override
	protected String doInBackground(Integer... params) {
		System.out.println("into doInBackground() ...");
		asyncTaskPB.setMax(params[0]);
		asyncTaskPB.setProgress(0);
		
		for(int i = 0; i < asyncTaskPB.getMax(); i++) {
			NetOperation.operate();
			asyncTaskPB.setProgress(asyncTaskPB.getProgress() + 1);
			publishProgress(asyncTaskPB.getProgress());
		}
		
		
		System.out.println("doInBackground() finished ...");
		return "resultString";
	}

	@Override
	protected void onPostExecute(String result) {
		System.out.println("onPostExecute()");
	}

	@Override
	protected void onPreExecute() {
		System.out.println("onPreExecute()");
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		asyncText.setText("AsyncTaskProgress:" + values[0]);
		System.out.println("onProgressUpdate()");
	}

	 
}
