package activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
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

import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import util.SpUtil;
import view.DialogProgressbar;

/**
 * 登录
 * Created by Administrator on 2016/7/27 0027.
 */
public class Activity_SignIn extends Activity {

    @Bind(R.id.et_signIn_phone)
    EditText etSignInPhone;
    @Bind(R.id.et_signIn_password)
    EditText etSignInPassword;
    @Bind(R.id.tv_signIn_register)
    TextView tvSignInRegister;
    @Bind(R.id.tv_signIn_forget)
    TextView tvSignInForget;
    @Bind(R.id.bt_signIn)
    Button btSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        etSignInPhone.setHintTextColor(Color.parseColor("#aad3f2"));
        etSignInPassword.setHintTextColor(Color.parseColor("#aad3f2"));


        if (!SpUtil.getString(getApplicationContext(),SpUtil.token,"").equals("")){
            if (!SpUtil.getString(getApplicationContext(),"authenticationJson","").equals("")){
                if (SpUtil.getInt(getApplicationContext(), SpUtil.state, 0)!=0){
                    Intent intent1 = new Intent(getApplicationContext(), Activity_Main.class);
                    //put_SpString(loginBean.getData().getShopMessge());
                    intent1.putExtra("index", etSignInPhone.getText().toString());
                    startActivity(intent1);
                    Activity_SignIn.this.finish();
                }
            }
        }

    }


    @OnClick({R.id.tv_signIn_register, R.id.tv_signIn_forget, R.id.bt_signIn})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_signIn_register:
                intent = new Intent(getApplicationContext(), Activity_Register.class);
                startActivity(intent);
                break;
            case R.id.tv_signIn_forget:
                intent = new Intent(getApplicationContext(), Activity_ForgetPassword.class);
                startActivity(intent);
                break;
            case R.id.bt_signIn:
                if (!VerificationUtil.isPhoneNumber(etSignInPhone.getText().toString()))
                    MyToast.SHow(Activity_SignIn.this, "请输入正确的手机号");
//                else if (etSignInPassword.getText().toString().equals("") || etSignInPassword.getText().toString().length() < 6)
//                    MyToast.SHow(Activity_SignIn.this, "请输入正确的密码");
                else
                    Login();
//                finish();
                //第一次注册成功后
//                if (etSignInPhone.getText().toString().equals("")){
//                    intent = new Intent(getApplicationContext(), Activity_Authentication.class);
//                    startActivity(intent);
//                }else {
//                    intent=new Intent(getApplicationContext(), Activity_Main.class);
//                    intent.putExtra("index",etSignInPhone.getText().toString());
//                    startActivity(intent);
//                }
                break;
        }
    }

    /**
     * 登录
     */
    public void Login() {
        RequestParams params = new RequestParams();


        params.addQueryStringParameter("telPhone", etSignInPhone.getText().toString());
        params.addQueryStringParameter("passWord", etSignInPassword.getText().toString());


        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET,
                URL.Login,
                params,
                new RequestCallBack<String>() {


                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_SignIn.this,R.style.AlertDialogStyle);
                    @Override
                    public void onStart() {
                        super.onStart();
                        dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
                        dialogProgressbar.show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        dialogProgressbar.dismiss();

                        if (responseInfo.result == null || responseInfo.result.equals(""))
                            MyToast.SHow(Activity_SignIn.this, "登录失败");
                        else try {
                            LoginBean loginBean = new Gson().fromJson(responseInfo.result, LoginBean.class);
                            if (loginBean.getHeader().getStatus() == 0) {
                                SpUtil.putString(Activity_SignIn.this,SpUtil.token,loginBean.getData().getToken());
                                if (loginBean.getData().getState() == 0) {
                                    MyToast.SHow(Activity_SignIn.this, "实名认证审核中");
                                } else if (loginBean.getData().getState() == 1) {
                                    SpUtil.putString(Activity_SignIn.this,SpUtil.token,loginBean.getData().getToken());
                                    SpUtil.putInt(Activity_SignIn.this,SpUtil.state,loginBean.getData().getState());
                                    SpUtil.putlong(Activity_SignIn.this,SpUtil.userId,loginBean.getData().getUserId());
                                    //向服务器修改自己的类别
                                    JPushInterface.setAlias(Activity_SignIn.this, loginBean.getData().getUserId()+"", null);

                                    Intent intent1 = new Intent(getApplicationContext(), Activity_Main.class);
                                    put_SpString(loginBean.getData().getShopMessge());
                                    intent1.putExtra("index", etSignInPhone.getText().toString());
                                    startActivity(intent1);
                                    Activity_SignIn.this.finish();

                                } else if (loginBean.getData().getState() == 2) {
                                    MyToast.SHow(Activity_SignIn.this, "实名认证审核失败");
                                    put_SpString(loginBean.getData().getShopMessge());
                                    Intent intent = new Intent(getApplicationContext(), Activity_Authentication.class);
                                    startActivity(intent);
                                    Activity_SignIn.this.finish();
                                } else if (loginBean.getData().getState() == 6) {
                                    Intent intent = new Intent(getApplicationContext(), Activity_Authentication.class);
//                                    remover_SpString();
                                    startActivity(intent);
                                    Activity_SignIn.this.finish();
                                }

                            } else
                                MyToast.SHow(Activity_SignIn.this, loginBean.getHeader().getMsg());
                        } catch (Exception e) {
                            MyToast.SHow(Activity_SignIn.this, e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        MyToast.SHownetworkField(Activity_SignIn.this);
                    }
                });

    }


    //保存信息  用来回显
    private void put_SpString(AuthenticationJson authenticationJson) {
        SpUtil.putString(getApplication(),"authenticationJson",new Gson().toJson(authenticationJson));
    }

    //保存信息  用来回显
    private void remover_SpString() {
        SpUtil.remove(getApplication(),"authenticationJson");
    }



}
