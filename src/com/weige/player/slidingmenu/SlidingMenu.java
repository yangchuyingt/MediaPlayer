package com.weige.player.slidingmenu;

import com.nineoldandroids.view.ViewHelper;
import com.weige.player.R;
import com.weige.player.utils.ScreenUtils;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SlidingMenu extends HorizontalScrollView {

	/**
	 * 屏幕宽度
	 */
	private int mScreenWidth;
	/**
	 * 菜单右边距
	 */
	private int mMenuRightPadding;
	/**
	 * 菜单的宽度
	 */
	private int mMenuWidth;
	private int mHalfMenuWidth;

	private boolean isOpen;

	private boolean once;

	private ViewGroup mMenu;
	private ViewGroup mMainUI;
	private RelativeLayout rl_bottom;

	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mScreenWidth = ScreenUtils.getScreenWidth(context);

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.SlidingMenu, defStyle, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.SlidingMenu_leftPadding:
				// 默认50
				mMenuRightPadding = a.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 50f,
								getResources().getDisplayMetrics()));// 默认为10DP
				break;
			}
		}
		a.recycle();
	}

	public SlidingMenu(Context context) {
		this(context, null, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/**
		 * 显示的设置一个宽度
		 */
		if (!once) {
			LinearLayout wrapper = (LinearLayout) getChildAt(0);
			mMainUI = (ViewGroup) wrapper.getChildAt(0);
			mMenu = (ViewGroup) wrapper.getChildAt(1);

			mMenuWidth = mScreenWidth - mMenuRightPadding;
			mHalfMenuWidth = mMenuWidth / 2;
			mMenu.getLayoutParams().width = mMenuWidth;
			mMainUI.getLayoutParams().width = mScreenWidth;
			rl_bottom = (RelativeLayout) mMainUI.findViewById(R.id.rl_bottom);

		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			// 将菜单隐藏
			this.scrollTo(0, 0);
			once = true;
		}
	}

	private Rect getrect() {
		Rect rect = new Rect(rl_bottom.getLeft(), rl_bottom.getTop(),
				rl_bottom.getRight(), rl_bottom.getBottom());
		return rect;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int downx = (int) ev.getRawX();
			int downy = (int) ev.getRawY();
			if (getrect().contains(downx, downy)) {

				return false;
			}
			break;

		default:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override  
    public boolean dispatchTouchEvent(MotionEvent ev)  
    {  
        int action = ev.getAction();  
        switch (action)  
        {  
        case MotionEvent.ACTION_DOWN:  
            break;  
        case MotionEvent.ACTION_MOVE:  
            break;  
        case MotionEvent.ACTION_UP:  
            break;  
        default:  
            break;  
        }  
        return super.dispatchTouchEvent(ev);  
    }
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int scrollX = getScrollX();
		int action = ev.getAction();
		if (getrect().contains((int) ev.getX(), (int) ev.getY())) {
			return true;
		} else {
			super.onTouchEvent(ev);
		}
		switch (action) {
		case MotionEvent.ACTION_DOWN:  
            break;  
        case MotionEvent.ACTION_MOVE: 
        	break;
		// Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
		case MotionEvent.ACTION_UP:
			if (scrollX > mHalfMenuWidth) {
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = true;
			} else {
				this.smoothScrollTo(0, 0);
				isOpen = false;
			}
			return true;
//			break;
		}
		return super.onTouchEvent(ev);
	}
	

	/**
	 * 打开菜单
	 */
	public void openMenu() {
		if (isOpen)
			return;
		this.smoothScrollTo(mMenuWidth, 0);
		isOpen = true;
	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu() {
		if (isOpen) {
			this.smoothScrollTo(0, 0);
			isOpen = false;
		}
	}

	/**
	 * 切换菜单状态
	 */
	public void toggle() {
		if (isOpen) {
			closeMenu();
		} else {
			openMenu();
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		float scale = l * 1.0f / mMenuWidth;
		float rightScale = (0.8f + scale * 0.2f);
		float leftScale = (1 - 0.3f * scale);

		ViewHelper.setPivotX(mMainUI, mMenuWidth);
		ViewHelper.setPivotY(mMainUI, mMainUI.getHeight() / 2);
		ViewHelper.setScaleX(mMainUI, leftScale);
		ViewHelper.setScaleY(mMainUI, leftScale);
		ViewHelper.setAlpha(mMainUI, 0.6f + 0.4f * (1 - scale));

		ViewHelper.setScaleX(mMenu, rightScale);
		ViewHelper.setScaleY(mMenu, rightScale);
		ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * scale);
		ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.05f);//偏移量
	}

}
