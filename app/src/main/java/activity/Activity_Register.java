package activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import butterknife.OnClick;
import view.DialogProgressbar;

/**
 * 注册
 * Created by Administrator on 2016/7/27 0027.
 */
public class Activity_Register extends Activity {


    TimeCount time;
    @Bind(R.id.im_return)
    ImageView imReturn;
    @Bind(R.id.et_signIn_phone)
    EditText etSignInPhone;
    @Bind(R.id.et_register_Code)
    EditText etRegisterCode;
    @Bind(R.id.bt_Obtain_Code)
    Button btObtainCode;
    @Bind(R.id.et_register_password)
    EditText etRegisterPassword;
    @Bind(R.id.et_register_password2)
    EditText etRegisterPassword2;
    @Bind(R.id.iv_signIn_checkbox)
    ImageView ivSignInCheckbox;
    @Bind(R.id.tv_signIn_checkbox)
    TextView tvSignInCheckbox;
    @Bind(R.id.bt_register_business)
    Button btRegisterBusiness;
    @Bind(R.id.tv_signin)
    TextView tvSignin;

    boolean aBoolean=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        time = new TimeCount(60000, 1000);
        etSignInPhone.setHintTextColor(Color.parseColor("#aad3f2"));
        etRegisterCode.setHintTextColor(Color.parseColor("#aad3f2"));
        etRegisterPassword.setHintTextColor(Color.parseColor("#aad3f2"));
        etRegisterPassword2.setHintTextColor(Color.parseColor("#aad3f2"));
    }

    @OnClick({R.id.bt_Obtain_Code, R.id.bt_register_business, R.id.tv_signin, R.id.im_return, R.id.iv_signIn_checkbox, R.id.tv_signIn_checkbox})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.bt_Obtain_Code:   //获取验证码
                if (VerificationUtil.isPhoneNumber(etSignInPhone.getText().toString())) {
                    time.start();//开始计时
                    getcode();
                } else MyToast.SHow(Activity_Register.this, "请输入正确的手机号");
                break;
            case R.id.bt_register_business:

                if (!VerificationUtil.isPhoneNumber(etSignInPhone.getText().toString()))
                    MyToast.SHow(Activity_Register.this, "请输入正确的手机号");
                else if (etRegisterCode.getText().toString().equals(""))
                    MyToast.SHow(Activity_Register.this, "请输入验证码");
                else if (etRegisterPassword.getText().toString().equals("") || etRegisterPassword2.getText().toString().equals(""))
                    MyToast.SHow(Activity_Register.this, "请输入密码");
                else if (etRegisterPassword.getText().toString().length() < 6)
                    MyToast.SHow(Activity_Register.this, "密码长度不能小于6位");
                else if (!etRegisterPassword.getText().toString().equals(etRegisterPassword2.getText().toString()))
                    MyToast.SHow(Activity_Register.this, "两次输入的密码不一致");
                else if (!aBoolean)
                    MyToast.SHow(Activity_Register.this, "请勾选商家入驻协议");
                else {
                    Registrequst();
                }

                break;
            case R.id.tv_signin:
//                intent = new Intent(getApplicationContext(), Activity_SignIn.class);
//                startActivity(intent);
//                Registrequst();
                Activity_Register.this.finish();
                break;
            case R.id.im_return:
                finish();
                break;
            case R.id.iv_signIn_checkbox:
                if (aBoolean){
                    aBoolean=false;
                    ivSignInCheckbox.setImageResource(R.mipmap.dl_fangkuang);
                }else {
                    aBoolean=true;
                    ivSignInCheckbox.setImageResource(R.mipmap.dl_duigouzuhe);
                }
                break;
            case R.id.tv_signIn_checkbox:
                intent=new Intent(Activity_Register.this,Activity_Zcxy.class);
                startActivity(intent);
                break;
        }
    }


    /**
     * 注册
     **/
    public void Registrequst() {

        RequestParams params = new RequestParams();


        params.addQueryStringParameter("telPhone", etSignInPhone.getText().toString());
        params.addQueryStringParameter("passWord", etRegisterPassword.getText().toString());
        params.addQueryStringParameter("checkcode", etRegisterCode.getText().toString());

        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.send(HttpRequest.HttpMethod.GET,
                URL.Regist,
                params,
                new RequestCallBack<String>() {


                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_Register.this, R.style.AlertDialogStyle);

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
                            MyToast.SHow(Activity_Register.this, "注册失败");
                        else try {
                            LoginBean loginBean = new Gson().fromJson(responseInfo.result, LoginBean.class);
                            if (loginBean.getHeader().getStatus() == 0) {
                                MyToast.SHow(Activity_Register.this, loginBean.getHeader().getMsg());
                                Activity_Register.this.finish();
                            } else
                                MyToast.SHow(Activity_Register.this, loginBean.getHeader().getMsg());
                        } catch (Exception e) {
                            MyToast.SHow(Activity_Register.this, "注册失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        MyToast.SHownetworkField(Activity_Register.this);
                    }
                });

    }

    /**
     * 获取验证验
     */
    public void getcode() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("phone", etSignInPhone.getText().toString());
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.send(HttpRequest.HttpMethod.POST,
                URL.getcode,
                params,
                new RequestCallBack<String>() {


                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_Register.this, R.style.AlertDialogStyle);

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
                            LoginBean loginBean = new Gson().fromJson(responseInfo.result, LoginBean.class);
                            if (loginBean.getHeader().getStatus() == 0)
                                MyToast.SHow(Activity_Register.this, "发送验证码成功");
                            else
                                MyToast.SHow(Activity_Register.this, "发送验证码失败");
                        } catch (Exception e) {
                            MyToast.SHow(Activity_Register.this, "发送验证码失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        MyToast.SHownetworkField(Activity_Register.this);
                    }
                });


    }


    /***
     * 倒计时
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            btObtainCode.setText("重新验证");
            btObtainCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            btObtainCode.setClickable(false);
            btObtainCode.setText(millisUntilFinished / 1000 + "秒");
        }

    }

}
