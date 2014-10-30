package com.lecz.clubdelosvencedores.register;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.lecz.clubdelosvencedores.DatabaseManagers.AchievementDataSource;
import com.lecz.clubdelosvencedores.MyActivity;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.general.MainActivity;
import com.lecz.clubdelosvencedores.objects.Achievement;


public class RegisterActivityThree extends Activity {

    private ViewPager viewPager;
    private Button button;
    private int time_of_day;
    private int days_of_week;
    private RadioButton tomorrow, afternoon, night, weekday, weekend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity_three);

        button = (Button) findViewById(R.id.savennext3);

        tomorrow = (RadioButton) findViewById(R.id.register_habits_tomorrow);
        afternoon = (RadioButton) findViewById(R.id.register_habits_afternoon);
        night = (RadioButton) findViewById(R.id.register_habits_night);
        weekday = (RadioButton) findViewById(R.id.register_habits_weekday);
        weekend = (RadioButton) findViewById(R.id.register_habits_weekend);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor habits = settings.edit();
                if(tomorrow.isChecked()){
                    time_of_day = 1;
                }else{
                    if(afternoon.isChecked()){
                        time_of_day = 2;
                    }else{
                        time_of_day = 3;
                    }
                }
                if(tomorrow.isChecked()){
                    days_of_week = 1;
                }else{
                    days_of_week = 2;
                }

                habits.putString("habits_time_of_day", time_of_day+"");
                habits.putString("habits_days_of_week", days_of_week+"");

                habits.commit();

                AchievementDataSource dsa = new AchievementDataSource(getApplicationContext());

                dsa.open();
                dsa.createAchievement(new Achievement("premio 1", "time", new Long(1000 * 60 * 60 * 2), false));
                dsa.createAchievement(new Achievement("premio 2", "time", new Long(1000 * 60 * 60 * 4), false));
                dsa.createAchievement(new Achievement("premio 3", "time", new Long(1000 * 60 * 60 * 8), false));
                dsa.createAchievement(new Achievement("premio 4", "money", new Long(2000), false));
                dsa.close();

                Intent myIntent = new Intent(getApplication(), MyActivity.class);
                startActivity(myIntent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register_activity_three, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
