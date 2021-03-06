package com.weige.player.fragment;

import java.util.ArrayList;

import com.weige.player.MainUI;
import com.weige.player.R;
import com.weige.player.adapter.MusicShowAdapter;
import com.weige.player.adapter.Spinneradapter;
import com.weige.player.localmusicfragment.AlbumFragment;
import com.weige.player.localmusicfragment.FolderFragment;
import com.weige.player.localmusicfragment.OneSongFragment;
import com.weige.player.localmusicfragment.SingerFragment;
import com.weige.player.slidingmenu.SlidingMenu;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class LocalMusicFragment extends Fragment implements
		OnCheckedChangeListener {

	private View view;
	private RadioGroup rg_local_music;
	// private Spinner sp_local_music;
	private Spinneradapter adapter;
	private ArrayList<Fragment> fragmentlist;
	private ImageButton ib_local_music_back;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragmentlist = new ArrayList<Fragment>();
		OneSongFragment singleSongFragement = new OneSongFragment();
		fragmentlist.add(singleSongFragement);
		SingerFragment singerfragment = new SingerFragment();
		fragmentlist.add(singerfragment);
		AlbumFragment albumfragment = new AlbumFragment();
		fragmentlist.add(albumfragment);
		FolderFragment folderfragment = new FolderFragment();
		fragmentlist.add(folderfragment);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.localmusic, container, false);
		initview();
		initdata();
		return view;
	}

	private void initview() {
		ib_local_music_back = (ImageButton) view.findViewById(R.id.ib_local_music_back);
		rg_local_music = (RadioGroup) view.findViewById(R.id.rg_local_music);
		((RadioButton) rg_local_music.getChildAt(0)).setChecked(true);

		//

	}

	private void initdata() {
		ib_local_music_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				fm.popBackStack();
				
			}
		});
		rg_local_music.setOnCheckedChangeListener(this);
		setcurrentfragment(1);
		/*	*/

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		setcurrentfragment(checkedId);

	}

	public void setcurrentfragment(int checkedid) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.fl_loacl_music, fragmentlist.get(checkedid - 1));
		ft.commit();
	}

	public MusicShowAdapter getMediaPlayeradapter() {
		return ((OneSongFragment) fragmentlist.get(0)).getmediaPlayeradapter();
	}

}
