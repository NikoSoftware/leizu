package com.project.leizu.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Niko on 2016/7/14.
 */
public class LeiZuDatabaseHelper extends SQLiteOpenHelper {


    public static final String CREATE_ACCOUNT  = "create table accounts ("
            + "Aid text primary key, "
            + "Apassword text) ";


    //商品表
    public static final String CREATE_GOODS = "create table goods ("
            + "Gid text primary key, "
            + "Gname text, "
            + "Gform text, "
            + "Gweight text, "
            + "Glenght integer,"
            + "Gprice real)";
    //客户表
    public static final String CREATE_CUSTOMER   = "create table customer ("
            + "Cid integer primary key autoincrement, "
            + "Ccompany text, "
            + "Cname text, "
            + "Cphone text)";

    //借书记录表
    public static final String CREATE_RECORD   = "create table record ("
            + "Rid integer primary key autoincrement, "
            + "Gid text, "
            + "Gname text, "
            + "Cid text, "
            + "Cname text, "
            + "Rstate integer, "
            + "Rbtime integer,"
            + "Rstime integer)";


    /**
     * @param context
     * @param name 数据库名
     * @param factory  允许我们在查询数据的时候返回一个自定义的 Cursor，一般都是传入 null
     * @param version 表示当前数据库的版本号，
     */
    public LeiZuDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_ACCOUNT);
        sqLiteDatabase.execSQL(CREATE_GOODS);
        sqLiteDatabase.execSQL(CREATE_CUSTOMER);
        sqLiteDatabase.execSQL(CREATE_RECORD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        if(i!=i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS accounts");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS record");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS goods");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS customer");
            onCreate(sqLiteDatabase);
        }
    }
}
