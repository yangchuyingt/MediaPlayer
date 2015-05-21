package com.weige.player.adapter;

import com.nineoldandroids.view.ViewHelper;
import com.weige.player.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LyricAdapter extends BaseAdapter {
	private String [] lric;
	private Context context;
	private ViewHolder[]  holderlist ;
	
    public LyricAdapter(String [] lric,Context context){
    	this.lric=lric;
    	this.context=context;
    	holderlist=new ViewHolder[lric.length];
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
		if(convertView==null){
			ViewHolder holder=new ViewHolder();
			convertView=View.inflate(context, R.layout.textview_adapter, null);
			 holder.tv_liric=(TextView) convertView.findViewById(R.id.tv_liric);
			 convertView.setTag(holder);
			  String str=lric[position];
				str=str.substring(str.indexOf("]")+2);
				holder.tv_liric.setText(str);
				holderlist[position]=holder;
				System.out.println("listposition:"+position);
		}else {
			ViewHolder holder=(ViewHolder) convertView.getTag();
			String str=lric[position];
			str=str.substring(str.indexOf("]")+2);
			holder.tv_liric.setText(str);
			holderlist[position]=holder;
			System.out.println("listposition:"+position);
		}
		
		return convertView;
	}
   public  class ViewHolder{
	   TextView tv_liric;

	public TextView getTv_liric() {
		return tv_liric;
	}


	   
   }
   public ViewHolder [] getholderlist(){
	   return holderlist;
   }
}
