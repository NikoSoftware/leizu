package com.project.leizu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.project.leizu.util.CustomDialog;

/**
 * Created by niko on 2017/5/12.
 */

public class BaseActivity extends Activity {
    private CustomDialog mCustomDialog;

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
     * 跳转界面
     *
     * @param mContext 当前跳转的界面
     * @param clazz    要跳转的界面
     */
    public  void skip(Context mContext, Class clazz) {
        Intent intent = new Intent(mContext, clazz);
        mContext.startActivity(intent);
    }

    /**
     * 跳转界面 bundle
     * @param mContext 当前跳转的界面
     * @param clazz 要跳转的界面
     * @param bundle 传值
     */
    public void skip(Context mContext, Class clazz, Bundle bundle) {
        Intent intent = new Intent(mContext, clazz);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    /**
     * 跳转界面带回调函数
     * @param mContext
     * @param clazz
     * @param bundle
     */
    public void skipForResult(Context mContext, Class clazz, Bundle bundle, int requestCode){
        Intent intent = new Intent(mContext, clazz);
        if(bundle!=null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,requestCode);
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
