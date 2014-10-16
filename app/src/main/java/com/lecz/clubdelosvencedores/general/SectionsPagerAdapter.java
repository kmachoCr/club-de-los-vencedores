package com.lecz.clubdelosvencedores.general;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.lecz.clubdelosvencedores.general.HomeFragment_1;
import com.lecz.clubdelosvencedores.general.HomeFragment_2;

import java.util.Locale;

/**
 * Created by Luis on 10/15/2014.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return new HomeFragment_1();
            case 1:
                return new HomeFragment_2();

        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "";
            case 1:
                return "";
        }
        return null;
    }
}
