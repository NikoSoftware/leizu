package com.project.leizu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.project.leizu.R;
import com.project.leizu.base.Record;
import com.project.leizu.fragment.CustomerBorrowFrag;
import com.project.leizu.fragment.CustomerRevertFrag;
import com.project.leizu.util.CustomDialog;
import com.project.leizu.util.TitleBuilder;

import java.util.ArrayList;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Niko on 2016/7/15.
 */
public class CustomerRecordActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    public static Record recordFlag=null;
    private CustomDialog mCustomDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Integer.parseInt(android.os.Build.VERSION.SDK)>=21 ) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorTitle));
        }
        getSupportActionBar().hide();
        setContentView(R.layout.customer_record_activity);

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
        fragments.add(new CustomerRevertFrag(CustomerRecordActivity.this));
        fragments.add(new CustomerBorrowFrag(CustomerRecordActivity.this));

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

    }


    public  class ViewPagerAdapter extends FragmentPagerAdapter {


        @Override
        public CharSequence getPageTitle(int position) {
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
            tabLayout.setupWithViewPager(viewPager);
        }



        @Override
        public Fragment getItem(int position) {
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
        if(resultCode==RESULT_OK) {

            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");

            if (recordFlag != null && result.equals(recordFlag.getGid().getGid()+"")) {
/*
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
                  */
                recordFlag.setRstate(ContentActivtiy.REVERT);
                recordFlag.setRstime(System.currentTimeMillis());
                recordFlag.update(recordFlag.getObjectId(), new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(CustomerRecordActivity.this, "归还成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i("bmob", "归还失败：" + e.getMessage() + "," + e.getErrorCode());
                            Toast.makeText(CustomerRecordActivity.this, "归还失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(CustomerRecordActivity.this, "商品编码不对应", Toast.LENGTH_SHORT).show();
            }

            recordFlag = null;
        }
    }


    /**
     * 默认不调用
     */
    public void setCustomDialog() {
        mCustomDialog = new CustomDialog(this, CustomDialog.DIALOG_THEME_LOADING).show();
    }

    /**
     * 默认不调用
     */
    public void closeCustomDialog() {
        if (mCustomDialog != null && mCustomDialog.isShowing()) {
            mCustomDialog.dismiss();
        }
    }
    /**
     * 显示Toast信息
     * @param msg  String
     */
    public void showSnackbar(String msg) {

        View view = this.getWindow().getDecorView();
        if (view != null && msg != null && !"".equals(msg) && !"null".equals(msg)) {
            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                    .setAction("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // nothing
                        }
                    })
                    .show();
        }
    }


    /**
     * 点击任意位置关闭软键盘
     * 在指派Touch事件时拦截，由于安卓的Touch事件是自顶而下的，所以Activity是第一响应者
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 类型为Down才处理，还有Move/Up之类的
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 当前拥有焦点的View
            if (this.getCurrentFocus() != null) {
                closeSoftInput(getCurrentFocus());
            }
        }
        // 继续指派Touch事件，如果这里不执行基类的dispatchTouchEvent，事件将不会继续往下传递
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 关闭软键盘
     *
     * @param view View
     */
    protected void closeSoftInput(View view) {
        if (view != null) {
            if (view.getWindowToken() != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } else {

            }
        }
    }

}
