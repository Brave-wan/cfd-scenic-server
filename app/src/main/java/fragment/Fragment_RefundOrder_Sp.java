package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.JsonBean.AuthenticationJson;
import com.JsonBean.RefundORderSpBean;
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

import activity.Activity_RefundOrder_Sp;
import adapter.Adapter_RefundOrder_SpEl;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.SpUtil;
import util.ToastUtil;
import view.DialogProgressbar;

/**
 * 退款订单
 * Created by Administrator on 2016/9/5 0005.
 */
public class Fragment_RefundOrder_Sp extends Fragment {


    public List<List<RefundORderSpBean.DataBean.OrderListBean>> list;
    Adapter_RefundOrder_SpEl adapter;
    @Bind(R.id.ll_wudingdan)
    LinearLayout llWudingdan;
    @Bind(R.id.elListView)
    ExpandableListView elListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expandablelistview, null);
        ButterKnife.bind(this, view);

        list=new ArrayList<>();
        adapter = new Adapter_RefundOrder_SpEl(getActivity(), list);
        elListView.setAdapter(adapter);

        elListView.setGroupIndicator(null);
        shopRefundORder();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public void shopRefundORder() {
        list.clear();
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getActivity(), SpUtil.token, ""));
        AuthenticationJson authenticationJson = new Gson().fromJson(SpUtil.getString(getContext(), "authenticationJson", ""), AuthenticationJson.class);
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
        params.addQueryStringParameter("type", getArguments().getInt("state") + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.shopRefundORder, params,
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
                            RefundORderSpBean refundORderBean = new Gson().fromJson(responseInfo.result, RefundORderSpBean.class);
                            int i = refundORderBean.getHeader().getStatus();
                            if (i == 0) {
                                if (getArguments().getInt("state") == 1) {
                                    Activity_RefundOrder_Sp activity = (Activity_RefundOrder_Sp) getActivity();
                                    activity.setCorner(refundORderBean.getData().getOrderCount());
                                }
                                if (refundORderBean.getData().getOrderList() != null) {
                                    list.addAll(refundORderBean.getData().getOrderList());
                                    if (list.size() > 0) {
                                        elListView.setVisibility(View.VISIBLE);
                                        llWudingdan.setVisibility(View.GONE);

                                        adapter.notifyDataSetChanged();


                                        //全部展开
                                        for (int zk = 0; zk < adapter.getGroupCount(); zk++) {
                                            elListView.expandGroup(zk);
                                        }
                                        //不能收缩
                                        elListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                                            @Override
                                            public boolean onGroupClick(ExpandableListView parent, View v,
                                                                        int groupPosition, long id) {
                                                return true;
                                            }
                                        });
                                    } else {
                                        elListView.setVisibility(View.GONE);
                                        llWudingdan.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    elListView.setVisibility(View.GONE);
                                    llWudingdan.setVisibility(View.VISIBLE);
                                }

                            } else {
                                ToastUtil.show(getContext(), refundORderBean.getHeader().getMsg());
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
