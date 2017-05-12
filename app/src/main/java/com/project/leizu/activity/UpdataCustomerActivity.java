package com.project.leizu.activity;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.project.leizu.R;
import com.project.leizu.base.Customer;
import com.project.leizu.base.ObtainData;
import com.project.leizu.base.Record;
import com.project.leizu.util.StringUtil;
import com.project.leizu.util.TitleBuilder;

import java.util.List;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Niko on 2016/7/15.
 */
public class UpdataCustomerActivity extends BaseActivity {

    private EditText ccompany;
    private EditText cname;
    private EditText cphone;
    private String Cid;
    private String Ccompany;
    private String Cname;
    private String Cphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Integer.parseInt(android.os.Build.VERSION.SDK)>=21 ) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorTitle));
        }
        setContentView(R.layout.add_customer_activity);
        Intent intent =getIntent();
        Cid = intent.getStringExtra("Cid");
        Ccompany = intent.getStringExtra("Ccompany");
        Cname = intent.getStringExtra("Cname");
        Cphone = intent.getStringExtra("Cphone");

        init();
        initTitle();

    }

    private void init() {

        ccompany = (EditText) findViewById(R.id.ccompany);
        cname= (EditText) findViewById(R.id.cname);
        cphone= (EditText) findViewById(R.id.cphone);

        ccompany.setText(Ccompany);
        cname.setText(Cname);
        cphone.setText(Cphone);

    }

    private void initTitle() {

        new TitleBuilder(this).setLeftIco(R.drawable.icon_fanhui)
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitleText("修改客户信息")
                .setRightIco(R.drawable.icon_baocun)
                .setRightIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String company = ccompany.getText().toString().trim();
                        String name = cname.getText().toString().trim();
                        String phone = cphone.getText().toString().trim();

                        //数据为空震动
                        if (TextUtils.isEmpty(ccompany.getText().toString().trim())) {
                            shakeAnimation(ccompany);
                            return;
                        }
                        if (TextUtils.isEmpty(cname.getText().toString().trim())) {
                            shakeAnimation(cname);
                            return;
                        }
                        if (!StringUtil.isPhoneNumber(cphone.getText().toString())) {

                            shakeAnimation(cphone);
                            return;
                        }

                        //未改动震荡
                       if(company.equals(Ccompany)&&name.equals(Cname)&&phone.equals(Cphone)){
                           shakeAnimation(ccompany);
                           shakeAnimation(cname);
                           shakeAnimation(cphone);
                       }


                           if (!TextUtils.isEmpty(company) &&
                                (!TextUtils.isEmpty(name))
                                && (!company.equals(Ccompany) || !name.equals(Cname) || !phone.equals(Cphone))
                                ) {
                               updataData();
/*
                            ContentValues values = new ContentValues();
                            values.put("Cid", Cid);
                            values.put("Ccompany", company);
                            values.put("Cname", name);
                            values.put("Cphone", phone);
                            getContentResolver().update(Uri.parse(ObtainData.UriPath + "/customer/" + Cid), values, null, null);
                            Toast.makeText(getBaseContext(), "插入成功", Toast.LENGTH_SHORT);

                            finish();
*/

                        }

                    }
                });

    }



    public void updataData(){

        BmobQuery<Customer> query = new BmobQuery<Customer>();
        query.addWhereEqualTo("user", BmobUser.getCurrentUser());

        query.findObjects(new FindListener<Customer>() {

            @Override
            public void done(List<Customer> customers, BmobException e) {

                if(e==null&&customers.size()>0) {

                    Customer customer = customers.get(0);
                    //注意：不能调用customer.setObjectId("")方法
                    customer.setCcompany(ccompany.getText().toString().trim());
                    customer.setCname(cname.getText().toString().trim());
                    customer.setCphone(cphone.getText().toString().trim());
                    customer.setUser(BmobUser.getCurrentUser());

                    customer.update(customer.getObjectId(),new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                showSnackbar("修改成功");
                                finish();
                            } else {
                                Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });
                }else{
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }

                }

            });

    }


    public void shakeAnimation(View view){
        Animation shake = AnimationUtils.loadAnimation(UpdataCustomerActivity.this, R.anim.edit_text_shake);
        view.startAnimation(shake);
    }

    public static boolean isMobile(String mobile) {
        String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

}
