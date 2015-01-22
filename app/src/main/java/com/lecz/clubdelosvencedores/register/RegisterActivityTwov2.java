package com.lecz.clubdelosvencedores.register;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.lecz.clubdelosvencedores.DatabaseManagers.ActivityDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.AdviceDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.MotivationsDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.PlanDetailsDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.UserDataSource;
import com.lecz.clubdelosvencedores.MyActivity;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.TourActivity;
import com.lecz.clubdelosvencedores.objects.Advice;
import com.lecz.clubdelosvencedores.objects.Motivations;
import com.lecz.clubdelosvencedores.objects.PlanDetail;
import com.lecz.clubdelosvencedores.objects.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


public class RegisterActivityTwov2 extends Activity {

    private SharedPreferences settings;
    private User user;
    private TextView count_cigarettes, years_smoking, years_not_smoking;
    private SeekBar dias_q_fumo, tiempo_sin_fumar, cigarettes_per_day;
    private CheckBox money, aesthetic, family, health;
    private boolean motivations_money, motivations_aesthetic, motivations_health, motivations_family = false;
    private ImageButton button, back;
    private UserDataSource userds;
    ArrayList<User> users;
    private PlanDetailsDataSource dspd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity_two_v2);



        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        dspd = new PlanDetailsDataSource(this);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        back = (ImageButton) findViewById(R.id.back1);
        button = (ImageButton) findViewById(R.id.savennext1);
        dias_q_fumo = (SeekBar) findViewById(R.id.days_quit);
        tiempo_sin_fumar = (SeekBar) findViewById(R.id.no_days_quit);
        money = (CheckBox) findViewById(R.id.register_motivations_money);
        aesthetic = (CheckBox) findViewById(R.id.register_motivations_aesthetic);
        family = (CheckBox) findViewById(R.id.register_motivations_family);
        health = (CheckBox) findViewById(R.id.register_motivations_health);

        years_smoking = (TextView) findViewById(R.id.years_smoking);
        count_cigarettes = (TextView) findViewById(R.id.count_cigarettes);
        cigarettes_per_day = (SeekBar) findViewById(R.id.register_cigarettes_per_day);
        years_not_smoking = (TextView) findViewById(R.id.years_not_smoking);


        SVG back_svg = SVGParser.getSVGFromResource(getResources(), R.raw.icn_pag_prev);
        back.setImageDrawable(back_svg.createPictureDrawable());

        userds = new UserDataSource(getApplicationContext());
        userds.open();
        users = userds.getUsers();
        user = users.get(0);

        cigarettes_per_day.setProgress(user.getCigarettes_per_day());
        userds.close();
        dias_q_fumo.setMax(30);
        tiempo_sin_fumar.setMax(30);
        cigarettes_per_day.setMax(30);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
            }
        });

        SVG next = SVGParser.getSVGFromResource(getResources(), R.raw.icn_pag_next);
        button.setImageDrawable(next.createPictureDrawable());

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
                MotivationsDataSource mds = new MotivationsDataSource(RegisterActivityTwov2.this);
                mds.open();
                Motivations m = mds.getMotivations();

                if(m != null){
                    mds.deleteMotivation(m);
                }

                mds.createMotivation(new Motivations(motivations_health, motivations_family, motivations_aesthetic, motivations_money));
                mds.close();


                mds.open();
                Motivations ms = mds.getMotivations();
                mds.close();

                AdviceDataSource adds = new AdviceDataSource(RegisterActivityTwov2.this);
                adds.open();
                ArrayList<Advice> list = adds.getAdvices(user.getGenre() ? 1 : 0, ms.isMotiv_money(), ms.isMotiv_aesthetic(), ms.isMotiv_family(), ms.isMotiv_health());
                if(list.size() == 0){
                    list = adds.getAdvices();
                }

                adds.close();


                for(int i = 0; i < list.size(); i++){
                    Log.i("nombre", list.get(i).getType());
                }
                Random r = new Random();
                int i1 = r.nextInt((list.size() - 1) + 1);

                ActivityDataSource acds = new ActivityDataSource(RegisterActivityTwov2.this);
                int icon = 0;
                if(list.get(i1).isMotiv_aesthetic()){
                    icon = R.drawable.icn_apariencia;
                }else{
                    if(list.get(i1).isMotiv_family()){
                        icon = R.drawable.icn_familia;
                    }else{
                        if(list.get(i1).isMotiv_health()){
                            icon = R.drawable.icn_logrosalud;
                        }else{
                            if(list.get(i1).isMotiv_money()){
                                icon = R.drawable.icn_logroahorro;
                            }
                        }
                    }
                }

                acds.open();
                acds.createActivity(new com.lecz.clubdelosvencedores.objects.Activity(icon, System.currentTimeMillis(), list.get(i1).getBody(), "consejo", list.get(i1).getType()));
                acds.close();

                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("count", 0);
                editor.commit();
                SharedPreferences mPrefs = getSharedPreferences("label", 0);
                Boolean register_completed = mPrefs.getBoolean("register_completed", false);
                Boolean show = mPrefs.getBoolean("first_run", true);
                Intent myIntent = new Intent(RegisterActivityTwov2.this, ActivityFriends.class);
                startActivity(myIntent);

            }
            }
        });


        dias_q_fumo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                if(progress == 1){
                    years_smoking.setText(progress + " año");
                }else{
                    if(progress < 1){
                        years_smoking.setText("Menos de un año");
                    }else{
                        if(progress == 30){
                            years_smoking.setText("Más de 30 años");
                        }else{
                            years_smoking.setText(progress + " años");
                        }
                    }
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

        tiempo_sin_fumar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                if(progress == 1){
                    years_not_smoking.setText(progress + " año");
                }else{
                    if(progress < 1){
                        years_not_smoking.setText("Menos de un año");
                    }else{
                        if(progress == 30){
                            years_not_smoking.setText("Más de 30 años");
                        }else{
                            years_not_smoking.setText(progress + " años");
                        }
                    }
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
