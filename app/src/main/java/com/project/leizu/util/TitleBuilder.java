package com.project.leizu.util;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.leizu.R;

/**
 * Created by Niko on 2016/7/15.
 */
public class TitleBuilder {

    private LinearLayout title_leftline;
    private LinearLayout title_rightline;
    private View titleView;
    private RelativeLayout titleBar;
    private TextView text;
    private ImageView leftIco;
    private ImageView rightIco;

    /**
     * 构造方法：用于获取对象
     * */
    public TitleBuilder(Activity context){
        titleView = context.findViewById(R.id.title_bar);
        text = (TextView)titleView.findViewById(R.id.title_text);
        titleBar = (RelativeLayout)titleView.findViewById(R.id.title_bar);
        leftIco = (ImageView)titleView.findViewById(R.id.title_leftIco);
        rightIco = (ImageView)titleView.findViewById(R.id.title_rightIco);
        title_leftline = (LinearLayout)titleView.findViewById(R.id.title_leftline);
        title_rightline = (LinearLayout)titleView.findViewById(R.id.title_rightline);
    }

    public TitleBuilder(ViewGroup context){
        titleView = context.findViewById(R.id.title_bar);
        text = (TextView)titleView.findViewById(R.id.title_text);
        titleBar = (RelativeLayout)titleView.findViewById(R.id.title_bar);
        leftIco = (ImageView)titleView.findViewById(R.id.title_leftIco);
        rightIco = (ImageView)titleView.findViewById(R.id.title_rightIco);
        title_leftline = (LinearLayout)titleView.findViewById(R.id.title_leftline);
        title_rightline = (LinearLayout)titleView.findViewById(R.id.title_rightline);
    }


    /**
     * 用于设置标题栏文字
     * */
    public TitleBuilder setTitleText(String titleText){
        if(!TextUtils.isEmpty(titleText)){
            text.setText(titleText);
        }
        return this;
    }

    public TitleBuilder setTitleTextVisible(int visible){
        return  hideOrDisplay(text,visible);
    }
    /**
     * 用于设置标题栏左边要显示的图片
     * */
    public TitleBuilder setLeftIco(int resId){
        leftIco.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        leftIco.setImageResource(resId);
        return this;
    }
    public TitleBuilder setLeftIcoVisible(int visible){
       return  hideOrDisplay(leftIco,visible);
    }

    private  TitleBuilder hideOrDisplay(View view,int visible ){
        if (view.getVisibility()!=visible){
            view.setVisibility(visible);
        }

        return this;
    }


    /**
     * 用于设置标题栏右边要显示的图片
     * */
    public TitleBuilder setRightIco(int resId){
        rightIco.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        rightIco.setImageResource(resId);
        return this;
    }

    public TitleBuilder setRightIcoVisible(int visible){
        return  hideOrDisplay(rightIco,visible);
    }

    /**
     * 用于设置标题栏左边图片的单击事件
     * */
    public TitleBuilder setLeftIcoListening(View.OnClickListener listener){
        if(leftIco.getVisibility() == View.VISIBLE){
            title_leftline.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 用于设置标题栏右边图片的单击事件
     * */
    public TitleBuilder setRightIcoListening(View.OnClickListener listener){
        if(rightIco.getVisibility() == View.VISIBLE){
            title_rightline.setOnClickListener(listener);
        }
        return this;
    }

}
