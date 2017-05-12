package com.project.leizu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.project.leizu.activity.BaseActivity;
import com.project.leizu.activity.ContentActivtiy;
import com.project.leizu.R;
import com.project.leizu.activity.CustomerRecordActivity;
import com.project.leizu.base.Customer;
import com.project.leizu.base.ObtainData;
import com.project.leizu.base.Record;
import com.project.leizu.recyclerview.CustomerBorrowAdapter;
import com.project.leizu.util.Tool;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Niko on 2016/7/15.
 */
public class CustomerBorrowFrag extends Fragment {

    private  Context context;
    private LinearLayout viewGroup;
    private RecyclerView mRecyclerView;
    private RecyclerAdapterWithHF mAdapter;

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private PtrClassicFrameLayout mPtrFrame;
    private ArrayList<Record> list = new ArrayList<>();
    private CustomerBorrowAdapter adapter;


    public CustomerBorrowFrag(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewGroup = (LinearLayout)inflater.inflate(R.layout.customer_borrow_frag, container, false);

        InitRecyclerView();
        return viewGroup;
    }

    private void InitRecyclerView() {

     //   list = Tool.cursorRecordToList(ObtainData.getRecordRecordByCidAndRstate(context, handler, Cid, ContentActivtiy.BORROW+""));
       // list = Tool.cursorRecordToList(ObtainData.getRecord(context, handler));
        mRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.rv_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new CustomerBorrowAdapter(context, list);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        mRecyclerView.setAdapter(mAdapter);
        mPtrFrame = (PtrClassicFrameLayout)viewGroup.findViewById(R.id.rotate_header_list_view_frame);
//下拉刷新支持时间
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
// default is false
        mPtrFrame.setPullToRefresh(false);
// default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
//进入Activity就进行自动下拉刷新
/*        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);*/
//下拉刷新
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                requestData();
//模拟数据
/*
                list.addAll(  list = Tool.cursorRecordToList(
                        ObtainData.getRecordRecordByCidAndRstate(context, handler, Cid, ContentActivtiy.BORROW + "")));

//模拟联网 延迟更新列表
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        mPtrFrame.refreshComplete();
                   //     mPtrFrame.setLoadMoreEnable(true);
                    }
                }, 1000);*/



            }
        });
//上拉加载
        mPtrFrame.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
//模拟联网延迟更新数据
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//模拟数据
                        mAdapter.notifyDataSetChanged();
                        mPtrFrame.loadMoreComplete(true);

                    }
                }, 500);
            }
        });

       // ((CustomerRecordActivity) context).setCustomDialog();
        requestData();

    }



    public void requestData(){
        BmobQuery<Record> query = new BmobQuery<Record>();

        BmobQuery<Customer> customer = new BmobQuery<Customer>();
        customer.addWhereEqualTo("user", BmobUser.getCurrentUser());
        query.addWhereMatchesQuery("Cid","Customer",customer);
        query.include("Cid,Gid");
        query.addWhereEqualTo("Rstate",ContentActivtiy.BORROW);
        query.order("-createdAt");
        query.findObjects(new FindListener<Record>() {

            @Override
            public void done(List<Record> records, BmobException e) {
                mPtrFrame.refreshComplete();
          //      ((CustomerRecordActivity) context).closeCustomDialog();
                if (e == null) {
                    list.clear();
                    list.addAll(records);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Log.i("bmob", "失败：" + e.getMessage());
                }
            }
        });
    }


    public int getCount(){

        return list==null?0:list.size();
    }
    
}
