package com.weige.player;


import com.weige.player.adapter.MusicShowAdapter;
import com.weige.player.fragment.MainFragment;
import com.weige.player.slidingmenu.SlidingMenu;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainUI extends FragmentActivity implements OnClickListener {

	private SlidingMenu mMenu;
	private MainFragment mMain;
	private RelativeLayout mbottom;
	private ImageView iv_player_img;
	private SeekBar sb_main_bottom;
	private TextView tv_song_name;
	private TextView tv_bar_singer;
	private ImageButton ib_playlist_queue;
	private ImageButton ib_bar_next;
	private static ImageButton ib_bar_play;
	private MusicShowAdapter mediaPlayeradapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 移除标题
		setContentView(R.layout.root);
		mMenu = (SlidingMenu) findViewById(R.id.menu);
		mbottom = (RelativeLayout) findViewById(R.id.rl_bottom);
		mbottom.setOnClickListener(this);
		iv_player_img = (ImageView) findViewById(R.id.ib_default_avatar);//歌手图片
		sb_main_bottom = (SeekBar)findViewById(R.id.sb_main_bottom);//进度条
		tv_song_name=(TextView)findViewById(R.id.tv_bar_song_name);//歌名
		tv_bar_singer=(TextView)findViewById(R.id.tv_bar_singer);//歌手
		ib_playlist_queue = (ImageButton) findViewById(R.id.ib_bar_playlist_queue);//播放队列
		ib_bar_next = (ImageButton) findViewById(R.id.ib_bar_next);//下一曲
		ib_bar_play = (ImageButton) findViewById(R.id.ib_bar_play);//播放
		ib_bar_next.setOnClickListener(this);
		ib_bar_play.setOnClickListener(this);
		initFragment();
	}
	private void initFragment() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		mMain = new MainFragment();
		ft.replace(R.id.fl_main, mMain);
		ft.commit();
	}

	public void toggleMenu(View view) {
		mMenu.toggle();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_bottom:
			startActivity(new Intent(this, LyricUI.class));
		case R.id.ib_bar_next:
			 mediaPlayeradapter = mMain.getmTingFragment().getlocalMusicFragment().getMediaPlayeradapter();
			mediaPlayeradapter.playnext();
			break;
		case R.id.ib_bar_play:
			 mediaPlayeradapter = mMain.getmTingFragment().getlocalMusicFragment().getMediaPlayeradapter();
			if(mediaPlayeradapter.isplayingmusic()){
				mediaPlayeradapter.playpase();
				ib_bar_play.setBackgroundResource(R.drawable.kg_ic_playing_bar_play_default);
			}else{
				mediaPlayeradapter.playMusic();
				ib_bar_play.setBackgroundResource(R.drawable.btn_down_stop);
			}
			break;

		default:
			break;
		}
	}
	public static ImageButton getplaybotton(){
		return ib_bar_play;
	}
}