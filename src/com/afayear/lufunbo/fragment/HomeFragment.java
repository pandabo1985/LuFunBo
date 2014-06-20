package com.afayear.lufunbo.fragment;

import com.afayear.lufunbo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {
	
	private  View mHomeView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mHomeView = inflater.inflate(R.layout.fragment_home, null);
		TextView tv =  (TextView) mHomeView.findViewById(R.id.tv_name);
		tv.setText("panda");
		return mHomeView;
	}
}
