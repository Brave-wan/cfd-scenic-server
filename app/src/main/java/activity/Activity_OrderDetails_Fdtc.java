package activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
import com.JsonBean.FandiantaocandetileJson;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import util.SpUtil;
import view.DialogCancelOrder;
import view.DialogProgressbar;

/**
 * 订单详情--套餐
 * Created by Administrator on 2016/7/28 0028.
 */
public class Activity_OrderDetails_Fdtc extends Activity {


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
    @Bind(R.id.scrollView4)
    ScrollView scrollView4;
    @Bind(R.id.tv_taochanneirong)
    TextView tv_taochanneirong;

    int state;
    AuthenticationJson authenticationJson;
    int type;
    int position;
    String orderCode;
    int goodsType = 0;
    String pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_fdtc);
        ButterKnife.bind(this);

        authenticationJson = new Gson().fromJson(SpUtil.getString(this, "authenticationJson", ""), AuthenticationJson.class);
        Intent intent = getIntent();
        state = intent.getIntExtra("state", 1);
        type = intent.getIntExtra("type", 1);
        position = intent.getIntExtra("position", 0);
        goodsType = intent.getIntExtra("goodsType", 0);
        orderCode = intent.getStringExtra("orderCode");
        pic = intent.getStringExtra("pic");

        getdetile();


        if (state == 1) {
            btQuxiao.setVisibility(View.VISIBLE);
            btYanzheng.setVisibility(View.VISIBLE);
            btWancheng.setVisibility(View.GONE);
            btGuoqi.setVisibility(View.GONE);
            btQuxiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogCancelOrder dialogCancelOrder = new DialogCancelOrder(Activity_OrderDetails_Fdtc.this, R.style.AlertDialogStyle);
                    dialogCancelOrder.show();
                }
            });
        } else if (state == 2) {
            btQuxiao.setVisibility(View.GONE);
            btYanzheng.setVisibility(View.GONE);
            btWancheng.setVisibility(View.VISIBLE);
            btGuoqi.setVisibility(View.GONE);
        } else if (state == 3) {
            btQuxiao.setVisibility(View.GONE);
            btYanzheng.setVisibility(View.GONE);
            btWancheng.setVisibility(View.GONE);
            btGuoqi.setVisibility(View.VISIBLE);
        }

        btQuxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Activity_OrderDetails_Fdtc.this, R.style.AlertDialogStyle);
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
                final  Dialog dialog1 = new Dialog(Activity_OrderDetails_Fdtc.this, R.style.AlertDialogStyle);
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

    /**
     * 获取订单详情
     */
    public void getdetile() {

        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(Activity_OrderDetails_Fdtc.this, SpUtil.token, ""));
        params.addQueryStringParameter("type", type + "");
//        params.addQueryStringParameter("informationId", authenticationJson.getId() + "");
        params.addQueryStringParameter("orderCode", orderCode);
        params.addQueryStringParameter("goodsType", goodsType + "");
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.POST,
                URL.restaurantOrderorderDetail,
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_OrderDetails_Fdtc.this,R.style.AlertDialogStyle);
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
                            FandiantaocandetileJson fandiantaocandetileJson = new Gson().fromJson(responseInfo.result, FandiantaocandetileJson.class);
                            if (fandiantaocandetileJson.getHeader().getStatus() == 0) {

//                                tvOrderNumber.setText("订单号：" + fandiantaocandetileJson.getData().getDetail().getOrder_code());
//                                ImageLoader.getInstance().displayImage(fandiantaocandetileJson.getData().get(0).getHead_img(),ivTouxiang);
//                                tvOrderNumber.setText( + fandiantaocandetileJson.getData().get(0).getNick_name());
                                ImageLoader.getInstance().displayImage(pic, imageView);
                                tvCommodityName.setText(fandiantaocandetileJson.getData().getDetail().getName());
                                tvJiucanshijian.setText("就餐时间：" + fandiantaocandetileJson.getData().getDetail().getEat_date());
                                tvShuliang.setText("数量：" + fandiantaocandetileJson.getData().getDetail().getQuantity());
                                tvZong.setText("￥" + fandiantaocandetileJson.getData().getDetail().getReal_price());
                                tvChuangjianshijian.setText("创建时间：" + fandiantaocandetileJson.getData().getDetail().getCreate_time());
                                tvFukuanshijian.setText("付款时间：" + fandiantaocandetileJson.getData().getDetail().getPay_time());
                                if (fandiantaocandetileJson.getData().getDetail().getPay_way() == 1)
                                    tvZhifufangshi.setText("支付方式：" + "余额支付");
                                else if (fandiantaocandetileJson.getData().getDetail().getPay_way() == 2)
                                    tvZhifufangshi.setText("支付方式：" + "支付宝支付");
                                else if (fandiantaocandetileJson.getData().getDetail().getPay_way() == 3)
                                    tvZhifufangshi.setText("支付方式：" + "微信支付");
                                tvJiucanshijian2.setText("就餐时间：" + fandiantaocandetileJson.getData().getDetail().getEat_date());
                                tvDianhua.setText("联系电话：" + fandiantaocandetileJson.getData().getDetail().getTelphone());
                                tvZong2.setText("总额：￥" + fandiantaocandetileJson.getData().getDetail().getReal_price());
                                tv_taochanneirong.setText(fandiantaocandetileJson.getData().getShopGoodsList().get(0).getGoods_name() + "," + fandiantaocandetileJson.getData().getShopGoodsList().get(0).getQuantity() + "份," + fandiantaocandetileJson.getData().getShopGoodsList().get(0).getNew_price() + "元");

                            } else
                                MyToast.SHow(Activity_OrderDetails_Fdtc.this, fandiantaocandetileJson.getHeader().getMsg());

                        } catch (Exception e) {
                            MyToast.SHow(Activity_OrderDetails_Fdtc.this, "数据获取失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        MyToast.SHownetworkField(Activity_OrderDetails_Fdtc.this);

                    }
                });


    }

    /**
     * 取消订单
     */
    public void Cancelorder() {
        AuthenticationJson authenticationJson= new Gson().fromJson(SpUtil.getString(Activity_OrderDetails_Fdtc.this, "authenticationJson", ""), AuthenticationJson.class);
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(Activity_OrderDetails_Fdtc.this, SpUtil.token, ""));
        params.addQueryStringParameter("orderCode", orderCode);
        params.addQueryStringParameter("siId", authenticationJson.getId()+"");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET,
                URL.restaurantOrdercancelOrder,
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
                                MyToast.SHow(Activity_OrderDetails_Fdtc.this, "取消订单成功");
                                finishthis();
                            } else
                                MyToast.SHow(Activity_OrderDetails_Fdtc.this, loginBean.getHeader().getMsg());

                        } catch (Exception e) {
                            MyToast.SHow(Activity_OrderDetails_Fdtc.this, "取消订单失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        MyToast.SHownetworkField(Activity_OrderDetails_Fdtc.this);

                    }
                });

    }

    /**
     * 验证销核订单
     */
    public void shenheorder() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(Activity_OrderDetails_Fdtc.this, SpUtil.token, ""));
        params.addQueryStringParameter("orderCode", orderCode);
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET,
                URL.restaurantOrderorderVerification,
                params,
                new RequestCallBack<String>() {

                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_OrderDetails_Fdtc.this,R.style.AlertDialogStyle);
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
                            LoginBean loginBean = new Gson().fromJson(responseInfo.result, LoginBean.class);
                            if (loginBean.getHeader().getStatus() == 0) {
                                MyToast.SHow(Activity_OrderDetails_Fdtc.this, "销核订单成功");
                                finishthis();
                            } else
                                MyToast.SHow(Activity_OrderDetails_Fdtc.this, loginBean.getHeader().getMsg());

                        } catch (Exception e) {
                            MyToast.SHow(Activity_OrderDetails_Fdtc.this, "销核订单失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        MyToast.SHownetworkField(Activity_OrderDetails_Fdtc.this);

                    }
                });

    }

    public  void finishthis(){
        Intent i=new Intent();
        Bundle bd=new Bundle();
        bd.putInt("position",position);
        i.putExtras(bd);
        setResult(122,i);
        this.finish();

    }
}
