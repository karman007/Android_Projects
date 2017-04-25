package com.example.karma.tourguide;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by karma on 20/02/2017.
 */

public class CustomAdapter extends FragmentPagerAdapter {

    private final String tabTitles[] = {"Restaurants", "Cafes", "Entertainment", "Must See"};

    public CustomAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new RestaurantsFragment();
        } else if (position == 1) {
            return new CafesFragment();
        } else if (position == 2) {
            return new EntertainmentFragment();
        } else {
            return new MustSeeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
