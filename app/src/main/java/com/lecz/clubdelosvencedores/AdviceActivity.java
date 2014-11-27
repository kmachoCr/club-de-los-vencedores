package com.lecz.clubdelosvencedores;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.lecz.clubdelosvencedores.DatabaseManagers.AdviceDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.MotivationsDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.UserDataSource;
import com.lecz.clubdelosvencedores.objects.Advice;
import com.lecz.clubdelosvencedores.objects.Motivations;
import com.lecz.clubdelosvencedores.objects.User;

import java.util.ArrayList;
import java.util.Random;


public class AdviceActivity extends Activity {

    private TextView type, body;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);

        type = (TextView) findViewById(R.id.type);
        body = (TextView) findViewById(R.id.body);
        image = (ImageView) findViewById(R.id.imageView);

        MotivationsDataSource mds = new MotivationsDataSource(AdviceActivity.this);


        SVG svg = SVGParser.getSVGFromResource(getResources(), R.raw.consejo_fumado);
        image.setImageDrawable(svg.createPictureDrawable());
        mds.open();
        Motivations m = mds.getMotivations();
          mds.close();


        UserDataSource uds = new UserDataSource(AdviceActivity.this);
        uds.open();
        User user = uds.getUser();
        uds.close();

        AdviceDataSource ads = new AdviceDataSource(AdviceActivity.this);
        ads.open();
        ArrayList<Advice> list = ads.getAdvices(user.getGenre() ? 1 : 0, m.isMotiv_money(), m.isMotiv_aesthetic(), m.isMotiv_family(), m.isMotiv_health());
        ads.close();


        for(int i = 0; i < list.size(); i++){
            Log.i("nombre", list.get(i).getType());
        }
        Random r = new Random();
        int i1 = r.nextInt((list.size() - 1) - 0 + 1) + 0;

        type.setText(list.get(i1).getType().toUpperCase());
        body.setText(list.get(i1).getBody());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.advice, menu);
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
