package activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import adapter.Adapter_Search_Jd;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import util.SpUtil;
import util.ToastUtil;
import view.DialogProgressbar;
import view.myListView.XListView;

/**
 * 搜索  饭店
 * Created by Administrator on 2016/10/11 0011.
 */
public class Activity_Search_Jd extends Activity implements XListView.IXListViewListener {

    @Bind(R.id.im_left_topbar)
    ImageView imLeftTopbar;
    @Bind(R.id.et_sousuo)
    EditText etSousuo;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.el_listView)
    ExpandableListView elListView;
    @Bind(R.id.tv_noresult)
    TextView tvNoresult;

    Adapter_Search_Jd search_jd;
    List<SelectOrderJdBean.DataBean.OrderListBean> list;
    @Bind(R.id.listView)
    XListView listView;

    int mPage = 1;
    boolean judge_Refresh=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Activity_RefundDetails_Jd.class);
                intent.putExtra("orderCode", list.get(position).getOrder_code() + "");
                startActivity(intent);
            }
        });

        //启用或禁用上拉加载更多功能。
        listView.setPullLoadEnable(false);
        //启用或禁用下拉刷新功能。
        listView.setPullRefreshEnable(true);

        listView.setXListViewListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!etSousuo.getText().toString().equals("")) {
            selectOrder();
        }
    }

    @OnClick({R.id.im_left_topbar, R.id.iv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_left_topbar:
                finish();
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(etSousuo.getWindowToken(), 0);
                break;
            case R.id.iv_search:
                if (etSousuo.getText().toString().equals("")) {
                    ToastUtil.show(getApplicationContext(), "请输入您要搜索的内容");
                    return;
                }
                if (isNumeric1(etSousuo.getText().toString())) {//数字
                    if (etSousuo.getText().toString().length() < 8) {
                        ToastUtil.show(getApplicationContext(), "订单号搜索不能小于8位");
                        return;
                    } else {
                        selectOrder();
                    }
                } else {
                    selectOrder();
                }

                break;
        }
    }

    public void selectOrder() {
        if (list != null) {
            list = new ArrayList<>();
        }
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        AuthenticationJson authenticationJson = new Gson().fromJson(SpUtil.getString(getApplicationContext(), "authenticationJson", ""), AuthenticationJson.class);
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");

        if (isNumeric1(etSousuo.getText().toString())) {//数字
            params.addQueryStringParameter("orderCode", etSousuo.getText().toString());
            params.addQueryStringParameter("type", "1");
            params.addQueryStringParameter("name", "");
        } else {//字符串
            params.addQueryStringParameter("orderCode", "");
            params.addQueryStringParameter("type", "1");
            params.addQueryStringParameter("name", etSousuo.getText().toString());
        }
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.selectOrder, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_Search_Jd.this,R.style.AlertDialogStyle);
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
                            SelectOrderJdBean bean = new Gson().fromJson(responseInfo.result, SelectOrderJdBean.class);
                            int i = bean.getHeader().getStatus();
                            if (i == 0) {
                                if (bean.getData().getOrderList() != null) {
                                    list = bean.getData().getOrderList();
                                    if (list.size() > 0) {
                                        tvNoresult.setVisibility(View.GONE);
                                        listView.setVisibility(View.VISIBLE);
                                        search_jd = new Adapter_Search_Jd(Activity_Search_Jd.this, list);
                                        listView.setAdapter(search_jd);

                                        onLoad();
                                    } else {
                                        tvNoresult.setVisibility(View.VISIBLE);
                                        listView.setVisibility(View.GONE);
                                    }
                                } else {
                                    tvNoresult.setVisibility(View.VISIBLE);
                                    listView.setVisibility(View.GONE);
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

    public static boolean isNumeric1(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }


    @Override
    public void onRefresh() {
        mPage=1;
        list.clear();
        judge_Refresh=true;
        selectOrder();
        //更新上方日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        listView.setRefreshTime(str);
    }

    @Override
    public void onLoadMore() {

    }

    private void onLoad() {
        listView.stopRefresh();
        listView.stopLoadMore();
    }
}
