package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.JsonBean.AuthenticationJson;
import com.JsonBean.FdRefundOrderBean;
import com.application.URL;
import com.cfd.business.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import activity.Activity_RefundDetails_Fd;
import adapter.Adapter_RefundOrder_Fd;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.SpUtil;
import util.ToastUtil;
import view.DialogProgressbar;

/**
 * 退款订单
 * Created by Administrator on 2016/9/5 0005.
 */
public class Fragment_RefundOrder_Fd extends Fragment {


    @Bind(R.id.listView)
    ListView listView;
    Adapter_RefundOrder_Fd adapter;

    List<List<FdRefundOrderBean.DataBean>> list;
    @Bind(R.id.ll_wudingdan)
    LinearLayout llWudingdan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        ButterKnife.bind(this, view);


        list=new ArrayList<>();
        adapter = new Adapter_RefundOrder_Fd(getContext(), list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Activity_RefundDetails_Fd.class);
                intent.putExtra("orderCode", list.get(position).get(0).getOrder_code() + "");
                startActivity(intent);
            }
        });

        restaurantRefundOrder();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public void restaurantRefundOrder() {
        list.clear();

        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getActivity(), SpUtil.token, ""));
        AuthenticationJson authenticationJson = new Gson().fromJson(SpUtil.getString(getContext(), "authenticationJson", ""), AuthenticationJson.class);
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
        params.addQueryStringParameter("type", getArguments().getInt("state") + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.restaurantRefundOrder, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(getActivity(),R.style.AlertDialogStyle);
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
                            FdRefundOrderBean fdRefundOrderBean = new Gson().fromJson(responseInfo.result, FdRefundOrderBean.class);
                            int i = fdRefundOrderBean.getHeader().getStatus();
                            if (i == 0) {
                                if (fdRefundOrderBean.getData()!=null){
                                    list.addAll(fdRefundOrderBean.getData());
                                    if (list.size()>0){
                                        llWudingdan.setVisibility(View.GONE);
                                        listView.setVisibility(View.VISIBLE);

                                        adapter.notifyDataSetChanged();
                                    }else {
                                        llWudingdan.setVisibility(View.VISIBLE);
                                        listView.setVisibility(View.GONE);
                                    }
                                }else {
                                    llWudingdan.setVisibility(View.VISIBLE);
                                    listView.setVisibility(View.GONE);
                                }


                            } else {
                                ToastUtil.show(getContext(), fdRefundOrderBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getContext(), s);
                    }
                });
    }
}
