package com.weige.player.adapter;

import com.weige.player.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LyricAdapter extends BaseAdapter {
	private String [] lric;
	private Context context;
	
    public LyricAdapter(String [] lric,Context context){
    	this.lric=lric;
    	this.context=context;
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lric.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lric[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=View.inflate(context, R.layout.textview_adapter, null);
		TextView  tv_liric=(TextView) convertView.findViewById(R.id.tv_liric);
		String str=lric[position];
		str=str.substring(str.indexOf("]")+2);
		tv_liric.setText(str);
		return convertView;
	}

}
