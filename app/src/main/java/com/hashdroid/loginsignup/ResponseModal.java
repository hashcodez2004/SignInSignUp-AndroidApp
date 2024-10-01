package com.hashdroid.loginsignup;

public class ResponseModal {
    private String token;

    public ResponseModal(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }
}
