package com.lecz.clubdelosvencedores;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.lecz.clubdelosvencedores.Game.Game;


public class NewScoreGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_score_game);

        TextView leveltv = (TextView)findViewById(R.id.level_game);
        TextView max_scoretv = (TextView)findViewById(R.id.max_score);
        ImageView imagev = (ImageView) findViewById(R.id.score_game);
        Button play_again = (Button)findViewById(R.id.play_again);

        String level = getIntent().getExtras().getString("level");
        String score = getIntent().getExtras().getString("score");


        SVG svg = SVGParser.getSVGFromResource(getResources(), R.raw.icn_personajes_mejorpuntaje);
        imagev.setImageDrawable(svg.createPictureDrawable());

        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        int max_score = mPrefs.getInt("max_score", 0);

        max_scoretv.setText(score);
        max_scoretv.setBackgroundResource(R.drawable.new_score_game);


        leveltv.setText(level);

        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewScoreGameActivity.this, Game.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            return true;
        }

        return super.onKeyDown(keyCode, event);
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