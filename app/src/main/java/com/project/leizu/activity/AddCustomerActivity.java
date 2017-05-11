package com.project.leizu.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
public class AddCustomerActivity extends Activity{

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
                        }
                        if(TextUtils.isEmpty(cname.getText().toString().trim())){
                            shakeAnimation(cname);
                        }
                        if (!isMobile(cphone.getText().toString())) {
                            shakeAnimation(cphone);
                            return;
                        }

                        if (!TextUtils.isEmpty(ccompany.getText().toString().trim()) &&
                                (!TextUtils.isEmpty(cname.getText().toString().trim()))) {

                            ContentValues values = new ContentValues();
                            values.put("Ccompany", ccompany.getText().toString().trim());
                            values.put("Cname", cname.getText().toString().trim());
                            values.put("Cphone", cphone.getText().toString().trim());
                            getContentResolver().insert(ObtainData.customerUri, values);
                            Toast.makeText(getBaseContext(), "插入成功", Toast.LENGTH_SHORT);

                            finish();
                        }

                    }
                });

        cphone.addTextChangedListener(new TextWatcher(){


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


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
