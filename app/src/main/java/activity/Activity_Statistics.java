package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
import com.JsonBean.OrderCountFdBean;
import com.JsonBean.OrderCountJdBean;
import com.JsonBean.OrderCountSpBean;
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
import java.util.Date;
import java.util.List;

import adapter.Adapter_Statistics_Fd;
import adapter.Adapter_Statistics_Jd;
import adapter.Adapter_Statistics_Sp;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.SpUtil;
import util.ToastUtil;
import view.DialogProgressbar;
import view.MyListView;
import view.MyTopBar;
import view.NoScrollExpandableListView;

/**
 * 数据统计
 * Created by Administrator on 2016/7/27 0027.
 */
public class Activity_Statistics extends Activity {

    @Bind(R.id.view_myTopBar)
    MyTopBar viewMyTopBar;
    @Bind(R.id.tv_yingyee)
    TextView tvYingyee;
    @Bind(R.id.tv_dingdanshu)
    TextView tvDingdanshu;
    @Bind(R.id.ll_wudingdan)
    LinearLayout llWudingdan;
    @Bind(R.id.listView)
    MyListView listView;
    @Bind(R.id.el_listView)
    NoScrollExpandableListView elListView;

    String type;
    Adapter_Statistics_Fd adapter_Fd;
    Adapter_Statistics_Sp adapter_Sp;
    Adapter_Statistics_Jd adapter_jd;

    List<List<OrderCountFdBean.DataBean.OrderListBean>> list_fd;
    List<List<OrderCountSpBean.DataBean.OrderListBean>> list_sp;
    List<OrderCountJdBean.DataBean.OrderListBean> list_jd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ButterKnife.bind(this);
        type = SpUtil.getString(getApplication(), "Login_Type", "1");


        //获取焦点
        viewMyTopBar.setFocusable(true);
        viewMyTopBar.setFocusableInTouchMode(true);
        viewMyTopBar.requestFocus();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {      //酒店listview
                String type = SpUtil.getString(getApplication(), "Login_Type", "1");
                Intent intent = new Intent();
                if (type.equals("1")) {
                    intent.setClass(getApplicationContext(), Activity_RefundDetails_Jd.class);
                    intent.putExtra("orderCode", list_jd.get(position).getOrder_code() + "");
                } else if (type.equals("2")) {
                    if (list_fd != null) {
                        if (list_fd.get(position).get(0).getGoods_type() == 0) {
                            intent = new Intent(Activity_Statistics.this, Activity_Details_FdDp.class);
                            intent.putExtra("orderCode", list_fd.get(position).get(0).getOrder_code() + "");
                            startActivity(intent);
                        } else if (list_fd.get(position).get(0).getGoods_type() == 1) {
                            intent = new Intent(Activity_Statistics.this, Activity_Details_FdTc.class);
                            intent.putExtra("orderCode", list_fd.get(position).get(0).getOrder_code() + "");
                            startActivity(intent);
                        }
                    } else {
                        ToastUtil.show(getApplicationContext(), "获取数据出错");
                    }
                    intent.setClass(getApplicationContext(), Activity_OrderDetails_Fddp.class);
                } else if (type.equals("3")) {
                    intent.setClass(getApplicationContext(), Activity_OrderDetails_Sp.class);
                    intent.putExtra("orderCode", list_sp.get(position).get(0).getOrder_code() + "");
                } else {
                    ToastUtil.show(getApplicationContext(), "获取数据出错");
                }
                startActivity(intent);
            }
        });
        //elListView点击事件
        elListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {//饭店   商品
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String type = SpUtil.getString(getApplication(), "Login_Type", "1");
                Intent intent = new Intent();
                if (type.equals("1")) {
                    intent.setClass(getApplicationContext(), Activity_OrderDetails_Jd.class);
                    intent.putExtra("orderCode", list_jd.get(groupPosition).getOrder_code() + "");
                } else if (type.equals("2")) {
                    if (list_fd != null) {
                        if (list_fd.get(groupPosition).get(0).getGoods_type() == 0) {//单品
                            intent = new Intent(Activity_Statistics.this, Activity_Details_FdDp.class);
                            intent.putExtra("orderCode", list_fd.get(groupPosition).get(0).getOrder_code() + "");
                        } else if (list_fd.get(groupPosition).get(0).getGoods_type() == 1) {//套餐
                            intent = new Intent(Activity_Statistics.this, Activity_Details_FdTc.class);
                            intent.putExtra("orderCode", list_fd.get(groupPosition).get(0).getOrder_code() + "");
                        }
                    } else {
                        ToastUtil.show(getApplicationContext(), "获取数据出错");
                    }
                } else if (type.equals("3")) {
                    intent.setClass(getApplicationContext(), Activity_OrderDetails_Sp.class);
                    intent.putExtra("orderCode", list_sp.get(groupPosition).get(0).getOrder_code() + "");
                } else {
                    ToastUtil.show(getApplicationContext(), "获取数据出错");
                }
                startActivity(intent);
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        orderCount();
    }

    //获取数据列表
    public void orderCount() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        AuthenticationJson authenticationJson = new Gson().fromJson(SpUtil.getString(getApplicationContext(), "authenticationJson", ""), AuthenticationJson.class);
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
        params.addQueryStringParameter("type", type + "");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        params.addQueryStringParameter("createTime", str);
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.orderCount, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_Statistics.this,R.style.AlertDialogStyle);
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
                            if (type.equals("1")) {
                                statistics_jd(responseInfo.result);
                            } else if (type.equals("2")) {
                                statistics_fd(responseInfo.result);
                            } else if (type.equals("3")) {
                                statistics_sp(responseInfo.result);
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

    //饭店
    private void statistics_fd(String json) {
        OrderCountFdBean bean = new Gson().fromJson(json, OrderCountFdBean.class);
        int i = bean.getHeader().getStatus();
        if (i == 0) {
            tvDingdanshu.setText(bean.getData().getToday().getCount()+"");
            tvYingyee.setText("¥" + bean.getData().getToday().getRealPrice());

            if (bean.getData().getOrderList()!=null){
                list_fd = bean.getData().getOrderList();
                if (list_fd.size()>0){
                    llWudingdan.setVisibility(View.GONE);
                    elListView.setVisibility(View.VISIBLE);
                    adapter_Fd = new Adapter_Statistics_Fd(list_fd, Activity_Statistics.this);
                    elListView.setAdapter(adapter_Fd);

                    elListView.setGroupIndicator(null);
                    //全部展开
                    for (int zk = 0; zk < adapter_Fd.getGroupCount(); zk++) {
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
                }else {
                    llWudingdan.setVisibility(View.VISIBLE);
                    elListView.setVisibility(View.GONE);
                }

            }else {
                llWudingdan.setVisibility(View.VISIBLE);
                elListView.setVisibility(View.GONE);
            }


        } else {
            ToastUtil.show(getApplicationContext(), bean.getHeader().getMsg());
        }
    }

    //商品
    private void statistics_sp(String json) {
        OrderCountSpBean bean = new Gson().fromJson(json, OrderCountSpBean.class);
        int i = bean.getHeader().getStatus();
        if (i == 0) {
            tvDingdanshu.setText(bean.getData().getToday().getCount()+"");
            tvYingyee.setText("¥" + bean.getData().getToday().getRealPrice());


            if (bean.getData().getOrderList()!=null){
                list_sp = bean.getData().getOrderList();
                if (list_sp.size()>0){
                    llWudingdan.setVisibility(View.GONE);
                    elListView.setVisibility(View.VISIBLE);
                    adapter_Sp = new Adapter_Statistics_Sp(Activity_Statistics.this,list_sp);
                    elListView.setAdapter(adapter_Sp);

                    elListView.setGroupIndicator(null);
                    //全部展开
                    for (int zk = 0; zk < adapter_Sp.getGroupCount(); zk++) {
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
                }else {
                    llWudingdan.setVisibility(View.VISIBLE);
                    elListView.setVisibility(View.GONE);
                }

            }else {
                llWudingdan.setVisibility(View.VISIBLE);
                elListView.setVisibility(View.GONE);
            }


        } else {
            ToastUtil.show(getApplicationContext(), bean.getHeader().getMsg());
        }
    }


    //酒店
    private void statistics_jd(String json) {
        OrderCountJdBean bean = new Gson().fromJson(json, OrderCountJdBean.class);
        int i = bean.getHeader().getStatus();
        if (i == 0) {
            tvDingdanshu.setText(bean.getData().getToday().getCount()+"");
            tvYingyee.setText("¥" + bean.getData().getToday().getRealPrice());

            if (bean.getData().getOrderList()!=null){
                list_jd = bean.getData().getOrderList();
                if (list_jd.size()>0){
                    llWudingdan.setVisibility(View.GONE);
                    elListView.setVisibility(View.VISIBLE);
                    adapter_jd=new Adapter_Statistics_Jd(Activity_Statistics.this,list_jd);
                    listView.setAdapter(adapter_jd);
                }else {
                    llWudingdan.setVisibility(View.VISIBLE);
                    elListView.setVisibility(View.GONE);
                }

            }else {
                llWudingdan.setVisibility(View.VISIBLE);
                elListView.setVisibility(View.GONE);
            }


        } else {
            ToastUtil.show(getApplicationContext(), bean.getHeader().getMsg());
        }
    }
}
