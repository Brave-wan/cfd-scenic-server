package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.JsonBean.OrderDetailFdDpBean;
import com.cfd.business.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12 0012.
 */
public class Adapter_Details_FdDp extends BaseAdapter {
    Context mContext;
    List<OrderDetailFdDpBean.DataBean> list;

    public Adapter_Details_FdDp(Context mContext, List<OrderDetailFdDpBean.DataBean> list) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_details_fddp,null);
            cyzMode.imageView= (ImageView) convertView.findViewById(R.id.imageView);
            cyzMode.tv_commodityName= (TextView) convertView.findViewById(R.id.tv_commodityName);
            cyzMode.tv_jiucanshijian= (TextView) convertView.findViewById(R.id.tv_jiucanshijian);
            cyzMode.tv_shuliang= (TextView) convertView.findViewById(R.id.tv_shuliang);
            cyzMode.tv_zong= (TextView) convertView.findViewById(R.id.tv_zong);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(list.get(position).getDescribe_img(), cyzMode.imageView);
        cyzMode.tv_commodityName.setText(list.get(position).getName());
        cyzMode.tv_jiucanshijian.setText(list.get(position).getEat_date());
        cyzMode.tv_shuliang.setText("数量："+list.get(position).getQuantity());
        cyzMode.tv_zong.setText("¥"+list.get(position).getReal_price());

        return convertView;
    }

    private class CyzMode{
        ImageView imageView;
        TextView tv_commodityName;
        TextView tv_jiucanshijian;
        TextView tv_shuliang;
        TextView tv_zong;
    }
}
