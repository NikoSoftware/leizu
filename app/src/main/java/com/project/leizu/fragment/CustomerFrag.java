package com.project.leizu.fragment;



import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.project.leizu.activity.AddCustomerActivity;
import com.project.leizu.R;
import com.project.leizu.activity.BaseActivity;
import com.project.leizu.base.Customer;
import com.project.leizu.base.Goods;
import com.project.leizu.base.ObtainData;
import com.project.leizu.recyclerview.CustomerAdapter;
import com.project.leizu.util.TitleBuilder;
import com.project.leizu.util.Tool;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class CustomerFrag extends Fragment {

	private Context context;
	private LinearLayout viewGroup;
	private RecyclerView mRecyclerView;
	private CustomerAdapter adapter;
	private ArrayList<Customer> list=new ArrayList<>();
	private RecyclerAdapterWithHF mAdapter;
	private PtrClassicFrameLayout mPtrFrame;

	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
	};
	private EditText search_text;


	public CustomerFrag(Context context){
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		viewGroup = (LinearLayout)inflater.inflate(R.layout.context_2, container, false);

		initTitle(viewGroup);

		InitRecyclerView();


		return viewGroup;
	}

	private void InitRecyclerView() {

/*				for(int i=0;i<5;i++) {
			HashMap<String, String> hashMap = new HashMap<>();
			hashMap.put("Ccompany", "000"+(i+1)+"有限公司");
			hashMap.put("Cname", "000"+(i+1)+"用户");
			hashMap.put("Cphone", "1256655451");

			ObtainData.insertCustomer(context,hashMap);

		}*/
	//	list = Tool.cursorCustomerToList(ObtainData.getCustomer(context, handler));

		mRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.rv_list);
		LinearLayoutManager layoutManager = new LinearLayoutManager(context);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);
		adapter = new CustomerAdapter(context, list);
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
		/*mPtrFrame.postDelayed(new Runnable() {
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
				}, 1000);
			}
		});


		((BaseActivity) context).setCustomDialog();
		requestData();

	}


	public void requestData(){
		BmobQuery<Customer> query = new BmobQuery<Customer>();
		query.addWhereEqualTo("user", BmobUser.getCurrentUser());
		query.include("user");
		query.findObjects(new FindListener<Customer>() {

			@Override
			public void done(List<Customer> customers, BmobException e) {
				mPtrFrame.refreshComplete();
				((BaseActivity) context).closeCustomDialog();
				if (e == null) {
					list.clear();
					list.addAll(customers);
					mAdapter.notifyDataSetChanged();
				} else {
					Log.i("bmob", "失败：" + e.getMessage());
				}
			}
		});
	}



	private void initTitle(LinearLayout viewGroup) {

		new TitleBuilder(viewGroup).setTitleText("客户列表")
				.setRightIco(R.drawable.icon_tianjia)
				.setRightIcoListening(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(context, AddCustomerActivity.class);
						startActivity(intent);

					}
				});

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

				new Handler().post(new Runnable() {
					@Override
					public void run() {
					list.clear();
						list.addAll(Tool.cursorCustomerToList(ObtainData.getCustomerByFuzzy(context,
								search_text.getText().toString())));
						mAdapter.notifyDataSetChanged();
					}
				});


			}
		});

	}
}
