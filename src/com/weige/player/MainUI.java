package com.weige.player;


import com.weige.player.fragment.MainFragment;
import com.weige.player.slidingmenu.SlidingMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;

public class MainUI extends FragmentActivity implements OnClickListener {

	private SlidingMenu mMenu;
	private MainFragment mMain;
	private RelativeLayout mbottom;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ÒÆ³ý±êÌâ
		setContentView(R.layout.root);
		mMenu = (SlidingMenu) findViewById(R.id.menu);
		mbottom = (RelativeLayout) findViewById(R.id.rl_bottom);
		mbottom.setOnClickListener(this);
		initFragment();
	}

	private void initFragment() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		mMain = new MainFragment();
		ft.replace(R.id.fl_main, mMain);
		ft.commit();
	}

	public void toggleMenu(View view) {
		mMenu.toggle();
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(this, LyricUI.class));
	}
	
}