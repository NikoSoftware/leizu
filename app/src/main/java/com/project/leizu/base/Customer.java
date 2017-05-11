package com.project.leizu.base;

/**
 * Created by Niko on 2016/7/15.
 */
public class Customer {

    private String Cid;
    private String Ccompany;
    private String Cname;
    private String Cphone;

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getCphone() {
        return Cphone;
    }

    public void setCphone(String cphone) {
        Cphone = cphone;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        Cname = cname;
    }

    public String getCcompany() {
        return Ccompany;
    }

    public void setCcompany(String ccompany) {
        Ccompany = ccompany;
    }
}
