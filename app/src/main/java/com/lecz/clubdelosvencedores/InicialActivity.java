package com.lecz.clubdelosvencedores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.lecz.clubdelosvencedores.register.RegisterActivityOne;


public class InicialActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        ImageView logo_img = (ImageView) findViewById(R.id.logo_img);
        ImageView logo_iafa = (ImageView) findViewById(R.id.logo_iafa);
        Button btn_start = (Button) findViewById(R.id.btn_start);

        SVG svg_iafa = SVGParser.getSVGFromResource(getResources(), R.raw.icn_iafa);
        logo_iafa.setImageDrawable(svg_iafa.createPictureDrawable());

        SVG svg_pulmon = SVGParser.getSVGFromResource(getResources(), R.raw.pulmon_puntaje);
        logo_img.setImageDrawable(svg_pulmon.createPictureDrawable());


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), RegisterActivityOne.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
