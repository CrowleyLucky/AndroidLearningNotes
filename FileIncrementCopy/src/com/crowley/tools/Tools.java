package com.crowley.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Stack;

import javax.swing.JProgressBar;

import com.crowley.fileincrementcopy.MainFrame;

public class Tools {

	public boolean fileCopy(String srcPath, String destPath, String fileName) {
		 
		return false;
	}
	
	public static void startIncrementCopy(String destPath, long totalFileLen, JProgressBar progressBar) {
		File fileRecord = new File(destPath, MainFrame.FILE_RECORD_NAME); 
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileRecord));
			//start copy ...
			//TODO
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(br !=null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//delete record file after finish increment copy
		fileRecord = new File(destPath, MainFrame.FILE_RECORD_NAME); 
		fileRecord.delete();
	}

	public static long[] searchNewFilesAndDirectorys(String sourceFolder, String destFolder, MainFrame mainFrame) {
		Stack<File> stack = new Stack<File>();
		stack.push(new File(sourceFolder));
		File fileRecord = new File(destFolder, MainFrame.FILE_RECORD_NAME);
		BufferedWriter bw = null;
		int sourceFolderLen = sourceFolder.length();
		long fileTotalLen = 0;
		long fileCount = 0;
		long dirCount = 0;
		try {
			fileRecord.createNewFile();
			bw = new BufferedWriter(new FileWriter(fileRecord));
			File cur = null;
			//System.out.println(stack.pop());System.out.println(stack.pop());
			while(!stack.isEmpty() && (cur = stack.pop()) != null) {
				//judge whether destination folder also has same file or directory, exclude the root folder.
				File[] files = cur.listFiles();
				for(File f : files) {
					if(f.isDirectory() && f.canRead()) {
						stack.push(f);
						File tmp = new File(destFolder, f.getAbsolutePath().substring(sourceFolderLen + 1));
						if(!tmp.exists()) {
							bw.write("d|" + tmp.getAbsolutePath() + "\n");
							mainFrame.appendLog("New Directory: " + f.getAbsolutePath());
							dirCount++;
						}
					} else if(f.isFile() && f.canRead()) {
						File tmp = new File(destFolder, f.getAbsolutePath().substring(sourceFolderLen + 1));
						if(!tmp.exists() || tmp.length() != f.length() || !getFileMD5(f).equals(getFileMD5(tmp))) {
							bw.write("f|" + f.getAbsolutePath() + "|" + tmp.getAbsolutePath() + "\n");
							mainFrame.appendLog("New File: " + f.getAbsolutePath());
							fileTotalLen += f.length();
							fileCount++;
						}
					}
				}
			}
			 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(fileCount + " files, " + fileTotalLen + " bytes.");
		return new long[]{fileCount, dirCount, fileTotalLen};
	}
	
	 
	public static String getFileMD5(File fileInput) {
		StringBuilder result = new StringBuilder();
		InputStream in = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			in = new FileInputStream(fileInput);
			byte[] buffer = new byte[1024 * 4];
			int readLen = in.read(buffer, 0, buffer.length);
			while(-1 != readLen) {
				md.update(buffer, 0, readLen);
				readLen = in.read(buffer, 0, buffer.length);
			}
	
			byte[] results = md.digest();
				
			for(byte b : results) {
				result.append(Integer.toHexString((b & 0xFF) + 0x100).substring(1).toUpperCase());
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				in = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//System.out.println(fileInput.getName() + "->" + result.toString());
		
		return result.toString();
	}


	
}
