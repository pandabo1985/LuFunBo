package com.afayear.lufunbo.fragment;

import com.afayear.lufunbo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SliderFragment extends Fragment {
	private  View mSliderView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mSliderView = inflater.inflate(R.layout.fragment_home, null);
		TextView tv =  (TextView) mSliderView.findViewById(R.id.tv_name);
		
		tv.setText("可笑的地球人呀你们在干嘛呢");
		return mSliderView;
	}
}
