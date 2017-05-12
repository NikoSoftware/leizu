package com.project.leizu.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.leizu.activity.ContentActivtiy;
import com.project.leizu.activity.CustomerRecordActivity;
import com.project.leizu.R;
import com.project.leizu.activity.UpdataCustomerActivity;
import com.project.leizu.base.Customer;
import com.xys.libzxing.zxing.activity.CaptureActivity;


import java.util.List;

/**
 * Created by Niko on 2016/7/15.
 */
public class CustomerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<Customer> mList;
    public CustomerAdapter(Context context, List<Customer> list){
        mContext=context;
        mList=list;
        mLayoutInflater=LayoutInflater.from(context);
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mCcompany;
        TextView mCname;
        TextView mCphone;
        RelativeLayout dengji;
        RelativeLayout dengjijilu;
        RelativeLayout guihuan;
        RelativeLayout bianji;
        
        public NormalViewHolder(View itemView) {
            super(itemView);
            mCardView=(CardView)itemView.findViewById(R.id.cv_item);
            mCcompany = (TextView) itemView.findViewById(R.id.ccompany);
            mCname = (TextView) itemView.findViewById(R.id.cname);
            mCphone = (TextView) itemView.findViewById(R.id.cphone);
            dengji = (RelativeLayout) itemView.findViewById(R.id.dengji);
            dengjijilu = (RelativeLayout) itemView.findViewById(R.id.dengjijilu);
            guihuan = (RelativeLayout) itemView.findViewById(R.id.guihuan);
            bianji = (RelativeLayout) itemView.findViewById(R.id.bianji);

        }

    }
    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(mLayoutInflater.inflate(R.layout.customer_item,parent,false));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NormalViewHolder viewholder = (NormalViewHolder) holder;
        final Customer customer = mList.get(position);
        viewholder.mCcompany.setText("" + customer.getCcompany());
        viewholder.mCname.setText("联系人："+customer.getCname());
        viewholder.mCphone.setText("电话号码：" + customer.getCphone());
        viewholder.dengji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentActivtiy.isBorrow=true;
                ContentActivtiy.customerFlag=customer;
                ((Activity) mContext).startActivityForResult(new Intent(mContext, CaptureActivity.class),0);

            }
        });

        viewholder.dengjijilu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, CustomerRecordActivity.class);
                mContext.startActivity(intent);

            }
        });
        viewholder.guihuan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                ContentActivtiy.isBorrow=false;
                ContentActivtiy.customerFlag=customer;
                ((Activity) mContext).startActivityForResult(new Intent(mContext, CaptureActivity.class), 0);

            }
        });
        viewholder.bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, UpdataCustomerActivity.class);
                intent.putExtra("Cid",customer.getCid());
                intent.putExtra("Ccompany",customer.getCcompany());
                intent.putExtra("Cname",customer.getCname());
                intent.putExtra("Cphone",customer.getCphone());
                mContext.startActivity(intent);
            }
        });

    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return mList==null ? 0 : mList.size();
    }
}
