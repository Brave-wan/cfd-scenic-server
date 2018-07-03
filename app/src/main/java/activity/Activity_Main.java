package activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.JsonBean.AuthenticationJson;
import com.cfd.business.R;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import fragment.Fragment_FanDian;
import fragment.Fragment_JiuDian;
import fragment.Fragment_ShangPin;
import fragment.Fragment_Shop;
import util.SpUtil;

/**
 * 主界面
 * Created by Administrator on 2016/7/27 0027.
 */

public class Activity_Main extends FragmentActivity {

    @Bind(R.id.im_main_order)
    ImageView imMainOrder;
    @Bind(R.id.tv_main_order)
    TextView tvMainOrder;
    @Bind(R.id.ll_main_order)
    LinearLayout llMainOrder;
    @Bind(R.id.im_main_shop)
    ImageView imMainShop;
    @Bind(R.id.tv_main_shop)
    TextView tvMainShop;
    @Bind(R.id.ll_main_shop)
    LinearLayout llMainShop;

    String i = "3";

    public Fragment shopFragment, currentFragment, initFragment;
    public AuthenticationJson authenticationJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        Activity_Authentication.activityHashMap.put("Activity_Main", Activity_Main.this);
        authenticationJson = new Gson().fromJson(SpUtil.getString(Activity_Main.this, "authenticationJson", ""), AuthenticationJson.class);
        if (authenticationJson.getShopId().equals("4"))
            SpUtil.putString(getApplication(), "Login_Type", "3");
        else
            i = authenticationJson.getShopId();
        SpUtil.putString(getApplication(), "Login_Type", i);
        initTab();
    }

    @OnClick({R.id.ll_main_order, R.id.ll_main_shop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_main_order:
                if (initFragment == null) {

                    if (i.equals("1")) {
                        initFragment = new Fragment_JiuDian();
                    } else if (i.equals("2")) {
                        initFragment = new Fragment_FanDian();
                    } else if (i.equals("3")) {
                        initFragment = new Fragment_ShangPin();
                    } else {
                        initFragment = new Fragment_JiuDian();
                    }
                }
                imMainOrder.setImageResource(R.mipmap.guanli_bai);
                imMainShop.setImageResource(R.mipmap.dianpu_hei);
                tvMainOrder.setTextColor(Color.parseColor("#ffffff"));
                tvMainShop.setTextColor(Color.parseColor("#000000"));
                llMainOrder.setBackgroundColor(Color.parseColor("#3598DB"));
                llMainShop.setBackgroundColor(Color.parseColor("#ffffff"));
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), initFragment);
                break;
            case R.id.ll_main_shop:
                if (shopFragment == null) {
                    shopFragment = new Fragment_Shop();
                }
                imMainOrder.setImageResource(R.mipmap.guanli_hei);
                imMainShop.setImageResource(R.mipmap.dianpu_bai);
                tvMainOrder.setTextColor(Color.parseColor("#000000"));
                tvMainShop.setTextColor(Color.parseColor("#ffffff"));
                llMainOrder.setBackgroundColor(Color.parseColor("#ffffff"));
                llMainShop.setBackgroundColor(Color.parseColor("#3598DB"));
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), shopFragment);
                break;
        }
    }

    /**
     * 初始化显示的第一个fragment
     */
    private void initTab() {
        //判断进入那个版本
        if (initFragment == null) {
            Log.i("initFragment", i + "");
            if (i.equals("1")) {
                initFragment = new Fragment_JiuDian();
            } else if (i.equals("2")) {
                initFragment = new Fragment_FanDian();
            } else if (i.equals("3")) {
                initFragment = new Fragment_ShangPin();
            } else {
                initFragment = new Fragment_JiuDian();
            }
        }

        if (!initFragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ll_main_fragment, initFragment).commit();
            // 记录当前Fragment
            currentFragment = initFragment;
        }
    }

    //fragment优化
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.ll_main_fragment, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }

    public void main_Corner(int type, int num) {
        if (type == 1) {
            Fragment_ShangPin shangPin = (Fragment_ShangPin) initFragment;
            shangPin.setCorner(num);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Activity_Authentication.activityHashMap.get("Activity_Main") != null)
            Activity_Authentication.activityHashMap.get("Activity_Main").finish();
    }


    /**
     * 再点一次退出登录
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            mHandle.sendEmptyMessage(MSG_EXIT);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private static final int MSG_EXIT = 1;
    private static final int MSG_EXIT_WAIT = 2;
    private static final long EXIT_DELAY_TIME = 2000;
    private Handler mHandle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_EXIT:
                    if (mHandle.hasMessages(MSG_EXIT_WAIT)) {
                        finish();
                    } else {
                        Toast.makeText(Activity_Main.this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                        mHandle.sendEmptyMessageDelayed(MSG_EXIT_WAIT, EXIT_DELAY_TIME);
                    }
                    break;
                case MSG_EXIT_WAIT:
                    break;
            }
        }
    };
}
