package com.weige.player.fragment;

import com.weige.player.R;
import com.weige.player.service.UpdateService;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TingFragment extends Fragment implements OnClickListener {

	private View tingView;
	private ImageView ivDelete;
	private EditText etSearch;
	private RelativeLayout rlLocalMusic;
	private LocalMusicFragment localmusicFragment;
	private ImageButton ibSearch;
	private int musiccount;
     @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	Cursor cursor = getActivity().getContentResolver().query(  
			       MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,  
			       MediaStore.Audio.Media.DEFAULT_SORT_ORDER); 
    	musiccount = cursor.getCount();
    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		tingView = inflater.inflate(R.layout.ting, container, false);
		initView();
		return tingView;
	}

	private void initView() {
		ivDelete = (ImageView) tingView
				.findViewById(R.id.iv_del_text_ting_search);
		etSearch = (EditText) tingView.findViewById(R.id.et_search);
		ibSearch = (ImageButton) tingView.findViewById(R.id.ib_ting_search);
		rlLocalMusic = (RelativeLayout) tingView
				.findViewById(R.id.rl_localmusic);
		TextView musiccount=(TextView) tingView.findViewById(R.id.tv_local_music_counts);
		musiccount.setText(this.musiccount+"首");
		ImageButton ib_search=(ImageButton) tingView.findViewById(R.id.ib_ting_search);
		ib_search.setOnClickListener(this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ivDelete.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				etSearch.setText("");
			}
		});

		etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				EditText text = (EditText) v;
				String hint;
				if (hasFocus) {
					hint = text.getHint().toString();
					text.setTag(hint);
					text.setHint("");
				} else {
					hint = text.getTag().toString();
					text.setHint(hint);
				}
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
				ft.setCustomAnimations(
						R.anim.localmusicfragment_left_enter,
						R.anim.localmusicfragment_left_exit,
						R.anim.localmusicfragment_pop_left_enter,
						R.anim.localmusicfragment_pop_left_exit);
				ft.addToBackStack(null);
				localmusicFragment = new LocalMusicFragment();
				ft.replace(R.id.fl_main, localmusicFragment);
				ft.commit();
			}
		});
	}

	public LocalMusicFragment getlocalMusicFragment() {
		return localmusicFragment;
	}
	@Override
	public void onClick(View v) {
	  getActivity().startService(new Intent(getActivity(), UpdateService.class));
		
	}

}
