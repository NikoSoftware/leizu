package com.project.leizu.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.project.leizu.R;
import com.project.leizu.base.ObtainData;
import com.project.leizu.util.TitleBuilder;

import java.util.regex.Pattern;

/**
 * Created by Niko on 2016/7/15.
 */
public class UpdataCustomerActivity extends Activity {

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
                        }
                        if (TextUtils.isEmpty(cname.getText().toString().trim())) {
                            shakeAnimation(cname);
                        }
                        if (!isMobile(cphone.getText().toString())) {

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

                            ContentValues values = new ContentValues();
                            values.put("Cid", Cid);
                            values.put("Ccompany", company);
                            values.put("Cname", name);
                            values.put("Cphone", phone);
                            getContentResolver().update(Uri.parse(ObtainData.UriPath + "/customer/" + Cid), values, null, null);
                            Toast.makeText(getBaseContext(), "插入成功", Toast.LENGTH_SHORT);

                            finish();
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
