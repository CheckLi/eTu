package com.yitu.etu.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yitu.etu.R;

/**
 * @className:activityUtil
 * @description:activity跳转工具
 * @author: JIAMING.LI
 * @date:2017年12月09日 14:00
 */
public  class  activityUtil {
    private static int openAnima = R.anim.open_animation;
    private static int closeAnima =R.anim.close_animation;

    public static void nextActivity(Context context, Class<?> toActivity) {
        nextActivity(context, toActivity, null, -1,false, openAnima, closeAnima);
    }



    public static void nextActivity(Fragment fragment, Class<?> toActivity) {
        nextActivity(fragment, toActivity, null, -1,false, openAnima, closeAnima);
    }
    public static void nextActivity(Context context, Class<?> toActivity,boolean isFinish) {
        nextActivity(context, toActivity, null, -1,isFinish, openAnima, closeAnima);
    }

    public static void nextActivity(Fragment fragment, Class<?> toActivity,boolean isFinish) {
        nextActivity(fragment, toActivity, null, -1,isFinish, openAnima, closeAnima);
    }

    public static void nextActivity(Context context, Class<?> toActivity,Bundle bundle,boolean isFinish) {
        nextActivity(context, toActivity, bundle, -1,isFinish, openAnima, closeAnima);
    }

    public static void nextActivity(Fragment fragment, Class<?> toActivity,Bundle bundle,boolean isFinish) {
        nextActivity(fragment, toActivity, bundle, -1,isFinish, openAnima, closeAnima);
    }

    public static void nextActivity(Context context, Class<?> toActivity,Bundle bundle,int requestCode,boolean isFinish) {
        nextActivity(context, toActivity, bundle, requestCode,isFinish, openAnima, closeAnima);
    }

    public static void nextActivity(Fragment fragment, Class<?> toActivity,Bundle bundle,int requestCode,boolean isFinish) {
        nextActivity(fragment, toActivity, bundle, requestCode,isFinish, openAnima, closeAnima);
    }


    /**
     * 进入下一个activity
     * @param context
     * @param toActivity
     * @param bundle
     * @param requestCode
     * @param openAnimation
     * @param closeAnimation
     */
    public static void nextActivity(Context context, Class<?> toActivity, Bundle bundle, int requestCode,boolean finish, int openAnimation, int closeAnimation) {
        Intent intent = new Intent(context, toActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        Activity activity=((Activity)context);


        if (requestCode == -1) {
            activity.startActivity(intent);
        } else if (requestCode != -1) {
            activity.startActivityForResult(intent, requestCode);
        }
        if (openAnimation != -1 && closeAnimation != -1) {
            activity.overridePendingTransition(openAnimation, closeAnimation);
        }
        if(finish){
            activity.finish();
        }
    }

    /**
     * 进入下一个activity
     * @param toActivity
     * @param bundle
     * @param requestCode
     * @param openAnimation
     * @param closeAnimation
     */
    public static void nextActivity(Fragment fragment, Class<?> toActivity, Bundle bundle, int requestCode,boolean finish, int openAnimation, int closeAnimation) {
        Intent intent = new Intent(fragment.getActivity(), toActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }

        if (requestCode == -1) {
            fragment.startActivity(intent);
        } else if (requestCode != -1) {
            fragment.startActivityForResult(intent, requestCode);
        }
        if (openAnimation != -1 && closeAnimation != -1) {
            fragment.getActivity().overridePendingTransition(openAnimation, closeAnimation);
        }
        if(finish){
            fragment.getActivity().finish();
        }
    }
}
