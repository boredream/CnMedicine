package com.boredream.cnmedicine.entity;


import com.boredream.bdcodehelper.entity.BaseUser;

public class User extends BaseUser {
    /**
     * 性别 0保密, 1男, 2女
     */
    private int gender;

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
