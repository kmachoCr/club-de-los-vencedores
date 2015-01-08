package com.lecz.clubdelosvencedores.general;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.support.v4.app.NotificationCompat;
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
import com.lecz.clubdelosvencedores.utilities.AdviceNotificationService;
import com.lecz.clubdelosvencedores.utilities.AdviceNotificationServiceTwo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

        long when = System.currentTimeMillis();


        settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();

        String dateVariable = DateFormat.format("yyyy.MM.dd", settings.getLong("date", System.currentTimeMillis())).toString();
        if(user != null){
            dateCurrentPlan = dateVariable;
            long time= System.currentTimeMillis();
            dateNow = DateFormat.format("yyyy.MM.dd", time).toString();

            if(!dateCurrentPlan.equals(dateNow)){

                int used_cigarettes = settings.getInt("count", 1);
                contentTitle = "No Equals" ;
                if(plan != null){
                    int total_cigarettes = plan.getTotal_cigarettes();
                    if(total_cigarettes >= used_cigarettes){
                        plan.setApproved(true);
                        user.setCigarettes_no_smoked(user.getCigarettes_no_smoked() + (user.getCigarettes_per_day() - used_cigarettes));
                        user.setMoney_saved(user.getCigarettes_no_smoked() * 200);
                        if(used_cigarettes == 0){
                            user.setDays_without_smoking(user.getDays_without_smoking() + 1);
                            user.setDays_without_smoking_count(user.getDays_without_smoking_count() + 1);
                            user.setDays_with_smoking(0);
                        }else{
                            user.setDays_without_smoking_count(0.0);
                            user.setDays_with_smoking(user.getDays_with_smoking() + 1);
                        }
                    }else{
                        plan.setApproved(false);

                    }


                    dspd.open();
                    PlanDetail new_plan = dspd.getPlanDetailByDay(plan.getNumber_day() + 1);

                    plan.setCurrent(false);
                    plan.setCompleted(true);
                    plan.setUsed_cigarettes(used_cigarettes);
                    dspd.updatePlanDetail(plan);

                    new_plan.setCurrent(true);
                    dspd.updatePlanDetail(new_plan);
                    dspd.close();
                }else{
                    user.setCigarettes_no_smoked(user.getCigarettes_no_smoked() + (user.getCigarettes_per_day() - used_cigarettes));
                    user.setMoney_saved(user.getCigarettes_no_smoked() * 200);
                    if(used_cigarettes == 0){
                        user.setDays_without_smoking(user.getDays_without_smoking() + 1);
                        user.setDays_without_smoking_count(user.getDays_without_smoking_count() + 1);
                    }else{
                        user.setDays_without_smoking_count(0.0);
                        user.setDays_with_smoking(user.getDays_with_smoking() + 1);
                    }

                }
                userds.open();
                userds.updateUser(user);
                userds.close();

                editor.putInt("count", 0);
                editor.commit();


                if(listAchievements.size() > 0){

                    for(int i = 0; i < listAchievements.size(); i++){
                        if(listAchievements.get(i).getType().equals("money")){
                            int money = user.getMoney_saved();
                            int achievement = Integer.parseInt(String.valueOf(listAchievements.get(i).getAmount()));

                            int result = money - achievement;

                            if(result > 0){
                                notificationManager.createNotification(this, R.drawable.pulmones, listAchievements.get(i).getTitle(), listAchievements.get(i).getTitle(),  listAchievements.get(i).getDescription(), when, MyActivity.class);
                                listAchievements.get(i).setCompleted(true);
                                ads.open();
                                ads.updateAchievement(listAchievements.get(i));
                                ads.close();

                                acds.open();
                                acds.createActivity(new Activity(listAchievements.get(i).getImage(), System.currentTimeMillis(),listAchievements.get(i).getDescription(), "logro", listAchievements.get(i).getTitle()));
                                acds.close();
                            }
                            /*else{
                                   notificationManager.createNotification(this, R.drawable.checkmark, "Ya casi", listAchievements.get(i).getTitle(),  listAchievements.get(i).isCompleted()+"", when, MyActivity.class);
                            }*/
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
                int i2 = r.nextInt((list.size() - 1) - 0 + 1) + 0;

                Calendar morning = Calendar.getInstance();
                morning.setTimeInMillis(System.currentTimeMillis());
                morning.set(Calendar.HOUR_OF_DAY, 8);
                morning.set(Calendar.MINUTE, 0

                );

                Calendar evening = Calendar.getInstance();
                evening.setTimeInMillis(System.currentTimeMillis());
                evening.set(Calendar.HOUR_OF_DAY, 18);
                evening.set(Calendar.MINUTE, 0);

                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                AlarmManager alarmManager2 = (AlarmManager)getSystemService(ALARM_SERVICE);

                Intent myIntent = new Intent(this, AdviceNotificationService.class);
                myIntent.putExtra("ticket", list.get(i1).getType().toUpperCase());
                myIntent.putExtra("title", list.get(i1).getType());
                myIntent.putExtra("body", list.get(i1).getBody());
                myIntent.putExtra("type", "consejo");

                Intent myIntent2 = new Intent(this, AdviceNotificationServiceTwo.class);
                myIntent2.putExtra("ticket", list.get(i2).getType().toUpperCase());
                myIntent2.putExtra("title", list.get(i2).getType());
                myIntent2.putExtra("body", list.get(i2).getBody());
                myIntent2.putExtra("type", "consejo");

                PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);
                PendingIntent pendingIntent2 = PendingIntent.getService(this, 0, myIntent2, 0);

                alarmManager.set(AlarmManager.RTC_WAKEUP, morning.getTimeInMillis(), pendingIntent);
                alarmManager2.set(AlarmManager.RTC_WAKEUP, evening.getTimeInMillis(), pendingIntent2);

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
                        notificationManager.createNotification(this, R.drawable.pulmones, listAchievements.get(i).getTitle(), listAchievements.get(i).getTitle(),  listAchievements.get(i).getDescription(), when, MyActivity.class);
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

            editor.putLong("date", System.currentTimeMillis());
            editor.commit();
        }

        //notificationManager.createNotification(this, R.drawable.icn_ahorro, "Ya casi", "flag", "flag", when, MyActivity.class);

        Notification notification = new Notification(R.drawable.pulmones, "Flag", when);

        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        Intent notificationIntent = new Intent(this, (Class<?>) MyActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(this, "Title", "Body", contentIntent);

        startForeground(1337, notification);
        stopForeground(true);
        stopSelf();

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  null;
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
