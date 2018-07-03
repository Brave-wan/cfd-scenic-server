package view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cfd.business.R;

import activity.Activity_Authentication;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * 温馨提示
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogPrompt extends Dialog {
    @Bind(R.id.bt_ok)
    LinearLayout btOk;

    public DialogPrompt(Context context) {
        super(context);
    }

    public DialogPrompt(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogPrompt(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_prompt);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_ok)
    public void onClick() {
        dismiss();
        Activity_Authentication.activityHashMap.get("Activity_Sjrz").finish();
        Activity_Authentication.activityHashMap.get("Activity_Authentication").finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //do something...
            dismiss();
            Activity_Authentication.activityHashMap.get("Activity_Sjrz").finish();
            Activity_Authentication.activityHashMap.get("Activity_Authentication").finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
