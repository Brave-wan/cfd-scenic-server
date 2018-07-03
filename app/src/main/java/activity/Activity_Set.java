package activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cfd.business.R;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import util.SpUtil;
import view.DialogSignOut;
import view.MyLinearLayoutItem;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class Activity_Set extends Activity {

    @Bind(R.id.view_SignOut)
    LinearLayout viewSignOut;
    @Bind(R.id.view_modify)
    LinearLayout viewModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        Activity_Authentication. activityHashMap.put("Activity_Set",Activity_Set.this);
    }

    @OnClick({R.id.view_SignOut, R.id.view_modify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_SignOut:

                final Dialog dialog1 = new Dialog(Activity_Set.this, R.style.AlertDialogStyle);
                dialog1.getWindow().setContentView(R.layout.dialog_sign_out);
                dialog1.setCanceledOnTouchOutside(false);
                dialog1.show();
                dialog1.getWindow().findViewById(R.id.bt_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {  //确定
                        //向服务器修改自己的类别
                        JPushInterface.setAlias(Activity_Set.this,  "", null);
                        SpUtil.putString(getApplicationContext(), SpUtil.token, "");
                        SpUtil.putString(getApplicationContext(), "authenticationJson", "");
                        SpUtil.putInt(getApplicationContext(), SpUtil.state, 0);
                        SpUtil.putlong(getApplicationContext(), SpUtil.userId, 0);

                        dialog1.dismiss();
                        Intent i=new Intent(Activity_Set.this,Activity_SignIn.class);
                        startActivity(i);
                        Activity_Authentication. activityHashMap.get("Activity_Main").finish();
                        Activity_Set.this.finish();

                    }
                });
                dialog1.getWindow().findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {  //取消

                        dialog1.dismiss();
                    }
                });
                break;
            case R.id.view_modify:
                Intent intent=new Intent(getApplicationContext(),Activity_ModifyPassword.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public  void onDestroy(){
        super.onDestroy();
        if( Activity_Authentication. activityHashMap.get("Activity_Set")!=null)
            Activity_Authentication. activityHashMap.get("Activity_Set").finish();
    }
}
