package com.afayear.lufunbo.adapter;


import static com.afayear.lufunbo.util.CustomTabUtils.getTabIconDrawable;

import java.util.ArrayList;
import java.util.Collection;
import com.afayear.lufunbo.model.SupportTabSpec;
import com.afayear.lufunbo.view.TabPageIndicator;
import com.afayear.lufunbo.view.TabPageIndicator.TabListener;
import com.afayear.lufunbo.view.TabPageIndicator.TabProvider;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SupportTabsAdapter extends FragmentStatePagerAdapter implements TabProvider,TabListener{
	
	private final TabPageIndicator mIndicator;
	private final Context mContext;
	private final ArrayList<SupportTabSpec> mTabs = new ArrayList<SupportTabSpec>();

	public SupportTabsAdapter(final Context context, final FragmentManager fm, final TabPageIndicator indicator) {
		super(fm);
		mContext = context;
		mIndicator = indicator;
		clear();
	}


	public void clear() {
		mTabs.clear();
		notifyDataSetChanged();
	}


	@Override
	public Fragment getItem(int position) {
		final Fragment fragment = Fragment.instantiate(mContext, mTabs.get(position).cls.getName());
		fragment.setArguments(mTabs.get(position).args);
		return fragment;
	}

	@Override
	public int getCount() {
		return mTabs.size();
	}


	public void addTabs(final Collection<? extends SupportTabSpec> specs) {
		mTabs.addAll(specs);
		notifyDataSetChanged();
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		if (mIndicator != null) {
			mIndicator.notifyDataSetChanged();
		}
	}

	@Override
	public void onPageReselected(int position) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onTabLongClick(int position) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Drawable getPageIcon(int position) {
		return getTabIconDrawable(mContext, mTabs.get(position).icon);
	}

}
