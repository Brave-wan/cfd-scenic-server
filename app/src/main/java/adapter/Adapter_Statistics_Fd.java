package adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
import com.JsonBean.LoginBean;
import com.JsonBean.OrderCountFdBean;
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

import java.util.List;

import activity.Activity_RefundOrder_Fd;
import activity.Activity_Statistics;
import fragment.Fragment_RefundOrder_Fd;
import util.SpUtil;
import util.ToastUtil;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
public class Adapter_Statistics_Fd extends BaseExpandableListAdapter {
    List<List<OrderCountFdBean.DataBean.OrderListBean>> list_fd;
    Context mContext;

    public Adapter_Statistics_Fd(List<List<OrderCountFdBean.DataBean.OrderListBean>> list_fd, Context mContext) {
        this.list_fd = list_fd;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return list_fd.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list_fd.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list_fd.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list_fd.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView == null) {
            cyzMode = new CyzMode();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_statistics_fd_shang, null);
            cyzMode.tv_dingdanhao = (TextView) convertView.findViewById(R.id.tv_dingdanhao);
            cyzMode.iv_touxiang = (ImageView) convertView.findViewById(R.id.iv_touxiang);
            cyzMode.tv_userName = (TextView) convertView.findViewById(R.id.tv_userName);
            convertView.setTag(cyzMode);
        } else {
            cyzMode = (CyzMode) convertView.getTag();
        }

        cyzMode.tv_dingdanhao.setText("订单号：" + list_fd.get(groupPosition).get(0).getOrder_code());
        ImageLoader.getInstance().displayImage(list_fd.get(groupPosition).get(0).getUserHeadImg(), cyzMode.iv_touxiang);
        cyzMode.tv_userName.setText(list_fd.get(groupPosition).get(0).getNick_name());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView == null) {
            cyzMode = new CyzMode();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_statistics_fd_xia, null);
            cyzMode.ll_state = (LinearLayout) convertView.findViewById(R.id.ll_state);
            cyzMode.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            cyzMode.tv_commodityName = (TextView) convertView.findViewById(R.id.tv_commodityName);
            cyzMode.tv_jiucanshijian = (TextView) convertView.findViewById(R.id.tv_jiucanshijian);
            cyzMode.tv_shuliang = (TextView) convertView.findViewById(R.id.tv_shuliang);
            cyzMode.tv_zong = (TextView) convertView.findViewById(R.id.tv_zong);
            cyzMode.tv_quxiao = (TextView) convertView.findViewById(R.id.tv_quxiao);
            cyzMode.tv_yanzheng = (TextView) convertView.findViewById(R.id.tv_yanzheng);
            cyzMode.tv_wancheng = (TextView) convertView.findViewById(R.id.tv_wancheng);
            cyzMode.tv_guoqi = (TextView) convertView.findViewById(R.id.tv_guoqi);
            cyzMode.bt_tongguo= (Button) convertView.findViewById(R.id.bt_tongguo);
            cyzMode.bt_tuikuan= (Button) convertView.findViewById(R.id.bt_tuikuan);
            convertView.setTag(cyzMode);
        } else {
            cyzMode = (CyzMode) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(list_fd.get(groupPosition).get(childPosition).getDescribe_img(), cyzMode.imageView);
        cyzMode.tv_commodityName.setText(list_fd.get(groupPosition).get(childPosition).getGoods_name());
        cyzMode.tv_jiucanshijian.setText("" + list_fd.get(groupPosition).get(childPosition).getEat_date());
        cyzMode.tv_shuliang.setText("数量：" + list_fd.get(groupPosition).get(childPosition).getQuantity());
        cyzMode.tv_zong.setText("￥" + list_fd.get(groupPosition).get(childPosition).getReal_price());


        if (childPosition == getChildrenCount(groupPosition) - 1) {
            cyzMode.ll_state.setVisibility(View.VISIBLE);
            setState(list_fd.get(groupPosition).get(childPosition).getOrder_state(), cyzMode,groupPosition,childPosition);
        } else {
            cyzMode.ll_state.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void setState(int i, CyzMode cyzMode,int groupPosition,int childPosition) {
        cyzMode.tv_quxiao.setVisibility(View.GONE);
        cyzMode.tv_yanzheng.setVisibility(View.GONE);
        cyzMode.tv_wancheng.setVisibility(View.GONE);
        cyzMode.tv_guoqi.setVisibility(View.GONE);
        cyzMode.bt_tongguo.setVisibility(View.GONE);
        cyzMode.bt_tuikuan.setVisibility(View.GONE);
        if (i == 2) {
            cyzMode.tv_quxiao.setVisibility(View.VISIBLE);
            cyzMode.tv_yanzheng.setVisibility(View.VISIBLE);
        } else if (i == 3) {
            cyzMode.bt_tongguo.setVisibility(View.VISIBLE);
        } else if (i == 4) {
            cyzMode.tv_wancheng.setVisibility(View.VISIBLE);
        } else if (i == 5) {
            cyzMode.tv_guoqi.setVisibility(View.VISIBLE);
        } else if (i == 6) {
            cyzMode.bt_tuikuan.setVisibility(View.VISIBLE);
        } else if (i == 7) {

        }

        cyzMode.tv_quxiao.setOnClickListener(new SetOnClick(groupPosition,childPosition));
        cyzMode.tv_yanzheng.setOnClickListener(new SetOnClick(groupPosition,childPosition));
        cyzMode.bt_tongguo.setOnClickListener(new SetOnClick(groupPosition,childPosition));
    }

    private class SetOnClick implements View.OnClickListener {
        int groupPosition;
        int childPosition;
        Dialog dialog1;

        public SetOnClick(int groupPosition, int childPosition) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_quxiao:
                    dialog1 = new Dialog(mContext, R.style.AlertDialogStyle);
                    dialog1.getWindow().setContentView(R.layout.dialog_cancel_order);
                    dialog1.setCanceledOnTouchOutside(false);
                    dialog1.show();
                    TextView text1 = (TextView) dialog1.getWindow().findViewById(R.id.text_cnacel_order);
                    text1.setText("确定取消该订单？");
                    dialog1.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            Cancelorder(groupPosition);
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
                case R.id.tv_yanzheng:
                    dialog1 = new Dialog(mContext, R.style.AlertDialogStyle);
                    dialog1.getWindow().setContentView(R.layout.dialog_cancel_order);
                    dialog1.setCanceledOnTouchOutside(false);
                    dialog1.show();
                    TextView text2 = (TextView) dialog1.getWindow().findViewById(R.id.text_cnacel_order);
                    text2.setText("确定销核该订单？");
                    dialog1.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            shenheorder(groupPosition);
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
                    dialog1 = new Dialog(mContext, R.style.AlertDialogStyle);
                    dialog1.getWindow().setContentView(R.layout.dialog_cancel_order);
                    dialog1.setCanceledOnTouchOutside(false);
                    dialog1.show();
                    TextView text3 = (TextView) dialog1.getWindow().findViewById(R.id.text_cnacel_order);
                    text3.setText("确定退款给顾客？");
                    dialog1.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            shopRefundFinsh(groupPosition);
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

    private class CyzMode {
        TextView tv_dingdanhao;
        ImageView iv_touxiang;
        TextView tv_userName;

        LinearLayout ll_state;
        ImageView imageView;
        TextView tv_commodityName;
        TextView tv_jiucanshijian;
        TextView tv_shuliang;
        TextView tv_zong;

        TextView tv_quxiao;
        TextView tv_yanzheng;
        TextView tv_wancheng;
        TextView tv_guoqi;
        Button bt_tongguo;
        Button bt_tuikuan;

    }

    /**
     * 验证销核订单
     */
    public void shenheorder(final int index) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpUtil.token, ""));
        params.addQueryStringParameter("orderCode", list_fd.get(index).get(0).getOrder_code());
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
                                list_fd.remove(index);
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

    /**
     * 取消订单
     */
    public void Cancelorder(final int index) {
        AuthenticationJson authenticationJson= new Gson().fromJson(SpUtil.getString(mContext, "authenticationJson", ""), AuthenticationJson.class);
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpUtil.token, ""));
        params.addQueryStringParameter("orderCode", list_fd.get(index).get(0).getOrder_code());
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
                                list_fd.remove(index);
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

    //确认退款
    public void shopRefundFinsh(int index){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpUtil.token, ""));
        AuthenticationJson authenticationJson=new Gson().fromJson(SpUtil.getString(mContext,"authenticationJson",""),AuthenticationJson.class);
        params.addQueryStringParameter("type", 2 + "");
        params.addQueryStringParameter("shopUserId",SpUtil.getLong(mContext,SpUtil.userId,1)+"" );
        params.addQueryStringParameter("useId",list_fd.get(index).get(0).getUserId()+"");
        params.addQueryStringParameter("balance",list_fd.get(index).get(0).getReal_price()+"");
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
        params.addQueryStringParameter("orderCode", list_fd.get(index).get(0).getOrder_code());
        params.addQueryStringParameter("orderState", 6 + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.shopRefundFinsh, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject=new JSONObject(responseInfo.result);
                            JSONObject header=jsonObject.getJSONObject("header");
                            int i=header.getInt("status");
                            if(i==0){
                                Activity_Statistics activity= (Activity_Statistics) mContext;
                                activity.orderCount();
                            }else {
                                ToastUtil.show(mContext, header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(mContext, "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(mContext, s);
                    }
                });

    }
}
