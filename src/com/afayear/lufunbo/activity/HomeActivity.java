package com.afayear.lufunbo.activity;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;

import com.afayear.lufunbo.R;
import com.afayear.lufunbo.adapter.SupportTabsAdapter;
import com.afayear.lufunbo.fragment.HomeFragment;
import com.afayear.lufunbo.model.SupportTabSpec;
import com.afayear.lufunbo.view.ExtendedViewPager;
import com.afayear.lufunbo.view.TabPageIndicator;

public class HomeActivity extends BaseSupportThemedActivity {
	
	private Context mContext;
	private ExtendedViewPager mViewPager;
	private TabPageIndicator mPageIndicator;
	private SupportTabsAdapter mPagerAdapter;
	
	private DrawerLayout mDrawerLayout;
	private View mLeftDrawerContainer;
	
	private final ArrayList<SupportTabSpec> mCustomTabs = new ArrayList<SupportTabSpec>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_home);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.base_tabs);
		mContext = this;
		
		initView();
		initData();
		initCListener();

	}




	private void initView() {
		mViewPager = (ExtendedViewPager) findViewById(R.id.main);
		mPageIndicator = (TabPageIndicator) findViewById(R.id.tabs);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mLeftDrawerContainer = findViewById(R.id.left_drawer_container);
		
	}

	private void initData() {
		mPagerAdapter = new SupportTabsAdapter(mContext, getSupportFragmentManager(), mPageIndicator);
		mViewPager.setAdapter(mPagerAdapter);
		mPageIndicator.setViewPager(mViewPager);
		
		initTabs();
		
	}
	

	private void initTabs() {
		final List<SupportTabSpec> tabs = getHomeTabs(this);
		mCustomTabs.clear();
		mCustomTabs.addAll(tabs);
		mPagerAdapter.clear();
		mPagerAdapter.addTabs(tabs);
	}




	private List<SupportTabSpec> getHomeTabs(Context context) {
		if (context == null) return Collections.emptyList();
		final ArrayList<SupportTabSpec> tabs = new ArrayList<SupportTabSpec>();
		for (int i = 0; i < 4; i++) {
			Bundle bundle = new Bundle();
			final Class<? extends Fragment> homeFragment = HomeFragment.class;
			Object object = new Object();
			SupportTabSpec tabSpec = new SupportTabSpec("name"+i,object, homeFragment, bundle, i);
			tabs.add(tabSpec);
		}
		return tabs;
	}




	private void initCListener() {
		
	}


}
