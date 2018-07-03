package view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cfd.business.R;

import activity.Activity_RefundDetails_Sp;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class DialogThroughAudit extends Dialog {
    @Bind(R.id.ll_confirm)
    LinearLayout llConfirm;
    @Bind(R.id.ll_cancel)
    LinearLayout llCancel;

    Context mContext;

    public DialogThroughAudit(Context context) {
        super(context);
        mContext=context;
    }

    public DialogThroughAudit(Context context, int themeResId) {
        super(context, themeResId);
        mContext=context;
    }

    protected DialogThroughAudit(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext=context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_through_audit);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_confirm, R.id.ll_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_confirm:
                Activity_RefundDetails_Sp activity= (Activity_RefundDetails_Sp) mContext;
                activity.updateGoodsOrder("7");
                dismiss();
                break;
            case R.id.ll_cancel:

                dismiss();
                break;
        }
    }
}
