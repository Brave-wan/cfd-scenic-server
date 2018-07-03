package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.JsonBean.MyBalancejson;
import com.cfd.business.R;

import java.util.List;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class Adapter_MerchantBalance extends BaseAdapter{
    Context mContext;
    List<MyBalancejson.DataBean.TradeLogListBean> tradeLogList;
    public Adapter_MerchantBalance(Context mContext, List<MyBalancejson.DataBean.TradeLogListBean> tradeLogList) {
        this.mContext = mContext;
        this.tradeLogList=tradeLogList;
    }

    @Override
    public int getCount() {
        return tradeLogList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_szmx,null);
            cyzMode.tv_details= (TextView) convertView.findViewById(R.id.tv_details);
            cyzMode.tv_jine= (TextView) convertView.findViewById(R.id.tv_jine);
            cyzMode.tv_balance= (TextView) convertView.findViewById(R.id.tv_balance);
            cyzMode.tv_Date= (TextView) convertView.findViewById(R.id.tv_Date);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }
        //支付类型（0(充值，1提现记录) 2 购买商品3积分购买4退款

        if (tradeLogList.get(position).getType()==0){
            cyzMode.tv_details.setText("充值");
            cyzMode.tv_jine.setText("+"+tradeLogList.get(position).getPrice());
        }else  if (tradeLogList.get(position).getType()==1){
            cyzMode.tv_details.setText("提现");
            cyzMode.tv_jine.setText("-"+tradeLogList.get(position).getPrice());
        }else  if (tradeLogList.get(position).getType()==2){
            cyzMode.tv_details.setText("收到"+tradeLogList.get(position).getNick_name()+"的付款");
            cyzMode.tv_jine.setText("+"+tradeLogList.get(position).getPrice());
        }else  if (tradeLogList.get(position).getType()==3){
            cyzMode.tv_details.setText("收到"+tradeLogList.get(position).getNick_name()+"的付款");
            cyzMode.tv_jine.setText("+"+tradeLogList.get(position).getPrice());
        }else  if (tradeLogList.get(position).getType()==4){
            cyzMode.tv_details.setText("退款给"+tradeLogList.get(position).getNick_name());
            cyzMode.tv_jine.setText("-"+tradeLogList.get(position).getPrice());
        }


        cyzMode.tv_balance.setText("余额:"+tradeLogList.get(position).getBalance()+"");
        cyzMode.tv_Date.setText(tradeLogList.get(position).getCreateTime());

        return convertView;
    }

    private class CyzMode{
        TextView tv_details;
        TextView tv_jine;
        TextView tv_balance;
        TextView tv_Date;
    }
}
