package com.codejies.lyb.bean;

/**
 * Created by Jies on 2018/6/12.
 */

public class RegisterRequest {
    String phone;
    String password;

    public RegisterRequest(String phone,String password){
        this.phone=phone;
        this.password=password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
