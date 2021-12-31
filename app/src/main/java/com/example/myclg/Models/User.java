package com.example.myclg.Models;

public class User {


    private String name;
    private String email;
    private String date;
    private String tasktype;
    private String taskdetails;
    private String status;
    private String remarks;
    private String empid;


    private String taskid;

    public User() {

    }
    public User(String empid,String name,String date,String email,String tasktype,String taskdetails,String status,String remarks,String taskid){
        this.name= name;
        this.taskid=taskid;
        this.email= email;
        this.tasktype=tasktype;
        this.date= date;
        this.empid=empid;
        this.taskdetails= taskdetails;

        this.status= status;
        this.remarks= remarks;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTasktype() {
        return tasktype;
    }

    public void setTasktype(String tasktype) {
        this.tasktype = tasktype;
    }

    public String getTaskdetails() {
        return taskdetails;
    }

    public void setTaskdetails(String taskdetails) {
        this.taskdetails = taskdetails;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

}
