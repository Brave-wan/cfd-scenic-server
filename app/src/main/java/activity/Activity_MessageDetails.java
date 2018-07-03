package activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.application.URL;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;
import com.cfd.business.R;

import util.SpUtil;
import util.ToastUtil;
import view.DialogProgressbar;

/**
 * 消息中心--详情页
 * Created by Administrator on 2016/7/28 0028.
 */
public class Activity_MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        myMessageDetail();
    }

    private void myMessageDetail(){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        params.addQueryStringParameter("dic",getIntent().getStringExtra("dic"));
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0*1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.myMessageDetail, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_MessageDetails.this,R.style.AlertDialogStyle);
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
                            /*if(){
                            }else if( == 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(mContext);
                            }else {
                                ToastUtil.show(getApplicationContext(), addressListBean.getHeader().getMsg());
                            }*/
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
}
