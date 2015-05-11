package com.zyxf.eazyworkdivision.factory;


import com.zyxf.eazyworkdivision.base.BaseFragment;
import com.zyxf.eazyworkdivision.fragment.HomeFragment;
import com.zyxf.eazyworkdivision.fragment.ScanFragment;
import com.zyxf.eazyworkdivision.fragment.SettingFragment;
import com.zyxf.eazyworkdivision.fragment.TotalFragment;

import java.util.HashMap;

public class FragmentFactory {
	public static final int FRAGMENT_HOME = 0;
	public static final int FRAGMENT_SCAN = 1;
	public static final int FRAGMENT_TOTAL = 2;
	public static final int FRAGMENT_SETTING = 3;
	
	public static HashMap<Integer, BaseFragment> mFragments = new HashMap<Integer, BaseFragment>();
	
	
	public static BaseFragment createFragment(int position){
		BaseFragment fragment = mFragments.get(position);
		if( fragment == null){
			switch (position) {
			case FRAGMENT_HOME:
				fragment = new HomeFragment();
				break;
			case FRAGMENT_SCAN:
				fragment = new ScanFragment();
				break;
			case FRAGMENT_TOTAL:
				fragment = new TotalFragment();
				break;
			case FRAGMENT_SETTING:
				fragment = new SettingFragment();
				break;

			default:
				break;
			}
			mFragments.put(position, fragment);
		}
		return fragment;
	}
}
