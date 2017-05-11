package com.project.leizu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.leizu.R;
import com.project.leizu.base.ObtainData;
import com.project.leizu.util.TitleBuilder;

/**
 * Created by niko on 2017/3/18.
 */

public class LoginActivity extends Activity implements View.OnClickListener{
    private Context mContext;
    private EditText mEtPhone;
    private EditText mEtPassword;
    private TextView mBtnLogin;
    private TextView mBtnShortcutLogin;
    private TextView mBtnFindBackPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Integer.parseInt(android.os.Build.VERSION.SDK)>=21 ) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorTitle));
        }
        this.mContext = this;
        setContentView(R.layout.login_activity);
        init();
    }

    private void init() {
        new TitleBuilder(this).setTitleText("登录");
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mBtnLogin = (TextView) findViewById(R.id.btn_login);
        mBtnShortcutLogin = (TextView)findViewById(R.id.tv_shortcut_login);
        mBtnFindBackPassword = (TextView)findViewById(R.id.tv_find_back_password);
        mBtnLogin.setOnClickListener(this);
        mBtnShortcutLogin.setOnClickListener(this);
        mBtnFindBackPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){


            /**
             * 登录
             */
            case R.id.btn_login:
                if(TextUtils.isEmpty(mEtPhone.getText().toString().trim())){

                    Toast.makeText(mContext,"用户名不能为空",Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(mEtPassword.getText().toString().trim())){

                    Toast.makeText(mContext,"密码不能为空",Toast.LENGTH_SHORT).show();
                }

                Cursor cursor = ObtainData.getAccountsByAid(mContext, mEtPhone.getText().toString());
                if(cursor==null)
                {
                    Toast.makeText(mContext,"没有此用户",Toast.LENGTH_SHORT).show();
                    break;
                }else{

                    while (cursor.moveToNext()){
                        if(mEtPassword.getText().toString().trim().equals(cursor.getString(cursor.getColumnIndex("Apassword")))){
                            Intent intent = new Intent(mContext,ContentActivtiy.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                }


                break;
            /**
             * 注册
             */
            case R.id.tv_shortcut_login:
                Intent intent = new Intent(mContext,RegiesterActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_find_back_password:


                break;

        }


    }
}
