package com.weige.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.weige.player.adapter.LyricAdapter;
import com.weige.player.utils.Constants;
import com.weige.player.utils.FileUtils;

public class LyricUI extends Activity {

	private ListView lv_lyric;
	private String musicName;
	private String[] gecis;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lyric);
		initview();
		initdata();
	}
	private void initview(){
		lv_lyric=(ListView)findViewById(R.id.lv_lyric);
	}
	private void initdata(){
		musicName=getIntent().getStringExtra("musicname");
		System.out.println("musicName:"+musicName);
		String[] split = new String[2];
		if(musicName!=null){
			split = musicName.split("m");
		}
		File file = FileUtils.readFileWithName(this,  Constants.FOLDER_NAME_PATH+split[0]+"lrc");
		if(file!=null&&file.exists()){
			String lyric=parseFile(file).trim();
			gecis = lyric.split("\\r");
			System.out.println("歌词的长度:"+gecis.length);
			lv_lyric.setAdapter(new LyricAdapter(gecis, this));
			
		}else{
			Toast.makeText(this, "没有歌词!", 1).show();
		}
	}
	private String parseFile(File file) {
		InputStreamReader reader = null;
		StringBuilder builder=new StringBuilder();
		try {
			reader = new InputStreamReader(new FileInputStream(file), "gbk");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		char [] chararray=new char[1024];
		int length=0;
		try {
			while((length=reader.read(chararray))!=-1){
				builder.append(chararray);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toString();
	} 
}
