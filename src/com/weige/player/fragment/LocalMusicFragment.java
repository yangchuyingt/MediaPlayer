package com.weige.player.fragment;

import com.weige.player.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LocalMusicFragment extends Fragment {

	private View localmusicView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		localmusicView = inflater.inflate(R.layout.localmusic, container, false);
		return localmusicView;
	}
}
