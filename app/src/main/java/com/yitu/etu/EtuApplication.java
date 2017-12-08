package com.yitu.etu;

import android.app.Application;

/**
 * @className:EtuApplocation
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月08日 16:25
 */
public class EtuApplication extends Application {
    private static EtuApplication mInstance;

    public static EtuApplication getInstance() {
        return mInstance = mInstance == null ? new EtuApplication() : mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
