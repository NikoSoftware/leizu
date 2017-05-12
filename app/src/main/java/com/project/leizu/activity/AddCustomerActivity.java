package com.project.leizu.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.project.leizu.R;
import com.project.leizu.base.Customer;
import com.project.leizu.base.ObtainData;
import com.project.leizu.util.StringUtil;
import com.project.leizu.util.TitleBuilder;

import java.util.regex.Pattern;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Niko on 2016/7/15.
 */
public class AddCustomerActivity extends BaseActivity{

    private EditText ccompany;
    private EditText cname;
    private EditText cphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Integer.parseInt(android.os.Build.VERSION.SDK)>=21 ) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorTitle));
        }
        setContentView(R.layout.add_customer_activity);
        init();
        initTitle();

    }

    private void init() {

        ccompany = (EditText) findViewById(R.id.ccompany);
        cname= (EditText) findViewById(R.id.cname);
        cphone= (EditText) findViewById(R.id.cphone);

    }

    private void initTitle() {

        TitleBuilder titleBuilder = new TitleBuilder(this).setLeftIco(R.drawable.icon_fanhui)
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitleText("添加客户信息")
                .setRightIco(R.drawable.icon_baocun)
                .setRightIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //数据为空震动
                        if(TextUtils.isEmpty(ccompany.getText().toString().trim())){
                            shakeAnimation(ccompany);
                            return;
                        }
                        if(TextUtils.isEmpty(cname.getText().toString().trim())){
                            shakeAnimation(cname);
                            return;
                        }
                        if (!StringUtil.isPhoneNumber(cphone.getText().toString())) {
                            shakeAnimation(cphone);
                            return;
                        }

                        if (!TextUtils.isEmpty(ccompany.getText().toString().trim()) &&
                                (!TextUtils.isEmpty(cname.getText().toString().trim()))) {

/*
                            ContentValues values = new ContentValues();
                            values.put("Ccompany", ccompany.getText().toString().trim());
                            values.put("Cname", cname.getText().toString().trim());
                            values.put("Cphone", cphone.getText().toString().trim());
                            getContentResolver().insert(ObtainData.customerUri, values);
                            Toast.makeText(getBaseContext(), "插入成功", Toast.LENGTH_SHORT);
*/

                            insertData();
                        }
                    }
                });

    }


    public void insertData(){

        Customer customer = new Customer();
        //注意：不能调用customer.setObjectId("")方法
        customer.setCcompany(ccompany.getText().toString().trim());
        customer.setCname(cname.getText().toString().trim());
        customer.setCphone(cphone.getText().toString().trim());
        customer.setUser(BmobUser.getCurrentUser());

        customer.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    showSnackbar("创建客户成功");
                    finish();
                }else{
                    showSnackbar("个人信息只能创建一次");
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });


    }



    public void shakeAnimation(View view){
        Animation shake = AnimationUtils.loadAnimation(AddCustomerActivity.this, R.anim.edit_text_shake);
        view.startAnimation(shake);
    }
    public static boolean isMobile(String mobile) {
         String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        return Pattern.matches(REGEX_MOBILE, mobile);
    }



}
