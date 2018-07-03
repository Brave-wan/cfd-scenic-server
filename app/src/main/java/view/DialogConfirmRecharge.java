package view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cfd.business.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 充值
 * Created by Administrator on 2016/7/28 0028.
 */
public class DialogConfirmRecharge extends Dialog {

    @Bind(R.id.bt_cancel)
    Button btCancel;
    @Bind(R.id.ll_confirm)
    LinearLayout llConfirm;
    @Bind(R.id.bt_confirm)
    Button btConfirm;
    @Bind(R.id.ll_cancel)
    LinearLayout llCancel;

    String money;
    @Bind(R.id.tv_money)
    TextView tvMoney;

    public DialogConfirmRecharge(Context context, String money) {
        super(context);
        this.money = money;
    }

    public DialogConfirmRecharge(Context context, int themeResId, String money) {
        super(context, themeResId);
        this.money = money;
    }

    protected DialogConfirmRecharge(Context context, boolean cancelable, OnCancelListener cancelListener, String money) {
        super(context, cancelable, cancelListener);
        this.money = money;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm_recharge);
        ButterKnife.bind(this);

        tvMoney.setText(money);
    }

    @OnClick({R.id.bt_cancel, R.id.bt_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_cancel:
                dismiss();
                break;
            case R.id.bt_confirm:
                DialogRdechargeOk dialogRdechargeOk = new DialogRdechargeOk(getContext(), R.style.AlertDialogStyle);
                dialogRdechargeOk.show();
                dismiss();
                break;
        }
    }
}
