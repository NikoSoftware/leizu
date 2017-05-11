package com.project.leizu.base;

/**
 * Created by Niko on 2016/7/15.
 */
public class Record {

    private int Rid;
    private String Gid;
    private String Cid;
    private int Rstate;
    private Long Rbtime;
    private Long Rstime;
    private String Gname;
    private String Cname;

    public Long getRbtime() {
        return Rbtime;
    }

    public void setRbtime(Long rbtime) {
        Rbtime = rbtime;
    }

    public Long getRstime() {
        return Rstime;
    }

    public void setRstime(Long rstime) {
        Rstime = rstime;
    }

    public int getRid() {
        return Rid;
    }

    public void setRid(int rid) {
        Rid = rid;
    }

    public String getGid() {
        return Gid;
    }

    public void setGid(String gid) {
        Gid = gid;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public int getRstate() {
        return Rstate;
    }

    public void setRstate(int rstate) {
        Rstate = rstate;
    }

    public String getGname() {
        return Gname;
    }

    public void setGname(String gname) {
        Gname = gname;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        Cname = cname;
    }
}
