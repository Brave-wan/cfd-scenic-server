package view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cfd.business.R;

import activity.Activity_RefundDetails_Fd;
import activity.Activity_RefundDetails_Sp;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 确认退款
 * Created by Administrator on 2016/7/28 0028.
 */
public class DialogConfirmRefund extends Dialog {


    @Bind(R.id.ll_confirm)
    LinearLayout llConfirm;
    @Bind(R.id.ll_cancel)
    LinearLayout llCancel;

    Context mContext;
    int mType=0;

    public DialogConfirmRefund(Context context,int type) {
        super(context);
        mContext=context;
        mType=type;
    }

    public DialogConfirmRefund(Context context, int themeResId,int type) {
        super(context, themeResId);
        mContext=context;
        mType=type;
    }

    protected DialogConfirmRefund(Context context, boolean cancelable, OnCancelListener cancelListener,int type) {
        super(context, cancelable, cancelListener);
        mContext=context;
        mType=type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm_refund);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_confirm, R.id.ll_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_confirm:
                if (mType==1){//酒店退款

                }else if (mType==2){//饭店退款
                    Activity_RefundDetails_Fd activity= (Activity_RefundDetails_Fd) mContext;
                    activity.shopRefundFinsh();
                }else if (mType==3){//商品退款
                    Activity_RefundDetails_Sp activity= (Activity_RefundDetails_Sp) mContext;
                    activity.shopRefundFinsh();
                }

                dismiss();
                break;
            case R.id.ll_cancel:
                dismiss();
                break;
        }
    }
}
