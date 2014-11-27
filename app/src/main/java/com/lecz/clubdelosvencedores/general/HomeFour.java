package com.lecz.clubdelosvencedores.general;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.larvalabs.svgandroid.SVG;
import com.lecz.clubdelosvencedores.DatabaseManagers.AchievementDataSource;
import com.lecz.clubdelosvencedores.MyActivity;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.Achievement;
import com.lecz.clubdelosvencedores.register.RegisterActivityTwo;
import com.lecz.clubdelosvencedores.utilities.OnSwipeTouchListener;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 *
 */
public class HomeFour extends Fragment implements TabHost.OnTabChangeListener {
    private ArrayList<Achievement> list_money, list_time;
    private ArrayList<String> array = new ArrayList<String>();
    private FrameLayout fl;
    private GridView list_achievements_money, list_achievements_health;
    private LinearLayout layout_health, layout_money;
    private RelativeLayout btn_health, btn_money;
    private boolean flag;
    private View rootView, rrrrr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.activity_achievements, container, false);

        AchievementDataSource ads = new AchievementDataSource(rootView.getContext());
        ads.open();
        list_money = ads.getAchievementsByType("money");
        list_time = ads.getAchievementsByType("time");

        ads.close();

        list_achievements_money = (GridView) rootView.findViewById(R.id.list_achievements_money);
        list_achievements_health = (GridView) rootView.findViewById(R.id.list_achievements_health);
        layout_money = (LinearLayout) rootView.findViewById(R.id.layout_money);
        layout_health = (LinearLayout) rootView.findViewById(R.id.layout_health);
        btn_money = (RelativeLayout) rootView.findViewById(R.id.btn_money);
        btn_health = (RelativeLayout) rootView.findViewById(R.id.btn_health);

        Activity host = getActivity();
        AchievementsAdapter adapter1 = new AchievementsAdapter(rootView.getContext(), list_money, host);
        AchievementsAdapter adapter2 = new AchievementsAdapter(rootView.getContext(), list_time, host);

        list_achievements_money.setAdapter(adapter1);
        list_achievements_health.setAdapter(adapter2);

        btn_money.setPadding(0, 20, 0, 0);

        btn_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("health", "health");

                layout_money.getLayoutParams().height = 50;
                layout_money.getLayoutParams().width = 50;
                layout_money.setVisibility(View.INVISIBLE);

                layout_health.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
                layout_health.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
                layout_health.setVisibility(View.VISIBLE);

                btn_money.setPadding(0, 20, 0, 0);

                btn_health.setPadding(0, 0, 0, 0);

            }
        });

        btn_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("money", "money");


                layout_health.getLayoutParams().height = 50;
                layout_health.getLayoutParams().width = 50;
                layout_health.setVisibility(View.INVISIBLE);

                layout_money.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
                layout_money.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
                layout_money.setVisibility(View.VISIBLE);

                btn_money.setPadding(0, 0, 0, 0);

                btn_health.setPadding(0, 20, 0, 0);

            }
        });

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

    public static void collapse(final FrameLayout v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }
            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    @Override
    public void onTabChanged(String tabId) {

    }


    public class AchievementsAdapter extends ArrayAdapter<Object> {
        private Context context;
        private Activity host;
        private ArrayList<Achievement> arrayList;
        ImageView image_achievement;
        TextView name_achievement;
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
                name_achievement = (TextView) convertView.findViewById(R.id.name_achievement);
            }else{
                convertView.getTag();
            }

            name_achievement.setText(arrayList.get(position).getTitle());
            if(arrayList.get(position).isCompleted()){
                image_achievement.setImageResource(arrayList.get(position).getImage());
            }else{
                image_achievement.setImageResource(R.drawable.cinturon_no_ganado);
            }


            image_achievement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());

                    builder.setMessage(arrayList.get(position).getDescription()).setTitle(arrayList.get(position).getTitle());

                    builder.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });


            return  (convertView);
        }

    }


}