package com.hashdroid.loginsignup;

public class DataModal {

    // string variables for our name and job
    private String email;
    private String password;

    public DataModal(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

