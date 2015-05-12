package com.weige.player.adapter;

import com.weige.player.R;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class MusicShowAdapter extends CursorAdapter {
 
	public MusicShowAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindView(View view, Context context , Cursor c) {
		ViewHolder viewholder=(ViewHolder) view.getTag();
		viewholder.tv_song_name.setText(c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME )));
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		View view = null;
		ViewHolder  holder;
			holder=new ViewHolder();
			view=View.inflate(mContext, R.layout.showmusicadapterview, null);
			holder.tv_alphabat=(TextView) view.findViewById(R.id.tv_alphabat);
			holder.tv_song_name=(TextView) view.findViewById(R.id.tv_song_name);
			holder.iv_add_to_play_list=(ImageView) view.findViewById(R.id.iv_add_to_play_list);
			holder.rb_more_options=(RadioButton) view.findViewById(R.id.rb_more_options);
			holder.ll_set_to_ring=(LinearLayout) view.findViewById(R.id.ll_set_to_ring);
			holder.ll_add=(LinearLayout) view.findViewById(R.id.ll_add);
			holder.ll_music_message=(LinearLayout) view.findViewById(R.id.ll_music_message);
			holder.ll_delete=(LinearLayout) view.findViewById(R.id.ll_delete);
			view.setTag(holder);
		return view;
	}
   private class ViewHolder{
	   TextView tv_alphabat,tv_song_name;
	   ImageView iv_add_to_play_list;
	   RadioButton rb_more_options;
	   LinearLayout ll_set_to_ring,ll_add,ll_music_message,ll_delete;
   }
	

}
