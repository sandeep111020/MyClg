package com.example.myclg.Models;

public class Leave {




    String leavedate,empid,empname,comment,leavetype;


    public Leave() {
    }

    public Leave(String leavedate, String empid, String empname, String comment, String leavetype) {
       this.leavedate=leavedate;
       this.empid=empid;
       this.empname=empname;
       this.comment=comment;
       this.leavetype=leavetype;
    }
    public String getLeavedate() {
        return leavedate;
    }

    public void setLeavedate(String leavedate) {
        this.leavedate = leavedate;
    }


    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLeavetype() {
        return leavetype;
    }

    public void setLeavetype(String leavetype) {
        this.leavetype = leavetype;
    }
}
