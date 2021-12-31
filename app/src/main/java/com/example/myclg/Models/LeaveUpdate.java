package com.example.myclg.Models;

public class LeaveUpdate {

    String id,update;

    public LeaveUpdate() {
    }

    public LeaveUpdate(String id, String update) {


        this.id=id;
        this.update=update;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

}
