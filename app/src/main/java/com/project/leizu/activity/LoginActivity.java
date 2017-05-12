package com.project.leizu.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.leizu.R;
import com.project.leizu.base.ObtainData;
import com.project.leizu.permission.PermissionFail;
import com.project.leizu.permission.PermissionHelper;
import com.project.leizu.permission.PermissionSucceed;
import com.project.leizu.util.TitleBuilder;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by niko on 2017/3/18.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private Context mContext;
    private EditText mEtPhone;
    private EditText mEtPassword;
    private TextView mBtnLogin;
    private TextView mBtnShortcutLogin;
    private TextView mBtnFindBackPassword;
    public static final int REQUECT_CODE_SDCARD = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "4cebb59b656948526632cf36cc03ccbd");
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
        /**
         * 运行时权限
         */
        PermissionHelper.requestPermission(this, REQUECT_CODE_SDCARD,
                new String[]{Manifest.permission.CAMERA}, "摄像机权限");


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        PermissionHelper.requestPermissionsResult(this, requestCode, permissions);
    }

    /**
     * 运行时权限成功时调用
     */
    @PermissionSucceed(requestCode = REQUECT_CODE_SDCARD)
    private void callPhone() {
    }


    /**
     * 运行时权限失败时调用
     */
    @PermissionFail(requestCode = REQUECT_CODE_SDCARD)
    private void failPermission() {
        Toast.makeText(mContext, "申请权限失败", Toast.LENGTH_SHORT).show();
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
                    break;
                }

                if(TextUtils.isEmpty(mEtPassword.getText().toString().trim())){

                    Toast.makeText(mContext,"密码不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }

                setCustomDialog();
                BmobUser bu = new BmobUser();
                bu.setUsername(mEtPhone.getText().toString());
                bu.setPassword(mEtPassword.getText().toString());
                bu.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser s, BmobException e) {
                        closeCustomDialog();

                        if (e == null) {
                            skip(mContext, ContentActivtiy.class);
                            finish();
                        } else {
                            showSnackbar("登录失败");
                            e.printStackTrace();
                        }
                    }
                });



             /*   Cursor cursor = ObtainData.getAccountsByAid(mContext, mEtPhone.getText().toString());
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
*/

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

    public void login(){

        setCustomDialog();
        BmobUser bu =BmobUser.getCurrentUser();
        bu.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser s, BmobException e) {
                closeCustomDialog();

                if (e == null) {
                    skip(mContext, ContentActivtiy.class);
                    finish();
                } else {
                    showSnackbar("登录失败");
                    e.printStackTrace();
                }
            }
        });

    }

}
