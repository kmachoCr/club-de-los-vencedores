package com.lecz.clubdelosvencedores.general;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityManager;
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
import android.view.KeyEvent;
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
import android.widget.Toast;

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
import com.lecz.clubdelosvencedores.register.RegisterActivityTwo;
import com.lecz.clubdelosvencedores.utilities.RelativeLayoutFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



/**
 *
 */
public class    HomeTwo extends Fragment implements Animation.AnimationListener {
    private ListView activityLog;
    private TextView textView, money, days, textView4, userName, cigarettes_smoked, tv_smoked, limite, days_to_quit, level;
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
    User user;
    ImageView image;
    private boolean botonPanic;
    private Activity myContext;

    private static final String LIST_FRAGMENT_TAG = "fragment_slide";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView =  inflater.inflate(R.layout.fragment_home_two, container, false);
        activityLog = (ListView) rootView.findViewById(R.id.listActivity);
        panic = (ImageButton) rootView.findViewById(R.id.add_button);
        String fontPath = "fonts/Go 2 Old Western.ttf";
        Typeface tf = Typeface.createFromAsset(rootView.getContext().getAssets(), fontPath);



        ActivityDataSource ads = new ActivityDataSource(rootView.getContext());
        ads.open();
        ArrayList list = ads.getActivities();
        ads.close();

        adapter = new ActivityAdapter(rootView.getContext(), list);

        View footer = getActivity().getLayoutInflater().inflate(R.layout.footer_actlist, null);
        View header = getActivity().getLayoutInflater().inflate(R.layout.header_list, null);

        activityLog.addFooterView(footer);
        activityLog.addHeaderView(header);
        activityLog.setAdapter(adapter);


        textView = (TextView) header.findViewById(R.id.textView);
        days_to_quit = (TextView) header.findViewById(R.id.days_to_quit);
        limite = (TextView) header.findViewById(R.id.limite);
        tv_smoked = (TextView) header.findViewById(R.id.tv_smoked);
        days = (TextView) header.findViewById(R.id.days);
        money = (TextView) header.findViewById(R.id.money);
        textView4 = (TextView) header.findViewById(R.id.textView4);
        cigarettes_smoked = (TextView) header.findViewById(R.id.cigarettes_smoked);
        userName = (TextView) header.findViewById(R.id.name_user);
        add_cigarette = (ImageButton) header.findViewById(R.id.add_cigarette);

        settings = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
        level = (TextView) header.findViewById(R.id.level);

        money.setTypeface(tf);
        days.setTypeface(tf);
        userds = new UserDataSource(rootView.getContext());
        userds.open();
        user = userds.getUser();
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



        dspd = new PlanDetailsDataSource(rootView.getContext());

        used_cigarettes = settings.getInt("count", 0);
        Log.i("used", used_cigarettes+"");
        dspd.open();
        plan = dspd.getCurrentPlanDetail();
        ArrayList<PlanDetail> plans = dspd.getPlanDetails();
        Log.i("plan: ", plans.size() + "");
        size = dspd.getPlanDetails().size();
        notCompleted = dspd.getNotCompletedPlanDetail().size();

        if(notCompleted == 0){
            tv_smoked.setVisibility(View.INVISIBLE);
            cigarettes_smoked.setVisibility(View.INVISIBLE);
            limite.setText("Deslices: ");
            textView4.setText(used_cigarettes+"");
            days_to_quit.setText("Días sin fumar");
            days.setText(((int)Math.round(user.getDays_without_smoking_count()))+"");
            level.setText("Nivel: Vencedor");

            Boolean show = settings.getBoolean("dayZero", true);
            Log.i("dayZero: ", show+"");
            if(show){
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("dayZero", false);
                editor.apply();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setMessage("Ahora tratá de mantenerte firme, y si sentís la necesidad de fumar oprimí el botón \"Ayuda\"")
                        .setTitle("¡Terminaste tu plan, ya sos un Vencedor!")
                        .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }else{
            textView4.setText(plan.getTotal_cigarettes()+"");
            days.setText(""+(size - plan.getNumber_day() + 1));

        }

        dspd.close();





        update_interface(used_cigarettes, user, plan, size);
        if(!isMyServiceRunning()){
            call_service();
        }
        add_cigarette.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String msj ="";
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                if(notCompleted  == 0){

                    msj = "No es tan grave como parece. Muchos vencedores cometen deslices durante su proceso de dejar de fumar. Mantén el buen ánimo y hacé lo posible porque no vuelva a ocurrir. ";

                }else{

                    settings = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
                    int ret = settings.getInt("count", 0);


                    if((ret) == plan.getTotal_cigarettes()){
                        msj = "Según tu plan, no te quedan más cigarrillos para hoy. Si sentís ganas de fumar, tratá de hacer algo diferente y divertido hasta que estas pasen. No deberían durar mucho.";
                    }else{
                        if((ret) == plan.getTotal_cigarettes() - 1){
                            msj = "Según tu plan, solamente te queda un cigarrillo más para el día de hoy. No te pasés de tu límite.";
                        }else{
                            if((ret) > plan.getTotal_cigarettes() ){
                                msj = "Has superado tu límite de cigarrillos para hoy. Tratá de que no ocurra mañana. Si sentís que no lo vas a lograr, hacé una lista con todas tus razones para dejar de fumar y llevala siempre contigo.";
                            }else{
                                if((ret) == 3){
                                    msj = "Tranquilo. Todavía vas bien. Intentá analizar que situaciones son las que te producen ganas de fumar y pensá en nuevas estrategias para enfrentarlas.";
                                }else{
                                    if((ret) == 6){
                                        msj = "Recordá que tu objetivo es dejar de fumar. Diariamente tenés que ir reduciendo el número de cigarrillos que consumis. Tratá de ir sustituyendo el fumar con otro tipo de actividades como ejercicios de relajación.";
                                    }else{
                                        if((ret) > 6){
                                            msj = "Tranquilo, todavía te encontrás dentro de tu límite para hoy. Sin embargo, recordá que entre menos cigarrillos fumés, mucho mejor.";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                builder.setMessage(msj).setTitle("¿Fumaste un cigarrillo?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int ret = settings.getInt("count", 0);

                                if (notCompleted == 0) {
                                    textView4.setText(String.valueOf(ret + 1));
                                } else {
                                    cigarettes_smoked.setText(String.valueOf(ret + 1));
                                }
                                Calendar s = Calendar.getInstance();
                                s.setTimeInMillis(System.currentTimeMillis());


                                userds.open();
                                User userR = userds.getUser();
                                userR.setLast_cigarette(s.getTimeInMillis());
                                userR.setDays_without_smoking_count(0.0);
                                userds.updateUser(userR);
                                userds.close();
                                if(notCompleted == 0){
                                    days.setText(((int)Math.round(userR.getDays_without_smoking_count()))+"");
                                }
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putInt("count", ret + 1);
                                editor.commit();
                                if (notCompleted == 0 && size == 0) {
                                    if (user.getDays_with_smoking() >= 2) {
                                        userR.setDays_with_smoking(userR.getDays_with_smoking() + 1);
                                        String msj = "Has fumado durante tres o más días seguidos. Es probable que estés experimentando una recaída. Reflexioná sobre lo que ha salido mal y volvelo a intentar. No te rindás.";
                                        AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());

                                        builder.setMessage(msj).setIcon(R.drawable.pulmones)
                                                .setTitle("¿Fumaste un cigarrillo?");
                                        builder.setPositiveButton("Cambiar plan (Reiniciar)", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent intents = new Intent(rootView.getContext(), RegisterActivityTwo.class);
                                                startActivity(intents);
                                            }
                                        });
                                        builder.setNegativeButton("Seguir con este plan", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // User cancelled the dialog
                                            }
                                        });
                                        AlertDialog diadlog = builder.create();
                                        diadlog.show();
                                    }
                                }
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

    public void toggleList() {
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


    private boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) rootView.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.lecz.clubdelosvencedores.general.NotificationService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void call_service(){
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

