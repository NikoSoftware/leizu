package com.project.leizu.base;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.project.leizu.contentprovider.LeiZuContentprovider;
import com.project.leizu.contentprovider.LeiZuDatabaseHelper;
import com.project.leizu.contentprovider.LeiZuProviderObserver;

import java.util.HashMap;

/**
 * Created by Niko on 2016/7/15.
 */
public class ObtainData {
    private Context context;
    private LeiZuDatabaseHelper goodsHelper;
    public static Uri goodsUri = Uri.parse("content://"+LeiZuContentprovider.AUTHORITY+"/goods");
    public static String UriPath = "content://"+LeiZuContentprovider.AUTHORITY;
    public static Uri customerUri = Uri.parse("content://"+LeiZuContentprovider.AUTHORITY+"/customer");
    public static Uri recordUri = Uri.parse("content://"+LeiZuContentprovider.AUTHORITY+"/record");

    public static Uri accountsUri = Uri.parse("content://"+LeiZuContentprovider.AUTHORITY+"/accounts");
    public static Uri accountsAidUri = Uri.parse("content://"+LeiZuContentprovider.AUTHORITY+"/accounts/Aid");


    public void getDatabase(HashMap<String,String> hashMap){

       goodsHelper  =  new LeiZuDatabaseHelper(context,"goods.db",null,1);
        SQLiteDatabase db = goodsHelper.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
         values.put("Gid", hashMap.get("Gid"));
        values.put("Gname", hashMap.get("Gname"));
        values.put("Gform", hashMap.get("Gform"));
        values.put("Gweight", hashMap.get("Gweight"));
        values.put("Glenght", Integer.parseInt(hashMap.get("Glenght")));
        values.put("Gprice", Float.parseFloat(hashMap.get("Gprice")));

        db.insert("Book", null, values);



    }


    /**
     * 插入用户名
     * @param context
     * @param hashMap
     */
    public static void insertAccounts(Context context, HashMap<String,String> hashMap){

        ContentValues values = new ContentValues();
        values.put("Aid", hashMap.get("Aid"));
        values.put("Apassword", hashMap.get("Apassword"));

        context.getContentResolver().insert(accountsUri
                , values);

    }


    public static void insertGoods(Context context, HashMap<String,String> hashMap){

        ContentValues values = new ContentValues();
        values.put("Gid", hashMap.get("Gid"));
        values.put("Gname", hashMap.get("Gname"));
        values.put("Gform", hashMap.get("Gform"));
        values.put("Gweight", hashMap.get("Gweight"));
        values.put("Glenght", Integer.parseInt(hashMap.get("Glenght")));
        values.put("Gprice", Float.parseFloat(hashMap.get("Gprice")));

        context.getContentResolver().insert(goodsUri
                , values);

    }

    public static void insertCustomer(Context context, HashMap<String,String> hashMap){

        ContentValues values = new ContentValues();
        values.put("Cname", hashMap.get("Cname"));
        values.put("Ccompany", hashMap.get("Ccompany"));
        values.put("Cphone", hashMap.get("Cphone"));
        context.getContentResolver().insert(customerUri
                , values);

    }

    public static void insertRecord(Context context, HashMap<String,String> hashMap){

        ContentValues values = new ContentValues();
        values.put("Cname", hashMap.get("Cname"));
        values.put("Cid", hashMap.get("Cid"));
        values.put("Gid", hashMap.get("Gid"));
        values.put("Gname", hashMap.get("Gname"));
        values.put("Rbtime", hashMap.get("Rbtime"));
        values.put("Rstate", hashMap.get("Rstate"));
        context.getContentResolver().insert(recordUri
                , values);

    }
    public static void updataRecord(Context context, HashMap<String,String> hashMap){

        ContentValues values = new ContentValues();
        values.put("Rid",Integer.parseInt(hashMap.get("Rid")));
        values.put("Cname", hashMap.get("Cname"));
        values.put("Cid", Integer.parseInt(hashMap.get("Cid")));
        values.put("Gid", hashMap.get("Gid"));
        values.put("Gname", hashMap.get("Gname"));
        values.put("Rbtime", Long.parseLong(hashMap.get("Rbtime")));
        values.put("Rstate", Integer.parseInt(hashMap.get("Rstate")));
        values.put("Rstime", Long.parseLong(hashMap.get("Rstime")));
        context.getContentResolver().update(Uri.parse(recordUri + "/" + values.getAsInteger("Rid"))
                , values, null, null);

    }

    public static Cursor getGoods(Context context, Handler handler) {
        Cursor cursor=null;
            cursor=context.getContentResolver().query(goodsUri
            ,null,null,null, "Gid ASC");
        LeiZuProviderObserver observer = new LeiZuProviderObserver(context, handler, cursor);
        context.getContentResolver().registerContentObserver(goodsUri, false, observer);

        return cursor;
    }

    public static Cursor getCustomer(Context context, Handler handler) {
        Cursor cursor=null;
        cursor=context.getContentResolver().query(customerUri
                ,null,null,null, "Cid ASC");
        LeiZuProviderObserver observer = new LeiZuProviderObserver(context, handler, cursor);
        context.getContentResolver().registerContentObserver(customerUri, false, observer);
        return cursor;
    }

    public static Cursor getRecord(Context context, Handler handler) {
        Cursor cursor=null;
        cursor=context.getContentResolver().query(recordUri
                ,null,null,null, "Rid ASC");
        LeiZuProviderObserver observer = new LeiZuProviderObserver(context, handler, cursor);
        context.getContentResolver().registerContentObserver(recordUri, false, observer);
        return cursor;
    }

    public static Cursor getRecordRecordByCustomer(Context context, Handler handler,String Cid) {
        Cursor cursor=null;
        cursor=context.getContentResolver().query(Uri.parse(UriPath+"/record/Cid/"+Cid)
                ,null,null,null, "Rid ASC");
        LeiZuProviderObserver observer = new LeiZuProviderObserver(context, handler, cursor);
        context.getContentResolver().registerContentObserver(recordUri, false, observer);
        return cursor;
    }
    public static Cursor getRecordRecordByCidAndRstate(Context context, Handler handler,String Cid,String Rstate) {
        Cursor cursor=null;
        cursor=context.getContentResolver().query(recordUri
                ,null,"Cid = ? and Rstate = ?",new String[]{Cid ,Rstate }, "Rid ASC");
        LeiZuProviderObserver observer = new LeiZuProviderObserver(context, handler, cursor);
        context.getContentResolver().registerContentObserver(recordUri, false, observer);
        context.getContentResolver().unregisterContentObserver(observer);
        return cursor;
    }


    public static Cursor getRecordRecordByCustomerAndGid(Context context, Handler handler,String Cid,String Gid) {
        Cursor cursor=null;
        cursor=context.getContentResolver().query(recordUri
                ,null,"Cid=? and Gid=? and Rstate=?",new String[]{Cid, Gid,0+""},"Rid ASC");
        LeiZuProviderObserver observer = new LeiZuProviderObserver(context, handler, cursor);
        context.getContentResolver().registerContentObserver(recordUri, false, observer);
        context.getContentResolver().unregisterContentObserver(observer);
        return cursor;
    }

       public static Cursor getGoodsByFuzzy(Context context,String str) {
        Cursor cursor=null;
        cursor=context.getContentResolver().query(goodsUri
                ,null,"Gname like '%" + str + "%'",null, "Gid ASC");
        return cursor;
    }

    public static Cursor getCustomerByFuzzy(Context context,String str) {
        Cursor cursor=null;
        cursor=context.getContentResolver().query(customerUri
                ,null,"Ccompany like '%" + str + "%'",null, "Cid ASC");
        return cursor;
    }

    public static Cursor getGoodsByGid(Context context,String str) {
        Cursor cursor=null;
        cursor=context.getContentResolver().query(goodsUri
                ,null,"Gid = ?"  ,new String[]{ str }, "Gid ASC");
        return cursor;
    }

    public static Cursor getAccountsByAid(Context context,String str) {
        Cursor cursor=null;
        cursor=context.getContentResolver().query(accountsUri
                ,null,"Aid = ?"  ,new String[]{ str }, null);
        return cursor;
    }

}
