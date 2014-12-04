package com.lecz.clubdelosvencedores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.lecz.clubdelosvencedores.Game.Game;
import com.lecz.clubdelosvencedores.register.RegisterActivityFive;
import com.lecz.clubdelosvencedores.register.RegisterActivityTwo;


public class PreGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_game);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Button gotoGame = (Button) findViewById(R.id.gotoGame);
        final CheckBox no_show_again = (CheckBox) findViewById(R.id.no_show_again);

        no_show_again.setChecked(false);
        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        final SharedPreferences.Editor mEditor = mPrefs.edit();
        gotoGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PreGameActivity.this, Game.class);
                startActivity(myIntent);

                if(no_show_again.isChecked()){

                    mEditor.putBoolean("show_again", false).commit();
                }else{
                    mEditor.putBoolean("show_again", true).commit();
                }
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
                Intent intent = new Intent(PreGameActivity.this, UpdateInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.gotoUpdateFriends:
                Intent intents = new Intent(PreGameActivity.this, RegisterActivityFive.class);
                startActivity(intents);
                break;
            case R.id.gotoRestartPlan:
                AlertDialog.Builder builder = new AlertDialog.Builder(PreGameActivity.this);

                builder.setMessage("Está seguro que desea reiniciar el plan de fumado?").setIcon(R.drawable.pulmones)
                        .setTitle("Reiniciar plan?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intents = new Intent(PreGameActivity.this, RegisterActivityTwo.class);
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
