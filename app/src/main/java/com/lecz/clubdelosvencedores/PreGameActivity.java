package com.lecz.clubdelosvencedores;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.lecz.clubdelosvencedores.Game.Game;


public class PreGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_game);

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
        getMenuInflater().inflate(R.menu.pre_game, menu);
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
