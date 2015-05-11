package com.weige.player.fragment;

import com.weige.player.R;
import com.weige.player.adapter.Spinneradapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class LocalMusicFragment extends Fragment implements OnCheckedChangeListener, OnItemSelectedListener {

	private View view;
	private RadioGroup rg_local_music;
	private Spinner sp_local_music;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.localmusic, container, false);
		initview();
		initdata();
		return view;
	}

	private void initview() {
		rg_local_music = (RadioGroup)view.findViewById(R.id.rg_local_music);
		((RadioButton)rg_local_music.getChildAt(0)).setChecked(true);
		sp_local_music = (Spinner) view.findViewById(R.id.sp_local_music);
		
		
	}
	
	private void initdata() {
		int[] img=new int[]{R.drawable.order_play,R.drawable.radom_play,R.drawable.cycle_play};
		String [] playtext =new String []{
				"顺序播放","随机播放","单曲循环"
		};
		rg_local_music.setOnCheckedChangeListener(this);
		sp_local_music.setOnItemSelectedListener(this);
		Spinneradapter adapter =new Spinneradapter(getActivity(),img,playtext);
		sp_local_music.setAdapter(adapter);
		
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	

}
