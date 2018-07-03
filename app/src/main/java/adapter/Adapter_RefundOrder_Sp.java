package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cfd.business.R;

import activity.Activity_RefundDetails_Sp;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class Adapter_RefundOrder_Sp extends BaseAdapter {
    Context mContext;
    int i;

    public Adapter_RefundOrder_Sp(Context mContext, int i) {
        this.mContext = mContext;
        this.i = i;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView=LayoutInflater.from(mContext).inflate(R.layout.adapter_refundorder_sp,null);
            cyzMode.tv_yituikuan= (TextView) convertView.findViewById(R.id.tv_yituikuan);
            cyzMode.tv_tuikuan= (TextView) convertView.findViewById(R.id.tv_tuikuan);
            cyzMode.tv_tongguo= (TextView) convertView.findViewById(R.id.tv_tongguo);
            cyzMode.tv_yibohui= (TextView) convertView.findViewById(R.id.tv_yibohui);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        if (i==1){
            cyzMode.tv_tongguo.setVisibility(View.VISIBLE);
            cyzMode.tv_yibohui.setVisibility(View.GONE);
            cyzMode.tv_tuikuan.setVisibility(View.GONE);
            cyzMode.tv_yituikuan.setVisibility(View.GONE);
        }else if (i==2){
            cyzMode.tv_tuikuan.setVisibility(View.VISIBLE);
            cyzMode.tv_tongguo.setVisibility(View.GONE);
            cyzMode.tv_yibohui.setVisibility(View.GONE);
            cyzMode.tv_yituikuan.setVisibility(View.GONE);
        }if (i==3){
            cyzMode.tv_yituikuan.setVisibility(View.VISIBLE);
            cyzMode.tv_tuikuan.setVisibility(View.GONE);
            cyzMode.tv_yibohui.setVisibility(View.GONE);
            cyzMode.tv_tongguo.setVisibility(View.GONE);
        }

        cyzMode.tv_tongguo.setOnClickListener(new SetOnClick());
        return convertView;
    }

    private class CyzMode{
        TextView tv_tongguo;
        TextView tv_tuikuan;
        TextView tv_yituikuan;
        TextView tv_yibohui;
    }

    private class SetOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_tongguo:
                    Intent intent=new Intent(mContext, Activity_RefundDetails_Sp.class);
                    mContext.startActivity(intent);
                break;
            }
        }
    }
}
