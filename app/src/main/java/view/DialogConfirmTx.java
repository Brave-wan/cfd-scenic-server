package view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cfd.business.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 取消订单
 * Created by Administrator on 2016/7/28 0028.
 */
public class DialogConfirmTx extends Dialog {


    @Bind(R.id.ll_confirm)
    LinearLayout llConfirm;
    @Bind(R.id.ll_cancel)
    LinearLayout llCancel;
    @Bind(R.id.tv_content)
    TextView tvContent;

    String mContext;

    public DialogConfirmTx(Context context, String content) {
        super(context);
        mContext=content;
    }

    public DialogConfirmTx(Context context, int themeResId, String content) {
        super(context, themeResId);
        mContext=content;
    }

    protected DialogConfirmTx(Context context, boolean cancelable, OnCancelListener cancelListener, String content) {
        super(context, cancelable, cancelListener);
        mContext=content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm_tx);
        ButterKnife.bind(this);
        tvContent.setText(mContext);
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
