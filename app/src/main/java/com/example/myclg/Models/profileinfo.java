package com.example.myclg.Models;


public class profileinfo {


    public String userName;
    public String userNumber,userEmail,userDob,userDept,userDesignation,userEmpid;





    public String imageURL;

    public profileinfo() {
    }

    public profileinfo(String userName, String userNumber, String userEmail, String userEmpid, String userDesignation, String userDept, String userDob, String url) {
        this.userName=userName;
        this.userNumber=userNumber;
        this.userEmail=userEmail;
        this.userDept=userDept;
        this.userDesignation=userDesignation;
        this.userEmpid=userEmpid;
        this.userDob=userDob;
        this.imageURL=url;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserDob() {
        return userDob;
    }

    public void setUserDob(String userDob) {
        this.userDob = userDob;
    }

    public String getUserDept() {
        return userDept;
    }

    public void setUserDept(String userDept) {
        this.userDept = userDept;
    }

    public String getUserDesignation() {
        return userDesignation;
    }

    public void setUserDesignation(String userDesignation) {
        this.userDesignation = userDesignation;
    }

    public String getUserEmpid() {
        return userEmpid;
    }

    public void setUserEmpid(String userEmpid) {
        this.userEmpid = userEmpid;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }



}