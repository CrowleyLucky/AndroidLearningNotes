package com.lmh.android.mobilenumberquery;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadService {

	public static byte[] readFromStream(InputStream in) throws IOException {
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int len = 0;
		while((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		byte[] bytes = out.toByteArray();
		out.close();
		return bytes;
	}
	
}
