package com.weige.player;

import com.weige.player.fragment.ChangFragment;
import com.weige.player.fragment.ContentFragment;
import com.weige.player.fragment.KanFragment;
import com.weige.player.slidingmenu.SlidingMenu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainUI extends FragmentActivity implements OnClickListener {

	private SlidingMenu mMenu;
	private RadioButton mTabTing;
	private RadioButton mTabKan;
	private RadioButton mTabChang;
	private ContentFragment mTing;
	private KanFragment mKan;
	private ChangFragment mChang;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 移除标题
		setContentView(R.layout.main);
		mMenu = (SlidingMenu) findViewById(R.id.id_menu);

		// 初始化控件
		mTabTing = (RadioButton) findViewById(R.id.ib_ic_main_top_ting);
		mTabKan = (RadioButton) findViewById(R.id.ib_ic_main_top_kan);
		mTabChang = (RadioButton) findViewById(R.id.ib_ic_main_top_chang);
		//设置监听
		mTabTing.setOnClickListener(this);
		mTabKan.setOnClickListener(this);
		mTabChang.setOnClickListener(this);
		initFragment();

	}

	private void initFragment() {

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		mTing = new ContentFragment();
		ft.replace(R.id.fl_content, mTing );
		ft.commit();

	}

	public void toggleMenu(View view) {
		mMenu.toggle();
	}

	@Override
	public void onClick(View v) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		
		switch (v.getId()) {
		case R.id.ib_ic_main_top_ting:
			if(mTing == null){
				mTing = new ContentFragment();
			}
			ft.replace(R.id.fl_content, mTing);
			break;
		case R.id.ib_ic_main_top_kan:
			if(mKan == null){
				mKan = new KanFragment();
			}
			ft.replace(R.id.fl_content, mKan);
			break;
		case R.id.ib_ic_main_top_chang:
			if(mChang == null){
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
