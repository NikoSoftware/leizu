package com.project.leizu.base;

import cn.bmob.v3.BmobObject;

/**
 * Created by Niko on 2016/7/15.
 */
public class Goods extends BmobObject {

    private String Gid;
    private String Gname;
    private String Gform;
    private String Gweight;
    private Integer Glenght;
    private float Gprice;

    public String getGid() {
        return Gid;
    }

    public void setGid(String gid) {
        Gid = gid;
    }

    public String getGname() {
        return Gname;
    }

    public void setGname(String gname) {
        Gname = gname;
    }

    public String getGform() {
        return Gform;
    }

    public void setGform(String gform) {
        Gform = gform;
    }

    public String getGweight() {
        return Gweight;
    }

    public void setGweight(String gweight) {
        Gweight = gweight;
    }

    public Integer getGlenght() {
        return Glenght;
    }

    public void setGlenght(Integer glenght) {
        Glenght = glenght;
    }

    public float getGprice() {
        return Gprice;
    }

    public void setGprice(float gprice) {
        Gprice = gprice;
    }
}
