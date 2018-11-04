package com.salei.administrator.wechat;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.salei.administrator.model.UserMsg;
import java.util.List;
/**
 * Created by john on 2018/10/27.
 */

public class ChatContentAdapter extends ArrayAdapter<UserMsg> {
    private Context context;
    private int resource;
    TextView tvf ;
    TextView tvr;
    private List<UserMsg> data;
    public ChatContentAdapter(@NonNull Context context, int resource, List<UserMsg> data) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public UserMsg getItem(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserMsg cc=data.get(position);
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(resource,parent,false);
        }
        convertView.findViewById(R.id.layout_left).setVisibility(View.VISIBLE);
        convertView.findViewById(R.id.layout_right).setVisibility(View.VISIBLE);
        if(cc.isme()){
            Log.i("ismeismeisme", "getView:ismeisme");
            convertView.findViewById(R.id.layout_left).setVisibility(View.GONE);
            convertView.findViewById(R.id.fnikaname).setVisibility(View.GONE);
            TextView tv= (TextView) convertView.findViewById(R.id.content_right);
            tv.setText(cc.getMsg());
            tvr = (TextView) convertView.findViewById(R.id.rnikaname);
            tvr.setText(cc.getU_nickName());
        }
        else{
            convertView.findViewById(R.id.layout_right).setVisibility(View.GONE);
            convertView.findViewById(R.id.rnikaname).setVisibility(View.GONE);
            TextView tv= (TextView) convertView.findViewById(R.id.content_left);
            tv.setText(cc.getMsg());
            tvf = (TextView) convertView.findViewById(R.id.fnikaname);
            tvf.setText(cc.getU_nickName());
        }
        return convertView;
    }
}
