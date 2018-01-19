package com.yitu.etu;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.SystemClock;

import com.yitu.etu.entity.ErrorBean;
import com.yitu.etu.tools.MyActivityManager;
import com.yitu.etu.util.DateUtil;
import com.yitu.etu.util.JsonUtil;
import com.yitu.etu.util.PrefrersUtil;
import com.yitu.etu.util.TextUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @className:CrashException
 * @description:崩溃信息代理防止重启
 * @author: JIAMING.LI
 * @date:2017年12月11日 14:43
 */
public class CrashException extends Exception implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        boolean is= PrefrersUtil.getInstance().getValue("isCrash1",false);
        if (ex != null&&!is) {
            ex.printStackTrace();
            postEx(getCrash(ex));
            PrefrersUtil.getInstance().saveValue("isCrash1",true);
        }
        SystemClock.sleep(2000);
        // 执行这句会杀死进程(优点：会把整个应用的静态变量全部释放)
        MyActivityManager.getInstance().finishAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
        System.gc();
    }

    private String getCrash(Throwable ex) {
        try {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            printWriter.close();
            String crashStr = writer.toString();
            writer.close();
            return crashStr;
        } catch (Exception e) {
            return "收集异常信息时程序出错啦！！";
        }
    }
    private void postEx(final String error) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String content = String.format("\n----崩溃开始分隔符----%n发生时间：%s%n手机信息：%s%n环境：%s%n%s\n----崩溃结束分隔符----\n",
                            DateUtil.getTime(String.valueOf(System.currentTimeMillis() / 1000), "yyyy年MM月dd日 HH:mm:ss")
                            , getMobileInfo(), BuildConfig.BUILD_TYPE, error);
                    ErrorBean crashInfo = new ErrorBean();
                    crashInfo.msgtype = "text";
                    crashInfo.text = new ErrorBean.TextBean();
                    crashInfo.text.content = content;
                    //申明给服务端传递一个json串
                    //创建一个OkHttpClient对象
                    OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
                    try {
                        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
                        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
                    } catch (Exception e) {
                    }
                    OkHttpClient okHttpClient = builder.build();
                    //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
                    RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), JsonUtil.getInstance().getJsonString(crashInfo));
                    //创建一个请求对象
                    Request request = new Request.Builder()
                            .url("https://oapi.dingtalk.com/robot/send?access_token=8302f5a77bbeb01f2578069b9a4dbd8580de770d743b49de7525dbfb561772f8")
                            .post(requestBody)
                            .build();
                    //发送请求获取响应
                    try {
                        Response response = okHttpClient.newCall(request).execute();
                        //判断请求是否成功
                        if (response.isSuccessful()) {
                            //打印服务端返回结果
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取手机信息
     *
     * @return
     */
    public static String getMobileInfo() {
        String handSetInfo =
                "手机型号：" + android.os.Build.MODEL +
                        "，DNS环境：" + BuildConfig.BUILD_TYPE +
                        "，SDK版本：" + android.os.Build.VERSION.SDK +
                        "，系统版本：" + android.os.Build.VERSION.RELEASE +
                        "，软件版本：" + getAppVersionName(EtuApplication.getInstance());
        return handSetInfo;
    }

    //获取当前版本号
    private static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
