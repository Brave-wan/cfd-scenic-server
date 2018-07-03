package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.JsonBean.AuthenticationJson;
import com.application.URL;
import com.cfd.business.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fragment.Fragment_ShangPin;
import fragment.Fragment_SpInside;
import util.SpUtil;
import util.ToastUtil;
import view.DialogProgressbar;

/**
 * 物流信息
 * Created by Administrator on 2016/9/28 0028.
 */
public class Activity_Logistics extends Activity {

    @Bind(R.id.et_company)
    EditText etCompany;
    @Bind(R.id.et_Number)
    EditText etNumber;
    @Bind(R.id.bt_tijiao)
    Button btTijiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.bt_tijiao)
    public void onClick() {
        if (etCompany.getText().toString().equals("")){
            ToastUtil.show(getApplicationContext(),"请填写物流公司");
            return;
        }
        if (etNumber.getText().toString().equals("")){
            ToastUtil.show(getApplicationContext(),"请填写物流单号");
            return;
        }
        saveExpress();
    }

    private void saveExpress() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpUtil.token, ""));
        params.addQueryStringParameter("orderState", 3 + "");
        params.addQueryStringParameter("orderCode", getIntent().getStringExtra("orderCode"));
        AuthenticationJson authenticationJson=new Gson().fromJson(SpUtil.getString(getApplicationContext(),"authenticationJson",""),AuthenticationJson.class);
        params.addQueryStringParameter("siId", authenticationJson.getId() + "");
        params.addQueryStringParameter("expressName", etCompany.getText().toString());
        params.addQueryStringParameter("expressCode", etNumber.getText().toString());
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.saveExpress, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_Logistics.this,R.style.AlertDialogStyle);
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
                            JSONObject jsonObject=new JSONObject(responseInfo.result);
                            JSONObject header=jsonObject.getJSONObject("header");
                            int i=header.getInt("status");
                            if(i==0){
                                finish();
                                Fragment_SpInside fragment_spInside1 = (Fragment_SpInside) Fragment_ShangPin.sp_1;
                                fragment_spInside1.list.clear();
                                fragment_spInside1.mPage=1;
                                fragment_spInside1.shopOrderList();
                                if (Fragment_ShangPin.sp_2 != null) {
                                    Fragment_SpInside fragment_spInside2 = (Fragment_SpInside) Fragment_ShangPin.sp_2;
                                    fragment_spInside1.list.clear();
                                    fragment_spInside1.mPage=1;
                                    fragment_spInside2.shopOrderList();
                                }
                            }else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
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
