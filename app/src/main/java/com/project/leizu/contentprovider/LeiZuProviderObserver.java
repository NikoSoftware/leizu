package com.project.leizu.contentprovider;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;

/**
 * Created by Niko on 2016/7/14.
 */
public class LeiZuProviderObserver extends ContentObserver {

    private Handler handler;
    private Cursor cursor;
    private Context context;

    public LeiZuProviderObserver(Context context,Handler handler,Cursor cursor) {
        super(handler);
        this.context = context;
        this.handler = handler;
        this.cursor = cursor;
    }
    @Override
    public void onChange(boolean selfChange) {
        // TODO Auto-generated method stub
        super.onChange(selfChange);

        cursor.requery();
      //  handler.sendEmptyMessage(2013);
    }
}
