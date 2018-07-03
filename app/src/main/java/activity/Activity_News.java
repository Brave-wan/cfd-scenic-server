package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.JsonBean.MyMessageBean;
import com.application.URL;
import com.cfd.business.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import util.SpUtil;
import util.ToastUtil;
import view.DialogProgressbar;
import view.myListView.XExpandableListView;
import view.myListView.XListView;

/**
 * 消息中心
 * Created by Administrator on 2016/7/27 0027.
 */
public class Activity_News extends Activity implements XListView.IXListViewListener {


    List<MyMessageBean.DataBean.RowsBean> list;

    @Bind(R.id.listView)
    XListView listView;

    int mPage = 1;
    boolean judge_Refresh = true;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Activity_MessageDetails.class);
                intent.putExtra("dic", list.get(position-1).getDetailId() + "");
                startActivity(intent);
            }
        });

        mPage=1;
        list=new ArrayList<>();
        adapter=new MyAdapter();
        listView.setAdapter(adapter);

        //启用或禁用上拉加载更多功能。
        listView.setPullLoadEnable(true);
        //启用或禁用下拉刷新功能。
        listView.setPullRefreshEnable(true);

        listView.setXListViewListener(this);

        myMessage();
    }

    private void myMessage() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        params.addQueryStringParameter("rows", 10 + "");
        params.addQueryStringParameter("page", mPage + "");
        params.addQueryStringParameter("userType", 0 + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.myMessage, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_News.this, R.style.AlertDialogStyle);

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
                            MyMessageBean bean = new Gson().fromJson(responseInfo.result, MyMessageBean.class);
                            int i = bean.getHeader().getStatus();
                            if (i == 0) {
                                if (bean.getData().getRows()!=null){
                                    list.addAll(bean.getData().getRows());
                                    adapter.notifyDataSetChanged();
                                    //分页
                                    onLoad();
                                    if (mPage == bean.getData().getLastPage()) {
                                        judge_Refresh = false;
                                        listView.setFooterTextView("已加载显示完全部内容");
                                    }
                                }else {
                                    judge_Refresh = false;
                                    listView.setFooterTextView("已加载显示完全部内容");
                                }
                            } else {
                                ToastUtil.show(getApplicationContext(), bean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
                    }
                });
    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CyzMode cyzMode;
            if (convertView == null) {
                cyzMode = new CyzMode();
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.adapter_news, null);
                cyzMode.ll_backage = (ImageView) convertView.findViewById(R.id.ll_backage);
                cyzMode.tv_context = (TextView) convertView.findViewById(R.id.tv_context);
                cyzMode.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
                convertView.setTag(cyzMode);
            } else {
                cyzMode = (CyzMode) convertView.getTag();
            }

            ImageLoader.getInstance().displayImage(list.get(position).getImage(), cyzMode.ll_backage);
            cyzMode.tv_context.setText(list.get(position).getTitle());
            cyzMode.tv_date.setText(list.get(position).getCreateDate() + "");


            return convertView;
        }

        private class CyzMode {
            ImageView ll_backage;
            TextView tv_context;
            TextView tv_date;
        }
    }


    @Override
    public void onRefresh() {
        mPage = 1;
        list.clear();
        judge_Refresh = true;
        myMessage();
        //更新上方日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        listView.setRefreshTime(str);
    }

    @Override
    public void onLoadMore() {
    //判断是否到底部  还是否需要刷新
        if (judge_Refresh) {
            mPage = mPage + 1;
            myMessage();
        } else {
            onLoad();
            listView.setFooterTextView("已加载显示完全部内容");
        }
    }

    private void onLoad() {
        listView.stopRefresh();
        listView.stopLoadMore();
    }
}
