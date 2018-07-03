package activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
import com.cfd.business.R;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import util.SpUtil;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class Activity_SmOk extends Activity {

    @Bind(R.id.activity_sm_ok_rename)
    TextView activitySmOkRename;
    @Bind(R.id.activity_sm_ok_sex)
    TextView activitySmOkSex;
    @Bind(R.id.ll_Gender)
    LinearLayout llGender;
    @Bind(R.id.activity_sm_ok_idcard)
    TextView activitySmOkIdcard;
    @Bind(R.id.activity_sm_ok_holdercard)
    ImageView activitySmOkHoldercard;
    @Bind(R.id.activity_sm_ok_cardfort)
    ImageView activitySmOkCardfort;
    @Bind(R.id.activity_sm_ok_cardback)
    ImageView activitySmOkCardback;

    AuthenticationJson authenticationJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sm_ok);
        ButterKnife.bind(this);
        authenticationJson = new Gson().fromJson(SpUtil.getString(Activity_SmOk.this, "authenticationJson", ""), AuthenticationJson.class);
        activitySmOkRename.setText(authenticationJson.getRealName());
        if(authenticationJson.getSex().equals("0"))
            activitySmOkSex.setText("男");
        else   activitySmOkSex.setText("女");
        activitySmOkIdcard.setText(authenticationJson.getIdCard());
        ImageLoader.getInstance().displayImage(authenticationJson.getHoldCardImg(),activitySmOkHoldercard);
        ImageLoader.getInstance().displayImage(authenticationJson.getFaceCardImg(),activitySmOkCardfort);
        ImageLoader.getInstance().displayImage(authenticationJson.getBackCardImg(),activitySmOkCardback);


    }
}
