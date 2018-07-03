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
import com.JsonBean.RefundOrderJdBean;
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

import activity.Activity_RefundDetails_Jd;
import adapter.Adapter_RefundOrder_Jd;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.SpUtil;
import util.ToastUtil;
import view.DialogProgressbar;

/**
 * 退款订单
 * Created by Administrator on 2016/9/5 0005.
 */
public class Fragment_RefundOrder_Jd extends Fragment {


    Adapter_RefundOrder_Jd adapter;
    List<RefundOrderJdBean.DataBean> list;

    @Bind(R.id.ll_wudingdan)
    LinearLayout llWudingdan;
    @Bind(R.id.listView)
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        ButterKnife.bind(this, view);

        list=new ArrayList<>();
        adapter = new Adapter_RefundOrder_Jd(getContext(),list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Activity_RefundDetails_Jd.class);
                intent.putExtra("orderCode", list.get(position).getOrder_code() + "");
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        hotelRefundOrder();
    }

    public void hotelRefundOrder() {
        list.clear();
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getActivity(), SpUtil.token, ""));
        AuthenticationJson authenticationJson = new Gson().fromJson(SpUtil.getString(getContext(), "authenticationJson", ""), AuthenticationJson.class);
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
        params.addQueryStringParameter("type", getArguments().getInt("state") + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.hotelRefundOrder, params,
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
                            RefundOrderJdBean refundOrderJdBean = new Gson().fromJson(responseInfo.result, RefundOrderJdBean.class);
                            int i = refundOrderJdBean.getHeader().getStatus();
                            if (i == 0) {
                                if (refundOrderJdBean.getData() != null) {
                                    list.addAll(refundOrderJdBean.getData());
                                    if (list.size() > 0) {
                                        listView.setVisibility(View.VISIBLE);
                                        llWudingdan.setVisibility(View.GONE);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        listView.setVisibility(View.GONE);
                                        llWudingdan.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    listView.setVisibility(View.GONE);
                                    llWudingdan.setVisibility(View.VISIBLE);
                                }

                            } else {
                                ToastUtil.show(getActivity(), refundOrderJdBean.getHeader().getMsg());
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
