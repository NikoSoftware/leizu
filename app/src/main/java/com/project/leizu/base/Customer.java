package com.project.leizu.base;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by Niko on 2016/7/15.
 */
public class Customer extends BmobObject{

    private int Cid;
    private String Ccompany;
    private String Cname;
    private String Cphone;
    private BmobUser user;

    public BmobUser getUser() {
        return user;
    }

    public void setUser(BmobUser user) {
        this.user = user;
    }

    public int getCid() {
        return Cid;
    }

    public void setCid(int cid) {
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
