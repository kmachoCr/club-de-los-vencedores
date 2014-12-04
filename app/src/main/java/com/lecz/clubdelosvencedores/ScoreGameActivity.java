package com.lecz.clubdelosvencedores;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.lecz.clubdelosvencedores.Game.Game;
import com.lecz.clubdelosvencedores.general.fragment_slide;


public class ScoreGameActivity extends Activity {
    private boolean botonPanic;
    private ImageButton panic;
    private static final String LIST_FRAGMENT_TAG = "fragment_slide";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_game);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        TextView leveltv = (TextView)findViewById(R.id.level_game);
        TextView scoretv = (TextView)findViewById(R.id.score);
        TextView max_scoretv = (TextView)findViewById(R.id.max_score);
        ImageView imagev = (ImageView) findViewById(R.id.score_game);
        Button play_again = (Button)findViewById(R.id.play_again);
        panic = (ImageButton) findViewById(R.id.add_button);

        String level = getIntent().getExtras().getString("level");
        String score = getIntent().getExtras().getString("score");


        SVG svg = SVGParser.getSVGFromResource(getResources(), R.raw.pulmon_puntaje);
        imagev.setImageDrawable(svg.createPictureDrawable());

        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        int max_score = mPrefs.getInt("max_score", 0);

        if(max_score < Integer.parseInt(score)){
            final SharedPreferences.Editor mEditor = mPrefs.edit();
            mEditor.putInt("max_score", Integer.parseInt(score)).commit();
            max_scoretv.setText("MEJOR PUNTAJE: " + score);
        }else{
            max_scoretv.setText("MEJOR PUNTAJE: " + max_score);
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

        panic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toggleList();
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(ScoreGameActivity.this, MyActivity.class);
            startActivity(intent);
        }

        return super.onKeyDown(keyCode, event);
    }

    private void toggleList() {
        if(!botonPanic){
            panic.setImageResource(R.drawable.salir);
            botonPanic = true;
        }else{
            panic.setImageResource(R.drawable.ayuda);
            botonPanic = false;
        }
        Fragment f = getFragmentManager()
                .findFragmentByTag(LIST_FRAGMENT_TAG);
        if (f != null) {
            getFragmentManager().popBackStack();
        } else {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_up,
                            R.anim.slide_down,
                            R.anim.slide_up,
                            R.anim.slide_down)
                    .add(R.id.list_fragment_container, Fragment.instantiate(ScoreGameActivity.this, fragment_slide.class.getName()),
                            LIST_FRAGMENT_TAG
                    ).addToBackStack(null).commit();
        }
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
