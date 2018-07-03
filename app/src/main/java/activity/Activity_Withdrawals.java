package activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.JsonBean.LoginBean;
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

import adapter.Adapter_MerchantBalance;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import util.SpUtil;
import view.DialogConfirmTx;

/**
 * 提现
 * Created by Administrator on 2016/9/6 0006.
 */
public class Activity_Withdrawals extends Activity {

    @Bind(R.id.et_txjine)
    EditText etTxjine;
    @Bind(R.id.tv_Submit)
    TextView tvSubmit;

     double balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawals);
        ButterKnife.bind(this);
        balance=getIntent().getDoubleExtra("balance",0);
    }

    @OnClick(R.id.tv_Submit)
    public void onClick() {

        double te;
        try {
            te=Double.parseDouble(etTxjine.getText().toString());
        }catch (Exception e){
            te=0;
        }
//        DialogConfirmTx dialogConfirmTx=new DialogConfirmTx(Activity_Withdrawals.this,R.style.AlertDialogStyle,"确认提现￥"+etTxjine.getText().toString()+"?");
//        dialogConfirmTx.show();
        if(etTxjine.getText().toString().equals("")){
            MyToast.SHow(Activity_Withdrawals.this,"请输入提现金额");
        }else if(te>balance){
            MyToast.SHow(Activity_Withdrawals.this,"提现金额不能大于剩余余额");
        }else{
           final Dialog dialog1 = new Dialog(Activity_Withdrawals.this, R.style.AlertDialogStyle);
           dialog1.getWindow().setContentView(R.layout.dialogedittext);
           dialog1.setCanceledOnTouchOutside(false);
           dialog1.show();
             TextView text = (TextView) dialog1.getWindow().findViewById(R.id.dialogedittext_tv_content);
           text.setText("确认提现￥"+etTxjine.getText().toString()+"?");
           final   EditText editText= (EditText) dialog1.getWindow().findViewById(R.id.dialogedittext_et);
           //确定
           dialog1.getWindow().findViewById(R.id.dialogedittext_ll_confirm).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {


                   if(editText.getText().toString().equals("")||(!editText.getText().toString().equals("admin")&&editText.getText().toString().length()<6)){

                   MyToast.SHow(Activity_Withdrawals.this,"请输入6位提现密码");
                   }
                   else {
                       RequestParams params = new RequestParams();
                       params.addHeader("Authorization", SpUtil.getString(getApplication(), SpUtil.token, ""));
                       params.addQueryStringParameter("passWord",editText.getText().toString());
                       params.addQueryStringParameter("balance",etTxjine.getText().toString());

                       HttpUtils http = new HttpUtils();
                       http.configResponseTextCharset(HTTP.UTF_8);
                       http.send(HttpRequest.HttpMethod.GET,
                               URL.pwWithdraw,
                               params,
                               new RequestCallBack<String>() {

                                   @Override
                                   public void onStart() {
                                   }

                                   @Override
                                   public void onLoading(long total, long current, boolean isUploading) {
                                   }

                                   @Override
                                   public void onSuccess(ResponseInfo<String> responseInfo) {
                                       dialog1.dismiss();
                                       try {
                                           LoginBean loginBean = new Gson().fromJson(responseInfo.result, LoginBean.class);
                                           if (loginBean.getHeader().getStatus() == 0) {

                                               MyToast.SHow(Activity_Withdrawals.this, "提现成功");
                                               Activity_Withdrawals.this.finish();
                                           } else
                                               MyToast.SHow(Activity_Withdrawals.this, loginBean.getHeader().getMsg());
                                       } catch (Exception e) {
                                           MyToast.SHow(Activity_Withdrawals.this, "提现失败");
                                       }
                                   }

                                   @Override
                                   public void onFailure(HttpException error, String msg) {
                                       MyToast.SHownetworkField(Activity_Withdrawals.this);
                                       dialog1.dismiss();
                                   }
                               });    
                       

                   }
               }
           });
           //取消
           dialog1.getWindow().findViewById(R.id.dialogedittext_ll_cancel).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   dialog1.dismiss();
               }
           });

       }


    }
}
