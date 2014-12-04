package com.lecz.clubdelosvencedores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lecz.clubdelosvencedores.DatabaseManagers.MotivationsDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.UserDataSource;
import com.lecz.clubdelosvencedores.objects.Motivations;
import com.lecz.clubdelosvencedores.objects.User;
import com.lecz.clubdelosvencedores.register.RegisterActivityFive;
import com.lecz.clubdelosvencedores.register.RegisterActivityTwo;

import java.util.Calendar;


public class UpdateInfoActivity extends Activity {
    private TextView name;
    private Spinner age;
    private Button button;
    private RadioButton radioM, radioF;
    private boolean motivations_money, motivations_aesthetic, motivations_health, motivations_family = false;
    private CheckBox money, aesthetic, family, health;
    private UserDataSource userds;
    private User validateUser;
    private MotivationsDataSource mds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        userds = new UserDataSource(getApplication().getApplicationContext());
        userds.open();
        validateUser = userds.getUser();
        userds.close();

        mds = new MotivationsDataSource(UpdateInfoActivity.this);
        mds.open();
        final Motivations motivations = mds.getMotivations();
        mds.close();

        button = (Button) findViewById(R.id.save);
        name = (TextView) findViewById(R.id.register_name);
        age = (Spinner) findViewById(R.id.register_age);
        radioM = (RadioButton) findViewById(R.id.radioM);
        radioF = (RadioButton) findViewById(R.id.radioF);
        money = (CheckBox) findViewById(R.id.register_motivations_money);
        aesthetic = (CheckBox) findViewById(R.id.register_motivations_aesthetic);
        family = (CheckBox) findViewById(R.id.register_motivations_family);
        health = (CheckBox) findViewById(R.id.register_motivations_health);
        final Calendar s = Calendar.getInstance();
        s.setTimeInMillis(System.currentTimeMillis());

        name.setText(validateUser.getName());
        age.setSelection(validateUser.getAge() - 18);

        if(validateUser.getGenre()){
            radioM.setChecked(true);
        }else{
            radioF.setChecked(true);
        }

        if(motivations.isMotiv_money()){
            money.setChecked(true);
        }
        if(motivations.isMotiv_aesthetic()){
            aesthetic.setChecked(true);
        }
        if(motivations.isMotiv_family()){
            family.setChecked(true);
        }
        if(motivations.isMotiv_health()){
            health.setChecked(true);
        }

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                validateUser.setName(name.getText().toString());
                validateUser.setAge(Integer.parseInt(age.getSelectedItem().toString()));
                validateUser.setGenre(radioM.isChecked());

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

                mds.open();
                mds.deleteMotivation(motivations);
                mds.createMotivation(new Motivations(motivations_health, motivations_family, motivations_aesthetic, motivations_money));
                mds.close();

                userds.open();
                userds.updateUser(validateUser);
                userds.close();

                Toast.makeText(UpdateInfoActivity.this, "Cambios guardados", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateInfoActivity.this, MyActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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
                Intent intents = new Intent(getApplicationContext(), RegisterActivityFive.class);
                startActivity(intents);
                break;
            case R.id.gotoRestartPlan:
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

                builder.setMessage("Está seguro que desea reiniciar el plan de fumado?").setIcon(R.drawable.pulmones)
                        .setTitle("Reiniciar plan?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intents = new Intent(getApplicationContext(), RegisterActivityTwo.class);
                        startActivity(intents);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
