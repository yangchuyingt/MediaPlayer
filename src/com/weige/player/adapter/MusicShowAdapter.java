package com.weige.player.adapter;

import java.io.IOException;

import com.weige.player.MainUI;
import com.weige.player.R;
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
	private int currentposition=0;

	public MusicShowAdapter(Context context, Cursor c) {
		super(context, c);
		this.context = context;

			cursor = c;
			// 此处还存在bug需要修改;
	}

	@Override
	public void bindView(View view, final Context context,  final Cursor c) {
		ViewHolder viewholder = (ViewHolder) view.getTag();
		final String name = c.getString(c
				.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
		final String url = c.getString(c
				.getColumnIndex(MediaStore.Audio.Media.DATA));
		
		final String singer = c.getString(c
				.getColumnIndex(MediaStore.Audio.Media.ARTIST));
		final int musictime = c.getInt(c
				.getColumnIndex(MediaStore.Audio.Media.DURATION));
		final int position=c.getPosition();
		viewholder.tv_song_name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				currentposition = position;
				playMusic(context, url);
				MainUI.getplaybutton().setBackgroundResource(
						R.drawable.ic_main_playing_bar_pause_selector);
				// Toast.makeText(context, "play !", 0).show();
				MainUI.getmusicbar().setMax(musictime);
				//System.out.println("onclik()方法中的音乐时间:"+FormatHelper.formatDuration(musictime)+",cursor 位置:"+currentposition+",歌名:"+name);
				MainUI.getsongnameview().setText(FormatHelper.getSongname(name));
				MainUI.getsingerview().setText(FormatHelper.getSinger(name));
				/*MainUI.getmusicbar().setMax(getmusictime());
				MainUI.getsongnameview().setText(getmusicname());
				MainUI.getsingerview().setText(getsinger());*/
			}

		});
		viewholder.tv_song_name.setText(FormatHelper.getListSongname(name));
		

	}

	public int getmusictime(Cursor c) {
		return c.getInt(c.getColumnIndex(MediaStore.Audio.Media.DURATION));
	}

	public int getmusictime() {
		if(cursor.moveToPosition(currentposition)){
		 int time =cursor.getInt(cursor
				.getColumnIndex(MediaStore.Audio.Media.DURATION));
		 //.out.println("currentcursor的时间:"+FormatHelper.formatDuration(time) +"位置:"+cursor.getPosition()+"歌名:"+name);
		 return time;
		}
		return 0;
	}

	public String getmusicname() {
		if(cursor.moveToPosition(currentposition)){
			
		return FormatHelper.getListSongname(cursor.getString(cursor
				.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
		}
		return null;
	}

	public String getsinger() {
		if(cursor.moveToPosition(currentposition)){
			
		return FormatHelper.getSinger(cursor.getString(cursor
				.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
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
			
			MainUI.getsongnameview().setText(getmusicname());
			MainUI.getsingerview().setText(getsinger());
		} catch (Exception e) {
			// TODO: handle exception
		}
		//System.out.println("setprogress 方法中的参数:"+getmusictime());
		setprogress(getmusictime());
	}

	public void playMusic() {
		if(cursor.moveToPosition(currentposition)){
			
		String url = cursor.getString(cursor
				.getColumnIndex(MediaStore.Audio.Media.DATA));
		playMusic(context, url);
		}
	}

	public MediaPlayer getplayer() {
		return player;
	}

	public void playnext() {
		if(cursor.moveToPosition(currentposition)){
			
		if(!cursor.moveToNext()){
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

	public void setOnCurrentmusicListener(CurrentmusicTimeListener listener) {
		this.listener = listener;
	}

	private void setprogress(final int times) {
		thread = new Thread() {
			public void run() {
				int time = -1;
				while (!interrupted()) {
					try {
						//System.out.println("setProgress()方法中的时间:"+FormatHelper.formatDuration(times)+",cursor的位置:");
						time = player.getCurrentPosition();
					} catch (Exception e) {
						time = 0;
					}
					SystemClock.sleep(1000);
					listener.getcurrentmusictime(time);
				    //System.out.println("没有playnext"+"当前播放时间:"+time+",总时间:"+times+","+(time >=times));
					if ( time+1000 >=times) {
						playnext();
						//System.out.println("playnext");
					} 
				}
			};
		};
		thread.start();
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

}
