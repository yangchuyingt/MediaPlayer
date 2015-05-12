package com.weige.player.adapter;

import com.weige.player.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AlphabatOrderAdapter extends BaseAdapter {
      private String[] alphabat;
	  private Context context;

	public AlphabatOrderAdapter(String [] alphabat,Context context){
    	  this.alphabat=alphabat;
    	  this.context =context;
      }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return alphabat.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return alphabat[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    View view =View.inflate(context, R.layout.alphabat_textview, null);
	    TextView tv=(TextView) view.findViewById(R.id.tv_alphabat_adapter);
	    tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//¼Ó´Ö
	    tv.getPaint().setFakeBoldText(true);//¼Ó´Ö
		tv.setText(alphabat[position]);
		return view;
	}

}
