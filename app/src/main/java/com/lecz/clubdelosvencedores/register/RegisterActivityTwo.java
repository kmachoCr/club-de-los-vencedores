package com.lecz.clubdelosvencedores.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.lecz.clubdelosvencedores.DatabaseManagers.PlanDetailsDataSource;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.DatabaseManagers.UserDataSource;
import com.lecz.clubdelosvencedores.objects.Achievement;
import com.lecz.clubdelosvencedores.objects.PlanDetail;
import com.lecz.clubdelosvencedores.objects.User;

import java.util.ArrayList;
import java.util.Calendar;


public class RegisterActivityTwo extends Activity {

    private ViewPager viewPager;
    private User user;
    private TextView tv_dias_q_fumo, textView5, textView4, cigarettes_per_day;
    private SeekBar dias_q_no_fumo, dias_q_fumo;
    private Button button;
    private Spinner plan_type;
    private UserDataSource userds;
    ArrayList<User> users;
    private PlanDetailsDataSource dspd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity_two);

        dspd = new PlanDetailsDataSource(this);
        button = (Button) findViewById(R.id.savennext2);
        dias_q_no_fumo = (SeekBar) findViewById(R.id.no_days_quit);
        tv_dias_q_fumo = (TextView) findViewById(R.id.tv_days_quit);
        dias_q_fumo = (SeekBar) findViewById(R.id.days_quit);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.name_user);
        cigarettes_per_day = (TextView) findViewById(R.id.register_cigarettes_per_day);
        plan_type = (Spinner) findViewById(R.id.plan_type);

        dias_q_fumo.setMax(30);
        dias_q_no_fumo.setMax(30);
        userds = new UserDataSource(getApplicationContext());
        userds.open();
        users = userds.getUsers();

        userds.close();

        if(!users.isEmpty()){
            Log.i("Fuma?", users.get(0).getSmoking() + "");
            if(!users.get(0).getSmoking()){
                textView5.setVisibility(View.VISIBLE);
                dias_q_no_fumo.setVisibility(View.VISIBLE);
                tv_dias_q_fumo.setVisibility(View.VISIBLE);
            }
            user = users.get(0);
        }

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userds.open();
                users = userds.getUsers();
                userds.close();

                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());

                if(!users.isEmpty()){
                    user = users.get(0);
                    user.setCigarettes_per_day(Integer.parseInt(cigarettes_per_day.getText().toString()));
                    user.setPlan_type(Integer.parseInt(plan_type.getSelectedItemId() + ""));
                    userds.open();
                    userds.updateUser(user);
                    userds.close();

                    dspd.open();
                    int total = Integer.parseInt(cigarettes_per_day.getText().toString());
                    PlanDetail plan = new PlanDetail();
                    plan.setNumber_day(1);
                    plan.setTotal_cigarettes(total);
                    plan.setUsed_cigarettes(0);
                    plan.setApproved(false);
                    plan.setCurrent(true);
                    plan.setCompleted(false);
                    plan.setDate(c.getTimeInMillis());
                    total--;
                    dspd.createPlanDetail(plan);

                    for(int i = 2; i <= Integer.parseInt(cigarettes_per_day.getText().toString()); i++){
                        plan = new PlanDetail();
                        plan.setNumber_day(i);
                        plan.setTotal_cigarettes(total);
                        plan.setUsed_cigarettes(0);
                        plan.setApproved(false);
                        plan.setCurrent(false);
                        plan.setCompleted(false);
                        c.add(Calendar.DATE, 1);
                        plan.setDate(c.getTimeInMillis());
                        total--;
                        dspd.createPlanDetail(plan);
                    }
                    dspd.close();
                    Intent myIntent = new Intent(getApplication(), RegisterActivityFour.class);
                    startActivity(myIntent);
                }

            }
        });

        plan_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        dias_q_fumo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                textView4.setText(progress + " años");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        dias_q_no_fumo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                textView5.setText(progress + " años");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register_activity_two, menu);
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
