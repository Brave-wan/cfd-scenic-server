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

import org.apache.http.protocol.HTTP;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import activity.Activity_OrderDetails_Fddp;
import adapter.Adapter_DanPin;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.SpUtil;
import view.myListView.XListView;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class Fragment_Danpin extends Fragment implements XListView.IXListViewListener {
    @Bind(R.id.ll_wudingdan)
    LinearLayout llWudingdan;
    @Bind(R.id.lv_fandian)
    XListView lvFandian;

    public int index;
    List<List<TaocanJson.DataBean.RowsBean>> list = new ArrayList<>();
    Adapter_DanPin adapter_danPin;
    int mPage = 1;
    boolean judge_Refresh = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fandian_list, null);
        ButterKnife.bind(this, view);

        mPage = 1;
        index = getArguments().getInt("index", 1);
        adapter_danPin = new Adapter_DanPin(getContext(), index, list);
        lvFandian.setAdapter(adapter_danPin);

       /* lvFandian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Activity_OrderDetails_Fddp.class);
                Bundle bundle = new Bundle();
                bundle.putInt("state", index);
                bundle.putInt("type", 2);
                bundle.putInt("goodsType", 0);
                bundle.putInt("position", position-1);
                bundle.putString("orderCode", list.get(position-1).get(0).getOrder_code());
                intent.putExtras(bundle);
                startActivityForResult(intent, 223);
            }
        });*/

        //启用或禁用上拉加载更多功能。
        lvFandian.setPullLoadEnable(true);
        //启用或禁用下拉刷新功能。
        //mListView.setPullRefreshEnable(true);

        lvFandian.setXListViewListener(this);
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
        list.clear();
        mPage = 1;
        Getdata();
    }

    /**
     * 获取数据
     */
    public void Getdata() {

        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getActivity(), SpUtil.token, ""));
        params.addQueryStringParameter("Page", mPage+"");
        params.addQueryStringParameter("rows", "20");
        params.addQueryStringParameter("status", index + "");
        params.addQueryStringParameter("type", "0");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.POST,
                URL.restaurantOrderorderList,
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
                            TaocanJson taocanJson = new Gson().fromJson(responseInfo.result, TaocanJson.class);

                            if (taocanJson.getHeader().getStatus() == 0) {
                                if (taocanJson.getData().getRows() != null) {
                                    list.addAll(taocanJson.getData().getRows());
                                    if (list.size() > 0) {
                                        llWudingdan.setVisibility(View.GONE);
                                        lvFandian.setVisibility(View.VISIBLE);
                                        adapter_danPin.notifyDataSetChanged();

                                        //分页
                                        onLoad();
                                        if (mPage == taocanJson.getData().getLastPage()) {
                                            judge_Refresh = false;
                                            lvFandian.setFooterTextView("已加载显示完全部内容");
                                        }
                                    } else {
                                        llWudingdan.setVisibility(View.VISIBLE);
                                        lvFandian.setVisibility(View.GONE);
                                    }
                                } else {
                                    llWudingdan.setVisibility(View.VISIBLE);
                                    lvFandian.setVisibility(View.GONE);
                                }
                            } else
                                MyToast.SHow(getActivity(), taocanJson.getHeader().getMsg());

                        } catch (Exception e) {
                            MyToast.SHow(getActivity(), "数据获取失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        MyToast.SHownetworkField(getActivity());

                    }
                });
    }

/*    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;

        if (requestCode == 223) {
            int pos = data.getIntExtra("position", 0);
            list.remove(pos);
            adapter_danPin.notifyDataSetChanged();

        }
    }*/

    @Override
    public void onRefresh() {
        //初始化数据
        mPage = 1;
        list.clear();
        judge_Refresh = true;
        lvFandian.setFooterTextView("显示更多");
        //访问网络
        Getdata();
        //更新上方日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        lvFandian.setRefreshTime(str);
    }

    @Override
    public void onLoadMore() {
        //判断是否到底部  还是否需要刷新
        if (judge_Refresh) {
            mPage = mPage + 1;
            Getdata();
        } else {
            onLoad();
            lvFandian.setFooterTextView("已加载显示完全部内容");
        }
    }


    private void onLoad() {
        lvFandian.stopRefresh();
        lvFandian.stopLoadMore();
    }

}
