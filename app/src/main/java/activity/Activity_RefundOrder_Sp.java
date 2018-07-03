package activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cfd.business.R;
import com.jauker.widget.BadgeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fragment.Fragment_RefundOrder_Sp;
import fragment.Fragment_ShangPin;
import view.MyTopBar;

/**
 * 退款订单--商品
 * Created by Administrator on 2016/9/5 0005.
 */
public class Activity_RefundOrder_Sp extends FragmentActivity {


    @Bind(R.id.view_myTopBar)
    MyTopBar viewMyTopBar;
    @Bind(R.id.tv_daishenhe)
    TextView tvDaishenhe;
    @Bind(R.id.tv_daishenhexian)
    TextView tvDaishenhexian;
    @Bind(R.id.ll_daishenhe)
    RelativeLayout llDaishenhe;
    @Bind(R.id.tv_shouhuozhong)
    TextView tvShouhuozhong;
    @Bind(R.id.tv_shouhuozhongxian)
    TextView tvShouhuozhongxian;
    @Bind(R.id.ll_shouhuozhong)
    RelativeLayout llShouhuozhong;
    @Bind(R.id.tv_yituikuan)
    TextView tvYituikuan;
    @Bind(R.id.tv_yituikuanxian)
    TextView tvYituikuanxian;
    @Bind(R.id.ll_yituikuan)
    LinearLayout llYituikuan;
    @Bind(R.id.fl_fragment)
    FrameLayout flFragment;
    int state = 1;
    public static Fragment fragment_dsh, fragment_tkz, fragment_ytk, currentFragment;

    private BadgeView badgeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_order_sp);
        ButterKnife.bind(this);

        badgeView = new com.jauker.widget.BadgeView(Activity_RefundOrder_Sp.this);
        initTab();
    }

    @OnClick({R.id.ll_daishenhe, R.id.ll_shouhuozhong, R.id.ll_yituikuan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_daishenhe:
                state = 1;
                if (fragment_dsh == null) {
                    fragment_dsh = new Fragment_RefundOrder_Sp();
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", state);
                    fragment_dsh.setArguments(bundle);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), fragment_dsh);
                chageView("#F75B5C", "#333333", "#333333", View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
                break;
            case R.id.ll_shouhuozhong:
                state = 2;
                if (fragment_tkz == null) {
                    fragment_tkz = new Fragment_RefundOrder_Sp();
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", state);
                    fragment_tkz.setArguments(bundle);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), fragment_tkz);
                chageView("#333333", "#F75B5C", "#333333", View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
                break;
            case R.id.ll_yituikuan:
                state = 3;
                if (fragment_ytk == null) {
                    fragment_ytk = new Fragment_RefundOrder_Sp();
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", state);
                    fragment_ytk.setArguments(bundle);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), fragment_ytk);
                chageView("#333333", "#333333", "#F75B5C", View.INVISIBLE, View.INVISIBLE, View.VISIBLE);
                break;
        }
    }


    private void chageView(String s1, String s2, String s3, int i1, int i2, int i3) {
        tvDaishenhe.setTextColor(Color.parseColor(s1));
        tvShouhuozhong.setTextColor(Color.parseColor(s2));
        tvYituikuan.setTextColor(Color.parseColor(s3));

        tvDaishenhexian.setVisibility(i1);
        tvShouhuozhongxian.setVisibility(i2);
        tvYituikuanxian.setVisibility(i3);
    }

    /**
     * 初始化显示的第一个fragment
     */
    private void initTab() {
        //判断进入那个版本
        if (fragment_dsh == null) {
            fragment_dsh = new Fragment_RefundOrder_Sp();
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

    public  void setCorner(int i){
        badgeView.setTargetView(tvDaishenhe);
        badgeView.setBadgeCount(i);
        badgeView.setBadgeMargin(0,5,0,0);
        badgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
    }
}
