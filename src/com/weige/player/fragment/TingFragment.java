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
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class TingFragment extends Fragment {

	private View tingView;
	private ImageView ivDelete;
	private EditText etSearch;
	private RelativeLayout rlLocalMusic;
	private LocalMusicFragment localmusicFragment;
	private ImageButton ibSearch;

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

}
