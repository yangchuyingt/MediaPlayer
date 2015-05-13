package com.weige.player.fragment;

import com.weige.player.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class TingFragment extends Fragment {

	private View tingView;
	private ImageView ivDelete;
	private EditText etSearch;
	private RelativeLayout rlLocalMusic;
	private LocalMusicFragment localmusicFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		tingView = inflater.inflate(R.layout.ting, container, false);
		return tingView;
	}
	
	private void findViewById() {
		ivDelete = (ImageView) tingView.findViewById(R.id.iv_del_text_ting_search);
		etSearch = (EditText) tingView.findViewById(R.id.et_search);
		rlLocalMusic = (RelativeLayout) tingView.findViewById(R.id.rl_localmusic);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		findViewById();
		
		ivDelete.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				etSearch.setText("");
			}
		});
		
		etSearch.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			public void afterTextChanged(Editable s) {
				if (s.length() == 0) {
					ivDelete.setVisibility(View.GONE);
				} else {
					ivDelete.setVisibility(View.VISIBLE);
				}
			}
		});
		
		rlLocalMusic.setOnClickListener(new OnClickListener() {
			
			

			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.addToBackStack(null);
				localmusicFragment=new LocalMusicFragment();
				ft.replace(R.id.fl_main,localmusicFragment );
				ft.commit();
			}
		});
	}
	public LocalMusicFragment getlocalMusicFragment(){
		return localmusicFragment;
	}
}
	
