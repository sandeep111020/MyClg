package com.example.myclg.Models;

public class Attendance {
    String id;
    String name;

    public Attendance(){

    }
    public Attendance(String id,String name,String status){
        this.id=id;
        this.name=name;
        this.status=status;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;
}
