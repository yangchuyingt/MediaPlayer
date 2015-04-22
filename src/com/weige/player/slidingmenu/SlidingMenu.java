package com.weige.player.slidingmenu;


import com.nineoldandroids.view.ViewHelper;
import com.weige.player.R;
import com.weige.player.utils.ScreenUtils;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView {

	/**
	 * ��Ļ���
	 */
	private int mScreenWidth;
	/**
	 * �˵��ұ߾�
	 */
	private int mMenuRightPadding;
	/**
	 * �˵��Ŀ��
	 */
	private int mMenuWidth;
	private int mHalfMenuWidth;

	private boolean isOpen;

	private boolean once;

	private ViewGroup mMenu;
	private ViewGroup mContent;

	public SlidingMenu(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);

	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		mScreenWidth = ScreenUtils.getScreenWidth(context);

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.SlidingMenu, defStyle, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++)
		{
			int attr = a.getIndex(i);
			switch (attr)
			{
			case R.styleable.SlidingMenu_leftPadding:
				// Ĭ��50
				mMenuRightPadding = a.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 50f,
								getResources().getDisplayMetrics()));// Ĭ��Ϊ10DP
				break;
			}
		}
		a.recycle();
	}

	public SlidingMenu(Context context)
	{
		this(context, null, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		/**
		 * ��ʾ������һ�����
		 */
		if (!once)
		{
			LinearLayout wrapper = (LinearLayout) getChildAt(0);
			mContent = (ViewGroup) wrapper.getChildAt(0);
			mMenu = (ViewGroup) wrapper.getChildAt(1);

			mMenuWidth = mScreenWidth - mMenuRightPadding;
			mHalfMenuWidth = mMenuWidth / 2;
			mMenu.getLayoutParams().width = mMenuWidth;
			mContent.getLayoutParams().width = mScreenWidth;

		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		if (changed)
		{
			// ���˵�����
			this.scrollTo(0, 0);
			once = true;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		int action = ev.getAction();
		switch (action)
		{
		// Upʱ�������жϣ������ʾ������ڲ˵����һ������ȫ��ʾ����������
		case MotionEvent.ACTION_UP:
			int scrollX = getScrollX();
			if (scrollX > mHalfMenuWidth)
			{
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = true;
			} else
			{
				this.smoothScrollTo(0, 0);
				isOpen = false;
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * �򿪲˵�
	 */
	public void openMenu()
	{
		if (isOpen)
			return;
		this.smoothScrollTo(mMenuWidth, 0);
		isOpen = true;
	}

	/**
	 * �رղ˵�
	 */
	public void closeMenu()
	{
		if (isOpen)
		{
			this.smoothScrollTo(0, 0);
			isOpen = false;
		}
	}

	/**
	 * �л��˵�״̬
	 */
	public void toggle()
	{
		if (isOpen)
		{
			closeMenu();
		} else
		{
			openMenu();
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt)
	{
		super.onScrollChanged(l, t, oldl, oldt);
		float scale = l * 1.0f / mMenuWidth;
		float rightScale = (0.8f + scale * 0.2f);
		float leftScale = (1 - 0.3f * scale);
		
		ViewHelper.setPivotX(mContent, mMenuWidth);
		ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
		ViewHelper.setScaleX(mContent, leftScale);
		ViewHelper.setScaleY(mContent, leftScale);
		ViewHelper.setAlpha(mContent, 0.6f + 0.4f * (1 - scale));
		
		ViewHelper.setScaleX(mMenu, rightScale);
		ViewHelper.setScaleY(mMenu, rightScale);
		ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * scale);
//		ViewHelper.setTranslationX(mMenu, mMenuWidth * scale);//ƫ����
	}

}
