package com.weige.player.utils;


public class FormatHelper {

	/**
	 * return a format time of 00:00
	 * @param milliseconds
	 * @return
	 */
	public static String formatDuration(int milliseconds){
		int seconds = milliseconds / 1000;
		int secondPart = seconds % 60;
		int minutePart = seconds / 60;
		return (minutePart >= 10 ? minutePart : "0" + minutePart) + ":" + (secondPart >= 10 ? secondPart : "0" + secondPart);
	}
	
	public static String formatTitle(String title, int length){
		int len = title.length() < length ? title.length():length;		
		String subString = title.substring(0, len);
		if(len < title.length()){
			subString += "...";
		}
		return subString;
	}
	
	public static String getSinger(String name){
		String str[] = name.split(" - ");
		String singer = str[0];
		return singer;
	}
	
	public static String getListSongname(String name){
		String str[] = name.split("\\.");
		String listsongname = str[0];
		return listsongname;
	}
	
	public static String getSongname(String name){
		String str[] = name.split(" - ");
		String temp = str[1];
		String str1[] = temp.split("\\.");
		String songname = str1[0];
		return songname;
	}
}
