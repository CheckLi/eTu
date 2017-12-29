package com.yitu.etu.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.amap.api.maps.model.Poi;
import com.yitu.etu.EtuApplication;
import com.yitu.etu.entity.HttpStateEntity;
import com.yitu.etu.eventBusItem.MLatLng;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/19.
 */

public class UpdateLocationService extends Service {
    Timer timer = new Timer();
    MLatLng latLng;
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("lng", latLng.lng + "");
            hashMap.put("lat", latLng.lat + "");
            Http.post(Urls.USER_LOCATION, hashMap, new GsonCallback<HttpStateEntity>() {
                @Override
                public void onError(Call call, Exception e, int id) {
                }

                @Override
                public void onResponse(HttpStateEntity response, int id) {

                }
            });
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Poi poi = EtuApplication.getInstance().myLocationPoi;
        if (poi != null) {
            latLng = new MLatLng(poi.getCoordinate().latitude, poi.getCoordinate().longitude);
        }
        startTimer();
        EventBus.getDefault().register(this);
    }

    boolean isStarTimer = false;

    public void startTimer() {
        if (latLng != null && !isStarTimer && EtuApplication.getInstance().isLogin()) {
            isStarTimer = true;
            timer.schedule(timerTask, 0, 5*60*1000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancel();
        EventBus.getDefault().unregister(this);
    }

    public void cancel() {
        if (timerTask != null) {
            timerTask.cancel();
        }
        if (timer != null) {
            timer.cancel();
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void getLatlng(MLatLng latLng) {
        this.latLng = latLng;
        startTimer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
