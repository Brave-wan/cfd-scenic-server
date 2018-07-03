package activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
import com.JsonBean.LoginBean;
import com.JsonBean.OrderDetailFdTcBean;
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
import util.SpUtil;
import util.ToastUtil;
import view.DialogProgressbar;

/**
 * 订单详情 通用  饭店套餐
 * Created by Administrator on 2016/10/11 0011.
 */
public class Activity_Details_FdTc extends Activity {


    OrderDetailFdTcBean bean;

    @Bind(R.id.tv_OrderNumber)
    TextView tvOrderNumber;
    @Bind(R.id.iv_touxiang)
    ImageView ivTouxiang;
    @Bind(R.id.tv_userName)
    TextView tvUserName;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.tv_commodityName)
    TextView tvCommodityName;
    @Bind(R.id.tv_jiucanshijian)
    TextView tvJiucanshijian;
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
    @Bind(R.id.tv_chuangjianshijian)
    TextView tvChuangjianshijian;
    @Bind(R.id.tv_fukuanshijian)
    TextView tvFukuanshijian;
    @Bind(R.id.tv_zhifufangshi)
    TextView tvZhifufangshi;
    @Bind(R.id.tv_shuliang)
    TextView tvShuliang;
    @Bind(R.id.tv_jiucanshijian2)
    TextView tvJiucanshijian2;
    @Bind(R.id.tv_dianhua)
    TextView tvDianhua;
    @Bind(R.id.tv_zong2)
    TextView tvZong2;
    @Bind(R.id.tv_taochanneirong)
    TextView tvTaochanneirong;
    @Bind(R.id.bt_tongguo)
    Button btTongguo;
    @Bind(R.id.bt_tuikuan)
    Button btTuikuan;
    @Bind(R.id.ll_add)
    LinearLayout llAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_fdtc);
        ButterKnife.bind(this);

        orderDetail();
    }


    private void orderDetail() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        params.addQueryStringParameter("goodsType", 1 + "");
        params.addQueryStringParameter("orderCode", getIntent().getStringExtra("orderCode"));
        AuthenticationJson authenticationJson = new Gson().fromJson(SpUtil.getString(getApplicationContext(), "authenticationJson", ""), AuthenticationJson.class);
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.orderDetail, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_Details_FdTc.this,R.style.AlertDialogStyle);
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

                            bean = new Gson().fromJson(responseInfo.result, OrderDetailFdTcBean.class);
                            int i = bean.getHeader().getStatus();
                            if (i == 0) {
                                tvOrderNumber.setText("订单号：" + bean.getData().getDetail().getOrder_code() + "");
                                ImageLoader.getInstance().displayImage(bean.getData().getDetail().getHead_img(), ivTouxiang);
                                tvUserName.setText(bean.getData().getDetail().getNick_name());
                                ImageLoader.getInstance().displayImage(bean.getData().getDetail().getGoodsImg(), imageView);
                                tvCommodityName.setText(bean.getData().getDetail().getName());
                                tvJiucanshijian.setText(bean.getData().getDetail().getEat_date());
                                tvZong.setText("￥" + bean.getData().getDetail().getReal_price());
                                if (bean.getData().getDetail().getPay_way() == 1) {
                                    tvZhifufangshi.setText("支付方式：余额支付");
                                } else if (bean.getData().getDetail().getPay_way() == 2) {
                                    tvZhifufangshi.setText("支付方式：支付宝支付");
                                } else if (bean.getData().getDetail().getPay_way() == 3) {
                                    tvZhifufangshi.setText("支付方式：微信支付");
                                }
                                tvChuangjianshijian.setText("创建时间：" + bean.getData().getDetail().getCreate_time());
                                tvFukuanshijian.setText("付款时间：" + bean.getData().getDetail().getPay_time());
                                tvJiucanshijian2.setText("就餐时间：" + bean.getData().getDetail().getEat_date());
                                tvDianhua.setText("联系电话：" + bean.getData().getDetail().getTelphone());
                                tvZong.setText("￥" + bean.getData().getDetail().getReal_price());
                                tvShuliang.setText("数量：" + bean.getData().getDetail().getQuantity());
                                tvZong2.setText("总额:" + bean.getData().getDetail().getReal_price());

                                for (int index=0;index<bean.getData().getShopGoodsList().size();index++){
                                    View view= LayoutInflater.from(Activity_Details_FdTc.this).inflate(R.layout.view_package_content, null);
                                    TextView t1= (TextView) view.findViewById(R.id.name);
                                    TextView t2= (TextView) view.findViewById(R.id.num);
                                    TextView t3= (TextView) view.findViewById(R.id.price);

                                    t1.setText(bean.getData().getShopGoodsList().get(index).getGoods_name()+",");
                                    t2.setText(bean.getData().getShopGoodsList().get(index).getQuantity()+"份,");
                                    t3.setText(bean.getData().getShopGoodsList().get(index).getNew_price()+"元");
                                    llAdd.addView(view);
                                }
                                setState(bean.getData().getDetail().getOrder_state());

                            } else {
                                ToastUtil.show(getApplicationContext(), bean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });

    }

    private void setState(int state) {
        if (state == 2) {
            btQuxiao.setVisibility(View.VISIBLE);
            btYanzheng.setVisibility(View.VISIBLE);
        } else if (state == 3) {
            btTongguo.setVisibility(View.VISIBLE);
        } else if (state == 4) {
            btWancheng.setVisibility(View.VISIBLE);
        } else if (state == 5) {
            btGuoqi.setVisibility(View.VISIBLE);
        } else if (state == 6) {
            btTuikuan.setVisibility(View.VISIBLE);
        } else if (state == 7) {

        }

        btQuxiao.setOnClickListener(new SetOnClick());
        btYanzheng.setOnClickListener(new SetOnClick());
        btTongguo.setOnClickListener(new SetOnClick());
    }


    private class SetOnClick implements View.OnClickListener {
        Dialog dialog1;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_quxiao:
                    dialog1 = new Dialog(Activity_Details_FdTc.this, R.style.AlertDialogStyle);
                    dialog1.getWindow().setContentView(R.layout.dialog_cancel_order);
                    dialog1.setCanceledOnTouchOutside(false);
                    dialog1.show();
                    TextView text1 = (TextView) dialog1.getWindow().findViewById(R.id.text_cnacel_order);
                    text1.setText("确定取消该订单？");
                    dialog1.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            Cancelorder();
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
                case R.id.bt_yanzheng:
                    dialog1 = new Dialog(Activity_Details_FdTc.this, R.style.AlertDialogStyle);
                    dialog1.getWindow().setContentView(R.layout.dialog_cancel_order);
                    dialog1.setCanceledOnTouchOutside(false);
                    dialog1.show();
                    TextView text2 = (TextView) dialog1.getWindow().findViewById(R.id.text_cnacel_order);
                    text2.setText("确定销核该订单？");
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
                    break;
                case R.id.bt_tongguo:
                    dialog1 = new Dialog(Activity_Details_FdTc.this, R.style.AlertDialogStyle);
                    dialog1.getWindow().setContentView(R.layout.dialog_cancel_order);
                    dialog1.setCanceledOnTouchOutside(false);
                    dialog1.show();
                    TextView text3 = (TextView) dialog1.getWindow().findViewById(R.id.text_cnacel_order);
                    text3.setText("确定退款给顾客？");
                    dialog1.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            shopRefundFinsh();
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
    }


    /**
     * 验证销核订单
     */
    public void shenheorder() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        params.addQueryStringParameter("orderCode", bean.getData().getDetail().getOrder_code());
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET,
                URL.restaurantOrderorderVerification,
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
                                ToastUtil.show(getApplicationContext(), "销核订单成功");
                                Activity_Search_Fd.refresh = true;
                                finish();
                            } else
                                MyToast.SHow(getApplicationContext(), loginBean.getHeader().getMsg());

                        } catch (Exception e) {
                            MyToast.SHow(getApplicationContext(), "销核订单失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");

                    }
                });

    }

    /**
     * 取消订单
     */
    public void Cancelorder() {
        AuthenticationJson authenticationJson = new Gson().fromJson(SpUtil.getString(getApplication(), "authenticationJson", ""), AuthenticationJson.class);
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpUtil.token, ""));
        params.addQueryStringParameter("orderCode", bean.getData().getDetail().getOrder_code());
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET,
                URL.restaurantOrdercancelOrder, params, new RequestCallBack<String>() {

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
                                MyToast.SHow(getApplicationContext(), "取消订单成功");
                                Activity_Search_Fd.refresh = true;
                                finish();
                            } else
                                MyToast.SHow(getApplicationContext(), loginBean.getHeader().getMsg());

                        } catch (Exception e) {
                            MyToast.SHow(getApplicationContext(), "取消订单失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });

    }

    //确认退款
    public void shopRefundFinsh() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        AuthenticationJson authenticationJson = new Gson().fromJson(SpUtil.getString(getApplicationContext(), "authenticationJson", ""), AuthenticationJson.class);
        params.addQueryStringParameter("type", 2 + "");
        params.addQueryStringParameter("shopUserId", SpUtil.getLong(getApplicationContext(), SpUtil.userId, 1) + "");
        params.addQueryStringParameter("useId", bean.getData().getDetail().getUser_id() + "");
        params.addQueryStringParameter("balance", bean.getData().getDetail().getReal_price() + "");
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
        params.addQueryStringParameter("orderCode", bean.getData().getDetail().getOrder_code());
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
                                Activity_Search_Fd.refresh = true;
                                orderDetail();
                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });

    }

}
