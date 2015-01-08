package com.lecz.clubdelosvencedores.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.lecz.clubdelosvencedores.DatabaseManagers.MotivationsDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.PlanDetailsDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.UserDataSource;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.Motivations;
import com.lecz.clubdelosvencedores.objects.PlanDetail;
import com.lecz.clubdelosvencedores.objects.User;

import java.util.ArrayList;
import java.util.Calendar;


public class RegisterActivityTwov2 extends Activity {

    private User user;
    private TextView count_cigarettes, textView4;
    private SeekBar dias_q_fumo, tiempo_sin_fumar, cigarettes_per_day;
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
        back = (ImageButton) findViewById(R.id.back1);
        button = (ImageButton) findViewById(R.id.savennext1);
        dias_q_fumo = (SeekBar) findViewById(R.id.days_quit);
        tiempo_sin_fumar = (SeekBar) findViewById(R.id.no_days_quit);

        textView4 = (TextView) findViewById(R.id.textView4);
        count_cigarettes = (TextView) findViewById(R.id.count_cigarettes);
        cigarettes_per_day = (SeekBar) findViewById(R.id.register_cigarettes_per_day);


        userds = new UserDataSource(getApplicationContext());
        userds.open();
        users = userds.getUsers();
        user = users.get(0);

        cigarettes_per_day.setProgress(user.getCigarettes_per_day());
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
                    user.setDays_without_smoking(Double.parseDouble(dias_q_fumo.getProgress()+""));
                    user.setDays_with_smoking(tiempo_sin_fumar.getProgress());
                    userds.open();
                    userds.updateUser(user);
                    userds.close();
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
                    if(progress == 30){
                        textView4.setText("Más de 30 años");
                    }else{
                        textView4.setText(progress + " años");
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
