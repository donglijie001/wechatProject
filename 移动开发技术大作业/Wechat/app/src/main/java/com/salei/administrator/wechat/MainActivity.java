package com.salei.administrator.wechat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.salei.administrator.util.GlobalParam;

import com.salei.administrator.model.*;
public class MainActivity extends Activity{
     ListView f_lv;
    ImageView  iv_back;
    Activity activity;
    List<Map<String, Object>> data=new ArrayList<Map<String, Object>>();
    List<tuser> listuser=new ArrayList<tuser>();
    SimpleAdapter adapter;
    RequestQueue mQueue;  //volley队列
    EditText name_view;
    EditText pwd_view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        f_lv = (ListView) findViewById(R.id.friends_list);
        iv_back = (ImageView) findViewById(R.id.main_back);
          name_view = (EditText) findViewById(R.id.login_uname);
          pwd_view = (EditText) findViewById(R.id.login_upwd);
        activity=this;
        mQueue = Volley.newRequestQueue(activity);  //初始化一个队列
        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("EXIT_TAG", "SINGLETASK");
                startActivity(intent);

            }
        });
        String [] from ={"headimage","nikaname","signalture"};
        int [] to = {R.id.headimage,R.id.nikaname,R.id.signalture};
        adapter=new SimpleAdapter(this, data, R.layout.userlist_item, from, to);
        f_lv = (ListView) findViewById(R.id.friends_list);
        f_lv.setAdapter(adapter);
        f_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) { // 查看
                Intent intent = new Intent(activity, ChatActivity.class);
                //Bundle bundle = new Bundle();
                //bundle.putExtras("user", listuser.get(position));//listview点击事件，intent带教室信息参数
                intent.putExtra("user",listuser.get(position));
                startActivity(intent);
            }
        });

    }
    StringRequest stringRequest = new StringRequest(Method.POST, GlobalParam.ser_url+"UserServlet",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // 返回结果
                    Log.i("11111111111","1111111111111");
                    Gson gson = new Gson();
                    ResponseJsonUser obj=(ResponseJsonUser)gson.fromJson(response,ResponseJsonUser.class);
                    listuser.clear();
                    data.clear();
                    for(int i=0;i<obj.data.size();++i ) {
                        listuser.add(obj.data.get(i));
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("headimage", "");
                        map.put("nikaname", obj.data.get(i).getU_nickName());
                        map.put("signalture", obj.data.get(i).getU_signaTure());
                        data.add(map);
                    }
                    adapter.notifyDataSetChanged(); // 更新数据
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("请求服务失败", error.getMessage(), error);
            Toast.makeText(activity, "请求服务器失败"+error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    })
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Log.i("3333333333333","1111111111111");
            Map<String, String> map = new HashMap<String, String>();
            map.put("method", "getalluser");
            map.put("logId",GlobalParam.user.getU_logId());
            return map;
       }
    };

    // 加载完成的事件
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {  Log.i("44444444444","1111111111111");
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
        {
            mQueue.add(stringRequest); // 开始网络访问
        }
    }
}

