package com.project.leizu.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Niko on 2016/7/14.
 */
public class LeiZuContentprovider extends ContentProvider {

    private LeiZuDatabaseHelper dbHelper;

    public static final int GOODS = 0;
    public static final int GOODS_ITEM = 1;
    public static final int GOODS_ITEM_GNAME = 2;
    public static final int CUSTOMER = 3;
    public static final int CUSTOMER_ITEM = 4;
    public static final int CUSTOMER_ITEM_CCOMPANY = 5;
    public static final int RECORD = 6;
    public static final int RECORD_ITEM = 7;
    public static final int RECORD_ITEM_GID = 8;
    public static final int RECORD_ITEM_CID = 9;
    public static final int ACCOUNTS = 10;
    public static final int ACCOUNTS_ITEM_AID = 11;

    public static final String AUTHORITY="com.project.leizu.provider";

    private static UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "goods", GOODS);
        uriMatcher.addURI(AUTHORITY, "goods/*", GOODS_ITEM);
        uriMatcher.addURI(AUTHORITY, "goods/Gname/*", GOODS_ITEM_GNAME);
        uriMatcher.addURI(AUTHORITY, "customer", CUSTOMER);
        uriMatcher.addURI(AUTHORITY, "customer/#", CUSTOMER_ITEM);
        uriMatcher.addURI(AUTHORITY, "customer/Ccompany/*", CUSTOMER_ITEM_CCOMPANY);
        uriMatcher.addURI(AUTHORITY, "record", RECORD);
        uriMatcher.addURI(AUTHORITY, "record/#", RECORD_ITEM);
        uriMatcher.addURI(AUTHORITY, "record/Gid/*", RECORD_ITEM_GID);
        uriMatcher.addURI(AUTHORITY, "record/Cid/*", RECORD_ITEM_CID);

        uriMatcher.addURI(AUTHORITY, "accounts", ACCOUNTS);
        uriMatcher.addURI(AUTHORITY, "accounts/Aid/*", ACCOUNTS_ITEM_AID);

    }


    @Override
    public boolean onCreate() {

        dbHelper=new LeiZuDatabaseHelper(getContext(),"LeizuDatabase.db",null,6);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        switch (uriMatcher.match(uri)) {
            case GOODS:
                cursor = db.query("goods", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case GOODS_ITEM: // 查询table1表中的单条数据
                String GId = uri.getPathSegments().get(1);
                cursor = db.query("goods", projection, "Gid = ?", new String[] { GId }, null, null, sortOrder);
                break;
            case GOODS_ITEM_GNAME:
                String CIDname = uri.getPathSegments().get(1);
                String CID = uri.getPathSegments().get(2);
                cursor = db.query("goods", projection, CIDname+ "= ?", new String[] { CID }, null, null, sortOrder);
                break;

            case CUSTOMER:
            // 查询table2表中的所有数据
                cursor = db.query("customer", projection, selection, selectionArgs, null, null, sortOrder);

             break;
            case CUSTOMER_ITEM: // 查询table2表中的单条数据
                String Cid = uri.getPathSegments().get(1);
                cursor = db.query("customer", projection, "Cid = ?", new String[] { Cid }, null, null, sortOrder);
             break;
            case CUSTOMER_ITEM_CCOMPANY:
                String Ccompany = uri.getPathSegments().get(2);
                cursor = db.query("customer", projection, "Ccompany = ?", new String[] { Ccompany }, null, null, sortOrder);
                break;

            case RECORD:
                // 查询table2表中的所有数据
                cursor = db.query("record", projection, selection, selectionArgs, null, null, sortOrder);

                break;
            case RECORD_ITEM: // 查询table2表中的单条数据
                String Rid = uri.getPathSegments().get(1);
                cursor = db.query("record", projection, "Rid = ?", new String[] { Rid }, null, null, sortOrder);
                break;
            case RECORD_ITEM_GID:
            case RECORD_ITEM_CID: // 查询table2表中的单条数据
                String Rname = uri.getPathSegments().get(1);
                String str = uri.getPathSegments().get(2);
                cursor = db.query("record", projection, Rname+" = ?", new String[] { str }, null, null, sortOrder);
                break;

            case ACCOUNTS:
            case ACCOUNTS_ITEM_AID: // 查询table2表中的单条数据
                cursor = db.query("accounts", projection, "Aid = ?", selectionArgs, null, null, sortOrder);
                break;

            default:
                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;

        switch (uriMatcher.match(uri)) {
            case GOODS:
            case GOODS_ITEM:
                long newBookId = db.insert("goods", null, contentValues);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/goods/" + newBookId);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);
                break;
            case CUSTOMER:
            case CUSTOMER_ITEM:
                long newCategoryId = db.insert("customer", null, contentValues);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/customer/" + newCategoryId);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);
                break;
            case RECORD:
            case RECORD_ITEM:
                long newRecordId = db.insert("record", null, contentValues);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/record/" + newRecordId);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);
                break;
            case ACCOUNTS:
            case ACCOUNTS_ITEM_AID:
                long newAccountId = db.insert("accounts", null, contentValues);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/accounts/" + newAccountId);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);

                break;

            default:
                break;
        }

        return uriReturn;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = 0;
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case GOODS:
                deletedRows = db.delete("goods", selection, selectionArgs);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/goods/" + deletedRows);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);
                break;
            case GOODS_ITEM:
                String bookId = uri.getPathSegments().get(1);
                deletedRows = db.delete("goods", "Gid = ?", new String[] { bookId });
                uriReturn = Uri.parse("content://" + AUTHORITY + "/goods/" + deletedRows);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);
                break;
            case CUSTOMER:
                deletedRows = db.delete("customer", selection, selectionArgs);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/customer/" + deletedRows);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);
                break;
            case CUSTOMER_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                deletedRows = db.delete("customer", "cid = ?", new String[] { categoryId });
                uriReturn = Uri.parse("content://" + AUTHORITY + "/customer/" + deletedRows);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);
                break;
            case CUSTOMER_ITEM_CCOMPANY:
                String Cname = uri.getPathSegments().get(1);
                String Ccompany = uri.getPathSegments().get(1);
                deletedRows = db.delete("customer", Cname+" = ?", new String[] { Ccompany });
                uriReturn = Uri.parse("content://" + AUTHORITY + "/customer/" + deletedRows);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);
                break;
            case RECORD:
                deletedRows = db.delete("record", selection, selectionArgs);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/record/" + deletedRows);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);
                break;
            case RECORD_ITEM:
                String RecordId = uri.getPathSegments().get(1);
                deletedRows = db.delete("record", "Rid = ?", new String[] { RecordId });
                uriReturn = Uri.parse("content://" + AUTHORITY + "/record/" + deletedRows);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);
                break;
            case RECORD_ITEM_GID:
            case RECORD_ITEM_CID:
                String Recordname = uri.getPathSegments().get(1);
                String Recorddata = uri.getPathSegments().get(2);
                deletedRows = db.delete("record", Recordname+" = ?", new String[] { Recorddata });
                uriReturn = Uri.parse("content://" + AUTHORITY + "/record/" + deletedRows);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);
                break;

            default:
                break;
        }

        return deletedRows;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = 0;
        Uri uriReturn=null;
        switch (uriMatcher.match(uri)) {
            case GOODS:
                updatedRows = db.update("goods", contentValues, selection, selectionArgs);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/goods/" + updatedRows);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);
                break;
            case GOODS_ITEM:
                String bookId = uri.getPathSegments().get(1);
                updatedRows = db.update("goods", contentValues, "Gid = ?", new String[]{bookId});
                uriReturn = Uri.parse("content://" + AUTHORITY + "/goods/" + updatedRows);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);
                break;
            case CUSTOMER:
                updatedRows = db.update("customer", contentValues, selection, selectionArgs);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/customer/" + updatedRows);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);
                break;
            case CUSTOMER_ITEM:
                String CId = uri.getPathSegments().get(1);
                updatedRows = db.update("customer", contentValues, "Cid = ?", new String[]{CId});
                uriReturn = Uri.parse("content://" + AUTHORITY + "/customer/" + updatedRows);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);
                break;
            case RECORD:
                updatedRows = db.update("record", contentValues, selection, selectionArgs);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/record/" + updatedRows);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);
                break;
            case RECORD_ITEM:
                String RId = uri.getPathSegments().get(1);
                updatedRows = db.update("record", contentValues, "Rid = ?", new String[]{RId});
                uriReturn = Uri.parse("content://" + AUTHORITY + "/record/" + updatedRows);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);
                break;

            case ACCOUNTS_ITEM_AID:
                String Aid = uri.getPathSegments().get(1);
                updatedRows = db.update("accounts", contentValues, "Aid = ?", new String[]{Aid});
                uriReturn = Uri.parse("content://" + AUTHORITY + "/accounts/" + updatedRows);
                this.getContext().getContentResolver().notifyChange(uriReturn,null);
                break;
        }

        return updatedRows;
    }
}
