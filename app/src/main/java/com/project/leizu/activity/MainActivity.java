package com.project.leizu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.project.leizu.R;
import com.project.leizu.util.TitleBuilder;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Integer.parseInt(android.os.Build.VERSION.SDK)>=21 ) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorTitle));
        }
        setContentView(R.layout.activity_main);
        initTitle();
    }

    private void initTitle() {


        new TitleBuilder(this).setLeftIco(R.drawable.icon_fanhui)
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitleText("添加客户信息")
                .setRightIco(R.drawable.icon_sousuo)
                .setRightIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

    }



}
