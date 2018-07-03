package adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
import com.JsonBean.OrderCountSpBean;
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
import activity.Activity_Statistics;
import fragment.Fragment_ShangPin;
import fragment.Fragment_SpInside;
import util.SpUtil;
import util.ToastUtil;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class Adapter_Statistics_Sp extends BaseExpandableListAdapter {
    Context mContext;
    List<List<OrderCountSpBean.DataBean.OrderListBean>> list;
    Activity_Statistics activity;

    public Adapter_Statistics_Sp(Context mContext, List<List<OrderCountSpBean.DataBean.OrderListBean>> list) {
        this.mContext = mContext;
        this.list = list;
        activity= (Activity_Statistics) mContext;
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

            cyzMode.bt_fahuo = (TextView) convertView.findViewById(R.id.bt_fahuo);
            cyzMode.bt_yiwancheng = (TextView) convertView.findViewById(R.id.bt_yiwancheng);
            cyzMode.bt_yifahuo = (TextView) convertView.findViewById(R.id.bt_yifahuo);
            cyzMode.tv_bohui = (TextView) convertView.findViewById(R.id.tv_bohui);
            cyzMode.tv_tongguo = (TextView) convertView.findViewById(R.id.tv_tongguo);
            cyzMode.tv_tuikuan = (TextView) convertView.findViewById(R.id.tv_tuikuan);
            cyzMode.tv_yibohui = (TextView) convertView.findViewById(R.id.tv_yibohui);
            cyzMode.tv_yituikuan = (TextView) convertView.findViewById(R.id.tv_yituikuan);
            cyzMode.tv_querenshouhuo= (TextView) convertView.findViewById(R.id.tv_querenshouhuo);
            cyzMode.tv_daifahuo= (TextView) convertView.findViewById(R.id.tv_daifahuo);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        initView(cyzMode);
        if (list.get(groupPosition).get(childPosition).getIs_deliver_fee()==1){
            cyzMode.ll_details.setVisibility(View.GONE);
        }else {
            cyzMode.ll_details.setVisibility(View.VISIBLE);
        }


        ImageLoader.getInstance().displayImage(list.get(groupPosition).get(childPosition).getDescribe_img(), cyzMode.imageView);
        cyzMode.tv_Sname.setText(list.get(groupPosition).get(childPosition).getGoods_name());
        cyzMode.tv_goods_price.setText("￥" + list.get(groupPosition).get(childPosition).getGoods_price());
        cyzMode.tv_price.setText("￥"+list.get(groupPosition).get(childPosition).getPrice());
        cyzMode.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        cyzMode.tv_deliver_fee.setText("配送费:￥"+list.get(groupPosition).get(childPosition).getDeliver_fee());
        cyzMode.tv_real_price.setText("￥"+list.get(groupPosition).get(childPosition).getReal_price());
        cyzMode.tv_quantity.setText("X"+list.get(groupPosition).get(childPosition).getQuantity());

        //状态值判断
        if (list.get(groupPosition).get(childPosition).getOrder_state() == 2) {
            cyzMode.bt_fahuo.setVisibility(View.VISIBLE);
        } else if (list.get(groupPosition).get(childPosition).getOrder_state() == 3) {
            cyzMode.bt_yifahuo.setVisibility(View.VISIBLE);
        } else if (list.get(groupPosition).get(childPosition).getOrder_state() == 4) {
            cyzMode.bt_yifahuo.setVisibility(View.VISIBLE);
        } else if (list.get(groupPosition).get(childPosition).getOrder_state() == 5) {
            cyzMode.bt_yiwancheng.setVisibility(View.VISIBLE);
        } else if (list.get(groupPosition).get(childPosition).getOrder_state() == 6) {
            cyzMode.tv_bohui.setVisibility(View.VISIBLE);
            cyzMode.tv_tongguo.setVisibility(View.VISIBLE);
        } else if (list.get(groupPosition).get(childPosition).getOrder_state() == 7) {
            cyzMode.tv_daifahuo.setVisibility(View.VISIBLE);
        } else if (list.get(groupPosition).get(childPosition).getOrder_state() == 8) {
            cyzMode.tv_tuikuan.setVisibility(View.VISIBLE);
        } else if (list.get(groupPosition).get(childPosition).getOrder_state() == 9) {
            cyzMode.tv_yituikuan.setVisibility(View.VISIBLE);
        } else if (list.get(groupPosition).get(childPosition).getOrder_state() == 10) {
            cyzMode.tv_yibohui.setVisibility(View.VISIBLE);
        }else if (list.get(groupPosition).get(childPosition).getOrder_state()==11){
            cyzMode.tv_querenshouhuo.setVisibility(View.VISIBLE);
        }

        if (childPosition==getChildrenCount(groupPosition)-1){
            cyzMode.ll_state.setVisibility(View.VISIBLE);
        }else {
            cyzMode.ll_state.setVisibility(View.GONE);
        }


        /*cyzMode.bt_fahuo.setOnClickListener(new View.OnClickListener() {
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
        });*/

        cyzMode.ll_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, Activity_OrderDetails_Sp.class);
                intent.putExtra("orderCode", list.get(groupPosition).get(childPosition).getOrder_code()+"");
                mContext.startActivity(intent);
            }
        });


        cyzMode.bt_fahuo.setOnClickListener(new SetOnClick(groupPosition, childPosition));
        cyzMode.tv_bohui.setOnClickListener(new SetOnClick(groupPosition, childPosition));
        cyzMode.tv_tongguo.setOnClickListener(new SetOnClick(groupPosition, childPosition));
        cyzMode.tv_tuikuan.setOnClickListener(new SetOnClick(groupPosition, childPosition));
        cyzMode.tv_querenshouhuo.setOnClickListener(new SetOnClick(groupPosition, childPosition));
        return convertView;
    }

    private class SetOnClick implements View.OnClickListener {
        int groupPosition;
        int childPosition;

        public SetOnClick(int groupPosition, int childPosition) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_fahuo:
                    if (list.get(groupPosition).get(childPosition).getIs_pickup() == 1) {//1是自提
                        siBackMoney(groupPosition, childPosition);
                    } else {
                        Intent intent = new Intent(mContext, Activity_Logistics.class);
                        intent.putExtra("orderCode", list.get(groupPosition).get(childPosition).getOrder_code() + "");
                        intent.putExtra("siId", list.get(groupPosition).get(childPosition).getId() + "");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.tv_tuikuan:
                    final Dialog dialog = new Dialog(mContext, R.style.AlertDialogStyle);
                    dialog.getWindow().setContentView(R.layout.dialog_cancel_order);
                    dialog.setCanceledOnTouchOutside(false);
                    TextView text = (TextView) dialog.getWindow().findViewById(R.id.text_cnacel_order);
                    text.setText("确定退款给顾客？");
                    dialog.show();

                    dialog.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            shopRefundFinsh(groupPosition,childPosition);
                            dialog.dismiss();
                        }
                    });
                    dialog.getWindow().findViewById(R.id.ll_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //取消
                            dialog.dismiss();
                        }
                    });
                    break;
                case R.id.tv_tongguo:
                    final Dialog dialog1 = new Dialog(mContext, R.style.AlertDialogStyle);
                    dialog1.getWindow().setContentView(R.layout.dialog_cancel_order);
                    dialog1.setCanceledOnTouchOutside(false);
                    TextView text1 = (TextView) dialog1.getWindow().findViewById(R.id.text_cnacel_order);
                    text1.setText("审核通过后，顾客将把商品退换给您？");
                    dialog1.show();

                    dialog1.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            updateGoodsOrder("7", groupPosition, childPosition);
                            dialog1.dismiss();
                        }
                    });
                    dialog1.getWindow().findViewById(R.id.ll_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //取消
                            dialog1.dismiss();
                        }
                    });
                    break;
                case R.id.tv_querenshouhuo:
                    final Dialog dialog3 = new Dialog(mContext, R.style.AlertDialogStyle);
                    dialog3.getWindow().setContentView(R.layout.dialog_cancel_order);
                    dialog3.setCanceledOnTouchOutside(false);
                    TextView text3 = (TextView) dialog3.getWindow().findViewById(R.id.text_cnacel_order);
                    text3.setText("确认收货？");
                    dialog3.show();

                    dialog3.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            updateGoodsOrder("8", groupPosition, childPosition);
                            dialog3.dismiss();
                        }
                    });
                    dialog3.getWindow().findViewById(R.id.ll_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //取消
                            dialog3.dismiss();
                        }
                    });
                    break;
                case R.id.tv_bohui:
                    final Dialog dialog2 = new Dialog(mContext, R.style.AlertDialogStyle);
                    dialog2.getWindow().setContentView(R.layout.dialog_cancel_order);
                    dialog2.setCanceledOnTouchOutside(false);
                    TextView text2 = (TextView) dialog2.getWindow().findViewById(R.id.text_cnacel_order);
                    text2.setText("确定驳回顾客的申请？");
                    dialog2.show();

                    dialog2.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            updateGoodsOrder("10",groupPosition,childPosition);
                            dialog2.dismiss();
                        }
                    });
                    dialog2.getWindow().findViewById(R.id.ll_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //取消
                            dialog2.dismiss();
                        }
                    });
                    break;

            }
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void updateGoodsOrder(String state,int groupPosition, int childPosition){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpUtil.token, ""));
        params.addQueryStringParameter("orderState", state);
        AuthenticationJson authenticationJson=new Gson().fromJson(SpUtil.getString(mContext,"authenticationJson",""),AuthenticationJson.class);
        params.addQueryStringParameter("shopInformationId", authenticationJson.getId() + "");
        params.addQueryStringParameter("orderCode", list.get(groupPosition).get(childPosition).getOrder_code() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.updateGoodsOrder, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                activity.orderCount();
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


    //确认退款
    public void shopRefundFinsh(int groupPosition, int childPosition){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpUtil.token, ""));
        AuthenticationJson authenticationJson=new Gson().fromJson(SpUtil.getString(mContext,"authenticationJson",""),AuthenticationJson.class);
        params.addQueryStringParameter("type", 3 + "");
        params.addQueryStringParameter("shopUserId",SpUtil.getLong(mContext, SpUtil.userId, 1)+"" );
        params.addQueryStringParameter("useId", list.get(groupPosition).get(childPosition).getUserId() + "");
        params.addQueryStringParameter("balance",list.get(groupPosition).get(childPosition).getReal_price()+"");
        params.addQueryStringParameter("siId",authenticationJson.getId() + "");
        params.addQueryStringParameter("orderCode",list.get(groupPosition).get(childPosition).getOrder_code());
        params.addQueryStringParameter("orderState",9+"");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.shopRefundFinsh, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                activity.orderCount();
                            } else {
                                ToastUtil.show(mContext, header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(mContext,"解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(mContext, s);
                    }
                });
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
                                activity.orderCount();
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
        cyzMode.tv_querenshouhuo.setVisibility(View.GONE);
        cyzMode.tv_daifahuo.setVisibility(View.GONE);
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
        TextView tv_bohui;
        TextView tv_tongguo;
        TextView tv_tuikuan;
        TextView tv_yibohui;
        TextView tv_yituikuan;
        TextView tv_querenshouhuo;
        TextView tv_daifahuo;
    }
}
