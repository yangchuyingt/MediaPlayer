package com.weige.player;

import com.weige.player.utils.CacheUtils;
import com.weige.player.utils.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

/**
 * @author Administrator
 * ��ӭ����
 */
public class WelcomeUI extends Activity implements AnimationListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		init();
	}

	/**
	 * ��ʼ������
	 */
	private void init() {
		View rlRootView = findViewById(R.id.rl_root);
		
		ScaleAnimation sa = new ScaleAnimation(
				0, 1, 
				0, 1, 
				Animation.RELATIVE_TO_SELF, 0.5f, 
				Animation.RELATIVE_TO_SELF, 0.5f);
		sa.setDuration(500);
		
		AlphaAnimation aa = new AlphaAnimation(0, 1);
		aa.setDuration(1000);
		
		//�����������ϲ���һ��������
		AnimationSet animSet = new AnimationSet(false);
		animSet.addAnimation(sa);
		animSet.addAnimation(aa);
		animSet.setAnimationListener(this);
		rlRootView.startAnimation(animSet);
	}
	
	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		boolean isOpenMainPage = CacheUtils.getBooleanData(this, Constants.IS_OPEN_MAIN_PAGE_KEY, false);
		if(isOpenMainPage){
			//��������
			startActivity(new Intent(this, MainUI.class));
		} else {
			//����������
			startActivity(new Intent(this, GuideUI.class));
		}
		finish();
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}
}
