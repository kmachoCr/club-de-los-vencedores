package com.lecz.clubdelosvencedores.register;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.lecz.clubdelosvencedores.DatabaseManagers.MotivationsDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.PlanDetailsDataSource;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.DatabaseManagers.UserDataSource;
import com.lecz.clubdelosvencedores.objects.Achievement;
import com.lecz.clubdelosvencedores.objects.Motivations;
import com.lecz.clubdelosvencedores.objects.PlanDetail;
import com.lecz.clubdelosvencedores.objects.User;

import java.util.ArrayList;
import java.util.Calendar;


public class RegisterActivityTwo extends Activity {

    private User user;
    private TextView count_cigarettes, textView4;
    private SeekBar dias_q_fumo, cigarettes_per_day;
    private ImageButton button, back;
    private CheckBox money, aesthetic, family, health;
    private boolean motivations_money, motivations_aesthetic, motivations_health, motivations_family = false;
    private Spinner plan_type;
    private UserDataSource userds;
    ArrayList<User> users;
    private PlanDetailsDataSource dspd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity_two);


        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        dspd = new PlanDetailsDataSource(this);
        back = (ImageButton) findViewById(R.id.back1);
        button = (ImageButton) findViewById(R.id.savennext1);
        dias_q_fumo = (SeekBar) findViewById(R.id.days_quit);
        textView4 = (TextView) findViewById(R.id.textView4);
        count_cigarettes = (TextView) findViewById(R.id.count_cigarettes);
        cigarettes_per_day = (SeekBar) findViewById(R.id.register_cigarettes_per_day);
        plan_type = (Spinner) findViewById(R.id.plan_type);
        money = (CheckBox) findViewById(R.id.register_motivations_money);
        aesthetic = (CheckBox) findViewById(R.id.register_motivations_aesthetic);
        family = (CheckBox) findViewById(R.id.register_motivations_family);
        health = (CheckBox) findViewById(R.id.register_motivations_health);


        userds = new UserDataSource(getApplicationContext());
        userds.open();
        users = userds.getUsers();
            user = users.get(0);

            cigarettes_per_day.setProgress(user.getCigarettes_per_day());
            plan_type.setSelection(user.getPlan_type());

        userds.close();
        dias_q_fumo.setMax(30);
        cigarettes_per_day.setMax(30);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userds.open();
                users = userds.getUsers();
                userds.close();

                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());

                if(!users.isEmpty()){
                    user = users.get(0);
                    user.setCigarettes_per_day(cigarettes_per_day.getProgress());
                    user.setPlan_type(Integer.parseInt(plan_type.getSelectedItemId() + ""));

                    userds.open();
                    userds.updateUser(user);
                    userds.close();

                    dspd.open();
                    ArrayList<PlanDetail> listPlan = dspd.getPlanDetails();

                    if(listPlan.size() > 0) {
                        for (int i = 0; i < listPlan.size(); i++) {
                            dspd.deletePlanDetail(listPlan.get(i));
                        }
                    }

                    int total = cigarettes_per_day.getProgress();
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

                    for(int i = 2; i <= cigarettes_per_day.getProgress(); i++){
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

                    if(money.isChecked()){
                        motivations_money = true;
                    }
                    if(aesthetic.isChecked()){
                        motivations_aesthetic = true;
                    }
                    if(family.isChecked()){
                        motivations_family = true;
                    }
                    if(health.isChecked()){
                        motivations_health = true;
                    }
                    MotivationsDataSource mds = new MotivationsDataSource(RegisterActivityTwo.this);



                    mds.open();
                    Motivations m = mds.getMotivations();

                    if(m != null){
                        mds.deleteMotivation(m);
                    }

                    mds.createMotivation(new Motivations(motivations_health, motivations_family, motivations_aesthetic, motivations_money));
                    mds.close();
                    Intent myIntent = new Intent(getApplication(), RegisterActivityFive.class);
                    startActivity(myIntent);
                }

            }
        });


        dias_q_fumo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                if(progress == 1){
                    textView4.setText(progress + " año");
                }else{
                    textView4.setText(progress + " años");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        cigarettes_per_day.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                count_cigarettes.setText(progress+"");
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
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
