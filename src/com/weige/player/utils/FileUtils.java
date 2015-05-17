package com.weige.player.utils;

import java.io.File;
import java.io.IOException;


import android.content.Context;
import android.os.Environment;

public class FileUtils {
	/**
	 * 获取文件绝对路径，如果文件夹不存在，则会尝试创建。
	 * @param context
	 * @param subPath 子文件名
	 * @author Anddward.Liao <Anddward@gmail.com>
	 */
	public static String getFilePath(Context context, String subPath) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File fileDir = new File(Environment.getExternalStorageDirectory()
					.getAbsoluteFile() + subPath);
			if(!fileDir.exists()){
				
				fileDir.mkdirs();
			}
			return Environment.getExternalStorageDirectory().getAbsolutePath()
					+ subPath;
		} else {
			File fileDir = new File(context.getFilesDir().getAbsoluteFile()
					+ subPath);
			if(!fileDir.exists()){
				fileDir.mkdirs();
			}
			return context.getFilesDir().getAbsolutePath() + subPath;
		}
	}
	/**
	 * 获取文件绝对路径，如果文件夹不存在，则不会尝试创建。
	 * @param context
	 * @param subPath 子文件名
	 * @author Anddward.Liao <Anddward@gmail.com>
	 */
	public static String getFilePathwithoutcreate(Context context, String subPath) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File fileDir = new File(Environment.getExternalStorageDirectory()
					.getAbsoluteFile() + subPath);
			if(!fileDir.exists()){
				return null;
				//fileDir.mkdirs();
			}
			return Environment.getExternalStorageDirectory().getAbsolutePath()
					+ subPath;
		} else {
			File fileDir = new File(context.getFilesDir().getAbsoluteFile()
					+ subPath);
			if(!fileDir.exists()){
				return null;
				//fileDir.mkdirs();
			}
			return context.getFilesDir().getAbsolutePath() + subPath;
		}
	}
	/**
	 * 通过文件名获取图片，
	 * @param fileName
	 * @param width
	 * @param height
	 * @throws IOException 
	 */
	public static File readFileWithName(Context context ,String fileName){
		String filePath = getFilePathwithoutcreate(context,fileName);
		if(filePath==null){
			return null;
		}
		return  new File(filePath);
	}
}
