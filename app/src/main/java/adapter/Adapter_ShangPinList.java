package adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.JsonBean.ShopOrderListSPBean;
import com.cfd.business.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class Adapter_ShangPinList extends BaseAdapter {
    Context mContext;
    List<ShopOrderListSPBean.DataBean.OrderListBean.RowsBean> list;

    public Adapter_ShangPinList(Context mContext, List<ShopOrderListSPBean.DataBean.OrderListBean.RowsBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_shangpin_zhong, null);
            cyzMode.ll_state= (LinearLayout) convertView.findViewById(R.id.ll_state);
            cyzMode.ll_details= (LinearLayout) convertView.findViewById(R.id.ll_details);
            cyzMode.imageView= (ImageView) convertView.findViewById(R.id.imageView);
            cyzMode.tv_Sname= (TextView) convertView.findViewById(R.id.tv_Sname);
            cyzMode.tv_goods_price= (TextView) convertView.findViewById(R.id.tv_goods_price);
            cyzMode.tv_price= (TextView) convertView.findViewById(R.id.tv_price);
            cyzMode.tv_deliver_fee= (TextView) convertView.findViewById(R.id.tv_deliver_fee);
            cyzMode.tv_real_price= (TextView) convertView.findViewById(R.id.tv_real_price);
            cyzMode.tv_quantity= (TextView) convertView.findViewById(R.id.tv_quantity);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }


        ImageLoader.getInstance().displayImage(list.get(position).getDescribe_img(), cyzMode.imageView);
        cyzMode.tv_Sname.setText(list.get(position).getName());
        cyzMode.tv_goods_price.setText("￥" + list.get(position).getPrice());
        cyzMode.tv_price.setText("￥"+list.get(position).getGoods_price());
        cyzMode.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        cyzMode.tv_deliver_fee.setText("配送费:￥"+list.get(position).getDeliver_fee());
        cyzMode.tv_real_price.setText("￥"+list.get(position).getReal_price());
        cyzMode.tv_quantity.setText("X"+list.get(position).getQuantity());

        return convertView;
    }

    private class CyzMode{
        LinearLayout ll_state;
        LinearLayout ll_details;
        ImageView imageView;
        TextView tv_Sname;
        TextView tv_goods_price;
        TextView tv_price;
        TextView tv_deliver_fee;
        TextView tv_real_price;
        TextView tv_quantity;
    }
}
