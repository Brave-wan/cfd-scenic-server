package activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
import util.ToastUtil;
import view.DialogProgressbar;

/**
 * 忘记密码
 * Created by Administrator on 2016/7/27 0027.
 */
public class Activity_ForgetPassword extends Activity {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_Code)
    EditText etCode;
    @Bind(R.id.bt_Code)
    Button btCode;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_newPassword)
    EditText etNewPassword;
    @Bind(R.id.bt_Submit)
    Button btSubmit;
    @Bind(R.id.im_return)
    ImageView imReturn;
    TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        time = new TimeCount(60000, 1000);
        etPhone.setHintTextColor(Color.parseColor("#aad3f2"));
        etCode.setHintTextColor(Color.parseColor("#aad3f2"));
        etPassword.setHintTextColor(Color.parseColor("#aad3f2"));
        etNewPassword.setHintTextColor(Color.parseColor("#aad3f2"));
    }

    @OnClick({R.id.bt_Code, R.id.bt_Submit, R.id.im_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_Code:
                if (VerificationUtil.isPhoneNumber(etPhone.getText().toString())) {
                    time.start();//开始计时
                    getcode();
                } else MyToast.SHow(Activity_ForgetPassword.this, "请输入正确的手机号");
                break;
            case R.id.bt_Submit:
                if (!VerificationUtil.isPhoneNumber(etPhone.getText().toString()))
                    MyToast.SHow(Activity_ForgetPassword.this, "请输入正确的手机号");
                else if (etCode.getText().toString().equals(""))
                    MyToast.SHow(Activity_ForgetPassword.this, "请输入验证码");
                else if (etPassword.getText().toString().equals("") || etNewPassword.getText().toString().equals(""))
                    MyToast.SHow(Activity_ForgetPassword.this, "请输入密码");
                else if (etPassword.getText().toString().length() < 6)
                    MyToast.SHow(Activity_ForgetPassword.this, "密码长度不能小于6位");
                else if (!etPassword.getText().toString().equals(etNewPassword.getText().toString()))
                    MyToast.SHow(Activity_ForgetPassword.this, "两次输入的密码不一致");
                else
                    Forgetpassword();
                break;
            case R.id.im_return:
                finish();
                break;
        }
    }


    /**
     * 找回密码
     **/
    public void Forgetpassword() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("telPhone", etPhone.getText().toString());
        params.addQueryStringParameter("passWord", etPassword.getText().toString());
        params.addQueryStringParameter("checkcode", etCode.getText().toString());
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.send(HttpRequest.HttpMethod.GET,
                URL.ForgetPassword,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_ForgetPassword.this, R.style.AlertDialogStyle);
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
                            MyToast.SHow(Activity_ForgetPassword.this, "找回密码失败");
                        else try {
                            LoginBean loginBean = new Gson().fromJson(responseInfo.result, LoginBean.class);
                            if (loginBean.getHeader().getStatus() == 0) {
                                MyToast.SHow(Activity_ForgetPassword.this, loginBean.getHeader().getMsg());
                                Activity_ForgetPassword.this.finish();
                            } else
                                MyToast.SHow(Activity_ForgetPassword.this, loginBean.getHeader().getMsg());
                        } catch (Exception e) {
                            MyToast.SHow(Activity_ForgetPassword.this, "找回密码失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });

    }

    /**
     * 获取验证验
     */
    public void getcode() {


        RequestParams params = new RequestParams();


        params.addQueryStringParameter("phone", etPhone.getText().toString());
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.send(HttpRequest.HttpMethod.POST,
                URL.getcode,
                params,
                new RequestCallBack<String>() {


                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_ForgetPassword.this, R.style.AlertDialogStyle);

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
                                MyToast.SHow(Activity_ForgetPassword.this, "发送验证码成功");
                            else
                                MyToast.SHow(Activity_ForgetPassword.this, "发送验证码失败");
                        } catch (Exception e) {
                            MyToast.SHow(Activity_ForgetPassword.this, "发送验证码失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
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
            btCode.setText("重新验证");
            btCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            btCode.setClickable(false);
            btCode.setText(millisUntilFinished / 1000 + "秒");
        }

    }

}
