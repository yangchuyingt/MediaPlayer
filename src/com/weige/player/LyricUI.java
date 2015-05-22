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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.weige.player.adapter.LyricAdapter;
import com.weige.player.adapter.LyricAdapter.ViewHolder;
import com.weige.player.adapter.MusicShowAdapter;
import com.weige.player.fragment.TingFragment;
import com.weige.player.listener.ChangeLyricListener;
import com.weige.player.localmusicfragment.OneSongFragment;
import com.weige.player.utils.Constants;
import com.weige.player.utils.FileUtils;
import com.weige.player.utils.FormatHelper;

public class LyricUI extends Activity implements ChangeLyricListener, OnClickListener, OnSeekBarChangeListener {

	private static final int CHANGECOLOR = 0;
	private static final int CHANGETIME = 1;
	private ListView lv_lyric;
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
	private MusicShowAdapter adapter;
	private Integer position;
	private int beforposition=-1;
    Handler myHandler=new Handler(){
    	private int selector;
		private TextView tv;

		public void handleMessage(android.os.Message msg) {
    		switch (msg.what) {
			case CHANGECOLOR:
				
				selector = position>6?position-6:0;
				lv_lyric.setSelection(selector);
				 ViewHolder[] holderlist = lyricadapter.getholderlist();
				 if( holderlist[position]!=null){
					 
					 holderlist[position].getTv_liric().setTextColor(android.graphics.Color.BLACK);
					
				 }
				 if (beforposition!=-1&&holderlist[beforposition]!=null&&beforposition!=position) {
					 holderlist[beforposition].getTv_liric().setTextColor(android.graphics.Color.WHITE);
				}
				
				 beforposition=position;
				/*if(selector==0){
					((TextView)((LinearLayout)lv_lyric.getChildAt(position)).getChildAt(0)).setTextColor(android.graphics.Color.BLACK);
					if(beforposition!=-1){
						((TextView)((LinearLayout)lv_lyric.getChildAt(beforposition)).getChildAt(0)).setTextColor(android.graphics.Color.WHITE);
					}
				}else{
					((TextView)((LinearLayout)lv_lyric.getChildAt(position-selector+1)).getChildAt(0)).setTextColor(android.graphics.Color.BLACK);
					if(beforposition!=-1){
						((TextView)((LinearLayout)lv_lyric.getChildAt(beforposition-selector+1)).getChildAt(0)).setTextColor(android.graphics.Color.WHITE);
					}
				}*/
				
				break;

			case CHANGETIME:
				 sb_lyric.setProgress(time);
				 tv_time_begin.setText(tm);
				break;
			}
    	};
    };
	private ImageButton ib_lyric_back;
	private LyricAdapter lyricadapter;
	private int time;
	private MusicShowAdapter playmusiadapter;
	private String tm;
	private String musicName;
	private String songname;
	private String singer;
	private String musicTime;
	private int progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lyric);
		initview();
		initdata();
		
	}

	private void initview() {
		ib_lyric_back = (ImageButton) findViewById(R.id.ib_lyric_title_bar_back);
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
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	if(playmusiadapter.isplayingmusic()){
    		ib_player_playorpause
			.setBackgroundResource(R.drawable.ic_player_pause_bg);
    	}else{
    		ib_player_playorpause
			.setBackgroundResource(R.drawable.ic_player_play_bg);
    	}
    	
    }
	private void initdata() {
		playmusiadapter=MainUI.getmusicadapter();
		ib_lyric_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		/*String musicName = getIntent().getStringExtra("musicname");
		String songname = getIntent().getStringExtra("songname");
		String singer = getIntent().getStringExtra("singer");
		String musicTime = getIntent().getStringExtra("musictime");*/
		
		loaddata();
		adapter = OneSongFragment.getadapter();
		adapter.setchangeliyricListener(this);
		ib_player_prev.setOnClickListener(this);
		ib_player_playorpause.setOnClickListener(this);
		ib_player_next.setOnClickListener(this);
		sb_lyric.setOnSeekBarChangeListener(this);
	}

	private void loaddata() {
		musicName = playmusiadapter.getmusicname();
		songname =FormatHelper.getSongname(playmusiadapter.getmusicname());
		singer= FormatHelper.getSinger(playmusiadapter.getmusicname());
		musicTime= FormatHelper.formatDuration(playmusiadapter.getmusictime());
		tv_lyric_songname.setText(songname);
		tv_lyric_singer.setText(singer);
		tv_time_end.setText(musicTime);
		sb_lyric.setMax(playmusiadapter.getmusictime());
		
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
			lyricadapter = new LyricAdapter(gecis, this);
			lv_lyric.setAdapter(lyricadapter);
			lv_lyric.setVisibility(View.VISIBLE);

		} else {
			Toast.makeText(this, "没有歌词!", 0).show();
			lv_lyric.setVisibility(View.INVISIBLE);
		}
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
	public void changelyrics(int time) {
		tm = FormatHelper.formatDuration(time);
		try {
			this.time=time;
			position = times.get(tm);
			
		} catch (Exception e) {
			myHandler.sendEmptyMessage(CHANGETIME);
			System.out.println("time:"+time);
			return;
		}
		if(position!=null){
			myHandler.sendEmptyMessage(CHANGECOLOR);
			System.out.println("position"+position);
			
		}else{
			myHandler.sendEmptyMessage(CHANGETIME);
			System.out.println("time:"+time);
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_player_prev:
			playmusiadapter.playPrivious();
			sb_lyric.setProgress(0);
			loaddata();
			break;
		case R.id.ib_player_playorpause:
			if (playmusiadapter.isplayingmusic()) {
				playmusiadapter.playpause();
				ib_player_playorpause
						.setBackgroundResource(R.drawable.ic_player_play_bg);
			} else {
				playmusiadapter.playresume();
				ib_player_playorpause
						.setBackgroundResource(R.drawable.ic_player_pause_bg);
			}
			break;
		case R.id.ib_player_next:
			playmusiadapter.playnext();
			sb_lyric.setProgress(0);
			loaddata();
			break;

		default:
			break;
		}
		
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		progress = seekBar.getProgress();
		playmusiadapter.playresume(progress);
		
	}
}
