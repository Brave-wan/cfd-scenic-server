package activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.JsonBean.LoginBean;
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
import com.mytools.VerificationUtil;

import org.apache.http.protocol.HTTP;

import butterknife.Bind;
import butterknife.ButterKnife;
import util.SpUtil;
import view.DialogProgressbar;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class Activity_ModifyPassword extends Activity {

    @Bind(R.id.et_dangqian)
    EditText etDangqian;
    @Bind(R.id.et_xinmima)
    EditText etXinmima;
    @Bind(R.id.et_zaicixin)
    EditText etZaicixin;
    @Bind(R.id.bt_tuichu)
    Button btTuichu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);

        etDangqian.setHintTextColor(Color.parseColor("#cecece"));
        etXinmima.setHintTextColor(Color.parseColor("#cecece"));
        etZaicixin.setHintTextColor(Color.parseColor("#cecece"));

        btTuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etDangqian.getText().toString().equals(""))
                    MyToast.SHow(Activity_ModifyPassword.this, "请输入当前密码");
                else if(etXinmima.getText().toString().equals("")||etZaicixin.getText().toString().equals(""))
                    MyToast.SHow(Activity_ModifyPassword.this, "请输入新密码");
                else if(etXinmima.getText().toString().length()<6)
                    MyToast.SHow(Activity_ModifyPassword.this, "密码长度不能小于6位");
                else if(!etXinmima.getText().toString().equals(etZaicixin.getText().toString()))
                    MyToast.SHow(Activity_ModifyPassword.this, "两次输入的密码不一致");
                else
                    Modifypassword();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Activity_Authentication.activityHashMap.get("Activity_ModifyPassword") != null)
            Activity_Authentication.activityHashMap.get("Activity_ModifyPassword").finish();
    }


    /**
     * 找回密码
     **/
    public void Modifypassword() {

        RequestParams params = new RequestParams();

        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpUtil.token, ""));
        params.addQueryStringParameter("oldPassWord", etDangqian.getText().toString());
        params.addQueryStringParameter("newPassWord", etXinmima.getText().toString());
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET,
                URL.updateShopPsw,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_ModifyPassword.this,R.style.AlertDialogStyle);
                    @Override
                    public void onStart() {
                        super.onStart();
                        dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
                        dialogProgressbar.show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        dialogProgressbar.dismiss();

                        if(responseInfo.result==null||responseInfo.result.equals(""))
                            MyToast.SHow(Activity_ModifyPassword.this,"修改密码失败");
                        else try {
                            LoginBean loginBean=new Gson().fromJson(responseInfo.result,LoginBean.class);
                            if(loginBean.getHeader().getStatus()==0){
                                MyToast.SHow(Activity_ModifyPassword.this,"修改密码成功");
                                Intent intent = new Intent(Activity_ModifyPassword.this, Activity_SignIn.class);
                                startActivity(intent);

                                Activity_Authentication.activityHashMap.get("Activity_Set").finish();
                                Activity_Authentication.activityHashMap.get("Activity_Main").finish();
                                Activity_ModifyPassword.this.finish();
                            }

                            else
                                MyToast.SHow(Activity_ModifyPassword.this,loginBean.getHeader().getMsg());
                        }catch (Exception e){
                            MyToast.SHow(Activity_ModifyPassword.this,"修改密码失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        MyToast.SHownetworkField(Activity_ModifyPassword.this);
                    }
                });

    }
}
