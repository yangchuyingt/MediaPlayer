package com.weige.player.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class CacheUtils {
	public static final String CACHE_FILE_NAME = "weige_config";
	private static SharedPreferences mSharedPreferences;
	
	/**
	 * ʹ��SharedPreferences�洢boolean��������
	 */
	public static void cacheBooleanData(Context context, String key, boolean value) {
		if(mSharedPreferences == null){
			mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		Editor edit = mSharedPreferences.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}
	/**
	 * ȡ�������boolean��������
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static boolean getBooleanData(Context context, String key, boolean defValue){
		if(mSharedPreferences == null){
			mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		return mSharedPreferences.getBoolean(key, defValue);
		
	}

}
