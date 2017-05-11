package com.project.leizu.customcontrols;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

import com.project.leizu.R;

/**
 * Created by Niko on 2016/7/15.
 */
public class LineEditText extends EditText {
    private Paint paint;
    private Rect mRect;
    private float mult = 1.5f;
    private float add = 2.0f;

    public LineEditText(Context context) {
        super(context);
        init();
    }

    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    
    public void init(){
        mRect = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(getResources().getColor(R.color.shallowblack));
        // 开启抗锯齿 较耗内存
        paint.setAntiAlias(true);
        this.setLineSpacing(add, mult);
        this.setCursorVisible(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {


        int lineCount = getLineCount();
        // 得到每行的高度
        int lineHeight = getLineHeight();
        // 根据行数循环画线

        for (int i = 0; i < lineCount; i++) {
            getLineBounds(i, mRect);
            int baseline = (i + 1) * getLineHeight();
            canvas.drawLine(0, baseline+3, mRect.width()+20, baseline+3,paint);
        }
        super.onDraw(canvas);



    }
}
