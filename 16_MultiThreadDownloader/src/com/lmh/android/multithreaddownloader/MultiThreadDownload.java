package com.lmh.android.multithreaddownloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Multithreading download, but it does not provides breakpoint downloading.
 * @author Administrator
 *
 */
public class MultiThreadDownload {

	public static void main(String[] args) throws Exception {
		String urlPath = "http://172.23.8.65/AndroidService/Angela-Chang_visible-wings.flac";
		new MultiThreadDownload().download(urlPath, 5);
	}

	private void download(String path, int threadSize) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
		//int length = conn.getContentLength();
		int length = Integer.valueOf(conn.getHeaderField("Content-Length"));
		int block = (int) Math.ceil(length / threadSize);
		File file = new File(getFileName(path));
		RandomAccessFile randomFile = new RandomAccessFile(file, "rwd");
		randomFile.setLength(length);
		randomFile.close();
		for(int threadId = 0; threadId < threadSize; threadId ++) {
			new DownloadThread(threadId, block, file, path).start();
		}
	}
	
	private class DownloadThread extends Thread {
		private int threadId;
		private int block;
		private File file;
		private String downloadPath;
		
		public DownloadThread(int threadId, int block, File file, String downloadPath) {
			this.threadId = threadId;
			this.block = block;
			this.file = file;
			this.downloadPath = downloadPath;
		}
		
		@Override
		public void run() {
			int startPosition = threadId * block;
			int endPosition = startPosition + block - 1;
			RandomAccessFile accessFile = null;
			try {
				accessFile = new RandomAccessFile(file, "rwd");
				accessFile.seek(startPosition);
				HttpURLConnection conn = (HttpURLConnection) new URL(downloadPath).openConnection();
				conn.setReadTimeout(2000);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Range", "bytes=" + startPosition + "-" + endPosition);
				//HttpURLConnection.HTTP_PARTIAL = 206  : 部分内容，一般表示断点续传
				if(conn.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
					InputStream in = conn.getInputStream();
					byte[] buffer = new byte[1024];
					int len = 0;
					while((len = in.read(buffer)) != -1) {
						accessFile.write(buffer, 0, len);
					}
					in.close();
					System.out.println("part " + (threadId+1) + " download success !");
				} else {
					System.out.println("download failed ! thread id:" + threadId);
				}
			} catch (FileNotFoundException e) {
				System.out.println("download failed ! thread id:" + threadId);
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("download failed ! thread id:" + threadId);
			} finally {
				if(accessFile != null)
					try {
						accessFile.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
		
	}
	private String getFileName(String urlPath) {
		return urlPath.substring(urlPath.lastIndexOf("/") + 1);
	}

}
