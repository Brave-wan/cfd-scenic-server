package activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
import com.JsonBean.LoginBean;
import com.JsonBean.OrderDetailsJson;
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
import com.mytools.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.protocol.HTTP;

import butterknife.Bind;
import butterknife.ButterKnife;
import util.SpUtil;
import view.DialogCancelOrder;
import view.DialogProgressbar;

/**
 * 订单详情--商品版
 * Created by Administrator on 2016/7/28 0028.
 */
public class Activity_OrderDetails_Jd extends Activity {


    @Bind(R.id.tv_OrderNumber)
    TextView tvOrderNumber;
    @Bind(R.id.iv_touxiang)
    RoundImageView ivTouxiang;
    @Bind(R.id.tv_userName)
    TextView tvUserName;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.tv_commodityName)
    TextView tvCommodityName;
    @Bind(R.id.tv_fangjianshu)
    TextView tvFangjianshu;
    @Bind(R.id.tv_ruzhu)
    TextView tvRuzhu;
    @Bind(R.id.tv_lidian)
    TextView tvLidian;
    @Bind(R.id.tv_jiwan)
    TextView tvJiwan;
    @Bind(R.id.tv_zong)
    TextView tvZong;
    @Bind(R.id.bt_quxiao)
    Button btQuxiao;
    @Bind(R.id.bt_yanzheng)
    Button btYanzheng;
    @Bind(R.id.bt_wancheng)
    Button btWancheng;
    @Bind(R.id.bt_guoqi)
    Button btGuoqi;
    @Bind(R.id.order_details_createtime)
    TextView orderDetailsCreatetime;
    @Bind(R.id.order_details_paytime)
    TextView orderDetailsPaytime;
    @Bind(R.id.order_details_paytype)
    TextView orderDetailsPaytype;
    @Bind(R.id.order_details_name)
    TextView orderDetailsName;
    @Bind(R.id.order_details_roomnumber)
    TextView orderDetailsRoomnumber;
    @Bind(R.id.order_details_starttime)
    TextView orderDetailsStarttime;
    @Bind(R.id.order_details_endtime)
    TextView orderDetailsendtime;
    @Bind(R.id.order_details_phone)
    TextView orderDetailsPhone;
    @Bind(R.id.order_details_money)
    TextView orderDetailsMoney;
    int state;   //订单状态  0未完成，1已完成,2已过期
    int type;      //2酒店，2饭店，3特产
    String orderCode;
    AuthenticationJson authenticationJson;
    int position;
    @Bind(R.id.iv_xiangyoujt)
    ImageView ivXiangyoujt;
    @Bind(R.id.tv_daishenhe)
    TextView tvDaishenhe;
    @Bind(R.id.tv_yituikuan)
    TextView tvYituikuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_jd);
        ButterKnife.bind(this);

        ivXiangyoujt.setVisibility(View.GONE);

        authenticationJson = new Gson().fromJson(SpUtil.getString(this, "authenticationJson", ""), AuthenticationJson.class);
        Intent intent = getIntent();
        state = intent.getIntExtra("state", 1);
        type = intent.getIntExtra("type", 1);
        position = intent.getIntExtra("position", 0);
        orderCode = intent.getStringExtra("orderCode");

        getdetile();
        if (state == 1) {
            btWancheng.setVisibility(View.GONE);
            btGuoqi.setVisibility(View.GONE);
            btYanzheng.setVisibility(View.VISIBLE);
            btQuxiao.setVisibility(View.VISIBLE);
            btQuxiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogCancelOrder dialogCancelOrder = new DialogCancelOrder(Activity_OrderDetails_Jd.this, R.style.AlertDialogStyle);
                    dialogCancelOrder.show();
                }
            });
        } else if (state == 2) {
            btWancheng.setVisibility(View.VISIBLE);
            btGuoqi.setVisibility(View.GONE);
            btYanzheng.setVisibility(View.GONE);
            btQuxiao.setVisibility(View.GONE);
        } else if (state == 3) {
            btGuoqi.setVisibility(View.VISIBLE);
            btWancheng.setVisibility(View.GONE);
            btYanzheng.setVisibility(View.GONE);
            btQuxiao.setVisibility(View.GONE);
        }

        btQuxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Activity_OrderDetails_Jd.this, R.style.AlertDialogStyle);
                dialog.getWindow().setContentView(R.layout.dialog_cancel_order);
                dialog.setCanceledOnTouchOutside(false);
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
            }
        });

        btYanzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog1 = new Dialog(Activity_OrderDetails_Jd.this, R.style.AlertDialogStyle);
                dialog1.getWindow().setContentView(R.layout.dialog_cancel_order);
                dialog1.setCanceledOnTouchOutside(false);
                dialog1.show();
                TextView text1 = (TextView) dialog1.getWindow().findViewById(R.id.text_cnacel_order);
                text1.setText("确定销核该订单？");
                dialog1.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {  //确定


                        shenheorder();
                        dialog1.dismiss();

                    }
                });
                dialog1.getWindow().findViewById(R.id.ll_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {  //取消

                        dialog1.dismiss();
                    }
                });
            }
        });
    }

//    @OnClick({R.id.bt_quxiao, R.id.bt_yanzheng})
//    public void onClick(View view) {
//
//        switch (view.getId()) {
//            case R.id.bt_quxiao:  //取消订单
//
//
//
//                break;
//            case R.id.bt_yanzheng: //验证审核
//
//                break;
//
//        }
//    }

    /**
     * 获取订单详情
     */
    public void getdetile() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(Activity_OrderDetails_Jd.this, SpUtil.token, ""));
        params.addQueryStringParameter("type", type + "");
        params.addQueryStringParameter("informationId", authenticationJson.getId() + "");
        params.addQueryStringParameter("orderCode", orderCode);
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.send(HttpRequest.HttpMethod.POST,
                URL.informationFindOrderDetail,
                params,
                new RequestCallBack<String>() {


                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_OrderDetails_Jd.this, R.style.AlertDialogStyle);

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
                            OrderDetailsJson orderDetailsJson = new Gson().fromJson(responseInfo.result, OrderDetailsJson.class);
                            if (orderDetailsJson.getHeader().getStatus() == 0) {


                                tvOrderNumber.setText("订单号：" + orderDetailsJson.getData().get(0).getOrder_code());
                                ImageLoader.getInstance().displayImage(orderDetailsJson.getData().get(0).getHead_img(), ivTouxiang);
                                tvUserName.setText(orderDetailsJson.getData().get(0).getNick_name());
                                tvCommodityName.setText(orderDetailsJson.getData().get(0).getName());
                                tvFangjianshu.setText("房间数：" + orderDetailsJson.getData().get(0).getQuantity() + "");
                                tvRuzhu.setText(orderDetailsJson.getData().get(0).getStart_date());
                                tvLidian.setText(orderDetailsJson.getData().get(0).getEnd_date());
                                tvJiwan.setText(orderDetailsJson.getData().get(0).getCheck_days() + "晚");
                                tvZong.setText("￥" + orderDetailsJson.getData().get(0).getReal_price());
                                ImageLoader.getInstance().displayImage(orderDetailsJson.getData().get(0).getDescribe_img(), imageView);

                                orderDetailsCreatetime.setText("创建时间：" + orderDetailsJson.getData().get(0).getCreate_time());
                                orderDetailsPaytime.setText("付款时间：" + orderDetailsJson.getData().get(0).getPay_time());
                                if (orderDetailsJson.getData().get(0).getPay_way() == 1)
                                    orderDetailsPaytype.setText("支付方式：" + "余额支付");
                                else if (orderDetailsJson.getData().get(0).getPay_way() == 2)
                                    orderDetailsPaytype.setText("支付方式：" + "支付宝支付");
                                else if (orderDetailsJson.getData().get(0).getPay_way() == 3)
                                    orderDetailsPaytype.setText("支付方式：" + "微信支付");


                                if (orderDetailsJson.getData().get(0).getPersonName() != null) {
                                    String name = "";
                                    for (int i = 0; i < orderDetailsJson.getData().get(0).getPersonName().size(); i++) {
                                        if (i < orderDetailsJson.getData().get(0).getPersonName().size() - 1)
                                            name += orderDetailsJson.getData().get(0).getPersonName().get(i) + ";";
                                        else {
                                            name += orderDetailsJson.getData().get(0).getPersonName().get(i);
                                            orderDetailsName.setText("入住人：" + name);
                                        }
                                    }
                                }

                                orderDetailsRoomnumber.setText("房间数：" + orderDetailsJson.getData().get(0).getQuantity() + "");
                                orderDetailsStarttime.setText("住店时间：" + orderDetailsJson.getData().get(0).getStart_date());
                                orderDetailsendtime.setText("离店时间：" + orderDetailsJson.getData().get(0).getEnd_date() + ";" + orderDetailsJson.getData().get(0).getCheck_days() + "晚");
                                orderDetailsPhone.setText("联系电话：" + orderDetailsJson.getData().get(0).getTelphone());
                                orderDetailsMoney.setText("总额：￥" + orderDetailsJson.getData().get(0).getReal_price() + "");

                            } else
                                MyToast.SHow(Activity_OrderDetails_Jd.this, orderDetailsJson.getHeader().getMsg());

                        } catch (Exception e) {
                            MyToast.SHow(Activity_OrderDetails_Jd.this, "数据获取失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        MyToast.SHownetworkField(Activity_OrderDetails_Jd.this);

                    }
                });


    }


    /**
     * 取消订单
     */
    public void Cancelorder() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(this, SpUtil.token, ""));
        params.addQueryStringParameter("orderCode", orderCode);
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.send(HttpRequest.HttpMethod.POST,
                URL.hotelOrdercancelOrder,
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            LoginBean loginBean = new Gson().fromJson(responseInfo.result, LoginBean.class);
                            if (loginBean.getHeader().getStatus() == 0) {
                                MyToast.SHow(Activity_OrderDetails_Jd.this, "取消订单成功");
                                finishthis();
                            } else
                                MyToast.SHow(Activity_OrderDetails_Jd.this, loginBean.getHeader().getMsg());

                        } catch (Exception e) {
                            MyToast.SHow(Activity_OrderDetails_Jd.this, "取消订单失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        MyToast.SHownetworkField(Activity_OrderDetails_Jd.this);

                    }
                });

    }

    /**
     * 验证销核订单
     */
    public void shenheorder() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(this, SpUtil.token, ""));
        params.addQueryStringParameter("orderCode", orderCode);
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.send(HttpRequest.HttpMethod.GET,
                URL.hotelOrderorderVerification,
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            LoginBean loginBean = new Gson().fromJson(responseInfo.result, LoginBean.class);
                            if (loginBean.getHeader().getStatus() == 0) {
                                MyToast.SHow(Activity_OrderDetails_Jd.this, "销核订单成功");
                                finishthis();

                            } else
                                MyToast.SHow(Activity_OrderDetails_Jd.this, loginBean.getHeader().getMsg());

                        } catch (Exception e) {
                            MyToast.SHow(Activity_OrderDetails_Jd.this, "销核订单失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        MyToast.SHownetworkField(Activity_OrderDetails_Jd.this);

                    }
                });

    }

    public void finishthis() {
        Intent i = new Intent();
        Bundle bd = new Bundle();
        bd.putInt("position", position);
        i.putExtras(bd);
        setResult(122, i);
        this.finish();

    }
}
