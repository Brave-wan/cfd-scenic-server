package activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
import com.JsonBean.FindOrderDetailSpBean;
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

import java.util.ArrayList;
import java.util.List;

import adapter.Adapter_RefundDetaislSp;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fragment.Fragment_RefundOrder_Sp;
import util.SpUtil;
import util.ToastUtil;
import view.DialogConfirmRefund;
import view.DialogProgressbar;
import view.DialogThroughAudit;
import view.MyListView;
import view.RoundImageView;

/**
 * 退款详情--商品
 * Created by Administrator on 2016/9/5 0005.
 */
public class Activity_RefundDetails_Sp extends Activity {


    List<FindOrderDetailSpBean.DataBean.MapBean> list;
    @Bind(R.id.tv_OrderNumber)
    TextView tvOrderNumber;
    @Bind(R.id.iv_user)
    RoundImageView ivUser;
    @Bind(R.id.tv_userName)
    TextView tvUserName;
    @Bind(R.id.lv_myListView)
    MyListView lvMyListView;
    @Bind(R.id.tv_bohui)
    TextView tvBohui;
    @Bind(R.id.tv_tongguo)
    TextView tvTongguo;
    @Bind(R.id.tv_tuikuan)
    TextView tvTuikuan;
    @Bind(R.id.tv_yituikuan)
    TextView tvYituikuan;
    @Bind(R.id.tv_xingming)
    TextView tvXingming;
    @Bind(R.id.tv_tuikuanshoujihao)
    TextView tvTuikuanshoujihao;
    @Bind(R.id.tv_tuikuanyuanyin)
    TextView tvTuikuanyuanyin;
    @Bind(R.id.tv_shouhuoren)
    TextView tvShouhuoren;
    @Bind(R.id.tv_dizhi)
    TextView tvDizhi;
    @Bind(R.id.tv_shoujihao)
    TextView tvShoujihao;
    @Bind(R.id.llAddress)
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
    @Bind(R.id.tv_yibohui)
    TextView tvYibohui;
    @Bind(R.id.tv_querenshouhuo)
    TextView tvQuerenshouhuo;
    @Bind(R.id.tv_daifahuo)
    TextView tvDaifahuo;
    @Bind(R.id.tv_wuliu)
    TextView tvWuliu;
    @Bind(R.id.tv_bianhao)
    TextView tvBianhao;
    @Bind(R.id.ll_wuliu)
    LinearLayout llWuliu;


    FindOrderDetailSpBean findOrderDetailSpBean;
    @Bind(R.id.ll_tkyy)
    LinearLayout llTkyy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_details_sp);
        ButterKnife.bind(this);

        shopFindOrderDetail();
    }

    @OnClick({R.id.tv_bohui, R.id.tv_tongguo, R.id.tv_tuikuan, R.id.tv_querenshouhuo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bohui:
                final Dialog dialog = new Dialog(Activity_RefundDetails_Sp.this, R.style.AlertDialogStyle);
                dialog.getWindow().setContentView(R.layout.dialog_cancel_order);
                dialog.setCanceledOnTouchOutside(false);
                TextView text = (TextView) dialog.getWindow().findViewById(R.id.text_cnacel_order);
                text.setText("确认驳回顾客的申请？");
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
            case R.id.tv_tongguo:
                DialogThroughAudit dialogThroughAudit = new DialogThroughAudit(Activity_RefundDetails_Sp.this, R.style.AlertDialogStyle);
                dialogThroughAudit.show();
                break;
            case R.id.tv_tuikuan:
                DialogConfirmRefund dialogConfirmRefund = new DialogConfirmRefund(Activity_RefundDetails_Sp.this, R.style.AlertDialogStyle, 3);
                dialogConfirmRefund.show();
                break;
            case R.id.tv_querenshouhuo:
                final Dialog dialog1 = new Dialog(Activity_RefundDetails_Sp.this, R.style.AlertDialogStyle);
                dialog1.getWindow().setContentView(R.layout.dialog_cancel_order);
                dialog1.setCanceledOnTouchOutside(false);
                TextView text1 = (TextView) dialog1.getWindow().findViewById(R.id.text_cnacel_order);
                text1.setText("确认收货？");
                dialog1.show();

                dialog1.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {  //确定
                        updateGoodsOrder("8");
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
        }
    }


    private void shopFindOrderDetail() {
        list = new ArrayList<>();
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        params.addQueryStringParameter("type", 3 + "");
        params.addQueryStringParameter("orderCode", getIntent().getStringExtra("orderCode"));
        AuthenticationJson authenticationJson = new Gson().fromJson(SpUtil.getString(getApplication(), "authenticationJson", ""), AuthenticationJson.class);
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.shopFindOrderDetail, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_RefundDetails_Sp.this, R.style.AlertDialogStyle);

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
                            findOrderDetailSpBean = new Gson().fromJson(responseInfo.result, FindOrderDetailSpBean.class);
                            int i = findOrderDetailSpBean.getHeader().getStatus();
                            if (i == 0) {
                                tvOrderNumber.setText("订单号:" + findOrderDetailSpBean.getData().getMap().get(0).getOrder_code());
                                ImageLoader.getInstance().displayImage(findOrderDetailSpBean.getData().getMap().get(0).getHead_img(), ivUser);
                                tvUserName.setText(findOrderDetailSpBean.getData().getMap().get(0).getNick_name());

                                list = findOrderDetailSpBean.getData().getMap();
                                lvMyListView.setAdapter(new Adapter_RefundDetaislSp(getApplicationContext(), list));

                                //获取配送费和总价
                                double fee = 0;
                                double total = 0;
                                for (int index = 0; index < findOrderDetailSpBean.getData().getMap().size(); index++) {
                                    if (findOrderDetailSpBean.getData().getMap().get(index).getIs_deliver_fee() == 1) {
                                        fee = findOrderDetailSpBean.getData().getMap().get(index).getDeliver_fee();
                                        total = findOrderDetailSpBean.getData().getMap().get(index).getReal_price();
                                    }
                                }

                                if (list.get(0).getIs_pickup() == 1) {
                                    llAddress.setVisibility(View.GONE);
                                    tvPeisongfangshi.setText("配送方式：自提");
                                    //llInformation.setVisibility(View.GONE);
                                    tvPeisong2.setVisibility(View.GONE);
                                } else {
                                    llAddress.setVisibility(View.VISIBLE);
                                    /*llInformation.setVisibility(View.VISIBLE);
                                    tvGongsi.setText("配送公司：" + orderDetailSpBean.getData().getExpress().getExpress_name());
                                    tvBianhao.setText("运单编号：" + orderDetailSpBean.getData().getExpress().getExpress_code());*/
                                    tvPeisong2.setText("配送费：" + fee + "");
                                    tvPeisongfangshi.setText("配送方式：送货上门");
                                    tvShouhuoren.setText(findOrderDetailSpBean.getData().getMap().get(0).getUser_name());
                                    tvShoujihao.setText(findOrderDetailSpBean.getData().getMap().get(0).getTelphone() + "");
                                    tvDizhi.setText(findOrderDetailSpBean.getData().getMap().get(0).getPlace_address() + findOrderDetailSpBean.getData().getMap().get(0).getDetail_address());
                                }

                                tvZong2.setText("总额：" + total);
                                tvChuangjianshijian.setText("创建时间：" + findOrderDetailSpBean.getData().getMap().get(0).getCreate_time());
                                tvFukuanshijian.setText("付款时间：" + findOrderDetailSpBean.getData().getMap().get(0).getCreate_time());
                                if (findOrderDetailSpBean.getData().getMap().get(0).getPay_way() == 1) {
                                    tvZhifufangshi.setText("支付方式：余额支付");
                                } else if (findOrderDetailSpBean.getData().getMap().get(0).getPay_way() == 1) {
                                    tvZhifufangshi.setText("支付方式：支付宝支付");
                                } else if (findOrderDetailSpBean.getData().getMap().get(0).getPay_way() == 1) {
                                    tvZhifufangshi.setText("支付方式：微信支付");
                                }

                                tvShuliang2.setText("数量：" + findOrderDetailSpBean.getData().getMap().get(0).getQuantity());


                                tvXingming.setText("姓名：" + findOrderDetailSpBean.getData().getRefundCause().getUserName());
                                tvTuikuanshoujihao.setText("手机号：" + findOrderDetailSpBean.getData().getRefundCause().getUserPhone() + "");
                                tvTuikuanyuanyin.setText("退款原因：" + findOrderDetailSpBean.getData().getRefundCause().getCause());

                                //用户填写的物流信息
                                if (!findOrderDetailSpBean.getData().getExpress().getExpress_code().equals("")) {
                                    tvWuliu.setText("物流信息：" + findOrderDetailSpBean.getData().getExpress().getExpress_name());
                                    tvBianhao.setText("运单编号：" + findOrderDetailSpBean.getData().getExpress().getExpress_code() + "");
                                } else {
                                    llWuliu.setVisibility(View.GONE);
                                }
                                state(findOrderDetailSpBean.getData().getMap().get(0).getOrder_state());
                            } else {
                                ToastUtil.show(getApplicationContext(), findOrderDetailSpBean.getHeader().getMsg());
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
        tvBohui.setVisibility(View.GONE);
        tvTongguo.setVisibility(View.GONE);
        tvYibohui.setVisibility(View.GONE);
        tvTuikuan.setVisibility(View.GONE);
        tvYituikuan.setVisibility(View.GONE);
        tvDaifahuo.setVisibility(View.GONE);
        tvQuerenshouhuo.setVisibility(View.GONE);


        if (state == 6) {
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
    }

    //审核通过
    public void updateGoodsOrder(String state) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        params.addQueryStringParameter("orderState", state);
        AuthenticationJson authenticationJson = new Gson().fromJson(SpUtil.getString(getApplicationContext(), "authenticationJson", ""), AuthenticationJson.class);
        params.addQueryStringParameter("shopInformationId", authenticationJson.getId() + "");
        params.addQueryStringParameter("orderCode", findOrderDetailSpBean.getData().getMap().get(0).getOrder_code() + "");
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
                                shopFindOrderDetail();
                                Fragment_RefundOrder_Sp sp1 = (Fragment_RefundOrder_Sp) Activity_RefundOrder_Sp.fragment_dsh;
                                sp1.shopRefundORder();

                                if (Activity_RefundOrder_Sp.fragment_tkz != null) {
                                    Fragment_RefundOrder_Sp sp2 = (Fragment_RefundOrder_Sp) Activity_RefundOrder_Sp.fragment_tkz;
                                    sp2.shopRefundORder();
                                }
                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                            Log.e("eeee", e.getMessage());
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
        params.addQueryStringParameter("useId", findOrderDetailSpBean.getData().getMap().get(0).getUser_id() + "");
        params.addQueryStringParameter("balance", findOrderDetailSpBean.getData().getMap().get(0).getReal_price() + "");
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
        params.addQueryStringParameter("orderCode", findOrderDetailSpBean.getData().getMap().get(0).getOrder_code());
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
                                shopFindOrderDetail();
                                Fragment_RefundOrder_Sp sp1 = (Fragment_RefundOrder_Sp) Activity_RefundOrder_Sp.fragment_tkz;
                                sp1.shopRefundORder();

                                if (Activity_RefundOrder_Sp.fragment_ytk != null) {
                                    Fragment_RefundOrder_Sp sp2 = (Fragment_RefundOrder_Sp) Activity_RefundOrder_Sp.fragment_ytk;
                                    sp2.shopRefundORder();
                                }
                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                            Log.e("eeee", e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
                    }
                });
    }
}
