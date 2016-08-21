package omz.android.lovenasa;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import omz.android.lovenasa.fragments.ApodFragment;
import omz.android.lovenasa.fragments.ImagesListFragment;
import omz.android.lovenasa.fragments.PatentListFragment;


public class MainActivity extends AppCompatActivity {

    private final static int NUMBER_OF_TABS=3;
    private TabLayout mTabsLayout;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }

    private void bindViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabsLayout=(TabLayout)findViewById(R.id.tabs);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    /*Initiate three tabs (there are 3 tabs):
    1. Astronomy Picture of the day: updated every 24 hours by nasa
    2. A list of older Astronomy of the dat images - currently loaded from a local asset file due to limits of the requests rate of the Nasa API
    3. A grid with a list of patents which nasa released for usage inside the US
    */

    private void setupViewPager(ViewPager viewPager) {
        MyMainAdapter adapter = new MyMainAdapter(getSupportFragmentManager());
        adapter.addFragment(new ApodFragment(), getString(R.string.title_fragment_apod));
        adapter.addFragment(new ImagesListFragment(), getString(R.string.title_fragment_images));
        adapter.addFragment(new PatentListFragment(), getString(R.string.title_fragment_Patents));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(NUMBER_OF_TABS);

    }




    //An adapter for the ViewPager
    private static class MyMainAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyMainAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
