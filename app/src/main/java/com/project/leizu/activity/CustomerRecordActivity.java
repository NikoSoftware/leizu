package com.project.leizu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.leizu.R;
import com.project.leizu.base.ObtainData;
import com.project.leizu.base.Record;
import com.project.leizu.fragment.CustomerBorrowFrag;
import com.project.leizu.fragment.CustomerRevertFrag;
import com.project.leizu.util.TitleBuilder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Niko on 2016/7/15.
 */
public class CustomerRecordActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private String Cid;
    private String Cname;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    public static Record recordFlag=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Integer.parseInt(android.os.Build.VERSION.SDK)>=21 ) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorTitle));
        }
        getSupportActionBar().hide();
        setContentView(R.layout.customer_record_activity);
        Intent intent =getIntent();
        Cid = intent.getStringExtra("Cid");
        Cname = intent.getStringExtra("Cname");

        Init();
        initTitle();
    }

    private void initTitle() {
        new TitleBuilder(this).setLeftIco(R.drawable.icon_fanhui)
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitleText("客户登录记录")
                .setRightIco(R.drawable.icon_sousuo)
                .setRightIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

    }

    private void Init() {

        tabLayout =  (TabLayout)findViewById(R.id.TabLayout_title);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragments.add(new CustomerRevertFrag(CustomerRecordActivity.this,Cid,Cname));
        fragments.add(new CustomerBorrowFrag(CustomerRecordActivity.this,Cid,Cname));

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

    }


    public  class ViewPagerAdapter extends FragmentPagerAdapter {


        @Override
        public CharSequence getPageTitle(int position) {
            Log.w("getPageTitle","getPageTitle"+position);
            if(position==0) {
                return "已归还（"+((CustomerRevertFrag) fragments.get(position)).getCount()+")";
            }
            return "未归还（"+((CustomerBorrowFrag) fragments.get(position)).getCount()+")";
        }

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
            Log.w("startUpdate", "startUpdate" );
            tabLayout.setupWithViewPager(viewPager);
        }



        @Override
        public Fragment getItem(int position) {
            Log.d("TAG=====>", "getItem()" + position);

            return fragments.get(position);

        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){

            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");

            if(recordFlag!=null&&result.equals(recordFlag.getGid())){

                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("Rid", recordFlag.getRid() + "");
                    hashMap.put("Cid", recordFlag.getCid());
                    hashMap.put("Cname", recordFlag.getCname());
                    hashMap.put("Gid", recordFlag.getGid());
                    hashMap.put("Gname", recordFlag.getGname());
                    hashMap.put("Rbtime", recordFlag.getRbtime()+"");
                    hashMap.put("Rstate", ContentActivtiy.REVERT + "");
                    hashMap.put("Rstime", System.currentTimeMillis() + "");
                    ObtainData.updataRecord(CustomerRecordActivity.this, hashMap);
                    Toast.makeText(CustomerRecordActivity.this, "归还成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CustomerRecordActivity.this, "商品编码不对应", Toast.LENGTH_SHORT).show();
                }
            recordFlag=null;
            }


    }




}
