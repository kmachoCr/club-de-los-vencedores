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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.lecz.clubdelosvencedores.DatabaseManagers.AchievementDataSource;
import com.lecz.clubdelosvencedores.MyActivity;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.Achievement;


public class RegisterActivityFour extends Activity {

    private ImageView imageView;
    private ViewPager viewPager;
    private Button button, uploadimage;
    private CheckBox money, aesthetic, family, health;
    private int motivations_money, motivations_aesthetic, motivations_health, motivations_family = 0;
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
                    motivations_money = 1;
                }
                if(aesthetic.isChecked()){
                    motivations_aesthetic = 1;
                }
                if(family.isChecked()){
                    motivations_family = 1;
                }
                if(health.isChecked()){
                    motivations_health = 1;
                }

                habits.putString("motivations_money", motivations_money+"");
                habits.putString("motivations_aesthetic", motivations_aesthetic+"");
                habits.putString("motivations_family", motivations_family+"");
                habits.putString("motivations_health", motivations_health+"");

                habits.commit();

                AchievementDataSource dsa = new AchievementDataSource(getApplicationContext());

                dsa.open();
                dsa.createAchievement(new Achievement("premio 1", "time", new Long(1000 * 60 * 60 * 2), false, R.drawable.checkmark, "Pasa un total de 2 horas sin fumar"));
                dsa.createAchievement(new Achievement("premio 2", "time", new Long(1000 * 60 * 60 * 4), false, R.drawable.checkmark, "Pasa un total de 4 horas sin fumar"));
                dsa.createAchievement(new Achievement("premio 3", "time", new Long(1000 * 60 * 60 * 8), false, R.drawable.checkmark, "Pasa un total de 8 horas sin fumar"));
                dsa.createAchievement(new Achievement("premio 4", "time", new Long(1000 * 60 * 60 * 12), false, R.drawable.checkmark, "Pasa un total de 12 horas sin fumar"));
                dsa.createAchievement(new Achievement("premio 5", "time", new Long(1000 * 60 * 60 * 24), false, R.drawable.checkmark, "Pasa un total de 1 día sin fumar"));
                dsa.createAchievement(new Achievement("premio 6", "time", new Long(1000 * 60 * 60 * 48), false, R.drawable.checkmark, "Pasa un total de 2 días sin fumar"));
                dsa.createAchievement(new Achievement("premio 7", "money", new Long(10000), false, R.drawable.checkmark, "Ahorra un total de 10000 colones"));
                dsa.createAchievement(new Achievement("premio 8", "money", new Long(30000), false, R.drawable.checkmark, "Ahorra un total de 30000 colones"));
                dsa.createAchievement(new Achievement("premio 9", "money", new Long(50000), false, R.drawable.checkmark, "Ahorra un total de 50000 colones"));
                dsa.close();

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
