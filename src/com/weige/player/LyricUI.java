package com.weige.player;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

public class LyricUI extends Activity {

	private ListView lv_lyric;
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
	private void initdata(){} 
}
