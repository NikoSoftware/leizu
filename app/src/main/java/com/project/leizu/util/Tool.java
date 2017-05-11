package com.project.leizu.util;

import android.database.Cursor;
import android.util.Log;

import com.project.leizu.base.Customer;
import com.project.leizu.base.Goods;
import com.project.leizu.base.Record;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Niko on 2016/7/15.
 */
public class Tool {

    public static  ArrayList<Goods> cursorGoodsToList(Cursor cursor){
        ArrayList<Goods> list =new ArrayList<Goods>();
        if(cursor==null){
            return list;
        }
        while (cursor.moveToNext()){
            Log.d("TAG===>",cursor.getString(cursor.getColumnIndex("Gname")));
            Goods goods = new Goods();
            goods.setGid(cursor.getString(cursor.getColumnIndex("Gid")));
            goods.setGname(cursor.getString(cursor.getColumnIndex("Gname")));
            goods.setGform(cursor.getString(cursor.getColumnIndex("Gform")));
            goods.setGweight(cursor.getString(cursor.getColumnIndex("Gweight")));
            goods.setGlenght(cursor.getInt(cursor.getColumnIndex("Glenght")));
            goods.setGprice(cursor.getFloat(cursor.getColumnIndex("Gprice")));
            list.add(goods);
        }
        return list;
    }

    public static  ArrayList<Customer> cursorCustomerToList(Cursor cursor) {
        ArrayList<Customer> list = new ArrayList<Customer>();
        if (cursor == null) {
            return list;
        }

        while (cursor.moveToNext()) {

            Log.d("TAG===>", cursor.getString(cursor.getColumnIndex("Cname")));
            Customer customer = new Customer();
            customer.setCid(cursor.getString(cursor.getColumnIndex("Cid")));
            customer.setCcompany(cursor.getString(cursor.getColumnIndex("Ccompany")));
            customer.setCname(cursor.getString(cursor.getColumnIndex("Cname")));
            customer.setCphone(cursor.getString(cursor.getColumnIndex("Cphone")));
            list.add(customer);
        }
        return list;
    }

    public static  ArrayList<Record> cursorRecordToList(Cursor cursor) {
        ArrayList<Record> list = new ArrayList<Record>();
        if (cursor == null) {
            return list;
        }
        while (cursor.moveToNext()) {

            Log.d("TAG===>", cursor.getString(cursor.getColumnIndex("Gname")));
            Record record = new Record();
            record.setCid(cursor.getString(cursor.getColumnIndex("Cid")));
            record.setGid(cursor.getString(cursor.getColumnIndex("Gid")));
            record.setGname(cursor.getString(cursor.getColumnIndex("Gname")));
            record.setCname(cursor.getString(cursor.getColumnIndex("Cname")));
            record.setRbtime(cursor.getLong(cursor.getColumnIndex("Rbtime")));
            record.setRstate(cursor.getInt(cursor.getColumnIndex("Rstate")));
            record.setRstime(cursor.getLong(cursor.getColumnIndex("Rstime")));
            record.setRid(cursor.getInt(cursor.getColumnIndex("Rid")));


            list.add(record);
        }
        return list;
    }

    public static String getStringDate(Long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


}
