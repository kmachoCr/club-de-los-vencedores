package com.lecz.clubdelosvencedores.register;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.lecz.clubdelosvencedores.DatabaseManagers.AchievementDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.MotivationsDataSource;
import com.lecz.clubdelosvencedores.MyActivity;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.Achievement;
import com.lecz.clubdelosvencedores.objects.Motivations;


public class RegisterActivityFour extends Activity {

    private ImageView imageView;
    private ViewPager viewPager;
    private Button button, uploadimage;
    private CheckBox money, aesthetic, family, health;
    private boolean motivations_money, motivations_aesthetic, motivations_health, motivations_family = false;
    private static final int IMAGE_PICKER_SELECT = 999;
    private String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity_four);

        button = (Button) findViewById(R.id.savennext4);
        uploadimage = (Button) findViewById(R.id.uploadimage);
        viewPager = (ViewPager) findViewById(R.id.as);
        imageView = (ImageView) findViewById(R.id.imageView);
        money = (CheckBox) findViewById(R.id.register_motivations_money);
        aesthetic = (CheckBox) findViewById(R.id.register_motivations_aesthetic);
        family = (CheckBox) findViewById(R.id.register_motivations_family);
        health = (CheckBox) findViewById(R.id.register_motivations_health);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor habits = settings.edit();
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
                MotivationsDataSource mds = new MotivationsDataSource(RegisterActivityFour.this);



                mds.open();
                Motivations m = mds.getMotivations();

                if(m != null){
                     mds.deleteMotivation(m);
                }

                mds.createMotivation(new Motivations(motivations_health, motivations_family, motivations_aesthetic, motivations_money));
                mds.close();

                Intent myIntent = new Intent(getApplication(),RegisterActivityFive.class);
                startActivity(myIntent);
            }
        });

        uploadimage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), IMAGE_PICKER_SELECT);
            }
        });




    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICKER_SELECT) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                imageView.setImageURI(selectedImageUri);
            }
        }
    }

    public String getPath(Uri uri) {
        Activity host = this;
        ContentResolver cr = host.getContentResolver();
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = cr.query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register_activity_four, menu);
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
