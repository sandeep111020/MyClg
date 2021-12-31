package com.example.myclg.Models;


public class notifcations {


    String title;
    String message;

    String currenttime;


    public notifcations() {
    }


    public notifcations(String title, String message, String currenttime) {
        this.message = message;
        this.title = title;

        this.currenttime = currenttime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime(String currenttime) {
        this.currenttime = currenttime;
    }
}