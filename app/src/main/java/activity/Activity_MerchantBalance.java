package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import com.JsonBean.MyBalancejson;
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

import java.util.ArrayList;
import java.util.List;

import adapter.Adapter_MerchantBalance;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import util.SpUtil;
import util.ToastUtil;
import view.DialogProgressbar;
import view.MyListView;

/**
 * 商户余额
 * Created by Administrator on 2016/9/5 0005.
 */
public class Activity_MerchantBalance extends Activity {

    @Bind(R.id.tv_jine)
    TextView tvJine;
    @Bind(R.id.tv_Withdrawals)
    TextView tvWithdrawals;
    @Bind(R.id.listView)
    MyListView listView;
    Adapter_MerchantBalance adapter_merchantBalance;
    List<MyBalancejson.DataBean.TradeLogListBean> tradeLogList = new ArrayList<>();
    @Bind(R.id.sv_shye)
    ScrollView svShye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_balance);
        ButterKnife.bind(this);
        adapter_merchantBalance = new Adapter_MerchantBalance(getApplicationContext(), tradeLogList);
        listView.setAdapter(adapter_merchantBalance);

        svShye.smoothScrollTo(0, 0);
    }

    @OnClick(R.id.tv_Withdrawals)
    public void onClick() {
        Intent intent = new Intent(getApplication(), Activity_Withdrawals.class);
        Bundle bundle = new Bundle();
        double te;
        try {
            te = Double.parseDouble(tvJine.getText().toString());
        } catch (Exception e) {
            te = 0;
        }

        bundle.putDouble("balance", te);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 获取商户余额
     */
    public void getmyBalance() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpUtil.token, ""));
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.myBalance, params, new RequestCallBack<String>() {
            DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_MerchantBalance.this, R.style.AlertDialogStyle);

            @Override
            public void onStart() {
                super.onStart();
                dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
                dialogProgressbar.show();
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.i("1111", responseInfo.result);
                dialogProgressbar.dismiss();
                try {
                    MyBalancejson myBalancejson = new Gson().fromJson(responseInfo.result, MyBalancejson.class);
                    if (myBalancejson.getHeader().getStatus() == 0) {
                        tvJine.setText(myBalancejson.getData().getBalanceMap().getBalance() + "");
                        tradeLogList.clear();
                        tradeLogList.addAll(myBalancejson.getData().getTradeLogList());

                        adapter_merchantBalance.notifyDataSetChanged();


                    } else
                        MyToast.SHow(Activity_MerchantBalance.this, myBalancejson.getHeader().getMsg());
                } catch (Exception e) {
                    MyToast.SHow(Activity_MerchantBalance.this, "获取数据失败");
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                ToastUtil.show(getApplicationContext(), "连接网络失败");
            }
        });


    }

    protected void onResume() {
        super.onResume();
        getmyBalance();
    }
}
