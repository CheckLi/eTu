package com.yitu.etu.tools;


import android.app.Activity;
import android.content.Context;

import java.util.Iterator;
import java.util.Stack;

/**
 * @author lim
 * @ClassName: ActivityStackManager
 * @Package com.huizhuang.zxsq
 * @Description: 应用activity堆栈管理器
 * @mail lgmshare@gmail.com
 * @date 2014年6月24日 下午4:39:59
 */
public class MyActivityManager {
    
    private static MyActivityManager instance;
    private static Stack<Activity> activityStack;
    
    private MyActivityManager() {
    }
    
    /**
     * 单例
     */
    public static MyActivityManager getInstance() {
        if (instance == null) {
            instance = new MyActivityManager();
        }
        return instance;
    }
    
    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity getCurrentActivity() {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        if (activityStack.size() > 0) {
            return activityStack.lastElement();
        }
        return null;
    }
    
    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.push(activity);
    }
    
    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }
    
    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        removeActivity(activity);
    }
    
    /**
     * 结束Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }
    
    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        Iterator<Activity> activityStackIterator = activityStack.iterator();
        while (activityStackIterator.hasNext()) {
            Activity activity = activityStackIterator.next();
            if (activity != null && activity.getClass().equals(cls)) {
                //这里要使用Iterator的remove方法移除当前对象，
                // 如果使用List的remove方法，则同样会出现ConcurrentModificationException
                activityStackIterator.remove();
                activity.finish();
            }
        }
    }
    
    /**
     * 结束指定类名的Activity
     *
     * @param noClose 指定不关闭的那个页面
     */
    public void finishActivity(Class<?> cls, int noClose) {
        Iterator<Activity> activityStackIterator = activityStack.iterator();
        int num = 0;
        while (activityStackIterator.hasNext()) {
            Activity activity = activityStackIterator.next();
            if (activity != null && activity.getClass().equals(cls)) {
                //这里要使用Iterator的remove方法移除当前对象，
                // 如果使用List的remove方法，则同样会出现ConcurrentModificationException
                if (num > 0) {
                    activityStackIterator.remove();
                    activity.finish();
                }
                num++;
            }
        }
    }
    
    /**
     * 判断activity是否存在
     *
     * @param activity
     * @return
     */
    public boolean isActivity(Activity activity) {
        if (activityStack != null && !activityStack.isEmpty()) {
            return activityStack.contains(activity);
        }
        return false;
    }
    
    /**
     * 判断activity是否存在
     *
     * @param activity
     * @return
     */
    public boolean hasActivity(Class<?> activity) {
       return  hasActivity(activity,0);
    }

    /**
     * 判断activity是否存在
     *判断数量
     * @param activity
     * @return
     */
    public boolean hasActivity(Class<?> activity,int maxCount) {
        int count=0;
        if (activityStack != null && !activityStack.isEmpty()) {
            for (Activity act : activityStack) {
                if (act.getClass().getSimpleName().equals(activity.getSimpleName())&&++count>maxCount) {
                  return true;
                }
            }
        }
        return false;
    }

    /**
     * 结束指定类名的Activity
     */
    public boolean isActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity != null && activity.getClass().equals(cls)) {
                //这里要使用Iterator的remove方法移除当前对象，
                // 如果使用List的remove方法，则同样会出现ConcurrentModificationException
                return true;
            }
        }
        return false;
    }
    
    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        
        while (activityStack != null && !activityStack.isEmpty()) {
            finishActivity(activityStack.pop());
        }
        if (activityStack != null) {
            activityStack.clear();
        }
    }
    
    /**
     * 退出应用程序
     *
     * @param context 上下文
     */
    public void appExit(Context context) {
        finishAllActivity();
//        System.exit(0);
    }
    
    /**
     * 显示的Activity是否在栈顶
     * 创建日期：2016/9/8 17:17
     * 修改记录：
     *
     * @param activity
     * @return 是否在栈顶
     */
    public boolean isTopActivity(Activity activity) {
        if (activity == null) {
            return false;
        }
        Activity currActivity1 = getCurrentActivity();
        return currActivity1 != null && activity == currActivity1;
    }
}