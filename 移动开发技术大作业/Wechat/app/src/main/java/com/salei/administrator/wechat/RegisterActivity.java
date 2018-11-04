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
import com.salei.administrator.util.GlobalParam;
import com.salei.administrator.model.*;
import com.google.gson.Gson;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	Activity activity;
	RequestQueue mQueue;  //volley队列
	private ProgressDialog progressDialog = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		activity=this;
		mQueue = Volley.newRequestQueue(activity);  //初始化一个队列

		ImageView back=(ImageView)findViewById(R.id.register_back);  // 返回
		back.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				activity.finish();
			}
		});

		Button reg = (Button) findViewById(R.id.reg_goreg);  // 注册按钮
		final EditText name_view = (EditText) findViewById(R.id.reg_username); // 用户名
		final EditText pwd_view = (EditText) findViewById(R.id.reg_userpwd);  // 密码
		final EditText pwd_con_view = (EditText) findViewById(R.id.reg_userpwd_confirm);  // 确认密码


		reg.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				final String username = name_view.getText().toString();
				final String password = pwd_view.getText().toString();
				final String password_confirm = pwd_con_view.getText().toString();


				if(username.equals("")){
					Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!password.equals(password_confirm)){
					Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
					return;
				}


				//验证通过 开始注册了
				StringRequest stringRequest = new StringRequest(Method.POST, GlobalParam.ser_url+"UserServlet",
						new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {
								//Toast.makeText(activity, "成功"+response, Toast.LENGTH_SHORT).show();
								if (progressDialog.isShowing())
									progressDialog.cancel();
								// 返回结果
								Gson gson = new Gson();
								ResponseJson obj=(ResponseJson)gson.fromJson(response,ResponseJson.class);
								if(obj.msg.equals("success")){
									//注册成功
									Toast.makeText(activity, "注册成功，请登录", Toast.LENGTH_LONG).show();
									finish();
								}else {
									// 注册失败  用户名存在
									Toast.makeText(activity, "注册失败，用户名已经存在", Toast.LENGTH_SHORT).show();
								}
							}
						}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (progressDialog.isShowing())
							progressDialog.cancel();
						Log.e("请求服务失败", error.getMessage(), error);
						Toast.makeText(activity, "注册失败，请确认是否联网", Toast.LENGTH_SHORT).show();
					}
				})
				{
					@Override
					protected Map<String, String> getParams() throws AuthFailureError {
						Map<String, String> map = new HashMap<String, String>();
						map.put("method", "registeruser");
						Log.i("++++++++++++",password);
						map.put("logId", username);
						map.put("psw", password);

						return map;
					}
				};
				mQueue.add(stringRequest); // 开始网络访问
				progressDialog = ProgressDialog.show(activity, "", "正在提交数据.....", true);
				progressDialog.setCancelable(true);// 当点击按钮返回的时候Dialog消失
			}
		});



	}

}
