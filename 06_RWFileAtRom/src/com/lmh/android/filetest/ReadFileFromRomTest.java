package com.lmh.android.filetest;

import java.io.IOException;

import android.test.AndroidTestCase;
import android.util.Log;

public class ReadFileFromRomTest extends AndroidTestCase {
	
	public void testReadFileFromRom() throws IOException {
		FileService service = new FileService(this.getContext());
		Log.d("TAG", service.readFromRom("lmh.txt"));
	}
	
	public void testReadFileFromSDCard() {
		//TODO
	}
}
	