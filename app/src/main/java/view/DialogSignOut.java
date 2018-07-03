package view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cfd.business.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 安全退出
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogSignOut extends Dialog {
    @Bind(R.id.ll_confirm)
    LinearLayout llConfirm;
    @Bind(R.id.ll_cancel)
    LinearLayout llCancel;
    @Bind(R.id.bt_confirm)
    Button btConfirm;
    @Bind(R.id.bt_cancel)
    Button btCancel;

    public DialogSignOut(Context context) {
        super(context);
    }

    public DialogSignOut(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogSignOut(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign_out);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.bt_confirm, R.id.bt_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_confirm:
                Toast.makeText(getContext(),"退出成功",Toast.LENGTH_LONG).show();
                dismiss();
                break;
            case R.id.bt_cancel:
                dismiss();
                break;
        }
    }
}
