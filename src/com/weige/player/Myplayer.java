package com.weige.player;

import com.weige.player.utils.Constants;
import com.weige.player.utils.FileUtils;

import android.app.Application;

public class Myplayer extends Application {
	@Override
	public void onCreate() {
		//System.out.println("执行前;");
		String path=FileUtils.getFilePath(getApplicationContext(), Constants.FOLDER_NAME_PATH);
		System.out.println("执行后;"+path);
	}
}
