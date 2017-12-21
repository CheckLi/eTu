package com.yitu.etu.tools;

/**
 * @className:Urls
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月16日 16:41
 */
public class Urls {
    public static final String address = "http://api.91eto.com";
    public static final String login = address + "/user/login";//⽤用户户登
    public static final String updateUserInfo = address + "/user/save";//修改用户信息
    public static final String sendCode = address + "/user/registersms";//发送验证码
    public static final String sendForgetCode = address + "/user/forgetpwdsms";//发送验证码
    public static final String regist = address + "/user/register";//⽤户注册
    public static final String foregetPassword = address + "/user/forgetpwd";//忘记密码提交新密
    public static final String chatToken = address + "/user/getIMToken";//获取融云toke
    public static final String loginPasswordChange = address + "/user/savePassword";//登陆密码修改
    public static final String putRenz = address + "/user/uprenz";//上传认证信息
    public static final String getRenz = address + "/user/renz";//获取认证信息
    public static final String setPayPassword = address + "/user/setPaypwd";//设置支付密码
    public static final String paySms = address + "/user/getPaypwdSms";//获取支付密码验证码
    public static final String reSetPayPassword = address + "/user/forgetPaypwd";//重新设置支付密码
}
