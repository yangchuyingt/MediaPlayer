package com.weige.player.adapter;

import java.io.IOException;

import com.weige.player.MainUI;
import com.weige.player.R;
import com.weige.player.listener.ChangeLyricListener;
import com.weige.player.listener.CurrentmusicTimeListener;
import com.weige.player.utils.FormatHelper;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MusicShowAdapter extends CursorAdapter {
	private MediaPlayer player;
	private Cursor cursor;
	private Context context;
	private boolean isplayingmusic = false;
	private int pause;
	private CurrentmusicTimeListener listener;
	private Thread thread;
	private int currentposition = 0;
	private ChangeLyricListener lyricListener;
	private int mode;

	public MusicShowAdapter(Context context, Cursor c) {
		super(context, c);
		this.context = context;

		cursor = c;
		// �˴�������bug��Ҫ�޸�;
	}

	@Override
	public void bindView(View view, final Context context, final Cursor c) {
		ViewHolder viewholder = (ViewHolder) view.getTag();
		final String name = c.getString(c
				.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
		final String url = c.getString(c
				.getColumnIndex(MediaStore.Audio.Media.DATA));

		final String singer = c.getString(c
				.getColumnIndex(MediaStore.Audio.Media.ARTIST));
		final int musictime = c.getInt(c
				.getColumnIndex(MediaStore.Audio.Media.DURATION));
		final int position = c.getPosition();
		viewholder.tv_song_name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				currentposition = position;
				playMusic(context, url);
				MainUI.getplaybutton().setBackgroundResource(
						R.drawable.ic_main_playing_bar_pause_selector);
				// Toast.makeText(context, "play !", 0).show();
				MainUI.getmusicbar().setMax(musictime);
				MainUI.getsongnameview()
						.setText(FormatHelper.getSongname(name));
				MainUI.getsingerview().setText(FormatHelper.getSinger(name));
				/*
				 * MainUI.getmusicbar().setMax(getmusictime());
				 * MainUI.getsongnameview().setText(getmusicname());
				 * MainUI.getsingerview().setText(getsinger());
				 */
			}

		});
		viewholder.tv_song_name.setText(FormatHelper.getListSongname(name));

	}

	public int getmusictime(Cursor c) {
		return c.getInt(c.getColumnIndex(MediaStore.Audio.Media.DURATION));
	}

	public int getmusictime() {
		if (cursor.moveToPosition(currentposition)) {
			int time = cursor.getInt(cursor
					.getColumnIndex(MediaStore.Audio.Media.DURATION));
			return time;
		}
		return 0;
	}

	public String getmusicname() {
		if (cursor.moveToPosition(currentposition)) {

			return cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
		}
		return null;
	}

	public String getsinger() {
		if (cursor.moveToPosition(currentposition)) {

			return cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.ARTIST));
		}
		return null;
	}

	private void playMusic(final Context context, final String url) {
		if (player != null) {
			player.release();
			// Toast.makeText(context, "stop!", 0).show();
		}
		player = MediaPlayer.create(context, Uri.parse(url));
		player.start();
		isplayingmusic = true;
		MainUI.getplaybutton().setBackgroundResource(
				R.drawable.ic_main_playing_bar_pause_selector);
		MainUI.getmusicbar().setMax(getmusictime());
		try {
			MainUI.getsongnameview().setText(
					FormatHelper.getSongname(getmusicname()));
			MainUI.getsingerview().setText(
					FormatHelper.getSinger(getmusicname()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		setprogress(getmusictime());
	}

	public void playMusic() {
		if (cursor.moveToPosition(currentposition)) {

			String url = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.DATA));
			playMusic(context, url);
		}
	}

	public MediaPlayer getplayer() {
		return player;
	}

	/**
	 * 顺序播放
	 */
	public void playnext() {
		if (cursor.moveToPosition(currentposition)) {

			if (!cursor.moveToNext()) {
				cursor.moveToFirst();

			}
			currentposition = cursor.getPosition();
			String url = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.DATA));
			playMusic(context, url);
		}

	}

	/**
	 * 单曲播放
	 */
	public void playsingle() {
		if (cursor.moveToPosition(currentposition)) {

			String url = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.DATA));
			playMusic(context, url);
		}
	}

	public void playradom() {
		int redom = (int) Math.random() * (cursor.getCount() - 1);
		if (cursor.moveToPosition(redom)) {

			String url = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.DATA));
			playMusic(context, url);
		}

	}

	public void playPrivious() {
		if (cursor.moveToPosition(currentposition)) {

			if (!cursor.moveToPrevious()) {
				cursor.moveToFirst();

			}
			currentposition = cursor.getPosition();
			String url = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.DATA));
			playMusic(context, url);
		}
	}

	public void playstop() {
		player.stop();
		thread.interrupt();
		isplayingmusic = false;
	}

	public void playpause() {
		player.pause();
		thread.interrupt();
		pause = player.getCurrentPosition();
		isplayingmusic = false;
	}

	public boolean isplayingmusic() {
		return isplayingmusic;
	}

	public int playresume() {
		if (player != null) {
			player.seekTo(pause);
			player.start();
			setprogress(getmusictime());
			isplayingmusic = true;
		} else {
			playMusic();
			return 0;
		}
		return pause;
	}

	public void playresume(int pauses) {
		if (player != null) {
			player.seekTo(pauses);
			player.start();
			setprogress(getmusictime());
			isplayingmusic = true;
		} else {
			playMusic();
		}
	}

	public void setOnCurrentmusicListener(CurrentmusicTimeListener listener) {
		this.listener = listener;
	}

	private void setprogress(final int times) {
		thread = new Thread() {
			public void run() {
				int time = -1;
				while (!interrupted()) {
					try {
						time = player.getCurrentPosition();
					} catch (Exception e) {
						time = 0;
					}
					if (listener != null) {
						listener.getcurrentmusictime(time);
					}
					if (lyricListener != null) {
						lyricListener.changelyrics(time);
					}
					if (time + 1000 >= times) {
                        switch (mode) {
						case 0:
							playnext();
							break;
						case 1:
							playradom();
							break;
						case 2:
							playsingle();
							break;
						default:
							break;
						}
						
					}
					SystemClock.sleep(900);
				}
			};
		};
		thread.start();
	}
   public void changeplaymode(int position){
    mode=position;
   }
	@Override
	public View newView(Context arg0, final Cursor c, ViewGroup arg2) {
		View view = null;
		ViewHolder holder;
		holder = new ViewHolder();
		view = View.inflate(mContext, R.layout.showmusicadapterview, null);
		holder.tv_alphabat = (TextView) view.findViewById(R.id.tv_alphabat);
		holder.tv_song_name = (TextView) view.findViewById(R.id.tv_song_name);
		holder.iv_add_to_play_list = (ImageView) view
				.findViewById(R.id.iv_add_to_play_list);
		holder.rb_more_options = (RadioButton) view
				.findViewById(R.id.rb_more_options);
		holder.ll_set_to_ring = (LinearLayout) view
				.findViewById(R.id.ll_set_to_ring);
		holder.ll_add = (LinearLayout) view.findViewById(R.id.ll_add);
		holder.ll_music_message = (LinearLayout) view
				.findViewById(R.id.ll_music_message);
		holder.ll_delete = (LinearLayout) view.findViewById(R.id.ll_delete);
		holder.ll_buttonitem = (LinearLayout) view
				.findViewById(R.id.ll_buttonitem);

		view.setTag(holder);
		return view;
	}

	private class ViewHolder {
		TextView tv_alphabat, tv_song_name;
		ImageView iv_add_to_play_list;
		RadioButton rb_more_options;
		LinearLayout ll_buttonitem, ll_set_to_ring, ll_add, ll_music_message,
				ll_delete;
	}

	public void setchangeliyricListener(ChangeLyricListener listener) {
		lyricListener = listener;

	}

}
