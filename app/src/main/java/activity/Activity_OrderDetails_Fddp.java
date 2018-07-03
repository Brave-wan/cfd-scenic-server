package activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
import com.JsonBean.FandiandanpinJson;
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
import com.mytools.NoScrollListView;
import com.mytools.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.protocol.HTTP;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import util.SpUtil;
import view.DialogCancelOrder;
import view.DialogProgressbar;

/**
 * 订单详情--单品
 * Created by Administrator on 2016/7/28 0028.
 */
public class Activity_OrderDetails_Fddp extends Activity {


    int state;
    AuthenticationJson authenticationJson;
    int type;
    int position;
    String orderCode;
    int goodsType = 0;
    List<FandiandanpinJson.DataBean> listData;


    @Bind(R.id.tv_OrderNumber)
    TextView tvOrderNumber;
    @Bind(R.id.iv_touxiang)
    RoundImageView ivTouxiang;
    @Bind(R.id.tv_userName)
    TextView tvUserName;
    @Bind(R.id.nl_listView)
    NoScrollListView nlListView;
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
    @Bind(R.id.tv_jiucanshijian2)
    TextView tvJiucanshijian2;
    @Bind(R.id.tv_dianhua)
    TextView tvDianhua;
    @Bind(R.id.tv_zong1)
    TextView tvZong1;
    @Bind(R.id.noscrolllistview)
    NoScrollListView noscrolllistview;
    @Bind(R.id.scrollView4)
    ScrollView scrollView4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_fddp);
        ButterKnife.bind(this);

        scrollView4.smoothScrollTo(0, 0);

        authenticationJson = new Gson().fromJson(SpUtil.getString(this, "authenticationJson", ""), AuthenticationJson.class);
        Intent intent = getIntent();
        state = intent.getIntExtra("state", 1);
        type = intent.getIntExtra("type", 1);
        position = intent.getIntExtra("position", 0);
        goodsType = intent.getIntExtra("goodsType", 0);
        orderCode = intent.getStringExtra("orderCode");

        getdetile();
        if (state == 1) {
            btQuxiao.setVisibility(View.VISIBLE);
            btYanzheng.setVisibility(View.VISIBLE);
            btWancheng.setVisibility(View.GONE);
            btGuoqi.setVisibility(View.GONE);
            btQuxiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogCancelOrder dialogCancelOrder = new DialogCancelOrder(Activity_OrderDetails_Fddp.this, R.style.AlertDialogStyle);
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
                final Dialog dialog = new Dialog(Activity_OrderDetails_Fddp.this, R.style.AlertDialogStyle);
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
                final Dialog dialog1 = new Dialog(Activity_OrderDetails_Fddp.this, R.style.AlertDialogStyle);
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

        scrollView4.smoothScrollTo(0, 0);
    }


    /**
     * 获取订单详情
     */
    public void getdetile() {

        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(Activity_OrderDetails_Fddp.this, SpUtil.token, ""));
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

                    DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_OrderDetails_Fddp.this, R.style.AlertDialogStyle);

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
                            FandiandanpinJson fandiandanpinJson = new Gson().fromJson(responseInfo.result, FandiandanpinJson.class);
                            if (fandiandanpinJson.getHeader().getStatus() == 0) {

                                tvOrderNumber.setText("订单号：" + fandiandanpinJson.getData().get(0).getOrder_code());
                                ImageLoader.getInstance().displayImage(fandiandanpinJson.getData().get(0).getHead_img(), ivTouxiang);
                                tvUserName.setText(fandiandanpinJson.getData().get(0).getNick_name());
                                /*ImageLoader.getInstance().displayImage(fandiandanpinJson.getData().get(0).getDescribe_img(), imageView);
                                tvCommodityName.setText(fandiandanpinJson.getData().get(0).getName());
                                tvJiucanshijian.setText("就餐时间：" + fandiandanpinJson.getData().get(0).getEat_date());
                                tvShuliang.setText("数量：" + fandiandanpinJson.getData().get(0).getQuantity());
                                tvZong.setText("￥" + fandiandanpinJson.getData().get(0).getReal_price());*/
                                tvChuangjianshijian.setText("创建时间：" + fandiandanpinJson.getData().get(0).getCreate_time());
                                tvFukuanshijian.setText("付款时间：" + fandiandanpinJson.getData().get(0).getPay_time());
                                if (fandiandanpinJson.getData().get(0).getPay_way() == 1)
                                    tvZhifufangshi.setText("支付方式：" + "余额支付");
                                else if (fandiandanpinJson.getData().get(0).getPay_way() == 2)
                                    tvZhifufangshi.setText("支付方式：" + "支付宝支付");
                                else if (fandiandanpinJson.getData().get(0).getPay_way() == 3)
                                    tvZhifufangshi.setText("支付方式：" + "微信支付");
                                tvJiucanshijian2.setText("就餐时间：" + fandiandanpinJson.getData().get(0).getEat_date());
                                tvDianhua.setText("联系电话：" + fandiandanpinJson.getData().get(0).getTelphone());

                                double real = 0;
                                for (int index = 0; index < fandiandanpinJson.getData().size(); index++) {
                                    real = real + fandiandanpinJson.getData().get(index).getReal_price();
                                }
                                tvZong1.setText("总额：￥" + real);
                                Myadpter myadpter = new Myadpter(Activity_OrderDetails_Fddp.this, fandiandanpinJson.getData());
                                noscrolllistview.setAdapter(myadpter);

                                listData = fandiandanpinJson.getData();
                                nlListView.setAdapter(new DetailsFdDp(getApplicationContext(), listData));
                            } else
                                MyToast.SHow(Activity_OrderDetails_Fddp.this, fandiandanpinJson.getHeader().getMsg());

                        } catch (Exception e) {
                            MyToast.SHow(Activity_OrderDetails_Fddp.this, "数据获取失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        MyToast.SHownetworkField(Activity_OrderDetails_Fddp.this);

                    }
                });


    }


    class Myadpter extends BaseAdapter {
        Context context;
        List<FandiandanpinJson.DataBean> list;

        public Myadpter(Context context, List<FandiandanpinJson.DataBean> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Viewholder viewhoder = null;
            if (viewhoder == null) {
                viewhoder = new Viewholder();
                convertView = LayoutInflater.from(context).inflate(R.layout.fddpdetileitem, null);
                viewhoder.fddpdetileitem = (TextView) convertView.findViewById(R.id.fddpdetileitem);

                convertView.setTag(viewhoder);
            } else {
                viewhoder = (Viewholder) convertView.getTag();
            }
            viewhoder.fddpdetileitem.setText(list.get(position).getName() + "," + list.get(position).getQuantity() + "份," + list.get(position).getReal_price() + "元");
            return convertView;
        }

        class Viewholder {
            TextView fddpdetileitem;
        }
    }

    /**
     * 取消订单
     */
    public void Cancelorder() {
        AuthenticationJson authenticationJson = new Gson().fromJson(SpUtil.getString(Activity_OrderDetails_Fddp.this, "authenticationJson", ""), AuthenticationJson.class);
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(Activity_OrderDetails_Fddp.this, SpUtil.token, ""));
        params.addQueryStringParameter("orderCode", orderCode);
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
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
                                MyToast.SHow(Activity_OrderDetails_Fddp.this, "取消订单成功");
                                finishthis();
                            } else
                                MyToast.SHow(Activity_OrderDetails_Fddp.this, loginBean.getHeader().getMsg());

                        } catch (Exception e) {
                            MyToast.SHow(Activity_OrderDetails_Fddp.this, "取消订单失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        MyToast.SHownetworkField(Activity_OrderDetails_Fddp.this);

                    }
                });

    }

    /**
     * 验证销核订单
     */
    public void shenheorder() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(Activity_OrderDetails_Fddp.this, SpUtil.token, ""));
        params.addQueryStringParameter("orderCode", orderCode);
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET,
                URL.restaurantOrderorderVerification,
                params,
                new RequestCallBack<String>() {

                    DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_OrderDetails_Fddp.this, R.style.AlertDialogStyle);

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
                                MyToast.SHow(Activity_OrderDetails_Fddp.this, "销核订单成功");
                                finishthis();
                            } else
                                MyToast.SHow(Activity_OrderDetails_Fddp.this, loginBean.getHeader().getMsg());

                        } catch (Exception e) {
                            MyToast.SHow(Activity_OrderDetails_Fddp.this, "销核订单失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        MyToast.SHownetworkField(Activity_OrderDetails_Fddp.this);

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

    private class DetailsFdDp extends BaseAdapter {
        Context mContext;
        List<FandiandanpinJson.DataBean> listData;

        public DetailsFdDp(Context mContext, List<FandiandanpinJson.DataBean> listData) {
            this.mContext = mContext;
            this.listData = listData;
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CyzMode cyzMode;
            if (convertView == null) {
                cyzMode = new CyzMode();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_details_fddp, null);
                cyzMode.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                cyzMode.tv_commodityName = (TextView) convertView.findViewById(R.id.tv_commodityName);
                cyzMode.tv_jiucanshijian = (TextView) convertView.findViewById(R.id.tv_jiucanshijian);
                cyzMode.tv_shuliang = (TextView) convertView.findViewById(R.id.tv_shuliang);
                cyzMode.tv_zong = (TextView) convertView.findViewById(R.id.tv_zong);
                convertView.setTag(cyzMode);
            } else {
                cyzMode = (CyzMode) convertView.getTag();
            }

            ImageLoader.getInstance().displayImage(listData.get(position).getDescribe_img(), cyzMode.imageView);
            cyzMode.tv_commodityName.setText(listData.get(position).getName());
            cyzMode.tv_jiucanshijian.setText(listData.get(position).getEat_date());
            cyzMode.tv_shuliang.setText("数量：" + listData.get(position).getQuantity());
            cyzMode.tv_zong.setText("¥" + listData.get(position).getReal_price());

            return convertView;
        }

        private class CyzMode {
            ImageView imageView;
            TextView tv_commodityName;
            TextView tv_jiucanshijian;
            TextView tv_shuliang;
            TextView tv_zong;
        }
    }
}
