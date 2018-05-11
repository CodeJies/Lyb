package com.codejies.lyb.bean;

/**
 * Created by Jies on 2018/5/11.
 */

public class LoginRequest {
    String password;
    String phone;
    public LoginRequest(String phone,String password){
        this.password=password;
        this.phone=phone;
    }

}
