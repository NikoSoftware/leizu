package com.project.leizu.fragment;





import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.zxing.WriterException;
import com.project.leizu.R;
import com.project.leizu.util.BitmapUtil;
import com.project.leizu.util.TitleBuilder;

public class UserFrag extends Fragment {

	private RelativeLayout viewGroup;

	private ImageView qcimage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		viewGroup =(RelativeLayout)inflater.inflate(R.layout.context_3, container, false);
		qcimage = (ImageView) viewGroup.findViewById(R.id.qcimage);
		initTitle(viewGroup);
		initQR("0004");

		return viewGroup;
	}

	private void initQR(String result) {
		Bitmap bitmap=null;
		try {
			 bitmap = BitmapUtil.createQRCode(result,100);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		Log.d("TAg===>",""+(bitmap==null));
		if(bitmap!=null){

			qcimage.setImageBitmap(bitmap);
		}


	}

	private void initTitle(RelativeLayout viewGroup) {
		new TitleBuilder(viewGroup).setTitleText("说明页面");
	}




}
