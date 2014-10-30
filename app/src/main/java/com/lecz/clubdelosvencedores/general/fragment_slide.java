package com.lecz.clubdelosvencedores.general;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lecz.clubdelosvencedores.DatabaseManagers.AchievementDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.PlanDetailsDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.UserDataSource;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.Achievement;
import com.lecz.clubdelosvencedores.objects.PlanDetail;
import com.lecz.clubdelosvencedores.objects.User;
import com.lecz.clubdelosvencedores.utilities.RelativeLayoutFragment;

import java.util.ArrayList;
import java.util.Calendar;


/**
 *
 */
public class fragment_slide extends Fragment {
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_slide, container, false);

        return rootView;
    }
}
