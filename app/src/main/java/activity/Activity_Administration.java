package activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.cfd.business.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fragment.Fragment_PendingSettlement;
import fragment.Fragment_TurnoverToday;

/**
 * 账户管理
 * Created by Administrator on 2016/7/27 0027.
 */
public class Activity_Administration extends FragmentActivity {

    @Bind(R.id.tv_yingyee)
    TextView tvYingyee;
    @Bind(R.id.tv_yingyeeQ)
    TextView tvYingyeeQ;
    @Bind(R.id.yingyeexian)
    TextView yingyeexian;
    @Bind(R.id.ll_yingyee)
    LinearLayout llYingyee;
    @Bind(R.id.tv_jine)
    TextView tvJine;
    @Bind(R.id.tv_jineQ)
    TextView tvJineQ;
    @Bind(R.id.tv_jinexian)
    TextView tvJinexian;
    @Bind(R.id.ll_jine)
    LinearLayout llJine;

    Fragment currentFragment, turnoverTodayFragment,pendingSettlementFragment;
    @Bind(R.id.ll_layout)
    LinearLayout llLayout;
    @Bind(R.id.fl_fragment)
    FrameLayout flFragment;
    @Bind(R.id.scrollView)
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration);
        ButterKnife.bind(this);

        initFragment();

        //获取焦点
        llLayout.setFocusable(true);
        llLayout.setFocusableInTouchMode(true);
        llLayout.requestFocus();

    }

    @OnClick({R.id.ll_yingyee, R.id.ll_jine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_yingyee:
                chageView(yingyeexian, tvYingyee, tvYingyeeQ, tvJinexian, tvJine, tvJineQ);
                if (turnoverTodayFragment == null) {
                    turnoverTodayFragment = new Fragment_TurnoverToday();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), turnoverTodayFragment);
                break;
            case R.id.ll_jine:
                chageView(tvJinexian, tvJine, tvJineQ, yingyeexian, tvYingyee, tvYingyeeQ);
                if (pendingSettlementFragment == null) {
                    pendingSettlementFragment = new Fragment_PendingSettlement();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), pendingSettlementFragment);
                break;
        }
    }

    private void chageView(TextView t_1, TextView t_2, TextView t_3, TextView f_1, TextView f_2, TextView f_3) {
        t_1.setVisibility(View.VISIBLE);
        t_2.setTextColor(Color.parseColor("#333333"));
        t_3.setTextColor(Color.parseColor("#F75B5C"));

        f_1.setVisibility(View.INVISIBLE);
        f_2.setTextColor(Color.parseColor("#999999"));
        f_3.setTextColor(Color.parseColor("#3598DB"));
    }

    /**
     * 初始化显示的第一个fragment
     */
    private void initFragment() {
        //判断进入那个版本
        if (turnoverTodayFragment == null) {
            turnoverTodayFragment = new Fragment_TurnoverToday();
        }

        if (!turnoverTodayFragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_fragment, turnoverTodayFragment).commit();
            // 记录当前Fragment
            currentFragment = turnoverTodayFragment;
        }
    }

    //fragment优化
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.fl_fragment, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }
}
