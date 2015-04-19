package com.weige.player;

import java.util.ArrayList;
import java.util.List;

import com.weige.player.utils.CacheUtils;
import com.weige.player.utils.Constants;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * @author Administrator 引导界面
 */
public class GuideUI extends Activity implements OnPageChangeListener, OnClickListener {
	private Button btnStartExperience;
	private LinearLayout llPointGroup;
	private View mSelectedPoint;
	private List<ImageView> imageViewList;
	private int basicWidth = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 移除标题
		setContentView(R.layout.guide);

		initView();
	}

	private void initView() {
		ViewPager mViewpager = (ViewPager) findViewById(R.id.viewpager_guide);
		btnStartExperience = (Button) findViewById(R.id.btn_guide_start_experience);
		llPointGroup = (LinearLayout) findViewById(R.id.ll_guide_point_group);
		mSelectedPoint = findViewById(R.id.v_guide_selected_point);

		imageViewList = new ArrayList<ImageView>();
		int[] imageIDs = { 
				R.drawable.guide_one,
				R.drawable.guide_two, 
				R.drawable.guide_three,
				R.drawable.guide_four };
		ImageView iv;
		View view;
		for (int i = 0; i < imageIDs.length; i++) {
			iv = new ImageView(this);
			iv.setBackgroundResource(imageIDs[i]);
			imageViewList.add(iv);
			
			// 向LinearLayout中添加一个点的控件
			view = new View(this);
			LayoutParams params = new LayoutParams(14, 14);
			if (i != 0) {
				params.leftMargin = 20;
			}
			view.setLayoutParams(params);
			view.setBackgroundResource(R.drawable.guide_point_group_bg);
			view.getBackground().setAlpha(100);
			llPointGroup.addView(view);
		}
		// 把Adapter适配器和ViewPager关联起来
		GuideAdapter mAdapter = new GuideAdapter();
		mViewpager.setAdapter(mAdapter);
		mViewpager.setOnPageChangeListener(this);
		btnStartExperience.setOnClickListener(this);

	}
	class GuideAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageViewList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// 向ViewPager中添加一个ImageView对象, 并且在此方法中把那个被添加的imageView对象返回
			ImageView imageView = imageViewList.get(position);
			container.addView(imageView);
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// 把对应position位置的Imageview在ViewPager中移除掉
			container.removeView(imageViewList.get(position));
		}

	}
	
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		if(basicWidth == -1){
			basicWidth = llPointGroup.getChildAt(1).getLeft() - llPointGroup.getChildAt(0).getLeft();
		}
		int leftMargin = (int) ((position + positionOffset) * basicWidth);
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(14,14);
		params.leftMargin = leftMargin;
		mSelectedPoint.setLayoutParams(params);
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		if(position == imageViewList.size() - 1){
			btnStartExperience.setVisibility(View.VISIBLE);
		} else {
			btnStartExperience.setVisibility(View.GONE);
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		CacheUtils.cacheBooleanData(this, Constants.IS_OPEN_MAIN_PAGE_KEY, true);
		startActivity(new Intent(this, MainUI.class));
		finish();
	}
}
