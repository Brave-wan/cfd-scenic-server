package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.JsonBean.RefundOrderJdBean;
import com.cfd.business.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class Adapter_RefundOrder_Jd extends BaseAdapter {
    Context mContext;
    List<RefundOrderJdBean.DataBean> list;

    public Adapter_RefundOrder_Jd(Context mContext, List<RefundOrderJdBean.DataBean> list) {
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
            convertView=LayoutInflater.from(mContext).inflate(R.layout.adapter_refundorder_jd,null);
            cyzMode.tv_daishenhe= (TextView) convertView.findViewById(R.id.tv_daishenhe);
            cyzMode.tv_yituikuan= (TextView) convertView.findViewById(R.id.tv_yituikuan);
            cyzMode.tv_OrderNumber= (TextView) convertView.findViewById(R.id.tv_OrderNumber);
            cyzMode.iv_touxiang= (ImageView) convertView.findViewById(R.id.iv_touxiang);
            cyzMode.tv_userName= (TextView) convertView.findViewById(R.id.tv_userName);
            cyzMode.imageView= (ImageView) convertView.findViewById(R.id.imageView);
            cyzMode.tv_commodityName= (TextView) convertView.findViewById(R.id.tv_commodityName);
            cyzMode.tv_fangjianshu= (TextView) convertView.findViewById(R.id.tv_fangjianshu);
            cyzMode.tv_ruzhu= (TextView) convertView.findViewById(R.id.tv_ruzhu);
            cyzMode.tv_lidian= (TextView) convertView.findViewById(R.id.tv_lidian);
            cyzMode.tv_zong= (TextView) convertView.findViewById(R.id.tv_zong);
            cyzMode.tv_jiwan= (TextView) convertView.findViewById(R.id.tv_jiwan);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        cyzMode.tv_OrderNumber.setText("订单号：" + list.get(position).getOrder_code());
        ImageLoader.getInstance().displayImage(list.get(position).getHead_img(), cyzMode.iv_touxiang);

        ImageLoader.getInstance().displayImage(list.get(position).getDescribe_img(), cyzMode.imageView);
        cyzMode.tv_commodityName.setText(list.get(position).getName());
        cyzMode.tv_fangjianshu.setText("房间数："+list.get(position).getQuantity()+"间");
        cyzMode.tv_ruzhu.setText("入住："+list.get(position).getStart_date());
        cyzMode.tv_lidian.setText("离店："+list.get(position).getEnd_date());
        cyzMode.tv_jiwan.setText(list.get(position).getCheck_days()+"晚");
        cyzMode.tv_zong.setText("￥"+list.get(position).getReal_price());
        cyzMode.tv_userName.setText(list.get(position).getNick_name());

        if (list.get(position).getOrder_state()==3){
            cyzMode.tv_daishenhe.setVisibility(View.VISIBLE);
        }else if (list.get(position).getOrder_state()==6){
            cyzMode.tv_yituikuan.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private class CyzMode{
        TextView tv_OrderNumber;
        ImageView iv_touxiang;
        TextView tv_userName;
        ImageView imageView;
        TextView tv_commodityName;
        TextView tv_fangjianshu;
        TextView tv_ruzhu;
        TextView tv_lidian;
        TextView tv_jiwan;
        TextView tv_zong;


        TextView tv_daishenhe;
        TextView tv_yituikuan;
    }

    private class SetOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){

            }
        }
    }
}
