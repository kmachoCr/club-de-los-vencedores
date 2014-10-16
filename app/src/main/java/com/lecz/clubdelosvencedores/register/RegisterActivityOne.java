package com.lecz.clubdelosvencedores.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.DatabaseManagers.UserDataSource;
import com.lecz.clubdelosvencedores.objects.User;


public class RegisterActivityOne extends Activity {
    private ViewPager viewPager;
    private TextView name, age;
    private Button button;
    private RadioButton radioM, radioF, smoking, noSmoking;
    private UserDataSource userds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity_one);

        button = (Button) findViewById(R.id.savennext1);
        viewPager = (ViewPager) findViewById(R.id.pager);
        name = (TextView) findViewById(R.id.register_name);
        age = (TextView) findViewById(R.id.register_age);
        radioM = (RadioButton)findViewById(R.id.radioM);
        smoking = (RadioButton) findViewById(R.id.smoking);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userds = new UserDataSource(getApplication().getApplicationContext());
                User user = new User();
                user.setName(name.getText().toString());
                user.setAge(Integer.parseInt(age.getText().toString()));
                user.setGenre(radioM.getText().equals("1"));
                user.setSmoking(smoking.getText().equals("1"));
                user.setMoney_saved(0);
                user.setCigarettes_per_day(0);
                user.setDays_without_smoking(0);
                user.setDays_without_smoking_count(0);
                user.setPlan_type(0);
                user.setCigarettes_no_smoked(0);

                userds.open();
                userds.createUser(user);
                userds.close();

                Intent myIntent = new Intent(getApplication(), RegisterActivityTwo.class);
                startActivity(myIntent);
            }
        });
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
