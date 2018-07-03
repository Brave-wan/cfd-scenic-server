package activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cfd.business.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fragment.Fragment_RefundOrder_Fd;
import fragment.Fragment_RefundOrder_Sp;

/**
 * 退款订单--饭店
 * Created by Administrator on 2016/9/5 0005.
 */
public class Activity_RefundOrder_Fd extends FragmentActivity {

    @Bind(R.id.tv_daishenhe)
    TextView tvDaishenhe;
    @Bind(R.id.tv_daishenhexian)
    TextView tvDaishenhexian;
    @Bind(R.id.ll_daishenhe)
    LinearLayout llDaishenhe;
    @Bind(R.id.tv_yituikuan)
    TextView tvYituikuan;
    @Bind(R.id.tv_yituikuanxian)
    TextView tvYituikuanxian;
    @Bind(R.id.ll_yituikuan)
    LinearLayout llYituikuan;
    @Bind(R.id.fl_fragment)
    FrameLayout flFragment;

    int state = 1;
    public static Fragment fragment_dsh,fragment_ytk, currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_order_fd);
        ButterKnife.bind(this);

        initTab();
    }
    @OnClick({R.id.ll_daishenhe, R.id.ll_yituikuan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_daishenhe:
                state = 1;
                if (fragment_dsh == null) {
                    fragment_dsh = new Fragment_RefundOrder_Fd();
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", state);
                    fragment_dsh.setArguments(bundle);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), fragment_dsh);
                chageView("#F75B5C", "#333333",  View.VISIBLE, View.INVISIBLE);
                break;
            case R.id.ll_yituikuan:
                state = 2;
                if (fragment_ytk == null) {
                    fragment_ytk = new Fragment_RefundOrder_Fd();
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", state);
                    fragment_ytk.setArguments(bundle);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), fragment_ytk);
                chageView("#333333", "#F75B5C", View.INVISIBLE,  View.VISIBLE);
                break;
        }
    }


    private void chageView(String s1, String s2,int i1, int i2) {
        tvDaishenhe.setTextColor(Color.parseColor(s1));
        tvYituikuan.setTextColor(Color.parseColor(s2));

        tvDaishenhexian.setVisibility(i1);
        tvYituikuanxian.setVisibility(i2);
    }

    /**
     * 初始化显示的第一个fragment
     */
    private void initTab() {
        //判断进入那个版本
        if (fragment_dsh == null) {
            fragment_dsh = new Fragment_RefundOrder_Fd();
            Bundle bundle = new Bundle();
            bundle.putInt("state", state);
            fragment_dsh.setArguments(bundle);
        }

        if (!fragment_dsh.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_fragment, fragment_dsh).commit();
            // 记录当前Fragment
            currentFragment = fragment_dsh;
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
