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

import com.JsonBean.ShopOrderListSPBean;
import com.application.URL;
import com.cfd.business.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import activity.Activity_Main;
import activity.Activity_OrderDetails_Sp;
import adapter.Adapter_ShangPin;
import adapter.Adapter_ShangPinEL;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.SpUtil;
import util.ToastUtil;
import view.myListView.XListView;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class Fragment_SpInside extends Fragment implements  XListView.IXListViewListener {


    Adapter_ShangPinEL adapterEl;
    Adapter_ShangPin adapter;
    public  List<List<ShopOrderListSPBean.DataBean.OrderListBean.RowsBean>> list;
    public int mPage = 1;
    boolean judge_Refresh = true;


    @Bind(R.id.ll_wudingdan)
    LinearLayout llWudingdan;
    @Bind(R.id.listView)
    XListView elListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xlistview, null);
        ButterKnife.bind(this, view);


        //elListView.setGroupIndicator(null);

        //启用或禁用上拉加载更多功能。
        elListView.setPullLoadEnable(true);
        //启用或禁用下拉刷新功能。
        //mListView.setPullRefreshEnable(true);

        elListView.setXListViewListener(this);

        mPage = 1;
        list=new ArrayList<>();
        adapter=new Adapter_ShangPin(getActivity(),list);
        elListView.setAdapter(adapter);

        /*elListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Activity_OrderDetails_Sp.class);
                intent.putExtra("orderCode", list.get(position).get(1).getOrder_code()+"");
                startActivity(intent);
            }
        });*/

        shopOrderList();
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
      /*  mPage=1;
        list.clear();
        shopOrderList();*/
    }


    public void shopOrderList() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getActivity(), SpUtil.token, ""));
        params.addQueryStringParameter("status", getArguments().getInt("state") + "");
        params.addQueryStringParameter("page", mPage + "");
        params.addQueryStringParameter("rows", 20 + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.shopOrderList, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            ShopOrderListSPBean shopOrderListSPBean = new Gson().fromJson(responseInfo.result, ShopOrderListSPBean.class);
                            int i = shopOrderListSPBean.getHeader().getStatus();
                            if (i == 0) {
                                //角标
                                if (getArguments().getInt("state") == 1) {
                                    Activity_Main activity_main = (Activity_Main) getActivity();
                                    if (shopOrderListSPBean.getData().getOrderCount() > 0) {
                                        activity_main.main_Corner(1, shopOrderListSPBean.getData().getOrderList().getTotal());
                                    } else {
                                        activity_main.main_Corner(1, 0);
                                    }
                                }

                                if (shopOrderListSPBean.getData().getOrderList() != null) {
                                    list.addAll(shopOrderListSPBean.getData().getOrderList().getRows());
                                    if (list.size() > 0) {
                                        elListView.setVisibility(View.VISIBLE);
                                        llWudingdan.setVisibility(View.GONE);

                                        adapter.notifyDataSetChanged();
                                        //分页
                                        onLoad();
                                        if (mPage == shopOrderListSPBean.getData().getOrderList().getLastPage()) {
                                            judge_Refresh = false;
                                            elListView.setFooterTextView("已加载显示完全部内容");
                                        }
                                    } else {
                                        elListView.setVisibility(View.GONE);
                                        llWudingdan.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    elListView.setVisibility(View.GONE);
                                    llWudingdan.setVisibility(View.VISIBLE);
                                }
                            } else {
                                ToastUtil.show(getContext(), shopOrderListSPBean.getHeader().getMsg());
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

    @Override
    public void onRefresh() {
        //初始化数据
        mPage = 1;
        list.clear();
        judge_Refresh = true;
        elListView.setFooterTextView("显示更多");
        //访问网络
        shopOrderList();
        //更新上方日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        elListView.setRefreshTime(str);
    }

    @Override
    public void onLoadMore() {
        //判断是否到底部  还是否需要刷新
        if (judge_Refresh) {
            mPage = mPage + 1;
            shopOrderList();
        } else {
            onLoad();
            elListView.setFooterTextView("已加载显示完全部内容");
        }
    }


    private void onLoad() {
        elListView.stopRefresh();
        elListView.stopLoadMore();
    }
}
