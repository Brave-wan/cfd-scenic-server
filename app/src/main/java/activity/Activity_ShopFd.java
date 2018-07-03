package activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.JsonBean.ShopFdBean;
import com.application.URL;
import com.cfd.business.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import butterknife.Bind;
import butterknife.ButterKnife;
import util.SpUtil;
import util.ToastUtil;
import view.DialogProgressbar;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class Activity_ShopFd extends Activity {

    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.ll_kd)
    LinearLayout llKd;
    @Bind(R.id.ll_ys)
    LinearLayout llYs;
    @Bind(R.id.ll_blss)
    LinearLayout llBlss;
    @Bind(R.id.ll_mtkj)
    LinearLayout llMtkj;
    @Bind(R.id.ll_spyp)
    LinearLayout llSpyp;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.scrollView3)
    ScrollView scrollView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_fandian);
        ButterKnife.bind(this);

        storeMessage();
    }

    private void storeMessage() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        params.addQueryStringParameter("status", 2 + "");//1(酒店)2(饭店)3(商品)
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.storeMessage, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_ShopFd.this,R.style.AlertDialogStyle);
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
                            ShopFdBean bean=new Gson().fromJson(responseInfo.result,ShopFdBean.class);
                            int i = bean.getHeader().getStatus();
                            if (i == 0) {
                                name.setText(bean.getData().getName());
                                address.setText(bean.getData().getAddress());
                                phone.setText(bean.getData().getPhone()+"");
                                if (bean.getData().getIs_wifi()==1){
                                    llKd.setVisibility(View.VISIBLE);
                                }
                                if (bean.getData().getIs_blss()==1){
                                    llBlss.setVisibility(View.VISIBLE);
                                }
                                if (bean.getData().getIs_media()==1){
                                    llMtkj.setVisibility(View.VISIBLE);
                                }
                                if (bean.getData().getIs_food()==1){
                                    llSpyp.setVisibility(View.VISIBLE);
                                }

                                tvContent.setText(bean.getData().getContent());
                            } else {
                                ToastUtil.show(getApplicationContext(), bean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });
    }
}
