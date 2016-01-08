package com.lmh.android.filetest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileService {
	private Context context = null;
	
	public FileService(Context context) {
		this.context = context;
	}
	
	public void saveToROM(String fileName, String content) throws IOException {
		FileOutputStream out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
		out.write(content.getBytes("utf8"));
		out.close();
	}
	
	public String readFromRom(String fileName) throws IOException {
		FileInputStream in = context.openFileInput(fileName);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int readLen = -1;
		while((readLen = in.read(buffer)) != -1) {
			out.write(buffer, 0, readLen);
		}
		String content = new String(out.toByteArray(), "utf8");
		out.close();
		in.close();
		return new String(content);
	}
	
	public void saveToSDCard(String fileName, String content) throws IOException {
		File file = new File(Environment.getExternalStorageDirectory() + "/crowley-test", fileName);
		//如果路径不存在，则创建目录！！
		File f = new File(Environment.getExternalStorageDirectory() + "/crowley-test");
		if(!f.exists()) f.mkdir();
		
		
		//Log.d("Crowley", "" + Environment.getExternalStorageDirectory() + "/documents");
		FileOutputStream out = new FileOutputStream(file);
		out.write(content.getBytes("utf8"));
		out.flush();
		out.close();
	}
	
	
	
}
