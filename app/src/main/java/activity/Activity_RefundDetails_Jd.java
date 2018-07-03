package activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
import com.JsonBean.FindOrderDetailJdBean;
import com.JsonBean.LoginBean;
import com.application.URL;
import com.cfd.business.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.mytools.MyToast;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fragment.Fragment_RefundOrder_Jd;
import util.SpUtil;
import util.ToastUtil;
import view.DialogProgressbar;
import view.RoundImageView;

/**
 * 通用
 * Created by Administrator on 2016/9/5 0005.
 */
public class Activity_RefundDetails_Jd extends Activity {

    @Bind(R.id.tv_OrderNumber)
    TextView tvOrderNumber;
    @Bind(R.id.iv_HeadImg)
    RoundImageView ivHeadImg;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.tv_commodityName)
    TextView tvCommodityName;
    @Bind(R.id.tv_fangjianshu)
    TextView tvFangjianshu;
    @Bind(R.id.tv_jiwan)
    TextView tvJiwan;
    @Bind(R.id.tv_ruzhu)
    TextView tvRuzhu;
    @Bind(R.id.tv_lidian)
    TextView tvLidian;
    @Bind(R.id.tv_zong)
    TextView tvZong;
    @Bind(R.id.tv_tongguo)
    TextView tvTongguo;
    @Bind(R.id.tv_yituikuan)
    TextView tvYituikuan;
    @Bind(R.id.tv_createTime)
    TextView tvCreateTime;
    @Bind(R.id.tv_payTime)
    TextView tvPayTime;
    @Bind(R.id.tv_payMode)
    TextView tvPayMode;
    @Bind(R.id.tv_people)
    TextView tvPeople;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.tv_start)
    TextView tvStart;
    @Bind(R.id.tv_end)
    TextView tvEnd;
    @Bind(R.id.tv_day)
    TextView tvDay;
    @Bind(R.id.tv_Telephone)
    TextView tvTelephone;
    @Bind(R.id.tv_total)
    TextView tvTotal;
    @Bind(R.id.bt_quxiao)
    Button btQuxiao;
    @Bind(R.id.bt_yanzheng)
    Button btYanzheng;
    @Bind(R.id.bt_wancheng)
    Button btWancheng;
    @Bind(R.id.bt_guoqi)
    Button btGuoqi;

    FindOrderDetailJdBean findOrderDetailJbBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_details_jd);
        ButterKnife.bind(this);

        shopFindOrderDetail();
    }


    private void shopFindOrderDetail() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        params.addQueryStringParameter("type", 1 + "");
        params.addQueryStringParameter("orderCode", getIntent().getStringExtra("orderCode"));
        AuthenticationJson authenticationJson = new Gson().fromJson(SpUtil.getString(getApplication(), "authenticationJson", ""), AuthenticationJson.class);
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.shopFindOrderDetail, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_RefundDetails_Jd.this,R.style.AlertDialogStyle);
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
                            findOrderDetailJbBean = new Gson().fromJson(responseInfo.result, FindOrderDetailJdBean.class);
                            int i = findOrderDetailJbBean.getHeader().getStatus();
                            if (i == 0) {
                                initView();

                                tvOrderNumber.setText("订单号：" + findOrderDetailJbBean.getData().get(0).getOrder_code());
                                ImageLoader.getInstance().displayImage(findOrderDetailJbBean.getData().get(0).getHead_img(), ivHeadImg);
                                tvName.setText(findOrderDetailJbBean.getData().get(0).getNick_name());
                                ImageLoader.getInstance().displayImage(findOrderDetailJbBean.getData().get(0).getDescribe_img(), imageView);
                                tvCommodityName.setText(findOrderDetailJbBean.getData().get(0).getGoods_name());
                                tvFangjianshu.setText("房间数：" + findOrderDetailJbBean.getData().get(0).getQuantity() + "间");
                                tvJiwan.setText(findOrderDetailJbBean.getData().get(0).getCheck_days() + "晚");
                                tvRuzhu.setText("入住日期：" + findOrderDetailJbBean.getData().get(0).getStart_date());
                                tvLidian.setText("离店日期：" + findOrderDetailJbBean.getData().get(0).getEnd_date());
                                tvZong.setText("￥" + findOrderDetailJbBean.getData().get(0).getReal_price());
                                tvCreateTime.setText("创建时间：" + findOrderDetailJbBean.getData().get(0).getCreate_time());
                                tvPayTime.setText("付款时间：" + findOrderDetailJbBean.getData().get(0).getPay_time());
                                if (findOrderDetailJbBean.getData().get(0).getPay_way() == 1) {
                                    tvPayMode.setText("支付方式：余额支付");
                                } else if (findOrderDetailJbBean.getData().get(0).getPay_way() == 2) {
                                    tvPayMode.setText("支付方式：支付宝支付");
                                } else if (findOrderDetailJbBean.getData().get(0).getPay_way() == 3) {
                                    tvPayMode.setText("支付方式：微信支付");
                                }

                                //状态值判断
                               /* if (findOrderDetailJbBean.getData().get(0).getOrder_state() == 3) {
                                    tvTongguo.setVisibility(View.VISIBLE);
                                } else if (findOrderDetailJbBean.getData().get(0).getOrder_state() == 6) {
                                    tvYituikuan.setVisibility(View.VISIBLE);
                                }*/
                                if (findOrderDetailJbBean.getData().get(0).getOrder_state() == 2) {
                                    btYanzheng.setVisibility(View.VISIBLE);
                                    btQuxiao.setVisibility(View.VISIBLE);
                                } else if (findOrderDetailJbBean.getData().get(0).getOrder_state() == 3) {
                                    tvTongguo.setVisibility(View.VISIBLE);
                                } else if (findOrderDetailJbBean.getData().get(0).getOrder_state() == 4) {
                                    btWancheng.setVisibility(View.VISIBLE);
                                } else if (findOrderDetailJbBean.getData().get(0).getOrder_state() == 5) {
                                    btGuoqi.setVisibility(View.VISIBLE);
                                } else if (findOrderDetailJbBean.getData().get(0).getOrder_state() == 6) {
                                    tvYituikuan.setVisibility(View.VISIBLE);
                                } else if (findOrderDetailJbBean.getData().get(0).getOrder_state() == 7) {

                                }


                                tvPeople.setText("入住人：" + findOrderDetailJbBean.getData().get(0).getPersonName());
                                tvNum.setText("房间数：" + findOrderDetailJbBean.getData().get(0).getQuantity());
                                tvStart.setText("入住时间：" + findOrderDetailJbBean.getData().get(0).getStart_date());
                                tvEnd.setText("离店时间：" + findOrderDetailJbBean.getData().get(0).getEnd_date());
                                tvDay.setText("共几晚：" + findOrderDetailJbBean.getData().get(0).getQuantity() + "晚");
                                tvTelephone.setText("联系电话：" + findOrderDetailJbBean.getData().get(0).getTelphone());
                                tvTotal.setText("总额：" + findOrderDetailJbBean.getData().get(0).getReal_price());


                                btQuxiao.setOnClickListener(new SetOnClick());
                                btYanzheng.setOnClickListener(new SetOnClick());
                                tvTongguo.setOnClickListener(new SetOnClick());
                            } else {
                                ToastUtil.show(getApplicationContext(), findOrderDetailJbBean.getHeader().getMsg());
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

    private void initView(){
        btQuxiao.setVisibility(View.GONE);
        btYanzheng.setVisibility(View.GONE);
        btWancheng.setVisibility(View.GONE);
        btGuoqi.setVisibility(View.GONE);
        tvTongguo.setVisibility(View.GONE);
        tvYituikuan.setVisibility(View.GONE);
    }


    private class SetOnClick implements View.OnClickListener {
        Dialog dialog;
        TextView text;

        public SetOnClick() {
            dialog= new Dialog(Activity_RefundDetails_Jd.this, R.style.AlertDialogStyle);
            dialog.getWindow().setContentView(R.layout.dialog_cancel_order);
            dialog.setCanceledOnTouchOutside(false);
            text= (TextView) dialog.getWindow().findViewById(R.id.text_cnacel_order);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_quxiao:
                    text.setText("确定取消该订单？");
                    dialog.show();
                    dialog.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            Cancelorder();
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
                case R.id.bt_yanzheng:
                    text.setText("确定销核该订单？");
                    dialog.show();
                    dialog.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            shenheorder();
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
            }

        }
    }


    /**
     * 取消订单
     */
    public void Cancelorder() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        params.addQueryStringParameter("orderCode", findOrderDetailJbBean.getData().get(0).getOrder_code());
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.send(HttpRequest.HttpMethod.GET, URL.hotelOrdercancelOrder, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    LoginBean loginBean = new Gson().fromJson(responseInfo.result, LoginBean.class);
                    if (loginBean.getHeader().getStatus() == 0) {
                        finish();
                        MyToast.SHow(getApplicationContext(), "取消订单成功");
                    } else
                        MyToast.SHow(getApplicationContext(), loginBean.getHeader().getMsg());

                } catch (Exception e) {
                    MyToast.SHow(getApplicationContext(), "取消订单失败");
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                MyToast.SHownetworkField(getApplicationContext());
            }
        });

    }

    /**
     * 验证销核订单
     */
    public void shenheorder() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        params.addQueryStringParameter("orderCode", findOrderDetailJbBean.getData().get(0).getOrder_code());
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.send(HttpRequest.HttpMethod.GET, URL.hotelOrderorderVerification, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    LoginBean loginBean = new Gson().fromJson(responseInfo.result, LoginBean.class);
                    if (loginBean.getHeader().getStatus() == 0) {
                        MyToast.SHow(getApplicationContext(), "销核订单成功");
                        shopFindOrderDetail();
                    } else
                        MyToast.SHow(getApplicationContext(), loginBean.getHeader().getMsg());

                } catch (Exception e) {
                    MyToast.SHow(getApplicationContext(), "销核订单失败");
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                MyToast.SHownetworkField(getApplicationContext());
            }
        });

    }


    //退款
    private void shopRefundFinsh() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        AuthenticationJson authenticationJson = new Gson().fromJson(SpUtil.getString(getApplicationContext(), "authenticationJson", ""), AuthenticationJson.class);
        params.addQueryStringParameter("type", 1 + "");
        params.addQueryStringParameter("shopUserId", SpUtil.getLong(getApplicationContext(), SpUtil.userId, 1) + "");
        params.addQueryStringParameter("useId", findOrderDetailJbBean.getData().get(0).getUser_id() + "");
        params.addQueryStringParameter("balance", findOrderDetailJbBean.getData().get(0).getReal_price() + "");
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
        params.addQueryStringParameter("orderCode", findOrderDetailJbBean.getData().get(0).getOrder_code());
        params.addQueryStringParameter("orderState", 6 + "");
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

                                /*Fragment_RefundOrder_Jd f1 = (Fragment_RefundOrder_Jd) Activity_RefundOrder_Jd.fragment_dsh;
                                f1.hotelRefundOrder();

                                if (Activity_RefundOrder_Jd.fragment_ytk != null) {
                                    Fragment_RefundOrder_Jd f2 = (Fragment_RefundOrder_Jd) Activity_RefundOrder_Jd.fragment_ytk;
                                    f2.hotelRefundOrder();
                                }*/
                            } else if (i==4){
                                ToastUtil.show(getApplicationContext(),"余额不足");
                            }else {
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
