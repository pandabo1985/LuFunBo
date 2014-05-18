package com.afayear.lufunbo.view.iface;

import android.support.v4.view.ViewPager;

public interface PagerIndicator extends ViewPager.OnPageChangeListener {
	void notifyDataSetChanged();
	
	void setCurrentItem(int item);
	/**
	 * set a page change listener which will receive forward events
	 * @param listener
	 */
	void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);
	/**
	 * bind the indicator to a viewpager
	 * @param view
	 */
	void setViewPager(ViewPager view);
	
	void setViewPager(ViewPager view, int initialPosition);
}
