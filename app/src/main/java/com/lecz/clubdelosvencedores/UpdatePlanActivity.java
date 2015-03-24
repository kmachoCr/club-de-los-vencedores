package com.lecz.clubdelosvencedores;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.lecz.clubdelosvencedores.DatabaseManagers.ActivityDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.AdviceDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.FifteenPlanDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.MotivationsDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.PlanDetailsDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.SevenPlanDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.ThirtyPlanDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.UserDataSource;
import com.lecz.clubdelosvencedores.general.NotificationMng;
import com.lecz.clubdelosvencedores.objects.Advice;
import com.lecz.clubdelosvencedores.objects.ConfigPlan;
import com.lecz.clubdelosvencedores.objects.Motivations;
import com.lecz.clubdelosvencedores.objects.PlanDetail;
import com.lecz.clubdelosvencedores.objects.User;
import com.lecz.clubdelosvencedores.register.ActivityFriends;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


public class UpdatePlanActivity extends Activity {

    private SharedPreferences settings;
    private User user;
    private TextView count_cigarettes, textView4;
    private SeekBar cigarettes_per_day;
    private Button button;
    private Spinner plan_type;
    private UserDataSource userds;
    ArrayList<User> users;
    private Calendar s;
    private PlanDetailsDataSource dspd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_plan);

        s = Calendar.getInstance();
        s.setTimeInMillis(System.currentTimeMillis());

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        dspd = new PlanDetailsDataSource(this);
        button = (Button) findViewById(R.id.savennext1);
        textView4 = (TextView) findViewById(R.id.textView4);
        count_cigarettes = (TextView) findViewById(R.id.count_cigarettes);
        cigarettes_per_day = (SeekBar) findViewById(R.id.register_cigarettes_per_day);
        plan_type = (Spinner) findViewById(R.id.plan_type);

        userds = new UserDataSource(getApplicationContext());
        userds.open();
        users = userds.getUsers();
        user = users.get(0);

        cigarettes_per_day.setProgress(user.getCigarettes_per_day());
        plan_type.setSelection(user.getPlan_type());
        if (user.getCigarettes_per_day() == 51) {
            count_cigarettes.setText("Más de 50");
        } else {
            count_cigarettes.setText(user.getCigarettes_per_day() + "");
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
                    user.setCigarettes_per_day(cigarettes_per_day.getProgress());
                    user.setPlan_type(1);
                    user.setLast_cigarette(s.getTimeInMillis());
                    user.setDays_without_smoking_count(0.0);
                    user.setDays_with_smoking(0);
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

                    String number_cigarettes;
                    if(cigarettes_per_day.getProgress() >= 10 && cigarettes_per_day.getProgress() <= 15){
                        number_cigarettes = "10-15";
                    }else{
                        if(cigarettes_per_day.getProgress() >= 16 && cigarettes_per_day.getProgress() <= 20){
                            number_cigarettes = "16-20";
                        }else{
                            if(cigarettes_per_day.getProgress() >= 21 && cigarettes_per_day.getProgress() <= 30){
                                number_cigarettes = "21-30";
                            }else{
                                if(cigarettes_per_day.getProgress() >= 31 && cigarettes_per_day.getProgress() <= 40){
                                    number_cigarettes = "31-40";
                                }else{
                                    if(cigarettes_per_day.getProgress() >= 41 && cigarettes_per_day.getProgress() <= 50){
                                        number_cigarettes = "41-50";
                                    }else{
                                        if(cigarettes_per_day.getProgress() >= 51){
                                            number_cigarettes = "50-more";
                                        }else{
                                            number_cigarettes = String.valueOf(cigarettes_per_day.getProgress());
                                        }
                                    }
                                }
                            }
                        }
                    }



                    ConfigPlan cp = new ConfigPlan();
                    if(cigarettes_per_day.getProgress() < 4 ){

                    }else{
                        if(cigarettes_per_day.getProgress() >= 4 && cigarettes_per_day.getProgress() <= 7){
                            SevenPlanDataSource sds = new SevenPlanDataSource(getApplicationContext());
                            sds.open();
                            cp = sds.getSevenPlanDataSourceByCigarettes(number_cigarettes);
                            sds.close();
                        }else{

                            if(cigarettes_per_day.getProgress() >= 8 && cigarettes_per_day.getProgress() <= 15){
                                if(plan_type.getSelectedItem().toString().equals("Desde ya")){

                                }else{
                                    if(plan_type.getSelectedItem().toString().equals("7 días.")){
                                        SevenPlanDataSource sds = new SevenPlanDataSource(getApplicationContext());
                                        sds.open();
                                        cp = sds.getSevenPlanDataSourceByCigarettes(number_cigarettes);
                                        sds.close();
                                    }else{
                                        if(plan_type.getSelectedItem().toString().equals("15 días.")){
                                            FifteenPlanDataSource fds = new FifteenPlanDataSource(getApplicationContext());
                                            fds.open();
                                            cp = fds.getFifteenPlanByCigarettes(number_cigarettes);
                                            fds.close();
                                        }
                                    }
                                }
                            }else{
                                if(cigarettes_per_day.getProgress() >= 16 && cigarettes_per_day.getProgress() <= 51){
                                    Log.i("Spinner", plan_type.getSelectedItem().toString());
                                    if(plan_type.getSelectedItem().toString().equals("Desde ya")){

                                    }else{
                                        if(plan_type.getSelectedItem().toString().equals("7 días.")){
                                            SevenPlanDataSource sds = new SevenPlanDataSource(getApplicationContext());
                                            sds.open();
                                            cp = sds.getSevenPlanDataSourceByCigarettes(number_cigarettes);
                                            sds.close();
                                        }else{
                                            if(plan_type.getSelectedItem().toString().equals("15 días.")){
                                                FifteenPlanDataSource fds = new FifteenPlanDataSource(getApplicationContext());
                                                fds.open();
                                                cp = fds.getFifteenPlanByCigarettes(number_cigarettes);
                                                fds.close();
                                            }else{
                                                if(plan_type.getSelectedItem().toString().equals("30 días.")){
                                                    ThirtyPlanDataSource tds = new ThirtyPlanDataSource(getApplicationContext());
                                                    tds.open();
                                                    cp = tds.getFifteenPlanByCigarettes(number_cigarettes);
                                                    tds.close();
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if(!plan_type.getSelectedItem().toString().equals("Desde ya")) {
                        for (int i = 0; i < cp.getDayConfig().size(); i++) {
                            PlanDetail plan = new PlanDetail();
                            plan.setNumber_day(i + 1);
                            plan.setTotal_cigarettes(cp.getDayConfig().get(i));
                            plan.setUsed_cigarettes(0);
                            plan.setApproved(false);
                            plan.setCurrent(false);
                            plan.setCompleted(false);
                            c.add(Calendar.DATE, 1);
                            plan.setDate(c.getTimeInMillis());
                            dspd.createPlanDetail(plan);
                        }

                        PlanDetail s = dspd.getPlanDetailByDay(1);
                        s.setCurrent(true);
                        dspd.updatePlanDetail(s);
                        dspd.close();
                    }



                    Intent myIntent = new Intent(UpdatePlanActivity.this, MyActivity.class);

                    startActivity(myIntent);


                }

            }
        });

        cigarettes_per_day.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                if(progress < 4){
                    ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<String>(UpdatePlanActivity.this, R.layout.spinner_item,  getResources().getStringArray(R.array.now));
                    plan_type.setAdapter(spinnerCountShoesArrayAdapter);

                }else {
                    if (progress > 3 && progress < 8) {
                        ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<String>(UpdatePlanActivity.this, R.layout.spinner_item,  getResources().getStringArray(R.array.seven));
                        plan_type.setAdapter(spinnerCountShoesArrayAdapter);
                    } else {
                        if (progress > 8 && progress < 16) {
                            ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<String>(UpdatePlanActivity.this, R.layout.spinner_item,  getResources().getStringArray(R.array.fifteen));
                            plan_type.setAdapter(spinnerCountShoesArrayAdapter);
                        } else {
                            if (progress > 15 && progress <= 51) {
                                ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<String>(UpdatePlanActivity.this, R.layout.spinner_item,  getResources().getStringArray(R.array.all));
                                plan_type.setAdapter(spinnerCountShoesArrayAdapter);
                            }
                        }
                    }

                }

                if (progress == 51) {
                    count_cigarettes.setText("Más de 50");
                } else {
                    count_cigarettes.setText(progress + "");
                }
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
        getMenuInflater().inflate(R.menu.intern, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.gotoUpdateInfo:
                Intent intent = new Intent(getApplicationContext(), UpdateInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.gotoUpdateFriends:
                Intent intents = new Intent(getApplicationContext(), ActivityFriends.class);
                startActivity(intents);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
