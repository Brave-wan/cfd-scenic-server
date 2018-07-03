package activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
import com.JsonBean.OrderDetailSpBean;
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

import adapter.Adapter_OrderDetaislSp;
import butterknife.Bind;
import butterknife.ButterKnife;
import fragment.Fragment_ShangPin;
import fragment.Fragment_SpInside;
import util.SpUtil;
import util.ToastUtil;
import view.DialogProgressbar;
import view.MyListView;
import view.RoundImageView;

/**
 * 订单详情--商品版
 * Created by Administrator on 2016/7/28 0028.
 */
public class Activity_OrderDetails_Sp extends Activity {

    @Bind(R.id.tv_OrderNumber)
    TextView tvOrderNumber;
    @Bind(R.id.iv_user)
    RoundImageView ivUser;
    @Bind(R.id.tv_userName)
    TextView tvUserName;
    @Bind(R.id.lv_myListView)
    MyListView lvMyListView;
    @Bind(R.id.bt_fahuo)
    Button btFahuo;
    @Bind(R.id.bt_yiwancheng)
    Button btYiwancheng;
    @Bind(R.id.bt_yifahuo)
    Button btYifahuo;
    @Bind(R.id.tv_shouhuoren)
    TextView tvShouhuoren;
    @Bind(R.id.tv_dizhi)
    TextView tvDizhi;
    @Bind(R.id.tv_shoujihao)
    TextView tvShoujihao;
    @Bind(R.id.ll_address)
    LinearLayout llAddress;
    @Bind(R.id.tv_chuangjianshijian)
    TextView tvChuangjianshijian;
    @Bind(R.id.tv_fukuanshijian)
    TextView tvFukuanshijian;
    @Bind(R.id.tv_zhifufangshi)
    TextView tvZhifufangshi;
    @Bind(R.id.tv_shuliang2)
    TextView tvShuliang2;
    @Bind(R.id.tv_peisongfangshi)
    TextView tvPeisongfangshi;
    @Bind(R.id.tv_peisong2)
    TextView tvPeisong2;
    @Bind(R.id.tv_zong2)
    TextView tvZong2;
    @Bind(R.id.tv_gongsi)
    TextView tvGongsi;
    @Bind(R.id.tv_bianhao)
    TextView tvBianhao;
    @Bind(R.id.tv_bohui)
    TextView tvBohui;
    @Bind(R.id.tv_tongguo)
    TextView tvTongguo;
    @Bind(R.id.tv_tuikuan)
    TextView tvTuikuan;
    @Bind(R.id.tv_yibohui)
    TextView tvYibohui;
    @Bind(R.id.tv_yituikuan)
    TextView tvYituikuan;
    @Bind(R.id.ll_information)
    LinearLayout llInformation;
    @Bind(R.id.tv_querenshouhuo)
    TextView tvQuerenshouhuo;
    @Bind(R.id.tv_daifahuo)
    TextView tvDaifahuo;

    AuthenticationJson authenticationJson;
    List<OrderDetailSpBean.DataBean.MapBean> list;
    OrderDetailSpBean orderDetailSpBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_sp);
        ButterKnife.bind(this);

        //tvYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        authenticationJson = new Gson().fromJson(SpUtil.getString(this, "authenticationJson", ""), AuthenticationJson.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        informationFindOrderDetail();
    }

    private void informationFindOrderDetail() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        params.addQueryStringParameter("type", 3 + "");
        params.addQueryStringParameter("orderCode", getIntent().getStringExtra("orderCode"));
        params.addQueryStringParameter("informationId", authenticationJson.getId() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.informationFindOrderDetail, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_OrderDetails_Sp.this, R.style.AlertDialogStyle);

                    @Override
                    public void onStart() {
                        super.onStart();
                        dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
                        dialogProgressbar.show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        dialogProgressbar.dismiss();
                        try {
                            orderDetailSpBean = new Gson().fromJson(responseInfo.result, OrderDetailSpBean.class);
                            int i = orderDetailSpBean.getHeader().getStatus();
                            if (i == 0) {
                                tvOrderNumber.setText("订单号:" + orderDetailSpBean.getData().getMap().get(0).getOrder_code());
                                ImageLoader.getInstance().displayImage(orderDetailSpBean.getData().getMap().get(0).getHead_img(), ivUser);
                                tvUserName.setText(orderDetailSpBean.getData().getMap().get(0).getNick_name());

                                list = orderDetailSpBean.getData().getMap();
                                lvMyListView.setAdapter(new Adapter_OrderDetaislSp(getApplicationContext(), list));

                                //获取配送费和总价
                                double fee = 0;
                                double total = 0;
                                for (int index = 0; index < orderDetailSpBean.getData().getMap().size(); index++) {
                                    if (orderDetailSpBean.getData().getMap().get(index).getIs_deliver_fee() == 1) {
                                        fee = orderDetailSpBean.getData().getMap().get(index).getDeliver_fee();
                                        total = orderDetailSpBean.getData().getMap().get(index).getReal_price();
                                        tvZong2.setText("总额：" + total);
                                        tvShuliang2.setText("数量：" + orderDetailSpBean.getData().getMap().get(index).getQuantity());
                                    }
                                }

                                if (list.get(0).getIs_pickup() == 1) {
                                    llAddress.setVisibility(View.GONE);
                                    tvPeisongfangshi.setText("配送方式：自提");
                                    llInformation.setVisibility(View.GONE);
                                    tvPeisong2.setVisibility(View.GONE);
                                } else {
                                    llAddress.setVisibility(View.VISIBLE);
                                    llInformation.setVisibility(View.VISIBLE);
                                    tvGongsi.setText("配送公司：" + orderDetailSpBean.getData().getExpress().getExpress_name());
                                    tvBianhao.setText("运单编号：" + orderDetailSpBean.getData().getExpress().getExpress_code());
                                    tvPeisong2.setText("配送费：" + fee + "");
                                    tvPeisongfangshi.setText("配送方式：送货上门");
                                    tvShouhuoren.setText(orderDetailSpBean.getData().getMap().get(0).getUser_name());
                                    tvShoujihao.setText(orderDetailSpBean.getData().getMap().get(0).getTelphone() + "");
                                    tvDizhi.setText(orderDetailSpBean.getData().getMap().get(0).getPlace_address() + orderDetailSpBean.getData().getMap().get(0).getDetail_address());
                                    if (orderDetailSpBean.getData().getExpress().getExpress_code() != null) {
                                        if (orderDetailSpBean.getData().getExpress().getExpress_code().equals("")) {
                                            llInformation.setVisibility(View.GONE);
                                        }
                                    }else {
                                        llInformation.setVisibility(View.GONE);
                                    }
                                }


                                tvChuangjianshijian.setText("创建时间：" + orderDetailSpBean.getData().getMap().get(0).getCreate_time());
                                tvFukuanshijian.setText("付款时间：" + orderDetailSpBean.getData().getMap().get(0).getCreate_time());
                                if (orderDetailSpBean.getData().getMap().get(0).getPay_way() == 1) {
                                    tvZhifufangshi.setText("支付方式：余额支付");
                                } else if (orderDetailSpBean.getData().getMap().get(0).getPay_way() == 1) {
                                    tvZhifufangshi.setText("支付方式：支付宝支付");
                                } else if (orderDetailSpBean.getData().getMap().get(0).getPay_way() == 1) {
                                    tvZhifufangshi.setText("支付方式：微信支付");
                                }
                               /* for (int index=0;index<orderDetailSpBean.getData().getMap().size();index++){
                                    if (orderDetailSpBean.getData().getMap().get(index).getIs_deliver_fee()==1){

                                    }
                                }*/


                                state(orderDetailSpBean.getData().getMap().get(0).getOrder_state());
                            } else {
                                ToastUtil.show(getApplicationContext(), orderDetailSpBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
                    }
                });
    }

    private void state(int state) {
        btFahuo.setVisibility(View.GONE);
        btYiwancheng.setVisibility(View.GONE);
        btYifahuo.setVisibility(View.GONE);
        btYiwancheng.setVisibility(View.GONE);
        tvBohui.setVisibility(View.GONE);
        tvTongguo.setVisibility(View.GONE);
        tvTuikuan.setVisibility(View.GONE);
        tvYituikuan.setVisibility(View.GONE);
        tvDaifahuo.setVisibility(View.GONE);
        tvQuerenshouhuo.setVisibility(View.GONE);
        if (state == 2) {
            btFahuo.setVisibility(View.VISIBLE);
            tvZhifufangshi.setVisibility(View.GONE);
        } else if (state == 3) {
            btYifahuo.setVisibility(View.VISIBLE);
        } else if (state == 4) {
            btYifahuo.setVisibility(View.VISIBLE);
        } else if (state == 5) {
            btYiwancheng.setVisibility(View.VISIBLE);
        } else if (state == 6) {
            tvBohui.setVisibility(View.VISIBLE);
            tvTongguo.setVisibility(View.VISIBLE);
        } else if (state == 7) {
            tvDaifahuo.setVisibility(View.VISIBLE);
        } else if (state == 8) {
            tvTuikuan.setVisibility(View.VISIBLE);
        } else if (state == 9) {
            tvYituikuan.setVisibility(View.VISIBLE);
        } else if (state == 10) {
            tvYibohui.setVisibility(View.VISIBLE);
        } else if (state == 11) {
            tvQuerenshouhuo.setVisibility(View.VISIBLE);
        }


        btFahuo.setOnClickListener(new SetOnClick());
        tvBohui.setOnClickListener(new SetOnClick());
        tvTongguo.setOnClickListener(new SetOnClick());
        tvTuikuan.setOnClickListener(new SetOnClick());
        tvQuerenshouhuo.setOnClickListener(new SetOnClick());
    }

    private class SetOnClick implements View.OnClickListener {
        Dialog dialog;
        TextView text;

        public SetOnClick() {
            dialog = new Dialog(Activity_OrderDetails_Sp.this, R.style.AlertDialogStyle);
            dialog.getWindow().setContentView(R.layout.dialog_cancel_order);
            dialog.setCanceledOnTouchOutside(false);

            text = (TextView) dialog.getWindow().findViewById(R.id.text_cnacel_order);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_fahuo:
                    if (orderDetailSpBean.getData().getMap().get(0).getIs_pickup() == 1) {
                        text.setText("确定发货？");
                        dialog.show();

                        dialog.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {  //确定
                                siBackMoney();
                                dialog.dismiss();
                            }
                        });
                        dialog.getWindow().findViewById(R.id.ll_cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {  //取消
                                dialog.dismiss();
                            }
                        });
                    } else {
                        Intent intent = new Intent(getApplicationContext(), Activity_Logistics.class);
                        intent.putExtra("orderCode", orderDetailSpBean.getData().getMap().get(0).getOrder_code() + "");
                        intent.putExtra("id", 1 + "");
                        startActivity(intent);
                        //startActivityForResult(intent, 0x001);
                    }
                    break;
                case R.id.tv_bohui:
                    text.setText("确定驳回顾客的申请？");
                    dialog.show();

                    dialog.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            updateGoodsOrder("10");
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
                case R.id.tv_tuikuan:
                    text.setText("确定退款给顾客？");
                    dialog.show();

                    dialog.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            shopRefundFinsh();
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
                    text.setText("审核通过后，顾客将把商品退换给您？");
                    dialog.show();

                    dialog.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            updateGoodsOrder("7");
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
                case R.id.tv_querenshouhuo:
                    text.setText("确认收货？");
                    dialog.show();

                    dialog.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            updateGoodsOrder("8");
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

            }
        }
    }


    private void siBackMoney() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpUtil.token, ""));
        params.addQueryStringParameter("type", 3 + "");
        params.addQueryStringParameter("orderState", "3");
        AuthenticationJson authenticationJson = new Gson().fromJson(SpUtil.getString(getApplication(), "authenticationJson", ""), AuthenticationJson.class);
        params.addQueryStringParameter("orderCode", orderDetailSpBean.getData().getMap().get(0).getOrder_code() + "");
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
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
                                informationFindOrderDetail();

                                Fragment_SpInside fragment_spInside1 = (Fragment_SpInside) Fragment_ShangPin.sp_1;
                                fragment_spInside1.list.clear();
                                fragment_spInside1.mPage = 1;
                                fragment_spInside1.shopOrderList();
                                if (Fragment_ShangPin.sp_2 != null) {
                                    Fragment_SpInside fragment_spInside2 = (Fragment_SpInside) Fragment_ShangPin.sp_2;
                                    fragment_spInside1.list.clear();
                                    fragment_spInside1.mPage = 1;
                                    fragment_spInside2.shopOrderList();
                                }

                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
                    }
                });
    }


    public void updateGoodsOrder(String state) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        params.addQueryStringParameter("orderState", state);
        AuthenticationJson authenticationJson = new Gson().fromJson(SpUtil.getString(getApplicationContext(), "authenticationJson", ""), AuthenticationJson.class);
        params.addQueryStringParameter("shopInformationId", authenticationJson.getId() + "");
        params.addQueryStringParameter("orderCode", orderDetailSpBean.getData().getMap().get(0).getOrder_code() + "");
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
                                informationFindOrderDetail();

                                Fragment_SpInside fragment_spInside1 = (Fragment_SpInside) Fragment_ShangPin.sp_1;
                                fragment_spInside1.list.clear();
                                fragment_spInside1.mPage = 1;
                                fragment_spInside1.shopOrderList();
                                if (Fragment_ShangPin.sp_2 != null) {
                                    Fragment_SpInside fragment_spInside2 = (Fragment_SpInside) Fragment_ShangPin.sp_2;
                                    fragment_spInside1.list.clear();
                                    fragment_spInside1.mPage = 1;
                                    fragment_spInside2.shopOrderList();
                                }
                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
                    }
                });
    }


    //确认退款
    public void shopRefundFinsh() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        AuthenticationJson authenticationJson = new Gson().fromJson(SpUtil.getString(getApplicationContext(), "authenticationJson", ""), AuthenticationJson.class);
        params.addQueryStringParameter("type", 3 + "");
        params.addQueryStringParameter("shopUserId", SpUtil.getLong(getApplicationContext(), SpUtil.userId, 1) + "");
        params.addQueryStringParameter("useId", orderDetailSpBean.getData().getMap().get(0).getUser_id() + "");
        params.addQueryStringParameter("balance", orderDetailSpBean.getData().getMap().get(0).getReal_price() + "");
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
        params.addQueryStringParameter("orderCode", orderDetailSpBean.getData().getMap().get(0).getOrder_code());
        params.addQueryStringParameter("orderState", 9 + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.shopRefundFinsh, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                informationFindOrderDetail();

                                Fragment_SpInside fragment_spInside1 = (Fragment_SpInside) Fragment_ShangPin.sp_1;
                                fragment_spInside1.list.clear();
                                fragment_spInside1.mPage = 1;
                                fragment_spInside1.shopOrderList();
                                if (Fragment_ShangPin.sp_2 != null) {
                                    Fragment_SpInside fragment_spInside2 = (Fragment_SpInside) Fragment_ShangPin.sp_2;
                                    fragment_spInside1.list.clear();
                                    fragment_spInside1.mPage = 1;
                                    fragment_spInside2.shopOrderList();
                                }
                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
                    }
                });
    }

}
