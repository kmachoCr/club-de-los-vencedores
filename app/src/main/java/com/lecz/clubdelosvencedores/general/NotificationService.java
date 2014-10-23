package com.lecz.clubdelosvencedores.general;


import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.text.format.Time;

import com.lecz.clubdelosvencedores.DatabaseManagers.PlanDetailsDataSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.UserDataSource;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.PlanDetail;
import com.lecz.clubdelosvencedores.objects.User;

import java.util.ArrayList;

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

        dspd.open();
        PlanDetail plan = dspd.getCurrentPlanDetail();
        dspd.close();

        userds.open();
        User user = userds.getUser();
        userds.close();

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

                    if(total_cigarettes > used_cigarettes){
                        int cigarettes = total_cigarettes - used_cigarettes;
                        user.setCigarettes_no_smoked(user.getCigarettes_no_smoked() + (user.getCigarettes_per_day() - cigarettes));
                        user.setMoney_saved(user.getCigarettes_no_smoked() * 200);
                        if(used_cigarettes == 0){
                            user.setDays_without_smoking(user.getDays_without_smoking() + 1);
                            user.setDays_without_smoking_count(user.getDays_without_smoking_count() + 1);
                        }else{
                            user.setDays_without_smoking_count(0);
                        }
                        userds.open();
                        userds.updateUser(user);
                        userds.close();
                    }

                    dspd.open();
                    PlanDetail new_plan = dspd.getPlanDetail(plan.getNumber_day() + 1);

                    plan.setCurrent(false);
                    dspd.updatePlanDetail(plan);

                    new_plan.setCurrent(true);
                    dspd.updatePlanDetail(new_plan);
                    dspd.close();

                }else{
                    contentTitle = user.getName() ;

                }
            }else{
                contentTitle = "No User" ;
            }

            int icon = R.drawable.ic_launcher;
            long when = System.currentTimeMillis();

            NotifictionManager notifictionManager = new NotifictionManager(this);
            notifictionManager.createNotification(this, icon, "Titulo", dateCurrentPlan, dateNow, when, MainActivity.class);

        }else{
            contentTitle = "Dia Cero" ;
        }


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
