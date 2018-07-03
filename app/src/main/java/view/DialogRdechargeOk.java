package view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.cfd.business.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 充值成功
 * Created by Administrator on 2016/7/28 0028.
 */
public class DialogRdechargeOk extends Dialog {


    @Bind(R.id.bt_ok)
    Button btOk;

    public DialogRdechargeOk(Context context) {
        super(context);
    }

    public DialogRdechargeOk(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogRdechargeOk(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_recharge_ok);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.bt_ok)
    public void onClick() {
        Toast.makeText(getContext(),"OK",Toast.LENGTH_LONG).show();
        dismiss();
    }
}
