package activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.JsonBean.ShopSpBean;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import util.SpUtil;
import util.ToastUtil;
import view.DialogProgressbar;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class Activity_ShopSp extends Activity {

    @Bind(R.id.head_img)
    ImageView headImg;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.content)
    TextView content;
    @Bind(R.id.backgroud_img)
    ImageView backgroudImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_shangpin);
        ButterKnife.bind(this);

        storeMessage();
    }

    private void storeMessage() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        params.addQueryStringParameter("status", 3 + "");//1(酒店)2(饭店)3(商品)
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.storeMessage, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_ShopSp.this,R.style.AlertDialogStyle);
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
                            ShopSpBean bean = new Gson().fromJson(responseInfo.result, ShopSpBean.class);
                            int i = bean.getHeader().getStatus();
                            if (i == 0) {
                                ImageLoader.getInstance().displayImage(bean.getData().getBackgroud_img(), backgroudImg);
                                ImageLoader.getInstance().displayImage(bean.getData().getHead_img(), headImg);
                                name.setText(bean.getData().getName());
                                address.setText(bean.getData().getAddress());
                                phone.setText(bean.getData().getPhone());
                                content.setText(bean.getData().getContent());
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
