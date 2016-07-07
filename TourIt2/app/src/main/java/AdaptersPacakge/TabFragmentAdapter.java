package AdaptersPacakge;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinit on 31-03-2016.
 */
//adapter used to display the fragments
public class TabFragmentAdapter  extends FragmentPagerAdapter{
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    public TabFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    //based on index the fragments will get displayed.
    @Override
    public Fragment getItem(int index) {

        return mFragmentList.get(index);
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return mFragmentList.size();
    }
    //it adds a fragment to the fragment list with title
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
