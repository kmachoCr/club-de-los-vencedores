package com.lecz.clubdelosvencedores.general;


import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.lecz.clubdelosvencedores.DatabaseManagers.AchievementDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.ActivityDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.AdviceDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.MotivationsDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.PlanDetailsDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.UserDataSource;
import com.lecz.clubdelosvencedores.MyActivity;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.Achievement;
import com.lecz.clubdelosvencedores.objects.Activity;
import com.lecz.clubdelosvencedores.objects.Advice;
import com.lecz.clubdelosvencedores.objects.Motivations;
import com.lecz.clubdelosvencedores.objects.PlanDetail;
import com.lecz.clubdelosvencedores.objects.User;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Luis on 9/8/2014.
 */
public class NotificationService extends Service {
    private SharedPreferences settings;
    private final IBinder mBinder = new MyBinder();
    private ArrayList<String> list = new ArrayList<String>();
    private String contentTitle, dateCurrentPlan, dateNow;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PlanDetailsDataSource dspd = new PlanDetailsDataSource(this);
        UserDataSource userds = new UserDataSource(getApplication().getApplicationContext());
        NotificationMng notificationManager = new NotificationMng(this);
        AchievementDataSource ads = new AchievementDataSource(this);
        Activity activity = new Activity();
        ActivityDataSource acds = new ActivityDataSource(this);

        dspd.open();
        PlanDetail plan = dspd.getCurrentPlanDetail();
        dspd.close();

        userds.open();
        User user = userds.getUser();
        userds.close();

        ads.open();
        ArrayList<Achievement> listAchievements = ads.getAchievements();
        ads.close();

        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();

        if(plan != null){
            if(user != null){
                dateCurrentPlan = DateFormat.format("yyyy.MM.dd", plan.getDate()).toString();
                long time= System.currentTimeMillis();
                dateNow = DateFormat.format("yyyy.MM.dd", time).toString();

                if(!dateCurrentPlan.equals(dateNow)){
                    int total_cigarettes = plan.getTotal_cigarettes();
                    settings = PreferenceManager.getDefaultSharedPreferences(this);
                    int used_cigarettes = settings.getInt("count", 1);
                    contentTitle = "No Equals" ;

                    if(total_cigarettes >= used_cigarettes){
                        plan.setApproved(true);
                        user.setCigarettes_no_smoked(user.getCigarettes_no_smoked() + (user.getCigarettes_per_day() - used_cigarettes));
                        user.setMoney_saved(user.getCigarettes_no_smoked() * 200);
                        if(used_cigarettes == 0){
                            user.setDays_without_smoking(user.getDays_without_smoking() + 1);
                            user.setDays_without_smoking_count(user.getDays_without_smoking_count() + 1);
                        }else{
                            user.setDays_without_smoking_count(0.0);
                        }
                    }else{
                        plan.setApproved(false);
                        user.setCigarettes_no_smoked(user.getCigarettes_no_smoked() + (user.getCigarettes_per_day() - used_cigarettes));
                        user.setMoney_saved(user.getCigarettes_no_smoked() * 200);
                    }
                    userds.open();
                    userds.updateUser(user);
                    userds.close();

                    dspd.open();
                    PlanDetail new_plan = dspd.getPlanDetail(plan.getNumber_day() + 1);

                    plan.setCurrent(false);
                    plan.setCompleted(true);
                    plan.setUsed_cigarettes(used_cigarettes);
                    dspd.updatePlanDetail(plan);


                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("count", 0);
                    editor.commit();

                    new_plan.setCurrent(true);
                    dspd.updatePlanDetail(new_plan);
                    dspd.close();

                    if(listAchievements.size() > 0){

                        for(int i = 0; i < listAchievements.size(); i++){
                            if(listAchievements.get(i).getType().equals("money")){
                                int money = user.getMoney_saved();
                                int achievement = Integer.parseInt(String.valueOf(listAchievements.get(i).getAmount()));

                                int result = money - achievement;

                                if(result > 0){
                                    notificationManager.createNotification(this, R.drawable.checkmark, listAchievements.get(i).getTitle(), listAchievements.get(i).getTitle(),  listAchievements.get(i).getDescription(), when, MyActivity.class);
                                    listAchievements.get(i).setCompleted(true);
                                    ads.open();
                                    ads.updateAchievement(listAchievements.get(i));
                                    ads.close();

                                    acds.open();
                                    acds.createActivity(new Activity(listAchievements.get(i).getImage(), System.currentTimeMillis(),listAchievements.get(i).getDescription(), "logro", listAchievements.get(i).getTitle()));
                                    acds.close();
                                }
//                                else{
//                                    notificationManager.createNotification(this, R.drawable.checkmark, "Ya casi", listAchievements.get(i).getTitle(),  listAchievements.get(i).isCompleted()+"", when, MyActivity.class);
//                                }
                            }
                        }
                    }

                    MotivationsDataSource mds = new MotivationsDataSource(this);
                    mds.open();
                    Motivations m = mds.getMotivations();
                    mds.close();

                    AdviceDataSource adds = new AdviceDataSource(this);
                    adds.open();
                    ArrayList<Advice> list = adds.getAdvices(user.getGenre() ? 1 : 0, m.isMotiv_money(), m.isMotiv_aesthetic(), m.isMotiv_family(), m.isMotiv_health());
                    adds.close();


                    for(int i = 0; i < list.size(); i++){
                        Log.i("nombre", list.get(i).getType());
                    }
                    Random r = new Random();
                    int i1 = r.nextInt((list.size() - 1) - 0 + 1) + 0;

                    acds.open();
                    acds.createActivity(new Activity(R.drawable.checkmark, System.currentTimeMillis(), list.get(i1).getBody(), "consejo", list.get(i1).getType()));
                    acds.close();


                }else{
                    contentTitle = user.getName() ;
                }
            }else{
                contentTitle = "No User" ;
            }
            ads.open();
            listAchievements = ads.getAchievements();
            ads.close();

            if(listAchievements.size() > 0){

                for(int i = 0; i < listAchievements.size(); i++){
                    if(listAchievements.get(i).getType().equals("time")){
                        Date last_cigarette = new Date(user.getLast_cigarette());
                        long now = System.currentTimeMillis();

                        long different = (now - last_cigarette.getTime()) / 1000 / 60;

                        long achievement = listAchievements.get(i).getAmount();
                        long result = different - achievement;
                        DecimalFormat df = new DecimalFormat("#.##");
                        if(result > 0){
                            notificationManager.createNotification(this, R.drawable.checkmark, listAchievements.get(i).getTitle(), listAchievements.get(i).getTitle(),  listAchievements.get(i).getDescription(), when, MyActivity.class);
                            listAchievements.get(i).setCompleted(true);
                            ads.open();
                            ads.updateAchievement(listAchievements.get(i));
                            ads.close();

                            acds.open();
                            acds.createActivity(new Activity(listAchievements.get(i).getImage(), System.currentTimeMillis(),listAchievements.get(i).getDescription(), "logro", listAchievements.get(i).getTitle()));
                            acds.close();
                        }
                        /*else{
                            notificationManager.createNotification(this, R.drawable.checkmark, "Ya casi", listAchievements.get(i).getTitle(),  result + " minutos", when, MyActivity.class);
                        }*/

                    }
                }
            }
        }else{
            contentTitle = "Dia Cero" ;
        }
//        notificationManager.createNotification(this, R.drawable.icn_ahorro, "Ya casi", "Bandera",  "Bandera", when, MyActivity.class);

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class MyBinder extends Binder {
        NotificationService getService() {
            return NotificationService.this;
        }
    }

}
