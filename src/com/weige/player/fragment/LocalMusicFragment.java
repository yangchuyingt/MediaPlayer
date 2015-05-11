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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class LocalMusicFragment extends Fragment implements OnCheckedChangeListener, OnItemClickListener {

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
		rg_local_music.setOnCheckedChangeListener(this);
		sp_local_music.setOnItemClickListener(this);
		Spinneradapter adapter =new Spinneradapter();
		sp_local_music.setAdapter(adapter);
		
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

}
