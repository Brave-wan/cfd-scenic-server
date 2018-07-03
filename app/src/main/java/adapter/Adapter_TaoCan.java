package adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mytools.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.protocol.HTTP;

import java.util.List;

import util.SpUtil;
import view.DialogCancelOrder;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class Adapter_TaoCan extends BaseAdapter {

    Context mContext;
    int state = 1;
    List<List<TaocanJson.DataBean.RowsBean>> list;

    public Adapter_TaoCan(Context mContext, int state, List<List<TaocanJson.DataBean.RowsBean>> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_fandian_taocan, null);
            viewholder.tv_quxiao = (TextView) convertView.findViewById(R.id.tv_quxiao);
            viewholder.tv_yanzheng = (TextView) convertView.findViewById(R.id.tv_yanzheng);
            viewholder.tv_wancheng = (TextView) convertView.findViewById(R.id.tv_wancheng);
            viewholder.tv_guoqi = (TextView) convertView.findViewById(R.id.tv_guoqi);
            viewholder.tv_dingdanhao = (TextView) convertView.findViewById(R.id.tv_dingdanhao);
            viewholder.iv_touxiang = (RoundImageView) convertView.findViewById(R.id.iv_touxiang);
            viewholder.tv_userName = (TextView) convertView.findViewById(R.id.tv_userName);
            viewholder.tv_commodityName = (TextView) convertView.findViewById(R.id.tv_commodityName);
            viewholder.tv_jiucanshijian = (TextView) convertView.findViewById(R.id.tv_jiucanshijian);
            viewholder.tv_shuliang = (TextView) convertView.findViewById(R.id.tv_shuliang);
            viewholder.tv_zong = (TextView) convertView.findViewById(R.id.tv_zong);
            viewholder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewholder);

        } else {
            viewholder = (Viewholder) convertView.getTag();
        }
        viewholder.tv_dingdanhao.setText("订单号："+list.get(position).get(0).getOrder_code());
        ImageLoader.getInstance().displayImage(list.get(position).get(0).getHead_img(),viewholder.iv_touxiang);
        viewholder.tv_userName.setText(list.get(position).get(0).getNick_name());
        viewholder.tv_commodityName.setText(list.get(position).get(0).getName());
        viewholder.tv_jiucanshijian.setText(list.get(position).get(0).getEat_date());
        viewholder.tv_shuliang.setText("数量："+list.get(position).get(0).getQuantity());
        viewholder.tv_zong.setText("￥"+list.get(position).get(0).getReal_price());


        initView(viewholder);
        if (state == 1) {
            //viewholder.tv_quxiao.setVisibility(View.VISIBLE);
            viewholder.tv_yanzheng.setVisibility(View.VISIBLE);
            viewholder.tv_quxiao.setOnClickListener(new View.OnClickListener() {
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
            viewholder.tv_yanzheng.setOnClickListener(new View.OnClickListener() {
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
            viewholder.tv_wancheng.setVisibility(View.VISIBLE);
        } else if (state == 3) {
            viewholder.tv_guoqi.setVisibility(View.VISIBLE);
        }



        return convertView;
    }

    private void initView(Viewholder viewholder) {
        viewholder.tv_guoqi.setVisibility(View.GONE);
        viewholder.tv_quxiao.setVisibility(View.GONE);
        viewholder.tv_wancheng.setVisibility(View.GONE);
        viewholder.tv_yanzheng.setVisibility(View.GONE);
    }

    class Viewholder {
        TextView tv_dingdanhao; //订单号
        RoundImageView iv_touxiang; //头像
        TextView tv_userName;// 客户昵称
        TextView tv_commodityName;//商户名
        TextView tv_jiucanshijian; //就餐时间
        TextView tv_shuliang;//数量
        TextView tv_zong; //总额
        ImageView imageView; //缩略图
        TextView tv_quxiao; //取消订单按钮
        TextView tv_yanzheng; //验证销核
        TextView tv_wancheng; //交易完成
        TextView tv_guoqi; //已过期


    }

    /**
     * 取消订单
     */
    public void Cancelorder(final int index) {
        AuthenticationJson authenticationJson= new Gson().fromJson(SpUtil.getString(mContext, "authenticationJson", ""), AuthenticationJson.class);
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpUtil.token, ""));
        params.addQueryStringParameter("orderCode", list.get(index).get(0).getOrder_code());
        params.addQueryStringParameter("siId", authenticationJson.getId()+"");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
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
}
