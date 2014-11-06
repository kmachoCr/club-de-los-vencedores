package com.lecz.clubdelosvencedores.general;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lecz.clubdelosvencedores.DatabaseManagers.AchievementDataSource;
import com.lecz.clubdelosvencedores.MyActivity;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.Achievement;
import com.lecz.clubdelosvencedores.utilities.OnSwipeTouchListener;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 *
 */
public class HomeFour extends Fragment {
    private ArrayList<Achievement> list;
    private ArrayList<String> array = new ArrayList<String>();
    private FrameLayout fl;
    private GridView list_achievements;
    private boolean flag;
    private View rootView, rrrrr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.activity_achievements, container, false);

        AchievementDataSource ads = new AchievementDataSource(rootView.getContext());
        ads.open();
        list = ads.getAllAchievements();
        Log.i("tamano", list.size() + "");
        ads.close();

        list_achievements = (GridView) rootView.findViewById(R.id.list_achievements);
        fl = (FrameLayout) rootView.findViewById(R.id.description_achievement_container);
        Activity host = getActivity();
        AchievementsAdapter adapter = new AchievementsAdapter(rootView.getContext(), list, host);
        list_achievements.setAdapter(adapter);

        fl.setOnTouchListener(new OnSwipeTouchListener(rootView.getContext()) {
            public void onSwipeBottom() {

                if(flag){
                    toggleList();
                    flag = false;
                    collapse (fl);
                }
            }
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
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

    public void toggleList(){
        Fragment f = getFragmentManager().findFragmentByTag("achievement_detail");
        if (f != null) {
            getFragmentManager().popBackStack();
        } else {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_up,
                            R.anim.slide_down,
                            R.anim.slide_up,
                            R.anim.slide_down)
                    .add(R.id.description_achievement_container,
                            Fragment.instantiate(
                                    rootView.getContext(),
                                    description_achievement.class.getName()
                            ),
                            "achievement_detail"
                    ).addToBackStack(null).commit();
        }


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

            if(arrayList.get(position).isCompleted()){
                image_achievement.setImageResource(arrayList.get(position).getImage());
            }else{
                image_achievement.setImageResource(R.drawable.xmark);
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