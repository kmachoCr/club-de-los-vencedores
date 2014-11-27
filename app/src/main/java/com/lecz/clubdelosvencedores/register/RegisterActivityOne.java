package com.lecz.clubdelosvencedores.register;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.lecz.clubdelosvencedores.MyActivity;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.DatabaseManagers.UserDataSource;
import com.lecz.clubdelosvencedores.objects.User;

import java.util.Calendar;


public class RegisterActivityOne extends Activity {
    private ViewPager viewPager;
    private TextView name;
    Spinner age;
    private ImageButton button;
    private RadioButton radioM, radioF, smoking, noSmoking;
    private UserDataSource userds;
    private User validateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity_one);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);




        userds = new UserDataSource(getApplication().getApplicationContext());
        userds.open();
        validateUser = userds.getUser();
        userds.close();
        Boolean value;
        Bundle b = getIntent().getExtras();
        if(b !=  null){
            value = b.getBoolean("update", false);
        }else{
            value = false;
        }


            button = (ImageButton) findViewById(R.id.savennext1);
            name = (TextView) findViewById(R.id.register_name);
            age = (Spinner) findViewById(R.id.register_age);
            radioM = (RadioButton) findViewById(R.id.radioM);
            radioF = (RadioButton) findViewById(R.id.radioF);
            smoking = (RadioButton) findViewById(R.id.smoking);
            final Calendar s = Calendar.getInstance();
            s.setTimeInMillis(System.currentTimeMillis());

            if(validateUser != null){
                name.setText(validateUser.getName());

                radioM.setChecked(false);
                radioF.setChecked(false);
                if(validateUser.getGenre()){
                    radioM.setChecked(true);
                }else{
                    radioF.setChecked(true);
                }
            }

            SVG next = SVGParser.getSVGFromResource(getResources(), R.raw.icn_pag_next);
            button.setImageDrawable(next.createPictureDrawable());

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if(validateUser != null){
                        validateUser.setName(name.getText().toString());
                        validateUser.setAge(Integer.parseInt(age.getSelectedItem().toString()));
                        validateUser.setGenre(radioM.isChecked());
                        validateUser.setSmoking(smoking.isChecked());

                        userds.open();
                        userds.updateUser(validateUser);
                        userds.close();
                    }else{
                        User user = new User();

                        user.setName(name.getText().toString());
                        user.setAge(Integer.parseInt(age.getSelectedItem().toString()));
                        user.setGenre(radioM.isChecked());
                        user.setSmoking(smoking.isChecked());
                        user.setMoney_saved(0);
                        user.setCigarettes_per_day(0);
                        user.setDays_without_smoking(0.0);
                        user.setDays_without_smoking_count(0.0);
                        user.setPlan_type(0);
                        user.setCigarettes_no_smoked(0);
                        user.setLast_cigarette(s.getTimeInMillis());

                        userds.open();
                        userds.createUser(user);
                        userds.close();
                    }


                    Intent myIntent = new Intent(getApplication(), RegisterActivityTwo.class);
                    startActivity(myIntent);
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
        getMenuInflater().inflate(R.menu.register_activity_one, menu);
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
