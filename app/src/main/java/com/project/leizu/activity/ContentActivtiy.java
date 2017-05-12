package com.project.leizu.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.project.leizu.R;
import com.project.leizu.base.Customer;
import com.project.leizu.base.Goods;
import com.project.leizu.base.ObtainData;
import com.project.leizu.base.Record;
import com.project.leizu.fragment.UserFrag;
import com.project.leizu.fragment.CustomerFrag;
import com.project.leizu.fragment.SearchFrag;
import com.project.leizu.util.Tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Niko on 2016/7/15.
 */
public class ContentActivtiy extends BaseActivity implements View.OnClickListener {

    public ImageView button1;
    public ImageView button2;
    public ImageView button3;

    public TextView text1;
    public TextView text2;
    public TextView text3;

    public SearchFrag searchFrag;
    public Fragment customerFrag;
    public Fragment userFrag;
    private LinearLayout linear1;
    private LinearLayout linear2;
    private LinearLayout linear3;
    public static Customer customerFlag;
    public static boolean isBorrow=true;
    private ArrayList<Goods> listGoods = new ArrayList<>();

    //借出
    public static int BORROW=0;
    //归还
    public static int REVERT=1;

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private ArrayList<Record> listRecord=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            if(Integer.parseInt(android.os.Build.VERSION.SDK)>=21 ) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorTitle));
            }
        setContentView(R.layout.content_activtiy);

        InitView();
    }


    private String getVersionName() throws Exception
    {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
        String version = packInfo.versionName;
        return version;
    }


    private void InitView() {
        // TODO Auto-generated method stub
        button1 =(ImageView) findViewById(R.id.img1);
        button2 =(ImageView) findViewById(R.id.img2);
        button3 =(ImageView) findViewById(R.id.img3);

        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);

        linear1 =(LinearLayout) findViewById(R.id.linear1);
        linear2 =(LinearLayout) findViewById(R.id.linear2);
        linear3 =(LinearLayout) findViewById(R.id.linear3);

        linear1.setOnClickListener(this);
        linear2.setOnClickListener(this);
        linear3.setOnClickListener(this);

        setSelet(1);
    }



    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        resetIcon();

        switch(v.getId()){

            case R.id.linear1:
                setSelet(1);
                break;

            case R.id.linear2:
                setSelet(2);
                break;
            case R.id.linear3:
                setSelet(3);
                break;

        }

    }

    private void setSelet(int i) {
        // TODO Auto-generated method stub
        FragmentManager fm = getFragmentManager();
        FragmentTransaction  ft =  fm.beginTransaction();
        hideTransaction(ft);

        switch (i) {
            case 1:

                if(searchFrag==null){

                    searchFrag = new SearchFrag();
                    ft.add(R.id.frameLayout, searchFrag);

                }else{
                    ft.show(searchFrag);
                }

                button1.setImageResource(R.drawable.icon_sousuo_selected1);
                text1.setTextColor(Color.RED);
                break;

            case 2:

                if(customerFrag==null){

                    customerFrag = new CustomerFrag();
                    ft.add(R.id.frameLayout, customerFrag);
                }else{
                    ft.show(customerFrag);

                }

                button2.setImageResource(R.drawable.icon_tianjiakehu_selected);
                text2.setTextColor(Color.RED);
                break;

            case 3:

                if(userFrag==null){
                    userFrag = new UserFrag();
                    ft.add(R.id.frameLayout, userFrag);
                }else{
                    ft.show(userFrag);

                }

                button3.setImageResource(R.drawable.icon_tianjiakehu_selected);
                text3.setTextColor(Color.RED);
                break;
        }

        ft.commit();

    }

    private void hideTransaction(FragmentTransaction ft) {
        // TODO Auto-generated method stub

        if(searchFrag!=null){

            ft.hide(searchFrag);
        }

        if(customerFrag!=null){

            ft.hide(customerFrag);
        }
        if(userFrag!=null){

            ft.hide(userFrag);
        }

    }

    private void resetIcon() {
        // TODO Auto-generated method stub

        button1.setImageResource(R.drawable.icon_sousuo_noselected1);
        text1.setTextColor(Color.GRAY);
        button2.setImageResource(R.drawable.icon_tianjiakehu_noselected);
        text2.setTextColor(Color.GRAY);
        button3.setImageResource(R.drawable.icon_tianjiakehu_noselected);
        text3.setTextColor(Color.GRAY);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            if (isBorrow) {
                requestBorrowData(result);
            } else {
                requestRevoteData(result);
            }


     /*       listGoods=Tool.cursorGoodsToList(ObtainData.getGoodsByGid(ContentActivtiy.this, result));

            Log.d("listGoods:",listGoods.size()+"");
            if(listGoods!=null&&listGoods.size()!=0&&isBorrow){
                HashMap<String,String> hashMap = new HashMap<String,String>();
                hashMap.put("Cid", customerFlag.getCid());
                hashMap.put("Cname", customerFlag.getCname());
                hashMap.put("Gid", listGoods.get(0).getGid());
                hashMap.put("Gname", listGoods.get(0).getGname());
                hashMap.put("Rbtime", System.currentTimeMillis() + "");
                hashMap.put("Rstate", BORROW + "");

                ObtainData.insertRecord(ContentActivtiy.this, hashMap);
                Toast.makeText(ContentActivtiy.this,"借阅成功",Toast.LENGTH_SHORT).show();
            }else if(listGoods!=null&&listGoods.size()!=0&&!isBorrow) {

                listRecord=Tool.cursorRecordToList(ObtainData.getRecordRecordByCustomerAndGid(
                        ContentActivtiy.this,handler,customerFlag.getCid(),result));
Log.d("listRecord.size():",listRecord.size()+"");
                if(listRecord!=null&&listRecord.size()!=0) {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("Rid", listRecord.get(0).getRid() + "");
                    hashMap.put("Cid", customerFlag.getCid());
                    hashMap.put("Cname", customerFlag.getCname());
                    hashMap.put("Gid", listGoods.get(0).getGid());
                    hashMap.put("Gname", listGoods.get(0).getGname());
                    hashMap.put("Rbtime", listRecord.get(0).getRbtime() + "");
                    hashMap.put("Rstate", REVERT + "");
                    hashMap.put("Rstime", System.currentTimeMillis() + "");
                    ObtainData.updataRecord(ContentActivtiy.this, hashMap);
                    Toast.makeText(ContentActivtiy.this, "归还成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ContentActivtiy.this, "并未借过此商品", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(ContentActivtiy.this, "未找到此商品信息", Toast.LENGTH_SHORT).show();
            }

            }*/

         //   customerFlag = null;

        }
    }

    /**
     * 借阅数据
     */
    public void requestBorrowData(String bookid){

        BmobQuery<Goods> query = new BmobQuery<Goods>();
        query.addWhereEqualTo("Gid", bookid.trim());
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> goods, BmobException e) {
                closeCustomDialog();
                if (e == null&&goods.size()>0&&isBorrow) {
                   Record record =  new Record();
                    record.setCid(customerFlag);
                    record.setCname(customerFlag.getCname());
                    record.setGid(goods.get(0));
                    record.setGname(goods.get(0).getGname());
                    record.setRstate(BORROW);
                    record.setRbtime(System.currentTimeMillis());

                    record.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                showSnackbar("借阅成功");
                            }else{
                                Log.i("bmob", "借阅失败：" + e.getMessage());
                                showSnackbar("借阅失败");
                            }
                        }
                    });
                } else {
                    Log.i("bmob", "失败：" + e.getMessage());
                }
            }
        });

    }


    /**
     * 归还数据
     */
    public void requestRevoteData(String bookid) {

        BmobQuery<Record> query = new BmobQuery<Record>();

        BmobQuery<Goods> goods = new BmobQuery<Goods>();
        goods.addWhereEqualTo("Gid", bookid.trim());
        query.addWhereMatchesQuery("Gid", "Goods", goods);
        query.addWhereEqualTo("Rstate",ContentActivtiy.BORROW);
        query.addWhereEqualTo("Cid",customerFlag);
        query.findObjects(new FindListener<Record>() {
            @Override
            public void done(List<Record> records, BmobException e) {
                closeCustomDialog();
                if (e == null && records.size() > 0 && !isBorrow) {
                    Record record =records.get(0);
                    record.setRstate(REVERT);
                    record.setRstime(System.currentTimeMillis());
                    record.update(record.getObjectId(), new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                showSnackbar("归还成功");
                            }else{
                                Log.i("bmob","归还失败："+e.getMessage()+","+e.getErrorCode());
                                showSnackbar("归还失败");
                            }
                        }
                    });
                }else if(e==null &&records.size()==0 ){
                    showSnackbar("并未借阅此书籍");
                } else {
                    Log.i("bmob", "失败：" + e.getMessage());
                }
            }
        });
    }

}
