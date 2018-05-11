package com.codejies.lyb.bean;

/**
 * Created by Jies on 2018/5/11.
 */

public class LoginResponse {

    /**
     * unionId : null
     * gender : 1
     * phone : 13800138000
     * signature : null
     * openId : null
     * nickname : 杰思 245d
     * userId : 1
     * token : e10adc3949ba59abbe56e057f20f883e
     */
    private String unionId;
    private int gender;
    private String phone;
    private String signature;
    private String openId;
    private String nickname;
    private int userId;
    private String token;

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUnionId() {
        return unionId;
    }

    public int getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getSignature() {
        return signature;
    }

    public String getOpenId() {
        return openId;
    }

    public String getNickname() {
        return nickname;
    }

    public int getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }
}
