package activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
import com.cfd.business.R;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import butterknife.Bind;
import butterknife.ButterKnife;
import util.SpUtil;
import util.StringUtil;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class Activity_SjOk extends Activity {

    @Bind(R.id.activity_sj_ok_name)
    TextView activitySjOkName;
    @Bind(R.id.tv_stop_type)
    TextView tvStopType;
    @Bind(R.id.ll_stop_type)
    LinearLayout llStopType;
    @Bind(R.id.activity_sj_ok_product)
    TextView activitySjOkProduct;
    @Bind(R.id.tv_account_type)
    TextView tvAccountType;
    @Bind(R.id.ll_account_type)
    LinearLayout llAccountType;
    @Bind(R.id.activity_sj_ok_rename)
    TextView activitySjOkRename;
    @Bind(R.id.activity_sj_ok_bankcard)
    TextView activitySjOkBankcard;
    @Bind(R.id.activity_sj_ok_bankname)
    TextView activitySjOkBankname;
    @Bind(R.id.activity_sj_ok_yingyezhizhaoiv)
    ImageView activitySjOkYingyezhizhaoiv;
    @Bind(R.id.activity_sj_ok_yingyezhizhaoll)
    LinearLayout activitySjOkYingyezhizhaoll;
    @Bind(R.id.activity_sj_ok_orther1)
    ImageView activitySjOkOrther1;
    @Bind(R.id.activity_sj_ok_orther2)
    ImageView activitySjOkOrther2;
    AuthenticationJson authenticationJson;

    private final Handler mHandler = new Handler() {
        @Override
        public void close() {

        }

        @Override
        public void flush() {

        }

        @Override
        public void publish(LogRecord record) {

        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sj_ok);
        ButterKnife.bind(this);
        authenticationJson = new Gson().fromJson(SpUtil.getString(Activity_SjOk.this, "authenticationJson", ""), AuthenticationJson.class);
        activitySjOkName.setText(authenticationJson.getName());

        if (authenticationJson.getShopId().equals("1"))
            tvStopType.setText("酒店");
        else if (authenticationJson.getShopId().equals("2"))
            tvStopType.setText("特产");
        else if (authenticationJson.getShopId().equals("3"))
            tvStopType.setText("饭店");
        else if (authenticationJson.getShopId().equals("4"))
            tvStopType.setText("小吃");

        activitySjOkProduct.setText(authenticationJson.getBusinessScope());
        String account = authenticationJson.getAccountType();
        if (!StringUtil.isEmpty(account) && account.equals("1"))
            tvAccountType.setText("个人");
        else tvAccountType.setText("对公");
        activitySjOkRename.setText(authenticationJson.getRealName());
        activitySjOkBankcard.setText(authenticationJson.getBankCard());
        activitySjOkBankname.setText(authenticationJson.getAccountBank());

        if (authenticationJson.getIsLicense().equals("1")) {
            activitySjOkYingyezhizhaoll.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(authenticationJson.getLicenseImg(), activitySjOkYingyezhizhaoiv);

        } else activitySjOkYingyezhizhaoll.setVisibility(View.GONE);
        ImageLoader.getInstance().displayImage(authenticationJson.getOtherImg1(), activitySjOkOrther1);
        ImageLoader.getInstance().displayImage(authenticationJson.getOtherImg2(), activitySjOkOrther2);
    }
}
