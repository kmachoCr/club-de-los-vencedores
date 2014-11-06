package com.lecz.clubdelosvencedores.general;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import com.lecz.clubdelosvencedores.DatabaseManagers.AchievementDataSource;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.Achievement;
import com.lecz.clubdelosvencedores.utilities.OnSwipeTouchListener;
import java.util.ArrayList;


public class AchievementsActivity extends Activity {
    private ArrayList<Achievement> list;
    private String title, description;
    private ArrayList<String> array = new ArrayList<String>();
    private FrameLayout fl;
    private GridView list_achievements;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        AchievementDataSource ads = new AchievementDataSource(getApplicationContext());
        ads.open();
        list = ads.getAllAchievements();
        ads.close();

        list_achievements = (GridView) findViewById(R.id.list_achievements);
        fl = (FrameLayout) findViewById(R.id.description_achievement_container);
        Activity host = (Activity) this;
        AchievementsAdapter adapter = new AchievementsAdapter(getApplicationContext(), list, host);
        list_achievements.setAdapter(adapter);

        fl.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
                Toast.makeText(AchievementsActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(AchievementsActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(AchievementsActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {


            }

            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });


    }

    public static void expand(final FrameLayout v) {

        v.measure(350, FrameLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1 ? 350 : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    protected Drawable convertToGrayscale(Drawable drawable)
    {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

        drawable.setColorFilter(filter);

        return drawable;
    }


    public ArrayList<String> getData(){
        array.add(title);
        array.add(description);
        return  array;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.achievements, menu);
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

    public class AchievementsAdapter extends ArrayAdapter<Object> {
        private Context context;
        private Activity host;
        private ArrayList<Achievement> arrayList;
        ImageView image_achievement;
        private static final String LIST_FRAGMENT_TAG = "";

        public AchievementsAdapter(Context context, ArrayList<Achievement> array, Activity host) {
            super(context, R.layout.item_achievement);
            this.arrayList = array;
            this.host = host;
            this.context = context;
        }

        @Override
        public int getCount(){
            return arrayList.size();
        }


        public View getView(final int position, View convertView, ViewGroup parent){

            if(convertView == null){
                convertView = View.inflate(context, R.layout.item_achievement, null);
                image_achievement = (ImageView) convertView.findViewById(R.id.image_achievement);
            }else{
                convertView.getTag();
            }

            image_achievement.setImageResource(arrayList.get(position).getImage());

            image_achievement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // 1. Instantiate an AlertDialog.Builder with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(AchievementsActivity.this);

// 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage(arrayList.get(position).getDescription()).setTitle(arrayList.get(position).getTitle());

// 3. Get the AlertDialog from create()
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });


            return  (convertView);
        }

    }


}
