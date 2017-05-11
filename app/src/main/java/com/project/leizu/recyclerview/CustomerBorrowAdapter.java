package com.project.leizu.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.project.leizu.activity.ContentActivtiy;
import com.project.leizu.activity.CustomerRecordActivity;
import com.project.leizu.R;
import com.project.leizu.base.Record;
import com.project.leizu.util.Tool;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.List;

/**
 * Created by Niko on 2016/7/15.
 */
public class CustomerBorrowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<Record> mList;

    public CustomerBorrowAdapter(Context context, List<Record> list){
        mContext=context;
        mList=list;
        mLayoutInflater=LayoutInflater.from(context);
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class NormalViewHolder extends RecyclerView.ViewHolder {

        TextView mGname;
        TextView mGid;
        TextView mRbtime;
        TextView mCname;
        TextView mRstate;
        Button mRevertButton;

        
        public NormalViewHolder(View itemView) {
            super(itemView);
            mGname = (TextView) itemView.findViewById(R.id.gname);
            mGid = (TextView) itemView.findViewById(R.id.gid);
            mRbtime = (TextView) itemView.findViewById(R.id.rbtime);
            mCname = (TextView) itemView.findViewById(R.id.cname);
            mRstate = (TextView) itemView.findViewById(R.id.rstate);
            mRevertButton = (Button) itemView.findViewById(R.id.revertbutton);

        }

    }
    //在该方法中我们创建一个ViewHolder并返回，ViewHolder必须有一个带有View的构造函数，这个View就是我们Item的根布局，在这里我们使用自定义Item的布局；
    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(mLayoutInflater.inflate(R.layout.customer_borrow_item, parent, false));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NormalViewHolder viewholder = (NormalViewHolder) holder;
        final Record record = mList.get(position);
        viewholder.mGname.setText("名称："+record.getGname());
        viewholder.mGid.setText("编号："+record.getGid());
        viewholder.mRbtime.setText(Tool.getStringDate(record.getRbtime()));
        viewholder.mCname.setText("交接人："+record.getCname()+"");
        viewholder.mRstate.setText("状态："+(
                record.getRstate()== ContentActivtiy.REVERT?"已归还 （"+Tool.getStringDate(record.getRstime())+"）":"未归还"));
        viewholder.mRevertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomerRecordActivity.recordFlag = record;
                ((Activity) mContext).startActivityForResult(new Intent(mContext, CaptureActivity.class), 0);

            }
        });

    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return mList==null ? 0 : mList.size();
    }
}
