package com.weige.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter.LengthFilter;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.weige.player.adapter.LyricAdapter;
import com.weige.player.adapter.MusicShowAdapter;
import com.weige.player.fragment.TingFragment;
import com.weige.player.listener.ChangeLyricListener;
import com.weige.player.localmusicfragment.OneSongFragment;
import com.weige.player.utils.Constants;
import com.weige.player.utils.FileUtils;
import com.weige.player.utils.FormatHelper;

public class LyricUI extends Activity implements ChangeLyricListener {

	private static final int CHANGECOLOR = 0;
	private ListView lv_lyric;
	private String musicName;
	private String musicTime;
	private String[] gecis;
	private Map<String,Integer > times;
	private TextView tv_lyric_songname;
	private TextView tv_lyric_singer;
	private TextView tv_time_begin;
	private SeekBar sb_lyric;
	private TextView tv_time_end;
	private ImageButton ib_player_prev;
	private ImageButton ib_player_playorpause;
	private ImageButton ib_player_next;
	private String songname;
	private String singer;
	private MusicShowAdapter adapter;
	private Integer position;
	private int beforposition=-1;
    Handler myHandler=new Handler(){
    	private int selector;

		public void handleMessage(android.os.Message msg) {
    		switch (msg.what) {
			case CHANGECOLOR:
				
				selector = position>6?position-6:0;
				lv_lyric.setSelection(selector);
				if(selector==0||position>gecis.length-6){
					((TextView)((LinearLayout)lv_lyric.getChildAt(position)).getChildAt(0)).setTextColor(android.graphics.Color.BLACK);
					if(beforposition!=-1){
						((TextView)((LinearLayout)lv_lyric.getChildAt(beforposition)).getChildAt(0)).setTextColor(android.graphics.Color.WHITE);
					}
				}else{
					((TextView)((LinearLayout)lv_lyric.getChildAt(position-selector+1)).getChildAt(0)).setTextColor(android.graphics.Color.BLACK);
					if(beforposition!=-1){
						((TextView)((LinearLayout)lv_lyric.getChildAt(beforposition-selector+1)).getChildAt(0)).setTextColor(android.graphics.Color.WHITE);
					}
				}
				beforposition=position;
				break;

			default:
				break;
			}
    	};
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lyric);
		initview();
		initdata();
	}

	private void initview() {
		lv_lyric = (ListView) findViewById(R.id.lv_lyric);//歌词
		tv_lyric_songname = (TextView) findViewById(R.id.tv_lyric_title_bar_songname);//歌曲名
		tv_lyric_singer = (TextView) findViewById(R.id.tv_lyric_title_bar_singer);//歌手
		tv_time_begin = (TextView) findViewById(R.id.tv_time_begin);//正在播放时间
		sb_lyric = (SeekBar) findViewById(R.id.sb_lyric);//歌词界面进度条
		tv_time_end = (TextView) findViewById(R.id.tv_time_end);//歌曲总时间
		ib_player_prev = (ImageButton) findViewById(R.id.ib_player_prev);//上一曲
		ib_player_playorpause = (ImageButton) findViewById(R.id.ib_player_playorpause);//播放或暂停
		ib_player_next = (ImageButton) findViewById(R.id.ib_player_next);//下一曲
	}

	private void initdata() {
		musicName = getIntent().getStringExtra("musicname");
		songname = getIntent().getStringExtra("songname");
		singer = getIntent().getStringExtra("singer");
		musicTime = getIntent().getStringExtra("musictime");
		
		tv_lyric_songname.setText(songname);
		tv_lyric_singer.setText(singer);
		tv_time_end.setText(musicTime);
		
		System.out.println("musicName:" + musicName);
		String[] split = new String[2];
		if (musicName != null) {
			split = musicName.split(".mp3");
		}
		File file = FileUtils.readFileWithName(this, Constants.FOLDER_NAME_PATH
				+ split[0] + ".lrc");
		if (file != null && file.exists()) {
			String lyric = parseFile(file).trim();
			gecis = lyric.split("\\n");
			gettimtefromgecis(gecis);
			System.out.println("歌词长度:" + gecis.length);
			lv_lyric.setAdapter(new LyricAdapter(gecis, this));

		} else {
			Toast.makeText(this, "没有歌词!", 0).show();
		}
		adapter = OneSongFragment.getadapter();
		adapter.setchangeliyricListener(this);
	}

	private void gettimtefromgecis(String[] gecis2) {
		times=new HashMap<String, Integer>();
		for(int i=0;i<gecis.length;i++){
			//times[i]=;
			//times.add(gecis[i].substring(1, 6));
			times.put(gecis[i].substring(1, 6), i);
		}
		
	}

	private String parseFile(File file) {
		InputStreamReader reader = null;
		StringBuilder builder = new StringBuilder();
		try {
			reader = new InputStreamReader(new FileInputStream(file), "gbk");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		char[] chararray = new char[512];
		int length = 0;
		try {
			while ((length = reader.read(chararray)) != -1) {
				builder.append(chararray, 0, length-1);
				//builder.append(chararray);//以前是这句，现在是上面的那句，读多了
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toString();
	}

	@Override
	public void changlyrics(int time) {
		String tm=FormatHelper.formatDuration(time);
		position = times.get(tm);
		if(position!=null){
			myHandler.sendEmptyMessage(CHANGECOLOR);
			
		}
		
	}
}
