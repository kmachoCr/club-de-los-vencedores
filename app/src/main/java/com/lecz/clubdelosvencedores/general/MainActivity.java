package com.lecz.clubdelosvencedores.general;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Chronometer;
import android.widget.TextView;

import com.lecz.clubdelosvencedores.DatabaseManagers.PlanDetailsDataSource;
import com.lecz.clubdelosvencedores.LocalService;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.PlanDetail;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends Activity {

    ViewPager viewPager, viewPager2;
    TextView textView, textView2;
    PagerAdapter adapter;
    String[] rank;
    String[] country;
    String[] population;
    private PendingIntent pendingIntent;
    int[] flag;
    SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

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

        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager2 = (ViewPager) findViewById(R.id.as);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        adapter = new ViewPagerAdapter(MainActivity.this, rank, country, population, flag);

        // Binds the Adapter to the ViewPager
        viewPager2.setAdapter(adapter);
        viewPager.setAdapter(mSectionsPagerAdapter);

        PlanDetailsDataSource dspd = new PlanDetailsDataSource(getApplicationContext());

        PlanDetail plan = new PlanDetail();
        plan.setNumber_day(1);
        plan.setTotal_cigarettes(3);
        plan.setUsed_cigarettes(0);
        plan.setApproved(false);
        plan.setCurrent(false);

        PlanDetail plan2 = new PlanDetail();
        plan2.setNumber_day(2);
        plan2.setTotal_cigarettes(3);
        plan2.setUsed_cigarettes(0);
        plan2.setApproved(false);
        plan2.setCurrent(false);

        PlanDetail plan3 = new PlanDetail();
        plan3.setNumber_day(3);
        plan3.setTotal_cigarettes(2);
        plan3.setUsed_cigarettes(0);
        plan3.setApproved(false);
        plan3.setCurrent(true);


        PlanDetail plan4 = new PlanDetail();
        plan4.setNumber_day(4);
        plan4.setTotal_cigarettes(1);
        plan4.setUsed_cigarettes(0);
        plan4.setApproved(false);
        plan4.setCurrent(false);

        dspd.open();
        ArrayList<PlanDetail> as =  dspd.getPlanDetails();

        if(as.size() < 4){
            dspd.createPlanDetail(plan);
            dspd.createPlanDetail(plan2);
            dspd.createPlanDetail(plan3);
            dspd.createPlanDetail(plan4);
        }

        ArrayList<PlanDetail> asas =  dspd.getPlanDetails();
        dspd.close();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 45);
        calendar.set(Calendar.AM_PM, Calendar.AM);

        Intent myIntent = new Intent(MainActivity.this, BroadcastService.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,0);

        if(!isMyServiceRunning(BroadcastService.class)){
            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 5, pendingIntent);
        }

        textView.setText(asas.size()+"");
        textView2.setText(asas.get(0).getNumber_day()+"");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

/**
 * A {@link android.support.v13.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
