package com.lecz.clubdelosvencedores.general;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.lecz.clubdelosvencedores.DatabaseManagers.AchievementDataSource;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.Achievement;
import com.lecz.clubdelosvencedores.utilities.OnSwipeTouchListener;
import java.util.ArrayList;


public class AchievementsActivity extends Fragment {
    private ArrayList<Achievement> list_money, list_time;
    private String title, description;
    private ArrayList<String> array = new ArrayList<String>();
    private FrameLayout fl;
    private GridView list_achievements;
    private boolean flag;
    private ImageButton achievement_money01, achievement_money02, achievement_money03, achievement_money04, achievement_money05, achievement_money06;
    private ImageButton achievement_health01, achievement_health02, achievement_health03, achievement_health04, achievement_health05, achievement_health06;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_home_four, container, false);

        AchievementDataSource ads = new AchievementDataSource(rootView.getContext());
        ads.open();
        list_money = ads.getAchievementsByType("money");
        list_time = ads.getAchievementsByType("time");
        ads.close();

        achievement_health01 = (ImageButton) rootView.findViewById(R.id.achievement_health01);
        achievement_health02 = (ImageButton) rootView.findViewById(R.id.achievement_health02);
        achievement_health03 = (ImageButton) rootView.findViewById(R.id.achievement_health03);
        achievement_health04 = (ImageButton) rootView.findViewById(R.id.achievement_health04);
        achievement_health05 = (ImageButton) rootView.findViewById(R.id.achievement_health05);
        achievement_health06 = (ImageButton) rootView.findViewById(R.id.achievement_health06);

        achievement_money01 = (ImageButton) rootView.findViewById(R.id.achievement_money01);
        achievement_money02 = (ImageButton) rootView.findViewById(R.id.achievement_money02);
        achievement_money03 = (ImageButton) rootView.findViewById(R.id.achievement_money03);
        achievement_money04 = (ImageButton) rootView.findViewById(R.id.achievement_money04);
        achievement_money05 = (ImageButton) rootView.findViewById(R.id.achievement_money05);
        achievement_money06 = (ImageButton) rootView.findViewById(R.id.achievement_money06);

        ArrayList<ImageButton> money = new ArrayList<ImageButton>();
        ArrayList<ImageButton> health = new ArrayList<ImageButton>();

        health.add(achievement_health01);
        health.add(achievement_health02);
        health.add(achievement_health03);
        health.add(achievement_health04);
        health.add(achievement_health05);
        health.add(achievement_health06);

        money.add(achievement_money01);
        money.add(achievement_money02);
        money.add(achievement_money03);
        money.add(achievement_money04);
        money.add(achievement_money05);
        money.add(achievement_money06);


        for(int i = 0; i < list_money.size(); i++){
            if(list_money.get(i).isCompleted()){
                money.get(i).setImageResource(list_money.get(i).getImage());
            }else{
                money.get(i).setImageResource(R.drawable.cinturon_no_ganado);
            }
        }

        for(int i = 0; i < list_time.size(); i++){
            if(list_time.get(i).isCompleted()){
                health.get(i).setImageResource(list_money.get(i).getImage());
            }else{
                health.get(i).setImageResource(R.drawable.cinturon_no_ganado);
            }
        }


    return rootView;

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
}
