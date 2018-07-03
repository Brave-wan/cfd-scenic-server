package adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.JsonBean.LoginBean;
import com.JsonBean.OrderCountJdBean;
import com.JsonBean.SelectOrderJdBean;
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

import activity.Activity_RefundDetails_Jd;
import activity.Activity_Search_Jd;
import activity.Activity_Statistics;
import util.SpUtil;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class Adapter_Search_Jd extends BaseAdapter {
    Context mContext;
    Activity_Search_Jd activity;
    List<SelectOrderJdBean.DataBean.OrderListBean> rows;

    public Adapter_Search_Jd(Context mContext, List<SelectOrderJdBean.DataBean.OrderListBean> list) {
        this.mContext = mContext;
        this.rows = list;
        activity= (Activity_Search_Jd) mContext;
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
        CyzMode cyzMode = null;
        if (cyzMode == null) {
            cyzMode = new CyzMode();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_jiudian, null);
            cyzMode.bt_quxiao = (Button) convertView.findViewById(R.id.bt_quxiao);
            cyzMode.bt_yanzheng = (Button) convertView.findViewById(R.id.bt_yanzheng);
            cyzMode.bt_wancheng = (Button) convertView.findViewById(R.id.bt_wancheng);
            cyzMode.bt_guoqi = (Button) convertView.findViewById(R.id.bt_guoqi);

            cyzMode.tv_OrderNumber = (TextView) convertView.findViewById(R.id.tv_OrderNumber);
            cyzMode.iv_touxiang = (RoundImageView) convertView.findViewById(R.id.iv_touxiang);
            cyzMode.tv_userName = (TextView) convertView.findViewById(R.id.tv_userName);
            cyzMode.tv_commodityName = (TextView) convertView.findViewById(R.id.tv_commodityName);
            cyzMode.tv_fangjianshu = (TextView) convertView.findViewById(R.id.tv_fangjianshu);
            cyzMode.tv_ruzhu = (TextView) convertView.findViewById(R.id.tv_ruzhu);
            cyzMode.tv_lidian = (TextView) convertView.findViewById(R.id.tv_lidian);
            cyzMode.tv_jiwan = (TextView) convertView.findViewById(R.id.tv_jiwan);
            cyzMode.tv_zong = (TextView) convertView.findViewById(R.id.tv_zong);
            cyzMode.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            cyzMode.tv_daishenhe = (TextView) convertView.findViewById(R.id.tv_daishenhe);
            cyzMode.tv_yituikuan = (TextView) convertView.findViewById(R.id.tv_yituikuan);
            convertView.setTag(cyzMode);

        } else {
            cyzMode = (CyzMode) convertView.getTag();
        }
        initView(cyzMode);

        //判断状态
        if (rows.get(position).getOrder_state() == 2) {
            cyzMode.bt_yanzheng.setVisibility(View.VISIBLE);
            cyzMode.bt_quxiao.setVisibility(View.VISIBLE);
        } else if (rows.get(position).getOrder_state() == 3) {
            cyzMode.tv_daishenhe.setVisibility(View.VISIBLE);
        } else if (rows.get(position).getOrder_state() == 4) {
            cyzMode.bt_wancheng.setVisibility(View.VISIBLE);
        } else if (rows.get(position).getOrder_state() == 5) {
            cyzMode.bt_guoqi.setVisibility(View.VISIBLE);
        } else if (rows.get(position).getOrder_state() == 6) {
            cyzMode.tv_yituikuan.setVisibility(View.VISIBLE);
        } else if (rows.get(position).getOrder_state() == 7) {

        }


        cyzMode.tv_OrderNumber.setText("订单号:" + rows.get(position).getOrder_code());
        ImageLoader.getInstance().displayImage(rows.get(position).getHead_img(), cyzMode.iv_touxiang);
        cyzMode.tv_userName.setText(rows.get(position).getNick_name());
        cyzMode.tv_commodityName.setText(rows.get(position).getName());
        cyzMode.tv_fangjianshu.setText("房间数:" + rows.get(position).getQuantity() + "");
        cyzMode.tv_ruzhu.setText(rows.get(position).getStart_date());
        cyzMode.tv_lidian.setText(rows.get(position).getEnd_date());
        cyzMode.tv_jiwan.setText(rows.get(position).getCheck_days() + "晚");
        cyzMode.tv_zong.setText("￥" + rows.get(position).getReal_price());
        ImageLoader.getInstance().displayImage(rows.get(position).getDescribe_img(), cyzMode.imageView);


        cyzMode.bt_quxiao.setOnClickListener(new SetOnClick(position));
        cyzMode.bt_yanzheng.setOnClickListener(new SetOnClick(position));
        cyzMode.tv_daishenhe.setOnClickListener(new SetOnClick(position));

        return convertView;
    }

    private void initView(CyzMode cyzMode) {
        cyzMode.bt_guoqi.setVisibility(View.GONE);
        cyzMode.bt_wancheng.setVisibility(View.GONE);
        cyzMode.bt_yanzheng.setVisibility(View.GONE);
        cyzMode.bt_quxiao.setVisibility(View.GONE);
        cyzMode.tv_daishenhe.setVisibility(View.GONE);
        cyzMode.tv_yituikuan.setVisibility(View.GONE);
    }

    private class SetOnClick implements View.OnClickListener {
        int position;
        Dialog dialog;
        TextView text;

        public SetOnClick(int position) {
            this.position = position;

            dialog= new Dialog(mContext, R.style.AlertDialogStyle);
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
                            Cancelorder(position);
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
                            shenheorder(position);
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
                case R.id.tv_daishenhe:
                    Intent intent = new Intent(mContext, Activity_RefundDetails_Jd.class);
                    intent.putExtra("orderCode",rows.get(position).getOrder_code()+"");
                    mContext.startActivity(intent);
                    break;
            }

        }
    }

    class CyzMode {
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
        TextView tv_daishenhe;
        TextView tv_yituikuan;

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
        http.send(HttpRequest.HttpMethod.GET, URL.hotelOrdercancelOrder, params, new RequestCallBack<String>() {
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
        http.send(HttpRequest.HttpMethod.GET, URL.hotelOrderorderVerification, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    LoginBean loginBean = new Gson().fromJson(responseInfo.result, LoginBean.class);
                    if (loginBean.getHeader().getStatus() == 0) {
                        MyToast.SHow(mContext, "销核订单成功");
                        activity.selectOrder();
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