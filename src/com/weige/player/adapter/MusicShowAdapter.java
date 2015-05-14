package com.weige.player.adapter;

import java.io.IOException;

import com.weige.player.MainUI;
import com.weige.player.R;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
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
	private Cursor currentcursor;
	private Context context;
	private boolean isplayingmusic = false;

	public MusicShowAdapter(Context context, Cursor c) {
		super(context, c);
		this.context = context;
		if (c.moveToFirst()) {

			currentcursor = c;
			// 此处还存在bug需要修改;
		}
	}

	@Override
	public void bindView(View view, final Context context, final Cursor c) {
		ViewHolder viewholder = (ViewHolder) view.getTag();
		final String name = c.getString(c
				.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
		final String url = c.getString(c
				.getColumnIndex(MediaStore.Audio.Media.DATA));

		viewholder.tv_song_name.setText(name);
		viewholder.tv_song_name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playMusic(context, url);
				currentcursor = c;
				MainUI.getplaybutton().setBackgroundResource(
						R.drawable.ic_main_playing_bar_pause_selector);
				// Toast.makeText(context, "play !", 0).show();
			}

		});

	}

	private void playMusic(final Context context, final String url) {
		if (player != null) {
			player.stop();
			// Toast.makeText(context, "stop!", 0).show();
		}
		player = MediaPlayer.create(context, Uri.parse(url));
		player.start();
		isplayingmusic = true;
	}

	public void playMusic() {
		String url = currentcursor.getString(currentcursor
				.getColumnIndex(MediaStore.Audio.Media.DATA));
		playMusic(context, url);
	}

	public MediaPlayer getplayer() {
		return player;
	}

	public void playnext() {
		currentcursor.moveToNext();
		String url = currentcursor.getString(currentcursor
				.getColumnIndex(MediaStore.Audio.Media.DATA));
		playMusic(context, url);

	}

	public void playstop() {
		player.stop();
		isplayingmusic = false;
	}

	public void playpase() {
		player.pause();
		isplayingmusic = false;
	}

	public boolean isplayingmusic() {
		return isplayingmusic;
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
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
