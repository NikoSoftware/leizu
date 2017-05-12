package com.project.leizu.fragment;


import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.project.leizu.R;
import com.project.leizu.activity.BaseActivity;
import com.project.leizu.base.Goods;
import com.project.leizu.base.ObtainData;
import com.project.leizu.recyclerview.RvAdapter;
import com.project.leizu.util.TitleBuilder;
import com.project.leizu.util.Tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class SearchFrag extends Fragment {

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private RecyclerView mRecyclerView;
    //支持下拉刷新的ViewGroup
    private PtrClassicFrameLayout mPtrFrame;
    //List数据
    //RecyclerView自定义Adapter
    private RvAdapter adapter;
    //添加Header和Footer的封装类
    private RecyclerAdapterWithHF mAdapter;
    private Context context;
    private LinearLayout viewGroup;
    private ArrayList<Goods> list = new ArrayList<>();
    private EditText search_text;


    public SearchFrag(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        viewGroup = (LinearLayout) inflater.inflate(R.layout.context_1, container, false);
        initTitle(viewGroup);


/*
        //初始化数据，如果数据库没有数据生成数据库数据
        if (list.size() <= 0) {
            for (int i = 0; i < 5; i++) {
                Goods goods = new Goods();
                goods.setGid("000" + (1 + i) + "");
                goods.setGname("第 000" + (i + 1) + " 号书籍");
                goods.setGform("kus3hg" + i);
                goods.setGweight(123 + "");
                goods.setGlenght(20);
                goods.setGprice(40 + (int) (Math.random() * 50));
                goods.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {

                    }
                });

            }
        }
*/

        mRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.rv_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new RvAdapter(context, list);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        mRecyclerView.setAdapter(mAdapter);
        mPtrFrame = (PtrClassicFrameLayout) viewGroup.findViewById(R.id.rotate_header_list_view_frame);
         //下拉刷新支持时间
        mPtrFrame.setLastUpdateTimeRelateObject(this);
       //下拉刷新一些设置 详情参考文档
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
// default is false
        mPtrFrame.setPullToRefresh(false);
// default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
       //进入Activity就进行自动下拉刷新
/*		mPtrFrame.postDelayed(new Runnable() {
            @Override
			public void run() {
				mPtrFrame.autoRefresh();
			}
		}, 100);*/
        //下拉刷新
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
               //seach为空进行所有搜索
                if(TextUtils.isEmpty(search_text.getText().toString().trim())) {
                   // list.addAll(Tool.cursorGoodsToList(ObtainData.getGoods(context, handler)));
                    requestData();
                }else {
                    /*list.addAll(Tool.cursorGoodsToList(ObtainData.getGoodsByFuzzy(context,
                            search_text.getText().toString())));*/
                requestLikeData();
                }
              //模拟联网 延迟更新列表
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        mPtrFrame.refreshComplete();

                        //关闭上拉加载
                        //   mPtrFrame.setLoadMoreEnable(true);
                    }
                }, 1000);
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

            /*		for (int i = 0; i <= 5; i++) {
                            title.add(String.valueOf(i));
						}*/
                        mAdapter.notifyDataSetChanged();
                        mPtrFrame.loadMoreComplete(true);

                    }
                }, 1000);
            }
        });


        ((BaseActivity) context).setCustomDialog();
        requestData();
        return viewGroup;
    }



    public void requestData(){

        BmobQuery<Goods> query = new BmobQuery<Goods>();
        query.findObjects(new FindListener<Goods>() {

            @Override
            public void done(List<Goods> videoModels, BmobException e) {
                mPtrFrame.refreshComplete();
                ((BaseActivity) context).closeCustomDialog();
                if (e == null) {
                    list.clear();
                    list.addAll(videoModels);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Log.i("bmob", "失败：" + e.getMessage());
                }
            }
        });
    }

    public void requestLikeData(){


        BmobQuery<Goods> query = new BmobQuery<Goods>();
        query.addWhereContains("Gname",search_text.getText().toString());

        query.findObjects(new FindListener<Goods>() {

            @Override
            public void done(List<Goods> videoModels, BmobException e) {
                mPtrFrame.refreshComplete();
                ((BaseActivity) context).closeCustomDialog();
                if (e == null) {
                    list.clear();
                    list.addAll(videoModels);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Log.i("bmob", "失败：" + e.getMessage());
                }
            }
        });


    }




    private void initTitle(LinearLayout viewGroup) {

        new TitleBuilder(viewGroup).setTitleText("搜索");

        search_text = (EditText) viewGroup.findViewById(R.id.search_text);


        search_text.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(final Editable editable) {

/*                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        list.addAll(Tool.cursorGoodsToList(ObtainData.getGoodsByFuzzy(context,
                                search_text.getText().toString())));
                        mAdapter.notifyDataSetChanged();
                    }
                });*/

                requestLikeData();


            }
        });


    }
}
