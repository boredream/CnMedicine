package com.boredream.bdcodehelper.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.boredream.bdcodehelper.constants.CommonConstants;
import com.boredream.bdcodehelper.entity.BaseUser;
import com.google.gson.Gson;

/**
 * 用户信息保存工具
 */
public class UserInfoKeeper {

    private final SharedPreferences sp;
    private final Context context;
    private UserInfoKeeper(Context context){
        this.context = context;
        sp = context.getSharedPreferences(CommonConstants.SP_NAME, Context.MODE_PRIVATE);
    }
    public synchronized static UserInfoKeeper getInstance(Context context) {
        return new UserInfoKeeper(context);
    }
    
    private final String SP_KEY_CURRENT_BaseUser = "current_BaseUser";
    private final String SP_KEY_BaseUser_ID = "BaseUser_id";
    private final String SP_KEY_TOKEN = "token";

    private BaseUser currentBaseUser;

    /**
     * 获取当前登录用户,先从缓存中获取,获取不到时从sp中获取
     */
    public BaseUser getCurrentUser() {
        String BaseUserJson = sp.getString(SP_KEY_CURRENT_BaseUser, null);
        if (currentBaseUser == null && !TextUtils.isEmpty(BaseUserJson)) {
            currentBaseUser = new Gson().fromJson(BaseUserJson, BaseUser.class);
        }
        return currentBaseUser;
    }

    /**
     * 保存设置当前登录用户,缓存和sp中都进行保存
     */
    public void setCurrentUser(BaseUser BaseUser) {
        if (BaseUser != null) {
            String BaseUserJson = new Gson().toJson(BaseUser);
            sp.edit().putString(SP_KEY_CURRENT_BaseUser, BaseUserJson).apply();
        }
        currentBaseUser = BaseUser;
    }

    /**
     * 清空当前登录用户,同时清空缓存和sp中信息
     */
    public void clearCurrentUser() {
        currentBaseUser = null;
        sp.edit().remove(SP_KEY_CURRENT_BaseUser).apply();
    }

    /**
     * 保存当前用户的登录信息,用于自动登录
     *
     * @param BaseUserid 用户id
     * @param token  用户口令
     */
    public void saveLoginData(String BaseUserid, String token) {
        // 正常逻辑应该是直接用token去获取当前用户信息,不需要id,但是接口没有提供获取当前登录用户信息接口
        if (TextUtils.isEmpty(BaseUserid) || TextUtils.isEmpty(token)) {
            return;
        }

        // 保存在sp中,不像是账号密码敏感信息,无需加密
        sp.edit().putString(SP_KEY_BaseUser_ID, BaseUserid)
                .putString(SP_KEY_TOKEN, token)
                .apply();
    }

    /**
     * 获取当前用户的登录信息,用于自动登录
     *
     * @return [0]-用户BaseUserid [1]-用户口令token, 未保存或只保存一者时都返回null
     */
    public String[] getLoginData() {
        String BaseUserid = sp.getString(SP_KEY_BaseUser_ID, null);
        String token = sp.getString(SP_KEY_TOKEN, null);
        if (TextUtils.isEmpty(BaseUserid) || TextUtils.isEmpty(token)) {
            return null;
        }

        return new String[]{BaseUserid, token};
    }

    /**
     * 清空登录信息
     */
    public void clearLoginData() {
        sp.edit().remove(SP_KEY_BaseUser_ID)
                .remove(SP_KEY_TOKEN)
                .apply();
    }

    public String getToken() {
        // 统一Header配置时用的token,没有的话要用空字符串,不能为null
        String token = "";
        if (currentBaseUser != null && currentBaseUser.getSessionToken() != null) {
            token = currentBaseUser.getSessionToken();
        }
        return token;
    }

    /**
     * 登出,同时清空用户信息和登录信息
     */
    public void logout() {
        clearCurrentUser();
        clearLoginData();
    }

    /**
     * 检测登录状态
     *
     * @return true-已登录 false-未登录,会自动跳转至登录页
     */
    public boolean checkLogin(Class<Activity> loginActivityClass) {
        if (currentBaseUser == null) {
            Intent intent = new Intent(context, loginActivityClass);
            intent.putExtra("checkLogin", true);
            context.startActivity(intent);
            return false;
        }
        return true;
    }

}
