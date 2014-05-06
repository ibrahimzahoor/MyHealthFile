package com.hibapps.healthfile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;
import com.astuetz.PagerSlidingTabStrip;



public class PageSlidingTabStripFragment extends Fragment 
{
	public static final String TAG = PageSlidingTabStripFragment.class
			.getSimpleName();

	public static PageSlidingTabStripFragment newInstance() 
	{
		return new PageSlidingTabStripFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.pager, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view
				.findViewById(R.id.tabs);
		ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
		MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
		pager.setAdapter(adapter);
		tabs.setViewPager(pager);

	}

	public class MyPagerAdapter extends FragmentPagerAdapter 
	{

		public MyPagerAdapter(android.support.v4.app.FragmentManager fm) 
		{
			super(fm);
		}

		private final String[] TITLES = { "Up Coming", "Medicine", "Appointments", "Reports" };

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		
		@Override
		public SherlockListFragment getItem(int position) 
		{
			if(position==1)
			{
				return MedicineFragment.newInstance();
			}
			else if(position==2)
			{
				return AppointmentFragment.newInstance();
			}
			else if(position==3)
			{
				return ReportFragment.newInstance();
			}
			else
			{
				return DashboardFragment.newInstance();
			}
		}
	}


}
