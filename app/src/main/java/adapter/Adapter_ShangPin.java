package adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
import com.JsonBean.ShopOrderListSPBean;
import com.application.URL;
import com.cfd.business.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.util.List;

import activity.Activity_Logistics;
import activity.Activity_OrderDetails_Sp;
import fragment.Fragment_ShangPin;
import fragment.Fragment_SpInside;
import util.SpUtil;
import util.ToastUtil;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class Adapter_ShangPin  extends BaseAdapter {
    Context mContext;
    List<List<ShopOrderListSPBean.DataBean.OrderListBean.RowsBean>> list;

    public Adapter_ShangPin(Context mContext, List<List<ShopOrderListSPBean.DataBean.OrderListBean.RowsBean>> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_shangpin, null);
            cyzMode.ll_details= (LinearLayout) convertView.findViewById(R.id.ll_details);
            cyzMode.iv_touxiang= (ImageView) convertView.findViewById(R.id.iv_touxiang);
            cyzMode.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            cyzMode.tv_OrderNumber= (TextView) convertView.findViewById(R.id.tv_OrderNumber);
            cyzMode.lv_listViewSp= (ListView) convertView.findViewById(R.id.lv_listViewSp);
            cyzMode.bt_fahuo= (TextView) convertView.findViewById(R.id.bt_fahuo);
            cyzMode.bt_yiwancheng= (TextView) convertView.findViewById(R.id.bt_yiwancheng);
            cyzMode.bt_yifahuo= (TextView) convertView.findViewById(R.id.bt_yifahuo);

            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        cyzMode.lv_listViewSp.setAdapter(new Adapter_ShangPinList(mContext,list.get(position)));

        initView(cyzMode);

        ImageLoader.getInstance().displayImage(list.get(position).get(0).getHead_img(), cyzMode.iv_touxiang);
        cyzMode.tv_OrderNumber.setText("订单号:" + list.get(position).get(0).getOrder_code());
        cyzMode.tv_name.setText(list.get(position).get(0).getNick_name());

        //状态值判断
        if (list.get(position).get(0).getOrder_state()==2){
            cyzMode.bt_fahuo.setVisibility(View.VISIBLE);
        }else if (list.get(position).get(0).getOrder_state()==3){
            cyzMode.bt_yifahuo.setVisibility(View.VISIBLE);
        }else if (list.get(position).get(0).getOrder_state()==4){
            cyzMode.bt_yifahuo.setVisibility(View.VISIBLE);
        }else if (list.get(position).get(0).getOrder_state()==5){
            cyzMode.bt_yiwancheng.setVisibility(View.VISIBLE);
        }

        cyzMode.bt_fahuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).get(0).getIs_pickup() == 1) {//1是自提
                    final Dialog dialog = new Dialog(mContext, R.style.AlertDialogStyle);
                    dialog .getWindow().setContentView(R.layout.dialog_cancel_order);
                    dialog .setCanceledOnTouchOutside(false);
                    dialog .show();
                    TextView text = (TextView) dialog .getWindow().findViewById(R.id.text_cnacel_order);
                    text.setText("确认发货？");

                    dialog .getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            siBackMoney(position,0);
                            dialog .dismiss();
                        }
                    });
                    dialog .getWindow().findViewById(R.id.ll_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //取消
                            dialog.dismiss();
                        }
                    });
                } else {
                    Intent intent=new Intent(mContext, Activity_Logistics.class);
                    intent.putExtra("orderCode",list.get(position).get(0).getOrder_code()+"");
                    intent.putExtra("siId",list.get(position).get(0).getId()+"");
                    mContext.startActivity(intent);
                }
            }
        });

        cyzMode.ll_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, Activity_OrderDetails_Sp.class);
                intent.putExtra("orderCode", list.get(position).get(0).getOrder_code()+"");
                mContext.startActivity(intent);
            }
        });

        cyzMode.lv_listViewSp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int cposition, long id) {
                Intent intent = new Intent();
                intent.setClass(mContext, Activity_OrderDetails_Sp.class);
                intent.putExtra("orderCode", list.get(position).get(0).getOrder_code()+"");
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }


    private void siBackMoney(int groupPosition, int childPosition){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpUtil.token, ""));
        params.addQueryStringParameter("type", 3 + "");
        params.addQueryStringParameter("orderState", "3");
        AuthenticationJson authenticationJson=new Gson().fromJson(SpUtil.getString(mContext,"authenticationJson",""),AuthenticationJson.class);
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
        params.addQueryStringParameter("orderCode", list.get(groupPosition).get(childPosition).getOrder_code() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.siBackMoney, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                  Fragment_SpInside fragment_spInside1 = (Fragment_SpInside) Fragment_ShangPin.sp_1;
                                fragment_spInside1.shopOrderList();
                                fragment_spInside1.list.clear();
                                if (Fragment_ShangPin.sp_2 != null) {
                                    Fragment_SpInside fragment_spInside2 = (Fragment_SpInside) Fragment_ShangPin.sp_2;
                                    fragment_spInside2.shopOrderList();
                                    fragment_spInside2.list.clear();
                                }

                            } else {
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
        LinearLayout ll_details;
        TextView tv_OrderNumber;
        ImageView iv_touxiang;
        TextView tv_name;

        ListView lv_listViewSp;

        TextView bt_fahuo;
        TextView bt_yiwancheng;
        TextView bt_yifahuo;
    }
}
