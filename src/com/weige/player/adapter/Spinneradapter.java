package com.weige.player.adapter;

import com.weige.player.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class Spinneradapter implements SpinnerAdapter {
     private Context context;
	private int[] img;
	private String[] playtext;

	public Spinneradapter(Context  context, int[] img, String[] playtext) {
		this.img=img;
		this.playtext=playtext;
		this.context =context;
	}
	


	private class ViewHolder{
		ImageView iv_sp_mark;
		ImageView iv_sp_play_img;
		TextView  tv_sp_play_text;
	}
	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}


	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}


	


	

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder;
		if(convertView==null){
			convertView=View.inflate(context, R.layout.spinneradapterview, null);
			viewholder=new ViewHolder();
			viewholder.iv_sp_mark=(ImageView) convertView.findViewById(R.id.iv_sp_mark);
			
			viewholder.iv_sp_play_img=(ImageView) convertView.findViewById(R.id.iv_sp_play_img);
			viewholder.tv_sp_play_text=(TextView) convertView.findViewById(R.id.tv_sp_play_text);
			convertView.setTag(viewholder);
		}else {
			viewholder=(ViewHolder) convertView.getTag();
			
		}
		viewholder.iv_sp_play_img.setImageResource(img[position]);
		viewholder.tv_sp_play_text.setText(playtext[position]);
		return convertView;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder;
		if(convertView==null){
			convertView=View.inflate(context, R.layout.spinneradapterview, null);
			viewholder=new ViewHolder();
			viewholder.iv_sp_mark=(ImageView) convertView.findViewById(R.id.iv_sp_mark);
			viewholder.iv_sp_mark.setVisibility(View.GONE);
			viewholder.iv_sp_play_img=(ImageView) convertView.findViewById(R.id.iv_sp_play_img);
			viewholder.tv_sp_play_text=(TextView) convertView.findViewById(R.id.tv_sp_play_text);
			convertView.setTag(viewholder);
		}else {
			viewholder=(ViewHolder) convertView.getTag();
			
		}
		viewholder.iv_sp_play_img.setImageResource(img[position]);
		viewholder.tv_sp_play_text.setText(playtext[position]);
		return convertView;
	}
	
}
