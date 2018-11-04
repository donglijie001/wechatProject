package com.salei.administrator.util;

import java.text.SimpleDateFormat;

import com.salei.administrator.model.tuser;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


public class GlobalParam {
	public final static String ser_url = "http://192.168.20.229:8080/WechatServer/servlet/";
	public static final SimpleDateFormat g_dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static Context mainContext;
	public static boolean islogin=false;
	public static tuser user;
	public static tuser friend;
	public static void showToast(String msg){
		Toast toast=Toast.makeText(mainContext, msg,Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
