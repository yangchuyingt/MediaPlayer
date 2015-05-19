package com.weige.player;

import android.content.Intent;
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
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.weige.player.adapter.MusicShowAdapter;
import com.weige.player.fragment.MainFragment;
import com.weige.player.listener.CurrentmusicTimeListener;
import com.weige.player.slidingmenu.SlidingMenu;
import com.weige.player.utils.FormatHelper;

public class MainUI extends FragmentActivity implements OnClickListener {

	private SlidingMenu mMenu;
	private MainFragment mMain;
	private RelativeLayout mbottom_bar;
	private ImageView iv_player_img;
	private static SeekBar sb_main;
	private static TextView tv_song_name;
	private static TextView tv_bar_singer;
	private ImageButton ib_playlist_queue;
	private ImageButton ib_bar_next;
	private static ImageButton ib_bar_play;
	private MusicShowAdapter mediaPlayeradapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//移除标题
		setContentView(R.layout.root);
		mMenu = (SlidingMenu) findViewById(R.id.menu);
		mbottom_bar = (RelativeLayout) findViewById(R.id.rl_bottom);
		iv_player_img = (ImageView) findViewById(R.id.ib_default_avatar);//歌手图片
		sb_main = (SeekBar) findViewById(R.id.sb_main_bottom);//进度条
		tv_song_name = (TextView) findViewById(R.id.tv_bar_song_name);//歌曲名
		tv_bar_singer = (TextView) findViewById(R.id.tv_bar_singer);//歌手
		ib_playlist_queue = (ImageButton) findViewById(R.id.ib_bar_playlist_queue);//播放队列
		ib_bar_next = (ImageButton) findViewById(R.id.ib_bar_next);//下一曲
		ib_bar_play = (ImageButton) findViewById(R.id.ib_bar_play);//播放

		mbottom_bar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mediaPlayeradapter = mMain.getmTingFragment()
						.getlocalMusicFragment().getMediaPlayeradapter();
				Intent intent = new Intent(MainUI.this, LyricUI.class);
				intent.putExtra("musicname", mediaPlayeradapter.getmusicname());
				intent.putExtra("songname", FormatHelper.getSongname(mediaPlayeradapter.getmusicname()));
				intent.putExtra("singer", FormatHelper.getSinger(mediaPlayeradapter.getmusicname()));
				intent.putExtra("musictime", FormatHelper.formatDuration(mediaPlayeradapter.getmusictime()));
				startActivity(intent);

			}
		});
		ib_bar_next.setOnClickListener(this);
		ib_bar_play.setOnClickListener(this);
		
		initFragment();
		initdata();
	}

	private void initdata() {
		
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
		case R.id.ib_bar_next:
			mediaPlayeradapter = mMain.getmTingFragment()
					.getlocalMusicFragment().getMediaPlayeradapter();
			mediaPlayeradapter.playnext();
			sb_main.setProgress(0);

			break;
		case R.id.ib_bar_play:
			mediaPlayeradapter = mMain.getmTingFragment()
					.getlocalMusicFragment().getMediaPlayeradapter();
			if (mediaPlayeradapter.isplayingmusic()) {
				mediaPlayeradapter.playpause();
				ib_bar_play
						.setBackgroundResource(R.drawable.ic_main_playing_bar_play_selector);
			} else {
				mediaPlayeradapter.playresume();
				ib_bar_play
						.setBackgroundResource(R.drawable.ic_main_playing_bar_pause_selector);
			}
			break;

		default:
			break;
		}
	}

	public static ImageButton getplaybutton() {
		return ib_bar_play;
	}

	public static SeekBar getmusicbar() {
		return sb_main;
	}

	public static TextView getsongnameview() {
		return tv_song_name;
	}

	public static TextView getsingerview() {
		return tv_bar_singer;
	}

}