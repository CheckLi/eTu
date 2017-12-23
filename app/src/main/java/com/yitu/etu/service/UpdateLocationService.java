package com.yitu.etu.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/19.
 */

public class UpdateLocationService extends Service{
    Timer timer=new Timer();

    TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {

        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        timer.schedule(timerTask,0,5000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timerTask!=null){
            timerTask.cancel();
        }
        if(timer!=null){
            timer.cancel();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MBinder();
    }
    public class MBinder extends Binder{
       public UpdateLocationService getService(){
           return UpdateLocationService.this;
       }
    }
}
