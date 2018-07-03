package view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cfd.business.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 取消订单
 * Created by Administrator on 2016/7/28 0028.
 */
public class DialogCancelOrder extends Dialog {


    @Bind(R.id.ll_confirm)
    LinearLayout llConfirm;
    @Bind(R.id.ll_cancel)
    LinearLayout llCancel;

    public DialogCancelOrder(Context context) {
        super(context);
    }

    public DialogCancelOrder(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogCancelOrder(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_cancel_order);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_confirm, R.id.ll_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_confirm:
                dismiss();
                break;
            case R.id.ll_cancel:
                dismiss();
                break;
        }
    }
}
