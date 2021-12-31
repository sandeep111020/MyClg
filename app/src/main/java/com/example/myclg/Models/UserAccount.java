package com.example.myclg.Models;

public class UserAccount {
    String userempid,userpassword;
    public UserAccount(){

    }
    public UserAccount(String userempid, String userpassword){
        this.userempid=userempid;
        this.userpassword=userpassword;
    }


    public String getUserempid() {
        return userempid;
    }

    public void setUserempid(String userempid) {
        this.userempid = userempid;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }


}
