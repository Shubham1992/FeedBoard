package in.feedboard.tabsswipe.adapter;

import in.feedboard.tabsswipe.Entertainment;
import in.feedboard.tabsswipe.Miscellaneous;
import in.feedboard.tabsswipe.Technology;
import in.feedboard.tabsswipe.News;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	private String[] tabsList = { "       News       ", "  Entertainment ", "  Technology ", "Miscellaneous" };
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// News fragment activity
			return new News();
		case 1:
			// Entertainment fragment activity
			return new Entertainment();
		case 2:
			// Technology fragment activity
			return new Technology();

		case 3:
		// Miscellaneous fragment activity
		return new Miscellaneous();
	}

		return null;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return tabsList[position];
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
