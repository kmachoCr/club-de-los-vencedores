package com.lecz.clubdelosvencedores;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lecz.clubdelosvencedores.Game.Game;


public class ScoreGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_game);

        TextView leveltv = (TextView)findViewById(R.id.level_game);
        TextView scoretv = (TextView)findViewById(R.id.score);
        TextView max_scoretv = (TextView)findViewById(R.id.max_score);
        Button play_again = (Button)findViewById(R.id.play_again);

        String level = getIntent().getExtras().getString("level");
        String score = getIntent().getExtras().getString("score");




        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        int max_score = mPrefs.getInt("max_score", 0);

        if(max_score < Integer.parseInt(score)){
            final SharedPreferences.Editor mEditor = mPrefs.edit();
            mEditor.putInt("max_score", Integer.parseInt(score)).commit();
            max_scoretv.setText(score);
        }else{
            max_scoretv.setText(max_score+"");
        }

        leveltv.setText(level);
        scoretv.setText(score);

        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreGameActivity.this, Game.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.score_game, menu);
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
