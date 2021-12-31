package com.example.myclg.Models;

public class Meets {
    String meetdate;
    String meettime;
    String meetlink;
    String meettitle;
    String meetperson1;
    String meetperson2;

    public String getEveryone() {
        return everyone;
    }

    public void setEveryone(String everyone) {
        this.everyone = everyone;
    }

    String everyone;


    String hostid;

    public Meets() {
    }

    public Meets(String meetdate, String meettime, String meettitle, String meetlink, String meetperson1, String meetperson2, String hostid,String everyone) {
        this.meetdate=meetdate;
        this.meettime=meettime;
        this.meettitle=meettitle;
        this.everyone=everyone;
        this.meetlink=meetlink;
        this.meetperson1=meetperson1;
        this.meetperson2=meetperson2;
        this.hostid=hostid;
    }
    public String getHostid() {
        return hostid;
    }

    public void setHostid(String hostid) {
        this.hostid = hostid;
    }

    public String getMeetdate() {
        return meetdate;
    }

    public void setMeetdate(String meetdate) {
        this.meetdate = meetdate;
    }

    public String getMeettime() {
        return meettime;
    }

    public void setMeettime(String meettime) {
        this.meettime = meettime;
    }

    public String getMeetlink() {
        return meetlink;
    }

    public void setMeetlink(String meetlink) {
        this.meetlink = meetlink;
    }

    public String getMeettitle() {
        return meettitle;
    }

    public void setMeettitle(String meettitle) {
        this.meettitle = meettitle;
    }

    public String getMeetperson1() {
        return meetperson1;
    }

    public void setMeetperson1(String meetperson1) {
        this.meetperson1 = meetperson1;
    }

    public String getMeetperson2() {
        return meetperson2;
    }

    public void setMeetperson2(String meetperson2) {
        this.meetperson2 = meetperson2;
    }


}
