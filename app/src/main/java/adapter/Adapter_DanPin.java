package adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
import com.JsonBean.LoginBean;
import com.JsonBean.TaocanJson;
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

import activity.Activity_OrderDetails_Fddp;
import activity.Activity_OrderDetails_Fdtc;
import util.SpUtil;
import view.DialogCancelOrder;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class Adapter_DanPin extends BaseAdapter {

    Context mContext;
    int state = 1;
    List<List<TaocanJson.DataBean.RowsBean>> list;

    public Adapter_DanPin(Context mContext, int state, List<List<TaocanJson.DataBean.RowsBean>> list) {
        this.mContext = mContext;
        this.state = state;
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

        Viewhoder viewhoder = null;
        if (viewhoder == null) {
            viewhoder = new Viewhoder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_fandian_danpin, null);
            viewhoder.tv_quxiao = (TextView) convertView.findViewById(R.id.tv_quxiao);
            viewhoder.tv_yanzheng = (TextView) convertView.findViewById(R.id.tv_yanzheng);
            viewhoder.tv_wancheng = (TextView) convertView.findViewById(R.id.tv_wancheng);
            viewhoder.tv_guoqi = (TextView) convertView.findViewById(R.id.tv_guoqi);
            viewhoder.tv_dingdanhao_danpin = (TextView) convertView.findViewById(R.id.tv_dingdanhao_danpin);
            viewhoder.iv_touxiang = (RoundImageView) convertView.findViewById(R.id.iv_touxiang);
            viewhoder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
            viewhoder.noscrolllistview = (NoScrollListView) convertView.findViewById(R.id.noscrolllistview);
            convertView.setTag(viewhoder);
        } else {
            viewhoder = (Viewhoder) convertView.getTag();
        }
        viewhoder.noscrolllistview.setClickable(false);

        viewhoder.tv_dingdanhao_danpin.setText("订单号：" + list.get(position).get(0).getOrder_code());
        ImageLoader.getInstance().displayImage(list.get(position).get(0).getHead_img(), viewhoder.iv_touxiang);
        viewhoder.tv_username.setText(list.get(position).get(0).getNick_name());
        Myadpter myadpter = new Myadpter(mContext, list.get(position));
        viewhoder.noscrolllistview.setAdapter(myadpter);

        initView(viewhoder);
        if (state == 1) {
            //viewhoder.tv_quxiao.setVisibility(View.VISIBLE);
            viewhoder.tv_yanzheng.setVisibility(View.VISIBLE);
            viewhoder.tv_quxiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog1 = new Dialog(mContext, R.style.AlertDialogStyle);
                    dialog1.getWindow().setContentView(R.layout.dialog_cancel_order);
                    dialog1.setCanceledOnTouchOutside(false);
                    dialog1.show();
                    TextView text = (TextView) dialog1.getWindow().findViewById(R.id.text_cnacel_order);
                    dialog1.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定


                            Cancelorder(position);
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
            viewhoder.tv_yanzheng.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog1 = new Dialog(mContext, R.style.AlertDialogStyle);
                    dialog1.getWindow().setContentView(R.layout.dialog_cancel_order);
                    dialog1.setCanceledOnTouchOutside(false);
                    dialog1.show();
                    TextView text = (TextView) dialog1.getWindow().findViewById(R.id.text_cnacel_order);
                    text.setText("确定销核该订单？");
                    dialog1.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定


                            shenheorder(position);
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
        } else if (state == 2) {
            viewhoder.tv_wancheng.setVisibility(View.VISIBLE);
        } else if (state == 3) {
            viewhoder.tv_guoqi.setVisibility(View.VISIBLE);
        }

        viewhoder.noscrolllistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int cposition, long id) {
                Intent intent = new Intent();
                intent.setClass(mContext, Activity_OrderDetails_Fddp.class);
                Bundle bundle = new Bundle();
                bundle.putInt("state", state);
                bundle.putInt("type", 2);
                bundle.putInt("goodsType", 0);
                bundle.putInt("position", position);
                bundle.putString("orderCode", list.get(position).get(0).getOrder_code());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    private void initView(Viewhoder viewhoder) {
        viewhoder.tv_guoqi.setVisibility(View.GONE);
        viewhoder.tv_quxiao.setVisibility(View.GONE);
        viewhoder.tv_wancheng.setVisibility(View.GONE);
        viewhoder.tv_yanzheng.setVisibility(View.GONE);
    }

    class Viewhoder {
        TextView tv_quxiao;  //取消
        TextView tv_yanzheng;  //审核
        TextView tv_wancheng;  //已完成
        TextView tv_guoqi;  //已过期
        TextView tv_dingdanhao_danpin; //订单号
        RoundImageView iv_touxiang; //头像
        TextView tv_username; //用户名
        NoScrollListView noscrolllistview;


    }

    /**
     * 取消订单
     */
    public void Cancelorder(final int index) {
        AuthenticationJson authenticationJson = new Gson().fromJson(SpUtil.getString(mContext, "authenticationJson", ""), AuthenticationJson.class);
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpUtil.token, ""));
        params.addQueryStringParameter("orderCode", list.get(index).get(0).getOrder_code());
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
                                MyToast.SHow(mContext, "取消订单成功");
                                list.remove(index);
                                notifyDataSetChanged();
                            } else
                                MyToast.SHow(mContext, loginBean.getHeader().getMsg());

                        } catch (Exception e) {
                            MyToast.SHow(mContext, "取消订单失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        MyToast.SHownetworkField(mContext);

                    }
                });

    }

    /**
     * 验证销核订单
     */
    public void shenheorder(final int index) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpUtil.token, ""));
        params.addQueryStringParameter("orderCode", list.get(index).get(0).getOrder_code());
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
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
                                MyToast.SHow(mContext, "销核订单成功");
                                list.remove(index);
                                notifyDataSetChanged();
                            } else
                                MyToast.SHow(mContext, loginBean.getHeader().getMsg());

                        } catch (Exception e) {
                            MyToast.SHow(mContext, "销核订单失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        MyToast.SHownetworkField(mContext);

                    }
                });

    }


    class Myadpter extends BaseAdapter {
        Context mContext;

        List<TaocanJson.DataBean.RowsBean> list;

        public Myadpter(Context mContext, List<TaocanJson.DataBean.RowsBean> list) {
            this.mContext = mContext;
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
            Holder viewhoder = null;
            if (viewhoder == null) {
                viewhoder = new Holder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.fandiandanpinseconditem, null);
                viewhoder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                viewhoder.ettime = (TextView) convertView.findViewById(R.id.ettime);
                viewhoder.number = (TextView) convertView.findViewById(R.id.number);
                viewhoder.zonge = (TextView) convertView.findViewById(R.id.zonge);
                viewhoder.imageView = (ImageView) convertView.findViewById(R.id.imageView);

                convertView.setTag(viewhoder);
            } else {
                viewhoder = (Holder) convertView.getTag();
            }
//            viewhoder.number.setVisibility(View.GONE);
            viewhoder.tv_name.setText(list.get(position).getName());
            viewhoder.ettime.setText(list.get(position).getEat_date());
            viewhoder.number.setText("数量：" + list.get(position).getQuantity());
            viewhoder.zonge.setText("￥" + list.get(position).getReal_price());
            ImageLoader.getInstance().displayImage(list.get(position).getDescribe_img(), viewhoder.imageView);


            return convertView;
        }

        class Holder {
            TextView tv_name; //名称
            TextView ettime; //就餐时间
            TextView number; //数量
            TextView zonge; //总额
            ImageView imageView; //缩略图
        }
    }


}
