package com.boredream.bdcodehelper.entity;


import com.boredream.bdcodehelper.base.BoreBaseEntity;

public class BaseUser extends BoreBaseEntity {

    protected String sessionToken;

    protected String username;

    protected String nickname;

    /**
     * 验证手机号
     */
    protected String mobilePhoneNumber;

    /**
     * 密码
     */
    protected String password;

    /**
     * 手机号验证码,发送短信验证时请求使用
     */
    protected String smsCode;

    /**
     * 头像图片地址
     */
    protected String avatar;

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
