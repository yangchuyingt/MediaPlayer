package com.weige.player.fragment;

import com.weige.player.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;

public class MainFragment extends Fragment implements OnClickListener {

	private View mainView;
	private RadioButton mTabTing;
	private RadioButton mTabKan;
	private RadioButton mTabChang;
	private TingFragment mTing;
	private KanFragment mKan;
	private ChangFragment mChang;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainView = inflater.inflate(R.layout.main, container, false);
		return mainView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		// 初始化控件
		mTabTing = (RadioButton) mainView.findViewById(R.id.rb_ic_main_top_ting);
		mTabKan = (RadioButton) mainView.findViewById(R.id.rb_ic_main_top_kan);
		mTabChang = (RadioButton) mainView.findViewById(R.id.rb_ic_main_top_chang);
		
		// 设置监听
		mTabTing.setOnClickListener(this);
		mTabKan.setOnClickListener(this);
		mTabChang.setOnClickListener(this);
		
		initMainFragment();
	}

	private void initMainFragment() {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		mTing = new TingFragment();
		ft.replace(R.id.fl_content, mTing);
		ft.commit();
	}

	@Override
	public void onClick(View v) {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		switch (v.getId()) {
		case R.id.rb_ic_main_top_ting:
			if (mTing == null) {
				mTing = new TingFragment();
			}
			ft.replace(R.id.fl_content, mTing);
			break;
		case R.id.rb_ic_main_top_kan:
			if (mKan == null) {
				mKan = new KanFragment();
			}
			ft.replace(R.id.fl_content, mKan);
			break;
		case R.id.rb_ic_main_top_chang:
			if (mChang == null) {
				mChang = new ChangFragment();
			}
			ft.replace(R.id.fl_content, mChang);
			break;

		default:
			break;
		}
		ft.commit();
	}
	
}
