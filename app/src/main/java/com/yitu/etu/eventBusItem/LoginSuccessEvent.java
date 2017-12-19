package com.yitu.etu.eventBusItem;

import com.yitu.etu.entity.UserInfo;

/**
 * @className:LoginSuccessEvent
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月19日 22:44
 */
public class LoginSuccessEvent {
    UserInfo mUserInfo;

    public LoginSuccessEvent(UserInfo userInfo) {
        mUserInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        mUserInfo = userInfo;
    }
}
