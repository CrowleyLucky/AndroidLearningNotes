package com.crowley.mp3player;

import java.io.File;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

public class MP3Service extends Service {
	private IBinder binder = new ServiceBinder();
	private String fileName;
	private MediaPlayer player;
	private int pos = 0;
	private boolean fileIsSet = false;

	@Override
	public IBinder onBind(Intent intent) {
		
		return binder;
	}
	
	public boolean setFileName(String fileName) {
		try {
			if(true == fileIsSet && null != fileName && fileName.equals(this.fileName)) {
				//already set, do nothing
			} else {
				this.fileName = fileName;
				File file = new File(Environment.getExternalStorageDirectory(), fileName);
				String path = file.getAbsolutePath();
				if(file.exists()) {
					player = new MediaPlayer();
					player.setDataSource(path);
					player.prepare();
					System.out.println("mp:" + player.toString());
				} else{
					//Toast.makeText(, "file is not exist!", Toast.LENGTH_SHORT).show();
					return false;
				}
				fileIsSet = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean playMP3() {
		if(!fileIsSet) return false;
		player.seekTo(pos);
		player.start();
		return true;
	}
	
	public boolean replayMP3() {
		if(!fileIsSet) return false;
		try {
			player.seekTo(0);
			player.start();
			pos = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean stopMP3() {
		if(!fileIsSet) return false;
		player.release();
		fileIsSet = false;
		pos = 0;
		return true;
	}
	
	public boolean pauseMP3() {
		if(!fileIsSet) return false;
		pos = player.getCurrentPosition();
		player.pause();
		return true;
	}
	
	public int getTotalTime() {
		return player.getDuration() / 1000;
	}
	
	public boolean isLoop() {
		return player.isLooping();
	}
	
	public int getTimeRemain() {
		return (player.getDuration() - player.getCurrentPosition()) / 1000;
	}
	
	class ServiceBinder extends Binder {

		public MP3Service getMP3Service() {
			return MP3Service.this;
		}
	}
	
}