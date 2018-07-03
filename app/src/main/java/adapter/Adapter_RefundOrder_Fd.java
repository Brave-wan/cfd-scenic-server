package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.JsonBean.FdRefundOrderBean;
import com.cfd.business.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import activity.Activity_RefundDetails_Sp;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class Adapter_RefundOrder_Fd extends BaseAdapter {
    Context mContext;
    List<List<FdRefundOrderBean.DataBean>> list;

    public Adapter_RefundOrder_Fd(Context mContext, List<List<FdRefundOrderBean.DataBean>> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            convertView=LayoutInflater.from(mContext).inflate(R.layout.adapter_refundorder_fd,null);
            cyzMode.tv_daishenhe= (TextView) convertView.findViewById(R.id.tv_daishenhe);
            cyzMode.tv_yituikuan= (TextView) convertView.findViewById(R.id.tv_yituikuan);

            cyzMode.iv_touxiang= (ImageView) convertView.findViewById(R.id.iv_touxiang);
            cyzMode.imageView= (ImageView) convertView.findViewById(R.id.imageView);
            cyzMode.tv_dingdanhao= (TextView) convertView.findViewById(R.id.tv_dingdanhao);
            cyzMode.tv_userName= (TextView) convertView.findViewById(R.id.tv_userName);
            cyzMode.tv_commodityName= (TextView) convertView.findViewById(R.id.tv_commodityName);
            cyzMode.tv_jiucanshijian= (TextView) convertView.findViewById(R.id.tv_jiucanshijian);
            cyzMode.tv_shuliang= (TextView) convertView.findViewById(R.id.tv_shuliang);
            cyzMode.tv_zong= (TextView) convertView.findViewById(R.id.tv_zong);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        cyzMode.tv_daishenhe.setVisibility(View.GONE);
        cyzMode.tv_yituikuan.setVisibility(View.GONE);

        if (list.get(position).get(0).getOrder_state()==3){
            cyzMode.tv_daishenhe.setVisibility(View.VISIBLE);
        }else if (list.get(position).get(0).getOrder_state()==6){
            cyzMode.tv_yituikuan.setVisibility(View.VISIBLE);
        }

        ImageLoader.getInstance().displayImage(list.get(position).get(0).getDescribe_img(),cyzMode.imageView);
        ImageLoader.getInstance().displayImage(list.get(position).get(0).getHead_img(),cyzMode.iv_touxiang);
        cyzMode.tv_dingdanhao.setText(list.get(position).get(0).getOrder_code() + "");
        cyzMode.tv_userName.setText(list.get(position).get(0).getNick_name());
        cyzMode.tv_commodityName.setText(list.get(position).get(0).getGoods_name());
        cyzMode.tv_jiucanshijian.setText(list.get(position).get(0).getEat_date());
        cyzMode.tv_shuliang.setText("数量："+list.get(position).get(0).getQuantity());
        cyzMode.tv_zong.setText("¥"+list.get(position).get(0).getReal_price());


        return convertView;
    }

    private class CyzMode{
        TextView tv_daishenhe;
        TextView tv_yituikuan;
        TextView tv_dingdanhao;
        ImageView iv_touxiang;
        TextView tv_userName;
        ImageView imageView;
        TextView tv_commodityName;
        TextView tv_jiucanshijian;
        TextView tv_shuliang;
        TextView tv_zong;
    }

    private class SetOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){

            }
        }
    }
}
