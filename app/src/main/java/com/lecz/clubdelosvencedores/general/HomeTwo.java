package com.lecz.clubdelosvencedores.general;

import android.animation.ObjectAnimator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
public class HomeTwo extends Fragment implements Animation.AnimationListener {
    private ViewPager viewPager, slider;
    private TextView textView, textView2, textView3, textView4, userName;
    private PagerAdapter adapter;
    private String[] rank;
    private PendingIntent pendingIntent;
    private Button add_cigarette;
    private ImageButton panic;
    private int[] flag;
    private UserDataSource userds;
    private PlanDetailsDataSource dspd;
    private SharedPreferences settings;
    private PlanDetail plan;
    private View rootView;
    ImageView image;
    private boolean botonPanic;

    private static final String LIST_FRAGMENT_TAG = "fragment_slide";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_home_two, container, false);

        slider = (ViewPager) rootView.findViewById(R.id.as);
        textView = (TextView) rootView.findViewById(R.id.textView);
        textView2 = (TextView) rootView.findViewById(R.id.textView2);
        textView3 = (TextView) rootView.findViewById(R.id.textView3);
        textView4 = (TextView) rootView.findViewById(R.id.textView4);
        userName = (TextView) rootView.findViewById(R.id.name_user);
        add_cigarette = (Button) rootView.findViewById(R.id.add_cigarette);
        panic = (ImageButton) rootView.findViewById(R.id.add_button);
        settings = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
        rank = new String[] { "China", "India", "United States",
                "Indonesia", "Brazil", "Pakistan", "Nigeria", "Bangladesh",
                "Russia", "Japan" };

        flag = new int[] {
                R.drawable.china,
                R.drawable.india,
                R.drawable.unitedstates,
                R.drawable.indonesia,
                R.drawable.brazil,
                R.drawable.pakistan,
                R.drawable.nigeria,
                R.drawable.bangladesh,
                R.drawable.russia,
                R.drawable.japan };

        adapter = new ViewPagerAdapter(rootView.getContext(), rank, flag);

        slider.setAdapter(adapter);

        dspd = new PlanDetailsDataSource(rootView.getContext());
        userds = new UserDataSource(rootView.getContext());

        dspd.open();
        plan = dspd.getCurrentPlanDetail();
        dspd.close();

        userds.open();
        User user = userds.getUser();
        userds.close();

        int used_cigarettes = settings.getInt("count", 0);

        update_interface(used_cigarettes, user, plan);
        call_service();

        add_cigarette.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                settings = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
                int ret = settings.getInt("count", 0);

                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("count", ret + 1);
                editor.commit();

                if(plan != null){
                    textView4.setText(String.valueOf(ret + 1) + " / " + plan.getTotal_cigarettes());
                    Calendar s = Calendar.getInstance();
                    s.setTimeInMillis(System.currentTimeMillis());

                    userds.open();
                    User userR = userds.getUser();
                    userR.setLast_cigarette(s.getTimeInMillis());
                    userds.updateUser(userR);
                    userds.close();
                }

            }
        });

        panic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toggleList();
            }
        });

        return rootView;
    }


    private void toggleList() {
        if(!botonPanic){
            panic.setImageResource(R.drawable.minus_test);
            botonPanic = true;
        }else{
            panic.setImageResource(R.drawable.plus_test);
            botonPanic = false;
        }
        Fragment f = getFragmentManager()
                .findFragmentByTag(LIST_FRAGMENT_TAG);
        if (f != null) {
            getFragmentManager().popBackStack();
        } else {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_up,
                            R.anim.slide_down,
                            R.anim.slide_up,
                            R.anim.slide_down)
                    .add(R.id.list_fragment_container, Fragment.instantiate(rootView.getContext(), fragment_slide.class.getName()),
                            LIST_FRAGMENT_TAG
                    ).addToBackStack(null).commit();
        }
    }

    public void call_service(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 10);
        calendar.set(Calendar.AM_PM, Calendar.AM);

        Intent myIntent = new Intent(rootView.getContext(), BroadcastService.class);
        pendingIntent = PendingIntent.getBroadcast(rootView.getContext(), 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager)rootView.getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 10, pendingIntent);
    }

    public void update_interface(int used_cigarettes, User user, PlanDetail plan){
        userName.setText(user.getName());
        textView.setText("$" + user.getMoney_saved());
        textView2.setText("No fumados:" + user.getCigarettes_no_smoked());
        textView4.setText(used_cigarettes + " / " + plan.getTotal_cigarettes());
        if(plan != null){
            textView3.setText("Día actual: " + (plan.getNumber_day()));
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}

