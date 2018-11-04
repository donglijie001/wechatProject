package com.salei.administrator.wechat;

import java.util.HashMap;
import java.util.Map;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import com.salei.administrator.util.GlobalParam;
import com.salei.administrator.model.*;

public class LoginActivity extends Activity {
	RequestQueue mQueue;  //volley队列
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mQueue = Volley.newRequestQueue(this);  //初始化一个队列
		final EditText name_view = (EditText) findViewById(R.id.login_uname);
		final EditText pwd_view = (EditText) findViewById(R.id.login_upwd);


		Button login = (Button) findViewById(R.id.login_login);
		TextView register = (TextView) findViewById(R.id.login_reg);

		register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
		register.setTextColor(Color.BLUE);
		//学生登录
		login.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				final String username = name_view.getText().toString();
				final String password = pwd_view.getText().toString();

				if(username.equals("")){
					Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if(password.equals("")){
					Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				{
					// 开始登录
					StringRequest stringRequest = new StringRequest(Method.POST, GlobalParam.ser_url+"UserServlet",
							new Response.Listener<String>() {
								@Override
								public void onResponse(String response) {
									// 返回结果
									Gson gson = new Gson();
									ResponseJsonUser obj=(ResponseJsonUser)gson.fromJson(response,ResponseJsonUser.class);
									if(obj.msg.equals("success")){
										//登陆成功
										//Log.i("loginnnnnnn",obj.data.get(0).getU_id()+"");
										GlobalParam.user=new tuser(obj.data.get(0));
										Intent in = new Intent();
										in.setClass(LoginActivity.this, MainActivity.class);
										startActivity(in);
										//finish();
									}else {
										// 登录失败
										Toast.makeText(LoginActivity.this, "登陆失败，用户名或密码错误", Toast.LENGTH_SHORT).show();
									}

								}
							}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Log.e("请求服务失败", error.getMessage(), error);
							Toast.makeText(LoginActivity.this, "登陆失败，请确认是否联网", Toast.LENGTH_SHORT).show();
						}
					})
					{
						@Override
						protected Map<String, String> getParams() throws AuthFailureError {
							Map<String, String> map = new HashMap<String, String>();

							map.put("method", "loginuser");

							map.put("logId", username);
							map.put("pwd", password);
							return map;
						}
					};
					mQueue.add(stringRequest); // 开始网络访问
				}

			}

		});

		// 注册
		register.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent in = new Intent();
				in.setClass(LoginActivity.this, RegisterActivity.class);
				startActivity(in);
			}

		});



	}
	@Override protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent); String tag = intent.getStringExtra("EXIT_TAG");
		if (tag != null&& !TextUtils.isEmpty(tag)) { if ("SINGLETASK".equals(tag)) {
			//退出程序
			finish(); } } }
		}



