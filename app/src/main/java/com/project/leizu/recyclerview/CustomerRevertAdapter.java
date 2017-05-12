package com.project.leizu.recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.project.leizu.activity.ContentActivtiy;
import com.project.leizu.R;
import com.project.leizu.base.Record;
import com.project.leizu.util.BitmapUtil;
import com.project.leizu.util.DensityUtil;
import com.project.leizu.util.Tool;

import java.util.List;

/**
 * Created by Niko on 2016/7/15.
 */
public class CustomerRevertAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<Record> mList;
    public CustomerRevertAdapter(Context context, List<Record> list){
        mContext=context;
        mList=list;
        mLayoutInflater=LayoutInflater.from(context);
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        private final ImageView qcimage;
        CardView mCardView;
        TextView mGname;
        TextView mGid;
        TextView mRbtime;
        TextView mCname;
        TextView mRstate;

        
        public NormalViewHolder(View itemView) {
            super(itemView);
            mCardView=(CardView)itemView.findViewById(R.id.cv_item);
            mGname = (TextView) itemView.findViewById(R.id.gname);
            mGid = (TextView) itemView.findViewById(R.id.gid);
            mRbtime = (TextView) itemView.findViewById(R.id.rbtime);
            mCname = (TextView) itemView.findViewById(R.id.cname);
            mRstate = (TextView) itemView.findViewById(R.id.rstate);
            qcimage = (ImageView) itemView.findViewById(R.id.qcimage);
        }

    }

    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(mLayoutInflater.inflate(R.layout.customer_revert_item,parent,false));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NormalViewHolder viewholder = (NormalViewHolder) holder;
        Record record = mList.get(position);
        viewholder.mGname.setText("名称："+record.getGname());
        viewholder.mGid.setText("编号："+record.getGid().getGid());
        viewholder.mRbtime.setText(Tool.getStringDate(record.getRbtime()));
        viewholder.mCname.setText("交接人："+record.getCname()+"");
        viewholder.mRstate.setText("状态："+(
                record.getRstate()== ContentActivtiy.REVERT?"已归还 （"+Tool.getStringDate(record.getRstime())+"）":"未归还"));

        Bitmap bitmap=null;
        try {

            bitmap= BitmapUtil.createQRCode(record.getGid().getGid(), DensityUtil.dip2px(mContext, 48));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        Log.d(" bitmap.getWidth()", "" + bitmap.getWidth());
        if(bitmap!=null)
            viewholder.qcimage.setImageBitmap(bitmap);
        
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return mList==null ? 0 : mList.size();
    }
}
