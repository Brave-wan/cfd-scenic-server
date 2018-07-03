package activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
import com.JsonBean.OrderDetailFdBean;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fragment.Fragment_RefundOrder_Fd;
import fragment.Fragment_RefundOrder_Jd;
import util.SpUtil;
import util.ToastUtil;
import view.DialogConfirmRefund;
import view.DialogProgressbar;
import view.RoundImageView;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class Activity_RefundDetails_Fd extends Activity {

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
    @Bind(R.id.tv_jiucanshijian)
    TextView tvJiucanshijian;
    @Bind(R.id.tv_zong)
    TextView tvZong;
    @Bind(R.id.bt_tongguo)
    Button btTongguo;
    @Bind(R.id.bt_tuikuan)
    Button btTuikuan;
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

    OrderDetailFdBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_details_fd);
        ButterKnife.bind(this);

        orderDetail();
    }

    private void orderDetail() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        params.addQueryStringParameter("type", 2 + "");
        params.addQueryStringParameter("goodsType", 1 + "");
        params.addQueryStringParameter("orderCode", getIntent().getStringExtra("orderCode"));
        AuthenticationJson authenticationJson = new Gson().fromJson(SpUtil.getString(getApplicationContext(), "authenticationJson", ""), AuthenticationJson.class);
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.orderDetail, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_RefundDetails_Fd.this,R.style.AlertDialogStyle);
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
                            bean = new Gson().fromJson(responseInfo.result, OrderDetailFdBean.class);
                            int i = bean.getHeader().getStatus();
                            if (i == 0) {
                                tvOrderNumber.setText("订单号：" + bean.getData().getDetail().getOrder_code() + "");
                                ImageLoader.getInstance().displayImage(bean.getData().getDetail().getHead_img(), ivTouxiang);
                                tvUserName.setText(bean.getData().getDetail().getNick_name());
                                ImageLoader.getInstance().displayImage(bean.getData().getDetail().getGoodsImg(), imageView);
                                tvCommodityName.setText(bean.getData().getDetail().getName());
                                tvJiucanshijian.setText(bean.getData().getDetail().getEat_date());
                                tvZong.setText("￥" + bean.getData().getDetail().getReal_price());
                                tvChuangjianshijian.setText("创建时间：" + bean.getData().getDetail().getCreate_time());
                                tvFukuanshijian.setText("付款时间：" + bean.getData().getDetail().getPay_time());
                                if (bean.getData().getDetail().getPay_way() == 1) {
                                    tvZhifufangshi.setText("支付方式：余额支付");
                                } else if (bean.getData().getDetail().getPay_way() == 2) {
                                    tvZhifufangshi.setText("支付方式：支付宝支付");
                                } else if (bean.getData().getDetail().getPay_way() == 3) {
                                    tvZhifufangshi.setText("支付方式：微信支付");
                                }
                                tvShuliang.setText("数量：" + bean.getData().getDetail().getQuantity());
                                tvJiucanshijian2.setText("就餐时间：" + bean.getData().getDetail().getEat_date());
                                tvDianhua.setText("联系电话：" + bean.getData().getDetail().getTelphone());
                                tvZong2.setText("总额：" + bean.getData().getDetail().getReal_price());

                                btTongguo.setVisibility(View.GONE);
                                btTuikuan.setVisibility(View.GONE);
                                if (bean.getData().getDetail().getOrder_state() == 3) {
                                    btTongguo.setVisibility(View.VISIBLE);
                                } else if (bean.getData().getDetail().getOrder_state() == 6) {
                                    btTuikuan.setVisibility(View.VISIBLE);
                                }
                            } else {
                                ToastUtil.show(getApplicationContext(), bean.getHeader().getMsg());
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

    @OnClick(R.id.bt_tongguo)
    public void onClick() {
        DialogConfirmRefund dialogConfirmRefund = new DialogConfirmRefund(Activity_RefundDetails_Fd.this, R.style.AlertDialogStyle,2);
        dialogConfirmRefund.show();
        //shopRefundFinsh();
    }


    public void shopRefundFinsh(){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        AuthenticationJson authenticationJson=new Gson().fromJson(SpUtil.getString(getApplicationContext(),"authenticationJson",""),AuthenticationJson.class);
        params.addQueryStringParameter("type", 2 + "");
        params.addQueryStringParameter("shopUserId",SpUtil.getLong(getApplicationContext(),SpUtil.userId,1)+"" );
        params.addQueryStringParameter("useId",bean.getData().getDetail().getUser_id()+"");
        params.addQueryStringParameter("balance",bean.getData().getDetail().getReal_price()+"");
        params.addQueryStringParameter("siId",authenticationJson.getId() + "");
        params.addQueryStringParameter("orderCode",bean.getData().getDetail().getOrder_code());
        params.addQueryStringParameter("orderState", 6 + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.shopRefundFinsh, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject=new JSONObject(responseInfo.result);
                            JSONObject header=jsonObject.getJSONObject("header");
                            int i=header.getInt("status");
                            if(i==0){
                                orderDetail();

                                Fragment_RefundOrder_Fd f1= (Fragment_RefundOrder_Fd) Activity_RefundOrder_Fd.fragment_dsh;
                                f1.restaurantRefundOrder();

                                if (Activity_RefundOrder_Fd.fragment_ytk!=null){
                                    Fragment_RefundOrder_Fd f2= (Fragment_RefundOrder_Fd) Activity_RefundOrder_Fd.fragment_ytk;
                                    f2.restaurantRefundOrder();
                                }
                            }else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(),"解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
                    }
                });

    }
}
