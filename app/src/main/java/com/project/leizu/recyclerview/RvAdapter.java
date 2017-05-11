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
import com.project.leizu.R;
import com.project.leizu.base.Goods;
import com.project.leizu.util.BitmapUtil;
import com.project.leizu.util.DensityUtil;

import java.util.List;

/**
 * Created by Niko on 2016/7/15.
 */
public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<Goods> mList;
    public RvAdapter(Context context,List<Goods> list){
        mContext=context;
        mList=list;
        mLayoutInflater=LayoutInflater.from(context);
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mGname;
        TextView mGid;
        TextView mGform;
        TextView mGweight;
        TextView mGlenght;
        TextView mGprice;
        ImageView qcimage;
        
        public NormalViewHolder(View itemView) {
            super(itemView);
            mCardView=(CardView)itemView.findViewById(R.id.cv_item);
            mGname = (TextView) itemView.findViewById(R.id.gname);
            mGid = (TextView) itemView.findViewById(R.id.gid);
            mGform = (TextView) itemView.findViewById(R.id.gform);
            mGweight = (TextView) itemView.findViewById(R.id.gweight);
            mGlenght = (TextView) itemView.findViewById(R.id.glenght);
            mGprice = (TextView) itemView.findViewById(R.id.gprice);
            qcimage = (ImageView) itemView.findViewById(R.id.qcimage);
        }

    }

    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(mLayoutInflater.inflate(R.layout.rv_item,parent,false));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NormalViewHolder viewholder = (NormalViewHolder) holder;
        Goods goods = mList.get(position);
        viewholder.mGname.setText("名称："+goods.getGname());
        viewholder.mGid.setText("编号："+goods.getGid());
        viewholder.mGform.setText("成分："+goods.getGform());
        viewholder.mGweight.setText("克重："+goods.getGweight()+"m/m");
        viewholder.mGlenght.setText("成品门幅：" + goods.getGlenght() + "cm");
        viewholder.mGprice.setText("￥"+goods.getGprice());
        Bitmap bitmap=null;
        try {

            bitmap=BitmapUtil.createQRCode(goods.getGid(), DensityUtil.dip2px(mContext,48));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        Log.d(" bitmap.getWidth()",""+ bitmap.getWidth());
        if(bitmap!=null)
        viewholder.qcimage.setImageBitmap(bitmap);


    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return mList==null ? 0 : mList.size();
    }
}
