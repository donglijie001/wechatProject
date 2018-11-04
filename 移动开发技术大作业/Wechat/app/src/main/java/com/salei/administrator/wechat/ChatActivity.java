package com.salei.administrator.wechat;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.salei.administrator.model.*;
import com.salei.administrator.util.GlobalParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.salei.administrator.wechat.ChatContentAdapter;
public class ChatActivity extends AppCompatActivity {
    tuser user;
    ImageView iv_back;
    Activity activity;
    List<Map<String, Object>> data=new ArrayList<Map<String, Object>>();
    List<UserMsg> listmsg=new ArrayList<UserMsg>();
    List<UserMsg> listmsg1=new ArrayList<UserMsg>();
    ChatContentAdapter adapter;
    RequestQueue mQueue;  //volley队列
    ListView lv;
    EditText msg;
    String sendmsg;
    StringRequest stringRequest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        setContentView(R.layout.activity_chart);
        lv = (ListView) findViewById(R.id.lv);
        msg = (EditText)findViewById(R.id.msg);
        activity=this;
        mQueue = Volley.newRequestQueue(activity);  //初始化一个队列
        Intent intent = getIntent();
        user = (tuser) intent.getExtras().getSerializable("user");
        GlobalParam.friend = user;
       // String [] from ={"headimage","nikaname","msgs"};
        Log.i("mememem",listmsg.size()+"");
        //int [] to = {R.id.fheadmagin,R.id.fnikaname,R.id.fcontent};
        adapter=new ChatContentAdapter(this, R.layout.chatlayout,listmsg);
        lv.setAdapter(adapter);
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i){
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        stringRequest1 = new StringRequest(Request.Method.POST, GlobalParam.ser_url+"UserServlet",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // 返回结果
                                        Gson gson = new Gson();
                                        ResponseJson obj=(ResponseJson)gson.fromJson(response,ResponseJson.class);
                                        listmsg.clear();
                                        data.clear();
                                        Log.i("wodedddd",GlobalParam.user.getU_logId());
                                        for(int i=0;i<obj.data.size();++i ) {
                                            listmsg.add(obj.data.get(i));
                                            Log.i("wode",GlobalParam.user.getU_logId());
                                            Log.i("tade",listmsg.get(i).getuId());
                                            if((GlobalParam.user.getU_logId()).equals(listmsg.get(i).getuId())){
                                                listmsg.get(i).setIsme(true);
                                            }else{
                                                listmsg.get(i).setIsme(false);
                                            }
                                            Log.i("mememem",String.valueOf(listmsg.get(i).isme()));
                                            Map<String, Object> map = new HashMap<String, Object>();
                                            map.put("headimage", "");
                                            map.put("nikaname", listmsg.get(i).getU_nickName());
                                            Log.i("11111111111",listmsg.size()+"");
                                            map.put("msgs", obj.data.get(i).getMsg());
                                            data.add(map);
                                            Log.i("ffffffffff2",listmsg.size()+"");

                                        } adapter.notifyDataSetChanged(); // 更新数据
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
                                map.put("method", "getallmsg");
                                map.put("logId",GlobalParam.user.getU_logId());
                                map.put("fId", user.getU_logId());
                                return map;
                            }
                        }; mQueue.add(stringRequest1);

                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        adapter.notifyDataSetChanged();
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        adapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            }
        });
    }
       public void send(View v){
           sendmsg = msg.getText().toString();
           msg.setText("");
           if(sendmsg.equals("")){
               Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
           }else{
           StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalParam.ser_url+"UserServlet",
                   new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           Gson gson = new Gson();
                           ResponseJson obj=(ResponseJson)gson.fromJson(response,ResponseJson.class);
                           if(obj.msg.equals("success")){
                               Toast.makeText(activity, "发送成功", Toast.LENGTH_SHORT).show();
                           }else {
                               //
                               Toast.makeText(activity, "发送失败", Toast.LENGTH_SHORT).show();
                           }
                           Log.i("ffffffffff",listmsg.size()+"");
                           adapter.notifyDataSetChanged(); // 更新数据
                           Log.i("ffffffffff1",listmsg.size()+"");
                       }

                   }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   Log.e("请求服务失败", error.getMessage(), error);
                   Toast.makeText(activity, "发送失败，请确认是否联网", Toast.LENGTH_SHORT).show();
               }
           })
           {
               @Override
               protected Map<String, String> getParams() throws AuthFailureError {
                   Map<String, String> map = new HashMap<String, String>();
                   map.put("method", "sendmsg");
                   map.put("logId", GlobalParam.user.getU_logId());
                   map.put("fId", user.getU_logId());
                   map.put("msg",sendmsg);
                   return map;
               }

           };
           mQueue.add(stringRequest); // 开始网络访问
         }
          stringRequest1 = new StringRequest(Request.Method.POST, GlobalParam.ser_url+"UserServlet",
                   new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           // 返回结果
                           Gson gson = new Gson();
                           ResponseJson obj=(ResponseJson)gson.fromJson(response,ResponseJson.class);
                           listmsg.clear();
                           data.clear();
                           Log.i("wodedddd",GlobalParam.user.getU_logId());
                           for(int i=0;i<obj.data.size();++i ) {
                               listmsg.add(obj.data.get(i));
                               Log.i("wode",GlobalParam.user.getU_logId());
                               Log.i("tade",listmsg.get(i).getuId());
                               if((GlobalParam.user.getU_logId()).equals(listmsg.get(i).getuId())){
                                   listmsg.get(i).setIsme(true);
                               }else{
                                   listmsg.get(i).setIsme(false);
                               }
                               Log.i("mememem",String.valueOf(listmsg.get(i).isme()));
                               Map<String, Object> map = new HashMap<String, Object>();
                               map.put("headimage", "");
                               map.put("nikaname", listmsg.get(i).getU_nickName());
                               Log.i("11111111111",listmsg.size()+"");
                               map.put("msgs", obj.data.get(i).getMsg());
                               data.add(map);
                               Log.i("ffffffffff2",listmsg.size()+"");

                           } adapter.notifyDataSetChanged(); // 更新数据
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
                   map.put("method", "getallmsg");
                   map.put("logId",GlobalParam.user.getU_logId());
                   map.put("fId", user.getU_logId());
                   return map;
               }
           }; mQueue.add(stringRequest1);

       }


   StringRequest stringRequest2 = new StringRequest(Request.Method.POST, GlobalParam.ser_url+"UserServlet",
                                       new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            // 返回结果
            Gson gson = new Gson();
            ResponseJson obj=(ResponseJson)gson.fromJson(response,ResponseJson.class);
            listmsg.clear();
            data.clear();
            Log.i("wodedddd",GlobalParam.user.getU_logId());
            for(int i=0;i<obj.data.size();++i ) {
                listmsg.add(obj.data.get(i));
                Log.i("wode",GlobalParam.user.getU_logId());
                Log.i("tade",listmsg.get(i).getuId());
                if((GlobalParam.user.getU_logId()).equals(listmsg.get(i).getuId())){
                    listmsg.get(i).setIsme(true);
                }else{
                    listmsg.get(i).setIsme(false);
                }
                Log.i("mememem",String.valueOf(listmsg.get(i).isme()));
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("headimage", "");
                map.put("nikaname", listmsg.get(i).getU_nickName());
                Log.i("11111111111",listmsg.size()+"");
                map.put("msgs", obj.data.get(i).getMsg());
                data.add(map);
                Log.i("ffffffffff2",listmsg.size()+"");

            }adapter.notifyDataSetChanged(); // 更新数据
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
        map.put("method", "getallmsg");
        map.put("logId",GlobalParam.user.getU_logId());
        map.put("fId", user.getU_logId());
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
            mQueue.add(stringRequest2);
           // 开始网络访问
        }
    }


}


