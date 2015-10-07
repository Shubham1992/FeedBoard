package in.feedboard.tabsswipe.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.feedboard.tabsswipe.headlineclasses.Head2;
import in.feedboard.tabsswipe.headlineclasses.Head3;

public class HeadlinesTabsPagerAdapter extends FragmentPagerAdapter {

	public HeadlinesTabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// News fragment activity
			return new Head3();
		case 1:
			// Entertainment fragment activity
			return new Head2();
		case 2:
			// Technology fragment activity
			return new Head3();

	/*	case 3:
		// Miscellaneous fragment activity
		return new Miscellaneous();*/
	}

		return null;
	}


	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
