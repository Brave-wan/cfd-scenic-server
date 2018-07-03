package adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.JsonBean.JiudiansecondJson;
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
import com.mytools.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.protocol.HTTP;

import java.util.List;

import util.SpUtil;
import view.DialogCancelOrder;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class Adapter_JiuDian extends BaseAdapter {
    Context mContext;
    int i;
    List<JiudiansecondJson.DataBean.RowsBean> rows;

    public Adapter_JiuDian(Context mContext, int i, List<JiudiansecondJson.DataBean.RowsBean> rows) {
        this.mContext = mContext;
        this.i = i;
        this.rows = rows;
    }

    @Override
    public int getCount() {
        return rows.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        Viewholder viewholder = null;
        if (viewholder == null) {
            viewholder = new Viewholder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_jiudian, null);
            viewholder.bt_quxiao = (Button) convertView.findViewById(R.id.bt_quxiao);
            viewholder.bt_yanzheng = (Button) convertView.findViewById(R.id.bt_yanzheng);
            viewholder.bt_wancheng = (Button) convertView.findViewById(R.id.bt_wancheng);
            viewholder.bt_guoqi = (Button) convertView.findViewById(R.id.bt_guoqi);

            viewholder.tv_OrderNumber = (TextView) convertView.findViewById(R.id.tv_OrderNumber);
            viewholder.iv_touxiang = (RoundImageView) convertView.findViewById(R.id.iv_touxiang);
            viewholder.tv_userName = (TextView) convertView.findViewById(R.id.tv_userName);
            viewholder.tv_commodityName = (TextView) convertView.findViewById(R.id.tv_commodityName);
            viewholder.tv_fangjianshu = (TextView) convertView.findViewById(R.id.tv_fangjianshu);
            viewholder.tv_ruzhu = (TextView) convertView.findViewById(R.id.tv_ruzhu);
            viewholder.tv_lidian = (TextView) convertView.findViewById(R.id.tv_lidian);
            viewholder.tv_jiwan = (TextView) convertView.findViewById(R.id.tv_jiwan);
            viewholder.tv_zong = (TextView) convertView.findViewById(R.id.tv_zong);
            viewholder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewholder);

        } else {
            viewholder = (Viewholder) convertView.getTag();
        }


        if (i == 1) {
            viewholder.bt_wancheng.setVisibility(View.GONE);
            viewholder.bt_guoqi.setVisibility(View.GONE);
            viewholder.bt_yanzheng.setVisibility(View.VISIBLE);
            //viewholder.bt_quxiao.setVisibility(View.VISIBLE);
            viewholder.bt_quxiao.setOnClickListener(new View.OnClickListener() {
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
            viewholder.bt_yanzheng.setOnClickListener(new View.OnClickListener() {
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

        } else if (i == 2) {
            viewholder.bt_wancheng.setVisibility(View.VISIBLE);
            viewholder.bt_guoqi.setVisibility(View.GONE);
            viewholder.bt_yanzheng.setVisibility(View.GONE);
            viewholder.bt_quxiao.setVisibility(View.GONE);
        } else if (i == 3) {
            viewholder.bt_guoqi.setVisibility(View.VISIBLE);
            viewholder.bt_wancheng.setVisibility(View.GONE);
            viewholder.bt_yanzheng.setVisibility(View.GONE);
            viewholder.bt_quxiao.setVisibility(View.GONE);
        }

        viewholder.tv_OrderNumber.setText("订单号:" + rows.get(position).getOrder_code());
        ImageLoader.getInstance().displayImage(rows.get(position).getHead_img(), viewholder.iv_touxiang);
        viewholder.tv_userName.setText(rows.get(position).getNick_name());
        viewholder.tv_commodityName.setText(rows.get(position).getName());
        viewholder.tv_fangjianshu.setText("房间数:" + rows.get(position).getQuantity() + "");
        viewholder.tv_ruzhu.setText(rows.get(position).getStart_date());
        viewholder.tv_lidian.setText(rows.get(position).getEnd_date());
        viewholder.tv_jiwan.setText(rows.get(position).getCheck_days() + "晚");
        viewholder.tv_zong.setText("￥" + rows.get(position).getReal_price());
        ImageLoader.getInstance().displayImage(rows.get(position).getDescribe_img(), viewholder.imageView);

//        //设制高度
//        int edgeLength = viewholder.imageView.getHeight() ;
//        int edgeLengtW = viewholder.imageView.getHeight();
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(edgeLength, edgeLengtW);
//        viewholder.imageView.setLayoutParams(layoutParams);


        return convertView;
    }

    class Viewholder {
        TextView tv_OrderNumber;//订单号
        RoundImageView iv_touxiang; //预订人头像
        TextView tv_userName; //预订人
        TextView tv_commodityName; //酒店名
        TextView tv_fangjianshu; //房间数
        TextView tv_ruzhu; //入住时间
        TextView tv_lidian; //离店时间
        TextView tv_jiwan; //几晚
        TextView tv_zong;//总额
        ImageView imageView; //酒店展示图

        Button bt_quxiao; //取消订单
        Button bt_yanzheng; //验证审核
        Button bt_wancheng; //交易完成
        Button bt_guoqi; //已过期


    }

    /**
     * 取消订单
     */
    public void Cancelorder(final int index) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpUtil.token, ""));
        params.addQueryStringParameter("orderCode", rows.get(index).getOrder_code());
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET,
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
                                MyToast.SHow(mContext, "取消订单成功");
                                rows.remove(index);
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
        params.addQueryStringParameter("orderCode", rows.get(index).getOrder_code());
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
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
                                MyToast.SHow(mContext, "销核订单成功");
                                rows.remove(index);
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
}