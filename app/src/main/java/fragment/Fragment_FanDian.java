package fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cfd.business.R;

import activity.Activity_Search_Fd;
import activity.Activity_Search_Sp;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 饭店版
 * Created by Administrator on 2016/7/28 0028.
 */
public class Fragment_FanDian extends Fragment {

    @Bind(R.id.et_sousuo)
    TextView etSousuo;
    @Bind(R.id.tv_weishiyong)
    TextView tvWeishiyong;
    @Bind(R.id.tv_weishiyongxian)
    TextView tvWeishiyongxian;
    @Bind(R.id.ll_weishiyong)
    LinearLayout llWeishiyong;
    @Bind(R.id.tv_yishiyong)
    TextView tvYishiyong;
    @Bind(R.id.tv_yishiyongxian)
    TextView tvYishiyongxian;
    @Bind(R.id.ll_yishiyong)
    LinearLayout llYishiyong;
    @Bind(R.id.tv_yiguoqi)
    TextView tvYiguoqi;
    @Bind(R.id.tv_yiguoqixian)
    TextView tvYiguoqixian;
    @Bind(R.id.ll_yiguoqi)
    LinearLayout llYiguoqi;
    @Bind(R.id.tv_danpin)
    TextView tvDanpin;
    @Bind(R.id.tv_taocan)
    TextView tvTaocan;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.fl_fd_fragment)
    FrameLayout flFdFragment;


    int state = 1;            //传给fragment用   判断是选中的未使用，已使用，已过期
    int reserve_State = 1;    //判断是套餐还是单品

    Fragment dpYiShiYong, dpWeiShiYong, dpYiGuoQi, currentFragment,
            tcYiShiYong, tcWeiShiYong, tcYiGuoQi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fan_dian, null);
        ButterKnife.bind(this, view);

        etSousuo.setHintTextColor(Color.parseColor("#3da2e6"));

        initTab();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ll_weishiyong, R.id.ll_yishiyong, R.id.ll_yiguoqi, R.id.tv_danpin, R.id.tv_taocan,R.id.iv_search,R.id.et_sousuo})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_search:
                intent=new Intent(getContext(), Activity_Search_Fd.class);
                startActivity(intent);
                break;
            case R.id.et_sousuo:
                intent=new Intent(getContext(), Activity_Search_Fd.class);
                startActivity(intent);
                break;
            case R.id.ll_weishiyong:
                chageView("#F75B5C", "#333333", "#333333", View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
                state = 1;
                replaceFragment(dpWeiShiYong);
                break;
            case R.id.ll_yishiyong:
                chageView("#333333", "#F75B5C", "#333333", View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
                state = 2;
                replaceFragment(dpYiShiYong);
                break;
            case R.id.ll_yiguoqi:
                chageView("#333333", "#333333", "#F75B5C", View.INVISIBLE, View.INVISIBLE, View.VISIBLE);
                state = 3;
                replaceFragment(dpYiGuoQi);
                break;
            case R.id.tv_danpin:
                chageText(tvDanpin, tvTaocan);
                reserve_State = 1;
                if (reserve_State == 1) {
                    if (state == 1) {
                        replaceFragment(dpWeiShiYong);
                    } else if (state == 2) {
                        replaceFragment(dpYiShiYong);
                    } else if (state == 3) {
                        replaceFragment(dpYiGuoQi);
                    }
                } else {
                    if (state == 1) {
                        replaceFragment(tcWeiShiYong);
                    } else if (state == 2) {
                        replaceFragment(tcYiShiYong);
                    } else if (state == 3) {
                        replaceFragment(tcYiGuoQi);
                    }
                }
                break;
            case R.id.tv_taocan:
                chageText(tvTaocan, tvDanpin);
                reserve_State = 2;
                if (reserve_State == 1) {
                    if (state == 1) {
                        replaceFragment(dpWeiShiYong);
                    } else if (state == 2) {
                        replaceFragment(dpYiShiYong);
                    } else if (state == 3) {
                        replaceFragment(dpYiGuoQi);
                    }
                } else {
                    if (state == 1) {
                        replaceFragment(tcWeiShiYong);
                    } else if (state == 2) {
                        replaceFragment(tcYiShiYong);
                    } else if (state == 3) {
                        replaceFragment(tcYiGuoQi);
                    }
                }
                break;
        }
    }

    private void chageView(String s1, String s2, String s3, int i1, int i2, int i3) {
        tvWeishiyong.setTextColor(Color.parseColor(s1));
        tvYishiyong.setTextColor(Color.parseColor(s2));
        tvYiguoqi.setTextColor(Color.parseColor(s3));

        tvWeishiyongxian.setVisibility(i1);
        tvYishiyongxian.setVisibility(i2);
        tvYiguoqixian.setVisibility(i3);
    }

    private void chageText(TextView tTrue, TextView tFalse) {
        tTrue.setTextColor(Color.parseColor("#ffffff"));
        tTrue.setBackgroundResource(R.mipmap.fandian_lankuang);

        tFalse.setTextColor(Color.parseColor("#333333"));
        tFalse.setBackgroundColor(Color.parseColor("#00000000"));
    }

    //替换Fragment
    private void replaceFragment(Fragment fragment) {
        if (reserve_State == 1) {
//            if (fragment == null) {
            fragment = new Fragment_Danpin();
            Bundle bundle = new Bundle();
            bundle.putInt("index", state);
            fragment.setArguments(bundle);
//            }
        } else if (reserve_State == 2) {
//            if (fragment == null) {
            fragment = new Fragment_TaoCan();
            Bundle bundle = new Bundle();
            bundle.putInt("index", state);
            fragment.setArguments(bundle);
//            }
        }

        addOrShowFragment(getActivity().getSupportFragmentManager().beginTransaction(), fragment);
    }

    /**
     * 初始化显示的第一个fragment
     */
    private void initTab() {
        //判断进入那个版本
        if (dpWeiShiYong == null) {
            dpWeiShiYong = new Fragment_Danpin();
            Bundle bundle = new Bundle();
            bundle.putInt("index", state);
            dpWeiShiYong.setArguments(bundle);
        }

        if (!dpWeiShiYong.isAdded()) {
            // 提交事务
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_fd_fragment, dpWeiShiYong).commit();
            // 记录当前Fragment
            currentFragment = dpWeiShiYong;
        }
    }

    //fragment优化
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.fl_fd_fragment, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }

}
