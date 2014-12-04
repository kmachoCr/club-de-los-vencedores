package com.lecz.clubdelosvencedores.Game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lecz.clubdelosvencedores.NewScoreGameActivity;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.ScoreGameActivity;
import com.lecz.clubdelosvencedores.UpdateInfoActivity;
import com.lecz.clubdelosvencedores.register.RegisterActivityFive;
import com.lecz.clubdelosvencedores.register.RegisterActivityTwo;


public class Game extends Activity{
    ProgressBar count;
    BlockAdapter bk;
    TextView level, points, chronometer;
    private int current_level;
    CountDownTimer ct;
    GridView gridview;
    Block[] blocks;
    private int score, reward;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        current_level = 1;
        setContentView(R.layout.game);
        count = (ProgressBar) findViewById(R.id.count);
        level = (TextView) findViewById(R.id.tv_level);
        points = (TextView) findViewById(R.id.tv_points);
        chronometer = (TextView) findViewById(R.id.tv_crono);
        count.setMax(10000);
        reward = 20;
        getActionBar().setDisplayHomeAsUpEnabled(true);
        chronometer.setText(10000/1000 + " seg");
            ct = new CountDownTimer(10000, 50) {

            public void onTick(long millisUntilFinished) {
                count.setProgress((int) millisUntilFinished);
                chronometer.setText(millisUntilFinished/1000 + " seg");
            }

            public void onFinish() {
                count.setProgress(0);
                boolean isWinner = true;
                for(int i = 0; i < blocks.length; i++){
                    if(blocks[i].isTarget()){
                        if(!blocks[i].isClicked()){
                            isWinner = false;
                        }
                    }
                }
                if(!isWinner){
                    ct.cancel();
                    finishGame();

                }
            }
        }.start();

        gridview = (GridView) findViewById(R.id.gridView2);
        bk = new BlockAdapter(this, getResources(), gridview.getMeasuredHeight());

        blocks = bk.setBlocks(13);
        gridview.setAdapter(bk);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if(!blocks[position].isClicked()){
                    if(blocks[position].isTarget()){
                        score = score + reward;
                        points.setText(score + "");
                        blocks[position].setClicked(true);
                        gridview.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                        ImageView iv = (ImageView) v.findViewById(R.id.imageView);
                        iv.setImageResource(android.R.color.transparent);
                        boolean isWinner = true;
                        for(int i = 0; i < blocks.length; i++){
                            if(blocks[i].isTarget()){
                                if(!blocks[i].isClicked()){
                                    isWinner = false;
                                }
                            }
                        }
                        if(isWinner){
                            current_level++;
                            score = score + ((count.getMax() - count.getProgress()) / 1100);
                            points.setText(score + "");
                            level.setText(" " + current_level);
                            ct.cancel();
                            setDifficulty(current_level);
                        }
                    }else{
                        gridview.getChildAt(position).setBackgroundColor(Color.BLACK);
                        score = score - 100;
                        points.setText(score + "");
                    }
                }else{
                    if(blocks[position].isTarget()){
                        gridview.getChildAt(position).setBackgroundColor(Color.BLACK);
                        score = score - 100;
                        points.setText(score + "");
                    }
                }

            }
        });
    }

    private void finishGame(){


        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        int max_score = mPrefs.getInt("max_score", 0);
        Intent intent;
        if(max_score < score){
            final SharedPreferences.Editor mEditor = mPrefs.edit();
            mEditor.putInt("max_score", score).commit();
            intent = new Intent(Game.this, NewScoreGameActivity.class);
        }else{
            intent = new Intent(Game.this, ScoreGameActivity.class);
        }

        intent.putExtra("level", current_level+"");
        intent.putExtra("score", score+"");
        startActivity(intent);
    }
    private void setDifficulty(int level){
        int max = 0, number_blocks = 0;
        if(level >= 1 && level < 5) {
            max = 10000;
            count.setMax(10000);
            number_blocks = 13;
            reward = 20;
        }
        if(level >= 5 && level < 10) {
            max = 8000;
            count.setMax(8000);
            reward = 20;
            number_blocks = 14;
        }
        if( level >= 10 && level < 15) {
            max = 7000;
            count.setMax(7000);
            reward = 25;
            number_blocks = 16;
        }
        if( level >= 15 && level < 20) {
            number_blocks = 17;
            reward = 25;
            count.setMax(6000);
            max = 6000;
        }
        if( level >= 20 && level < 25) {
            number_blocks = 18;
            reward = 30;
            count.setMax(5000);
            max = 5000;
        }
        if( level >= 25 && level < 30) {
            number_blocks = 18;
            reward = 30;
            max = 5000;
        }
        if( level >= 30 && level < 40) {
            number_blocks = 18;
            reward = 30;
            count.setMax(4000);
            max = 4000;
        }

        if( level >= 40) {
            reward = 40;
            number_blocks = 18;
            count.setMax(4000);
            max = 4000;
        }

        ct = new CountDownTimer(max, 100) {

            public void onTick(long millisUntilFinished) {
                count.setProgress((int) millisUntilFinished);
                chronometer.setText(millisUntilFinished/1000 + " seg");

            }

            public void onFinish() {
                count.setProgress(0);
                boolean isWinner = true;
                for(int i = 0; i < blocks.length; i++){
                    if(blocks[i].isTarget()){
                        if(!blocks[i].isClicked()){
                            isWinner = false;
                        }
                    }
                }
                if(!isWinner){
                    ct.cancel();
                    finishGame();
                }
            }
        }.start();

        blocks = bk.setBlocks(number_blocks);
        gridview.invalidateViews();
        gridview.setAdapter(bk);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {


            return super.onKeyDown(keyCode, event);
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.gotoUpdateInfo:
                Intent intent = new Intent(Game.this, UpdateInfoActivity.class);
                ct.cancel();
                startActivity(intent);
                break;
            case R.id.gotoUpdateFriends:
                Intent intents = new Intent(Game.this, RegisterActivityFive.class);
                ct.cancel();
                startActivity(intents);
                break;
            case R.id.gotoRestartPlan:
                AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);

                builder.setMessage("Está seguro que desea reiniciar el plan de fumado?").setIcon(R.drawable.pulmones)
                        .setTitle("Reiniciar plan?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intents = new Intent(Game.this, RegisterActivityTwo.class);
                        ct.cancel();
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_my);
    }
}
