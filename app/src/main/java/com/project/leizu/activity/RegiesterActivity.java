package com.project.leizu.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.leizu.R;
import com.project.leizu.base.ObtainData;
import com.project.leizu.util.TitleBuilder;

import java.util.HashMap;

/**
 * Created by niko on 2017/3/18.
 */

public class RegiesterActivity extends Activity {


    private Context mContext;
    private EditText mEtName;
    private EditText mEtPassword;
    private TextView mTvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Integer.parseInt(android.os.Build.VERSION.SDK)>=21 ) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorTitle));
        }
        this.mContext = this;
        setContentView(R.layout.register_activity);
        init();


    }

    /**
     * 初始化
     */
    private void init() {
        new TitleBuilder(this).setTitleText("注册");
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mTvRegister = (TextView)findViewById(R.id.btn_done);

        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isCheck()){
                    HashMap<String,String > hashMap = new HashMap<String, String>();

                    hashMap.put("Aid", mEtName.getText().toString().trim());
                    hashMap.put("Apassword",mEtPassword.getText().toString().trim());
                    ObtainData.insertAccounts(mContext,hashMap);
                    finish();
                }
            }
        });

    }


    public boolean isCheck(){

        if(TextUtils.isEmpty(mEtName.getText().toString())){
            Toast.makeText(mContext,"用户名为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(mEtPassword.getText().toString())){
            Toast.makeText(mContext,"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }







}
