package com.afayear.lufunbo.view;


import com.afayear.lufunbo.R;
import com.afayear.lufunbo.view.iface.PagerIndicator;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class TabPageIndicator extends HorizontalScrollView implements
		PagerIndicator {

	private final LayoutInflater mInflater;
	private final LinearLayout mLayout;
	int mMaxTabWidth;
	private ViewPager.OnPageChangeListener mPageListener;
	private ViewPager mViewPager;
	private TabListener mTabListener;
	private int mCurrentItem;
	private int mSelectedTabIndex;
	private Runnable mTabSelector;
	private TabProvider mTabProvider;
	private boolean mDisplayLabel, mDisplayIcon = true;
	
	private boolean mSwitchingEnabled = true;
	private final OnClickListener mTabClickListener = new OnClickListener() {
		@Override
		public void onClick(final View view) {
			if (!mSwitchingEnabled) return;
			final TabView tabView = (TabView) view;
			if (mCurrentItem == tabView.getIndex() && mTabListener != null) {
				mTabListener.onPageReselected(mCurrentItem);
			}
			mCurrentItem = tabView.getIndex();
			setCurrentItem(mCurrentItem);
		}
	};
	private final OnLongClickListener mTabLongClickListener = new OnLongClickListener() {

		@Override
		public boolean onLongClick(final View view) {
			if (!mSwitchingEnabled) return false;
			final TabView tabView = (TabView) view;
			return mTabListener != null && mTabListener.onTabLongClick(tabView.getIndex());
		}
	};
	
	public TabPageIndicator(final Context context) {
		this(context, null);
	}

	public TabPageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		setHorizontalScrollBarEnabled(false);
		mInflater = LayoutInflater.from(context);
		mLayout = new LinearLayout(context);
		mLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
		addView(mLayout, new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
	}

	@Override
	public void notifyDataSetChanged() {
		if (mLayout == null || mViewPager == null)
			return;
		mLayout.removeAllViews();
		final PagerAdapter adapter = mViewPager.getAdapter();
		mTabProvider = adapter instanceof TabProvider ? (TabProvider) adapter
				: null;
		mTabListener = adapter instanceof TabListener ? (TabListener) adapter
				: null;
		if (mTabProvider==null) return;
		final int count = adapter.getCount();
		for(int i =0;i<count;i++){
			final CharSequence title = mTabProvider.getPageTitle(i);
			final Drawable icon= mTabProvider.getPageIcon(i);
			if (title != null && icon != null) {
				addTab(title, icon, i);
			} else if (title == null && icon != null) {
				addTab(null, icon, i);
			} else if (title != null && icon == null) {
				addTab(title, null, i);
			}
		}
	}

	@Override
	protected void onAttachedToWindow() {

		super.onAttachedToWindow();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}

	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {

		return true;
	}

	@Override
	protected void onMeasure(final int widthMeasureSpec,
			final int heightMeasureSpec) {
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
		setFillViewport(lockedExpanded);
		final int childCount = mLayout.getChildCount();
		if (childCount > 1
				&& (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)) {
			if (childCount > 2) {
				mMaxTabWidth = (int) (MeasureSpec.getSize(widthMeasureSpec) * 0.4f);
			} else {
				mMaxTabWidth = MeasureSpec.getSize(widthMeasureSpec) / 2;
			}
		} else {
			mMaxTabWidth = -1;
		}

		final int oldWith = getMeasuredWidth();
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int newWidth = getMeasuredWidth();
		if (lockedExpanded && oldWith != newWidth) {

		}
	}

	@Override
	public void onPageScrolled(final int position, final float positionOffset,
			final int positionOffsetPixels) {
		if (mPageListener != null) {
			mPageListener.onPageScrolled(position, positionOffset,
					positionOffsetPixels);
		}
	
	}

	@Override
	public void onPageScrollStateChanged(final int state) {
		if (mPageListener != null) {
			mPageListener.onPageScrollStateChanged(state);
		}

	}

	@Override
	public void onPageSelected(final int position) {
		setCurrentItem(position);
		if (mTabListener != null) {
			mTabListener.onPageSelected(position);
		}
		if (mPageListener != null) {
			mPageListener.onPageSelected(position);
		}
	}

	@Override
	public void setCurrentItem(int item) {
		if (mViewPager == null)
			return;
		mCurrentItem = item;
		mViewPager.setCurrentItem(item);
		mSelectedTabIndex = item;
		final int tabCount = mLayout.getChildCount();
		for (int i = 0; i < tabCount; i++) {
			final View child = mLayout.getChildAt(i);
			final boolean isSelected = i == item;
			child.setSelected(isSelected);
			if (isSelected) {
				animateToTab(item);
			}
		}
	}

	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setViewPager(ViewPager view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setViewPager(ViewPager view, int initialPosition) {
		// TODO Auto-generated method stub

	}

	private void addTab(final CharSequence label, final Drawable icon, final int index) {
			// Workaround for not being able to pass a defStyle on pre-3.0
			final TabView tabView = (TabView) mInflater.inflate(R.layout.vpi__tab, null);
			tabView.init(this, mDisplayLabel ? label : null, mDisplayIcon ? icon : null, index);
			tabView.setFocusable(true);
			tabView.setOnClickListener(mTabClickListener);
			tabView.setOnLongClickListener(mTabLongClickListener);
			tabView.setContentDescription(label);
			mLayout.addView(tabView, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
	//		if (ThemeUtils.shouldApplyColorFilter(getContext())) {
	//			ThemeUtils.applyBackground(tabView, mTabColor);
	//		}
		}

	private void animateToTab(final int position) {
		final View tabView = mLayout.getChildAt(position);
		if (mTabSelector != null) {
			removeCallbacks(mTabSelector);
		}
		mTabSelector = new Runnable() {

			@Override
			public void run() {
				final int scrollPos = tabView.getLeft()
						- (getWidth() - tabView.getWidth()) / 2;
				smoothScrollTo(scrollPos, 0);
				mTabSelector = null;
			}
		};
		post(mTabSelector);
	}

	public interface TabListener {

		public void onPageReselected(int position);

		public void onPageSelected(int position);

		public boolean onTabLongClick(int position);
	}

	/**
	 * A TitleProvider provides the title to display according to a view.
	 */
	public interface TabProvider {

		public int getCount();

		/**
		 * Returns the icon of the view at position
		 * 
		 * @param position
		 * @return
		 */
		public Drawable getPageIcon(int position);

		/**
		 * Returns the title of the view at position
		 * 
		 * @param position
		 * @return
		 */
		public CharSequence getPageTitle(int position);

		public float getPageWidth(int position);
	}
	
	public static class TabView extends FrameLayout {

		private TabPageIndicator mParent;
		private int mIndex;

		public TabView(final Context context, final AttributeSet attrs) {
			super(context, attrs);
		}

		public int getIndex() {
			return mIndex;
		}

		public void init(final TabPageIndicator parent, final CharSequence label, final Drawable icon, final int index) {
			if (isInEditMode()) return;
			mParent = parent;
			mIndex = index;

			final ImageView imageView = (ImageView) findViewById(R.id.tab_item_icon);
			imageView.setVisibility(icon != null ? View.VISIBLE : View.GONE);
			imageView.setImageDrawable(icon);
//			if (parent.shouldApplyColorFilterToTabIcons()) {
//				imageView.setColorFilter(parent.getTabIconColor(), Mode.SRC_ATOP);
//			}
			final TextView textView = (TextView) findViewById(R.id.tab_item_title);
			textView.setVisibility(TextUtils.isEmpty(label) ? View.GONE : View.VISIBLE);
			textView.setText(label);
		}

		public void init(final TabPageIndicator parent, final CharSequence text, final int index) {
			init(parent, text, null, index);
		}

		public void init(final TabPageIndicator parent, final Drawable icon, final int index) {
			init(parent, null, icon, index);
		}

		@Override
		public void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			// Re-measure if we went beyond our maximum size.
			if (!isInEditMode() && mParent.mMaxTabWidth > 0 && getMeasuredWidth() > mParent.mMaxTabWidth) {
				super.onMeasure(MeasureSpec.makeMeasureSpec(mParent.mMaxTabWidth, MeasureSpec.EXACTLY),
						heightMeasureSpec);
			}
		}
	}
}
