package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
import com.application.URL;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;
import com.JsonBean.ShopOrderListSPBean;
import com.cfd.business.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import activity.Activity_Logistics;
import activity.Activity_OrderDetails_Sp;
import fragment.Fragment_ShangPin;
import fragment.Fragment_SpInside;
import util.SpUtil;
import util.ToastUtil;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class Adapter_ShangPinEL extends BaseExpandableListAdapter {
    Context mContext;
    List<List<ShopOrderListSPBean.DataBean.OrderListBean.RowsBean>> list;

    public Adapter_ShangPinEL(Context mContext, List<List<ShopOrderListSPBean.DataBean.OrderListBean.RowsBean>> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_shangpin_shang,null);
            cyzMode.iv_touxiang= (ImageView) convertView.findViewById(R.id.iv_touxiang);
            cyzMode.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            cyzMode.tv_OrderNumber= (TextView) convertView.findViewById(R.id.tv_OrderNumber);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(list.get(groupPosition).get(0).getHead_img(), cyzMode.iv_touxiang);
        cyzMode.tv_OrderNumber.setText("订单号:" + list.get(groupPosition).get(0).getOrder_code());
        cyzMode.tv_name.setText(list.get(groupPosition).get(0).getNick_name());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_shangpin_xia,null);
            cyzMode.ll_state= (LinearLayout) convertView.findViewById(R.id.ll_state);
            cyzMode.ll_details= (LinearLayout) convertView.findViewById(R.id.ll_details);
            cyzMode.imageView= (ImageView) convertView.findViewById(R.id.imageView);
            cyzMode.tv_Sname= (TextView) convertView.findViewById(R.id.tv_Sname);
            cyzMode.tv_goods_price= (TextView) convertView.findViewById(R.id.tv_goods_price);
            cyzMode.tv_price= (TextView) convertView.findViewById(R.id.tv_price);
            cyzMode.tv_deliver_fee= (TextView) convertView.findViewById(R.id.tv_deliver_fee);
            cyzMode.tv_real_price= (TextView) convertView.findViewById(R.id.tv_real_price);
            cyzMode.tv_quantity= (TextView) convertView.findViewById(R.id.tv_quantity);

            cyzMode.bt_fahuo= (TextView) convertView.findViewById(R.id.bt_fahuo);
            cyzMode.bt_yiwancheng= (TextView) convertView.findViewById(R.id.bt_yiwancheng);
            cyzMode.bt_yifahuo= (TextView) convertView.findViewById(R.id.bt_yifahuo);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        initView(cyzMode);

        ImageLoader.getInstance().displayImage(list.get(groupPosition).get(childPosition).getDescribe_img(), cyzMode.imageView);
        cyzMode.tv_Sname.setText(list.get(groupPosition).get(childPosition).getName());
        cyzMode.tv_goods_price.setText("￥" + list.get(groupPosition).get(childPosition).getGoods_price());
        cyzMode.tv_price.setText("￥"+list.get(groupPosition).get(childPosition).getPrice());
        cyzMode.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        cyzMode.tv_deliver_fee.setText("配送费:￥"+list.get(groupPosition).get(childPosition).getDeliver_fee());
        cyzMode.tv_real_price.setText("￥"+list.get(groupPosition).get(childPosition).getReal_price());
        cyzMode.tv_quantity.setText("X"+list.get(groupPosition).get(childPosition).getQuantity());

        //状态值判断
        if (list.get(groupPosition).get(childPosition).getOrder_state()==2){
            cyzMode.bt_fahuo.setVisibility(View.VISIBLE);
        }else if (list.get(groupPosition).get(childPosition).getOrder_state()==3){
            cyzMode.bt_yifahuo.setVisibility(View.VISIBLE);
        }else if (list.get(groupPosition).get(childPosition).getOrder_state()==4){
            cyzMode.bt_yifahuo.setVisibility(View.VISIBLE);
        }else if (list.get(groupPosition).get(childPosition).getOrder_state()==5){
            cyzMode.bt_yiwancheng.setVisibility(View.VISIBLE);
        }

        if (childPosition==getChildrenCount(groupPosition)-1){
            cyzMode.ll_state.setVisibility(View.VISIBLE);
        }else {
            cyzMode.ll_state.setVisibility(View.GONE);
        }

        cyzMode.bt_fahuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(groupPosition).get(childPosition).getIs_pickup() == 1) {//1是自提
                    siBackMoney(groupPosition,childPosition);
                } else {
                    Intent intent=new Intent(mContext,Activity_Logistics.class);
                    intent.putExtra("orderCode",list.get(groupPosition).get(childPosition).getOrder_code()+"");
                    intent.putExtra("siId",list.get(groupPosition).get(childPosition).getId()+"");
                    mContext.startActivity(intent);
                }
            }
        });

        cyzMode.ll_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, Activity_OrderDetails_Sp.class);
                intent.putExtra("orderCode", list.get(groupPosition).get(childPosition).getOrder_code()+"");
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    private void siBackMoney(int groupPosition, int childPosition){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpUtil.token, ""));
        params.addQueryStringParameter("type", 3 + "");
        params.addQueryStringParameter("orderState","3");
        AuthenticationJson authenticationJson=new Gson().fromJson(SpUtil.getString(mContext,"authenticationJson",""),AuthenticationJson.class);
        params.addQueryStringParameter("siId",authenticationJson.getId()+"");
        params.addQueryStringParameter("orderCode", list.get(groupPosition).get(childPosition).getOrder_code() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.siBackMoney, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject=new JSONObject(responseInfo.result);
                            JSONObject header=jsonObject.getJSONObject("header");
                            int i=header.getInt("status");
                            if(i==0){
                                ToastUtil.show(mContext,"确认完成");
                                Fragment_SpInside fragment_spInside1= (Fragment_SpInside) Fragment_ShangPin.sp_1;
                                fragment_spInside1.shopOrderList();
                                if (Fragment_ShangPin.sp_2!=null){
                                    Fragment_SpInside fragment_spInside2= (Fragment_SpInside) Fragment_ShangPin.sp_2;
                                    fragment_spInside2.shopOrderList();
                                }

                            }else {
                                ToastUtil.show(mContext, header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(mContext, "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(mContext, s);
                    }
                });
    }






    private void initView(CyzMode cyzMode){
        cyzMode.bt_fahuo.setVisibility(View.GONE);
        cyzMode.bt_yiwancheng.setVisibility(View.GONE);
        cyzMode.bt_yifahuo.setVisibility(View.GONE);
    }

    private class CyzMode{
        TextView tv_OrderNumber;
        ImageView iv_touxiang;
        TextView tv_name;

        LinearLayout ll_state;
        LinearLayout ll_details;
        ImageView imageView;
        TextView tv_Sname;
        TextView tv_goods_price;
        TextView tv_price;
        TextView tv_deliver_fee;
        TextView tv_real_price;
        TextView tv_quantity;

        TextView bt_fahuo;
        TextView bt_yiwancheng;
        TextView bt_yifahuo;
    }
}
