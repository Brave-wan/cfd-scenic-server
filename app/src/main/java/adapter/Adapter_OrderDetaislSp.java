package adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.JsonBean.OrderDetailSpBean;
import com.cfd.business.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class Adapter_OrderDetaislSp extends BaseAdapter {
    Context mContext;
    List<OrderDetailSpBean.DataBean.MapBean> list;

    public Adapter_OrderDetaislSp(Context mContext, List<OrderDetailSpBean.DataBean.MapBean> list) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_order_details_sp,null);

            cyzMode.ll_all= (LinearLayout) convertView.findViewById(R.id.ll_all);
            cyzMode.iv_xiangyoujt= (ImageView) convertView.findViewById(R.id.iv_xiangyoujt);

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

        cyzMode.iv_xiangyoujt.setVisibility(View.INVISIBLE);

        if (list.get(position).getIs_deliver_fee()==1){
            cyzMode.ll_all.setVisibility(View.GONE);
            return convertView;
        }else {
            cyzMode.ll_all.setVisibility(View.VISIBLE);
        }

        ImageLoader.getInstance().displayImage(list.get(position).getDescribe_img(), cyzMode.imageView);
        cyzMode.tv_Sname.setText(list.get(position).getInformationName());
        cyzMode.tv_goods_price.setText("￥" + list.get(position).getNewPrice());
        cyzMode.tv_price.setText("￥" + list.get(position).getPrice());
        cyzMode.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        cyzMode.tv_deliver_fee.setText("配送费:￥"+list.get(position).getDeliver_fee());
        cyzMode.tv_real_price.setText("￥"+list.get(position).getReal_price());
        cyzMode.tv_quantity.setText("X"+list.get(position).getQuantity());
        return convertView;
    }

    private class CyzMode{
        LinearLayout ll_all;
        ImageView iv_xiangyoujt;

        ImageView imageView;
        TextView tv_Sname;
        TextView tv_goods_price;
        TextView tv_price;
        TextView tv_deliver_fee;
        TextView tv_real_price;
        TextView tv_quantity;

    }
}
