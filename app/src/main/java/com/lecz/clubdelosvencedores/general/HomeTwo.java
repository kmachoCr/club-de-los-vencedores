package com.lecz.clubdelosvencedores.general;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Picture;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.lecz.clubdelosvencedores.DatabaseManagers.AchievementDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.ActivityDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.PlanDetailsDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.UserDataSource;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.Achievement;
import com.lecz.clubdelosvencedores.objects.PlanDetail;
import com.lecz.clubdelosvencedores.objects.User;
import com.lecz.clubdelosvencedores.utilities.RelativeLayoutFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



/**
 *
 */
public class HomeTwo extends Fragment implements Animation.AnimationListener {
    private ListView activityLog;
    private TextView textView, money, days, textView4, userName, cigarettes_smoked, tv_smoked, limite, days_to_quit;
    private ActivityAdapter adapter;
    private String[] rank;
    private PendingIntent pendingIntent;
    private ImageButton add_cigarette, panic;
    private int used_cigarettes, notCompleted, size;
    private UserDataSource userds;
    private PlanDetailsDataSource dspd;
    private SharedPreferences settings;
    private PlanDetail plan;
    private View rootView;
    ImageView image;
    private boolean botonPanic;
    private Activity myContext;

    private static final String LIST_FRAGMENT_TAG = "fragment_slide";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView =  inflater.inflate(R.layout.fragment_home_two, container, false);


        activityLog = (ListView) rootView.findViewById(R.id.listActivity);
        textView = (TextView) rootView.findViewById(R.id.textView);
        days_to_quit = (TextView) rootView.findViewById(R.id.days_to_quit);
        limite = (TextView) rootView.findViewById(R.id.limite);
        tv_smoked = (TextView) rootView.findViewById(R.id.tv_smoked);
        days = (TextView) rootView.findViewById(R.id.days);
        money = (TextView) rootView.findViewById(R.id.money);
        textView4 = (TextView) rootView.findViewById(R.id.textView4);
        cigarettes_smoked = (TextView) rootView.findViewById(R.id.cigarettes_smoked);
        userName = (TextView) rootView.findViewById(R.id.name_user);
        add_cigarette = (ImageButton) rootView.findViewById(R.id.add_cigarette);
        panic = (ImageButton) rootView.findViewById(R.id.add_button);
        settings = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());

        String fontPath = "fonts/Go 2 Old Western.ttf";
        Typeface tf = Typeface.createFromAsset(rootView.getContext().getAssets(), fontPath);

        money.setTypeface(tf);
        days.setTypeface(tf);
        userds = new UserDataSource(rootView.getContext());
        userds.open();
        User user = userds.getUser();
        userds.close();
        SVG svg;
        if(user.getGenre()){
            svg = SVGParser.getSVGFromResource(getResources(), R.raw.icn_usuariohombre);
        }else{
            svg = SVGParser.getSVGFromResource(getResources(), R.raw.prueba_icn_usuaria);
        }

        SVG svgadd = SVGParser.getSVGFromResource(getResources(), R.raw.icn_meta_de_hoy);
        SVG addbutton = SVGParser.getSVGFromResource(getResources(), R.raw.icn_fume);

        Drawable drawable = svg.createPictureDrawable();
        add_cigarette.setImageDrawable(addbutton.createPictureDrawable());

        ImageView d = (ImageView) rootView.findViewById(R.id.img_user_home);
        ImageView add_cigarette_ly = (ImageView) rootView.findViewById(R.id.add_cigarette_ly);
        ImageView add_slip_ly = (ImageView) rootView.findViewById(R.id.add_cigarette_ly);
        d.setImageDrawable(drawable);
        add_cigarette_ly.setImageDrawable(svgadd.createPictureDrawable());
        add_slip_ly.setImageDrawable(svgadd.createPictureDrawable());
        ActivityDataSource ads = new ActivityDataSource(rootView.getContext());
        ads.open();
        ArrayList list = ads.getActivities();
        ads.close();

        adapter = new ActivityAdapter(rootView.getContext(), list);

        activityLog.setAdapter(adapter);

        dspd = new PlanDetailsDataSource(rootView.getContext());

        used_cigarettes = settings.getInt("count", 0);
        Log.i("used", used_cigarettes+"");
        dspd.open();
        plan = dspd.getCurrentPlanDetail();
        size = dspd.getPlanDetails().size();
        notCompleted = dspd.getNotCompletedPlanDetail().size();
        if(notCompleted == 0 && size == 0){
            tv_smoked.setVisibility(View.INVISIBLE);
            cigarettes_smoked.setVisibility(View.INVISIBLE);
            limite.setText("Deslices: ");
            textView4.setText(used_cigarettes+"");
            days_to_quit.setText("Días sin fumar");
            days.setText(((int)Math.round(user.getDays_without_smoking_count()))+"");
            // RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            // params.setMargins(200, 200, 10, 0);
            // limite.setLayoutParams(params);
        }else{
            textView4.setText(plan.getTotal_cigarettes()+"");
            days.setText(""+(size - plan.getNumber_day() + 1));
        }

        dspd.close();




        update_interface(used_cigarettes, user, plan, size);
        call_service();

        add_cigarette.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                String msj = "Seguro que fumó?";
                settings = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
                int ret = settings.getInt("count", 0);
                if((ret + 1)  == (plan.getTotal_cigarettes() - 1 ) ){
                    msj = "Seguro que fumó? Solo te quedaría un cigarrillo por fumaría";
                }else{
                    if((ret + 1) == plan.getTotal_cigarettes() ){
                        msj = "Seguro que fumó? Solo te queda un cigarrillo por el día de hoy";
                    }else{
                        if((ret + 1) > plan.getTotal_cigarettes() ){
                            msj = "Seguro que fumó? Ya has pado tu límite del día.";
                        }
                    }
                }
                if(notCompleted  == 0 && size == 0){
                    msj = "Seguro que fumó?";
                }

                builder.setMessage(msj).setTitle("Has fumado?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                int ret = settings.getInt("count", 0);


                                Log.i("Long", "ANTES");
                                if(notCompleted == 0 && size == 0){
                                    textView4.setText(String.valueOf(ret + 1));
                                    Log.i("Long", "1");

                                }else{
                                    Log.i("Long", "2");

                                    cigarettes_smoked.setText(String.valueOf(ret + 1));
                                }
                                Calendar s = Calendar.getInstance();
                                s.setTimeInMillis(System.currentTimeMillis());

                                userds.open();
                                User userR = userds.getUser();
                                userR.setLast_cigarette(s.getTimeInMillis());
                                userds.updateUser(userR);
                                userds.close();


                                SharedPreferences.Editor editor = settings.edit();
                                editor.putInt("count", ret + 1);
                                editor.commit();
                            }
                        })

                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        panic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toggleList();
            }
        });

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        myContext=(Activity) activity;
        super.onAttach(activity);
    }

    private void toggleList() {
        if(!botonPanic){
            panic.setImageResource(R.drawable.salir);
            botonPanic = true;
        }else{
            panic.setImageResource(R.drawable.ayuda);
            botonPanic = false;
        }
        Fragment f = getFragmentManager()
                .findFragmentByTag(LIST_FRAGMENT_TAG);
        if (f != null) {
            getFragmentManager().popBackStack();
        } else {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_up,
                            R.anim.slide_down,
                            R.anim.slide_up,
                            R.anim.slide_down)
                    .add(R.id.list_fragment_container, Fragment.instantiate(rootView.getContext(), fragment_slide.class.getName()),
                            LIST_FRAGMENT_TAG
                    ).addToBackStack(null).commit();
        }
    }



    public void call_service(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 10);
        calendar.set(Calendar.AM_PM, Calendar.AM);

        Intent myIntent = new Intent(rootView.getContext(), BroadcastService.class);
        pendingIntent = PendingIntent.getBroadcast(rootView.getContext(), 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager)rootView.getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 10, pendingIntent);
    }

    public void update_interface(int used_cigarettes, User user, PlanDetail plan, int size){
        userName.setText(user.getName().toUpperCase());
        money.setText("¢" + user.getMoney_saved());

        cigarettes_smoked.setText(used_cigarettes +"");

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}

