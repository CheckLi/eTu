package com.yitu.etu;

import com.yitu.etu.tools.MyActivityManager;

/**
 * @className:CrashException
 * @description:崩溃信息代理防止重启
 * @author: JIAMING.LI
 * @date:2017年12月11日 14:43
 */
public class CrashException extends Exception implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (ex != null) {
            ex.printStackTrace();
            // 执行这句会杀死进程(优点：会把整个应用的静态变量全部释放)
            MyActivityManager.getInstance().finishAllActivity();
            /*android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
            System.gc();*/
        }
    }
}
