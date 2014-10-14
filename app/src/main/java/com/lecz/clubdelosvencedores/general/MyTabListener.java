package com.lecz.clubdelosvencedores.general;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;

import com.lecz.clubdelosvencedores.R;

public class MyTabListener implements ActionBar.TabListener {
    Fragment fragment;

    public MyTabListener(Fragment fragment) {
        this.fragment = fragment;
    }

    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
      
    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.remove(fragment);
    }

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // nothing done here
    }
}