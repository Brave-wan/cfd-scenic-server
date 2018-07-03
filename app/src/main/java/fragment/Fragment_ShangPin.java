package fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cfd.business.R;
import com.jauker.widget.BadgeView;

import activity.Activity_Search_Fd;
import activity.Activity_Search_Sp;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class Fragment_ShangPin extends Fragment {

    @Bind(R.id.et_sousuo)
    TextView etSousuo;
    @Bind(R.id.tv_daifahuo)
    TextView tvDaifahuo;
    @Bind(R.id.tv_daifahuoxian)
    TextView tvDaifahuoxian;
    @Bind(R.id.ll_daifahuo)
    LinearLayout llDaifahuo;
    @Bind(R.id.tv_yifahuo)
    TextView tvYifahuo;
    @Bind(R.id.tv_yifahuoxian)
    TextView tvYifahuoxian;
    @Bind(R.id.ll_yifahuo)
    LinearLayout llYifahuo;
    @Bind(R.id.tv_yiwancheng)
    TextView tvYiwancheng;
    @Bind(R.id.tv_yiwanchengxian)
    TextView tvYiwanchengxian;
    @Bind(R.id.ll_yiwancheng)
    LinearLayout llYiwancheng;
    @Bind(R.id.fl_fragment)
    FrameLayout flFragment;

    int i = 1;

    public static Fragment sp_1, sp_2, sp_3,
            currentFragment;
    int state_xia = 1;//状态值state_xia下边选中的位置
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    private BadgeView badgeView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shang_pin, null);
        ButterKnife.bind(this, view);

        etSousuo.setHintTextColor(Color.parseColor("#3da2e6"));

        /*listView.setAdapter(new Adapter_ShangPin(getContext(), i));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Activity_OrderDetails_Sp.class);
                intent.putExtra("state", i);
                startActivity(intent);
            }
        });*/

        badgeView = new BadgeView(getActivity());
        initFragment();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    private void chageView(String s1, String s2, String s3, int i1, int i2, int i3) {
        tvDaifahuo.setTextColor(Color.parseColor(s1));
        tvYifahuo.setTextColor(Color.parseColor(s2));
        tvYiwancheng.setTextColor(Color.parseColor(s3));

        tvDaifahuoxian.setVisibility(i1);
        tvYifahuoxian.setVisibility(i2);
        tvYiwanchengxian.setVisibility(i3);
    }

    @OnClick({R.id.ll_daifahuo, R.id.ll_yifahuo, R.id.ll_yiwancheng,R.id.iv_search,R.id.et_sousuo})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_search:
                intent=new Intent(getContext(), Activity_Search_Sp.class);
                startActivity(intent);
                break;
            case R.id.et_sousuo:
                intent=new Intent(getContext(), Activity_Search_Sp.class);
                startActivity(intent);
                break;
            case R.id.ll_daifahuo:
                state_xia = 1;
                chageView("#F75B5C", "#333333", "#333333", View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
                /*i = 1;
                listView.setAdapter(new Adapter_ShangPin(getContext(), i));*/
                if (sp_1 == null) {
                    sp_1 = new Fragment_SpInside();
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", state_xia);
                    sp_1.setArguments(bundle);
                }
                addOrShowFragment(getActivity().getSupportFragmentManager().beginTransaction(), sp_1);
                break;
            case R.id.ll_yifahuo:
                state_xia = 3;
                chageView("#333333", "#F75B5C", "#333333", View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
                if (sp_2 == null) {
                    sp_2 = new Fragment_SpInside();
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", state_xia);
                    sp_2.setArguments(bundle);
                }
                addOrShowFragment(getActivity().getSupportFragmentManager().beginTransaction(), sp_2);
                break;
            case R.id.ll_yiwancheng:
                state_xia = 2;
                chageView("#333333", "#333333", "#F75B5C", View.INVISIBLE, View.INVISIBLE, View.VISIBLE);
                if (sp_3 == null) {
                    sp_3 = new Fragment_SpInside();
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", state_xia);
                    sp_3.setArguments(bundle);
                }
                addOrShowFragment(getActivity().getSupportFragmentManager().beginTransaction(), sp_3);
                break;
        }
    }


    /**
     * 初始化显示的第一个fragment
     */
    private void initFragment() {
        if (sp_1 == null) {
            sp_1 = new Fragment_SpInside();
            Bundle bundle = new Bundle();
            bundle.putInt("state", state_xia);
            sp_1.setArguments(bundle);
        }

        if (!sp_1.isAdded()) {
            // 提交事务
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_fragment, sp_1).commit();
            // 记录当前Fragment
            currentFragment = sp_1;
        }
    }

    //H服务fragment优化
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


    public void setCorner(int i) {
        badgeView.setTargetView(tvDaifahuo);
        badgeView.setBadgeCount(i);
        badgeView.setBadgeMargin(0, 5, 0, 0);
        badgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
    }
}
