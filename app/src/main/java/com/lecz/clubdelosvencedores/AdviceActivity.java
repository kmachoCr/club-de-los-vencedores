package com.lecz.clubdelosvencedores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.lecz.clubdelosvencedores.DatabaseManagers.AdviceDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.MotivationsDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.UserDataSource;
import com.lecz.clubdelosvencedores.objects.Achievement;
import com.lecz.clubdelosvencedores.objects.Advice;
import com.lecz.clubdelosvencedores.objects.Motivations;
import com.lecz.clubdelosvencedores.objects.User;
import com.lecz.clubdelosvencedores.register.ActivityFriends;
import com.lecz.clubdelosvencedores.register.RegisterActivityFive;
import com.lecz.clubdelosvencedores.register.RegisterActivityTwo;
import com.lecz.clubdelosvencedores.utilities.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class AdviceActivity extends Activity {

    private TextView type, body;
    private ImageView image;

    private ViewPager viewPager;
    private ImageView intruccion_swipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        viewPager = (ViewPager) findViewById(R.id.viewAdvice);
        intruccion_swipe = (ImageView) findViewById(R.id.intruccion_swipe);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        MotivationsDataSource mds = new MotivationsDataSource(AdviceActivity.this);
        mds.open();
        Motivations m = mds.getMotivations();
        mds.close();

        SVG back_svg = SVGParser.getSVGFromResource(getResources(), R.raw.intruccion_swipe);
        intruccion_swipe.setImageDrawable(back_svg.createPictureDrawable());

        UserDataSource uds = new UserDataSource(AdviceActivity.this);
        uds.open();
        User user = uds.getUser();
        uds.close();

        AdviceDataSource ads = new AdviceDataSource(AdviceActivity.this);
        ads.open();
        ArrayList<Advice> list = ads.getAdvices(user.getGenre() ? 1 : 0, m.isMotiv_money(), m.isMotiv_aesthetic(), m.isMotiv_family(), m.isMotiv_health());
        ads.close();


        long seed = System.nanoTime();
        Collections.shuffle(list, new Random(seed));
        CustomPagerAdapter cs = new CustomPagerAdapter(AdviceActivity.this, list);
        viewPager.setAdapter(cs);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

                intruccion_swipe.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.intern, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.gotoUpdateInfo:
                Intent intent = new Intent(getApplicationContext(), UpdateInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.gotoUpdateFriends:
                Intent intents = new Intent(getApplicationContext(), ActivityFriends.class);
                startActivity(intents);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public class CustomPagerAdapter extends PagerAdapter {

        private ArrayList<Advice> arrayList;


        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context, ArrayList<Advice> list) {
            this.arrayList = list;
            this.mContext = context;
            this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.item_advice, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            TextView type = (TextView) itemView.findViewById(R.id.type);
            TextView body = (TextView) itemView.findViewById(R.id.body);

            if(arrayList.get(position).isMotiv_aesthetic()){
                imageView.setImageResource(R.drawable.icn_consejo_apariencia);
            }else{
                if(arrayList.get(position).isMotiv_family()){
                    imageView.setImageResource(R.drawable.icn_consejo_familia);
                }else{
                    if(arrayList.get(position).isMotiv_health()){
                        imageView.setImageResource(R.drawable.icn_consejo_salud);
                    }else{
                        if(arrayList.get(position).isMotiv_money()){
                            imageView.setImageResource(R.drawable.icn_consejo_ahorro);
                        }else{
                            imageView.setImageResource(R.drawable.icn_consejo_general);
                        }
                    }
                }
            }

            type.setText(arrayList.get(position).getType().toUpperCase());
            body.setText(arrayList.get(position).getBody());

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }
}
