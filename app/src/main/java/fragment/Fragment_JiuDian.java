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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cfd.business.R;

import activity.Activity_Search_Jd;
import activity.Activity_Search_Sp;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 酒店
 * Created by Administrator on 2016/7/27 0027.
 */
public class Fragment_JiuDian extends Fragment {

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
    @Bind(R.id.et_sousuo)
    TextView etSousuo;
    @Bind(R.id.content_linear)
    LinearLayout content_linear;

    public int i = 1;
//    @Bind(R.id.listView)
//    ListView listView;

    Fragment jiudian1, jiudian2, jiudian3, jiudiantem;
    @Bind(R.id.iv_search)
    ImageView ivSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jiu_dian, null);
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


    @OnClick({R.id.ll_weishiyong, R.id.ll_yishiyong, R.id.ll_yiguoqi,R.id.iv_search,R.id.et_sousuo})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_search:
                intent=new Intent(getContext(), Activity_Search_Jd.class);
                startActivity(intent);
                break;
            case R.id.et_sousuo:
                intent=new Intent(getContext(), Activity_Search_Jd.class);
                startActivity(intent);
                break;
            case R.id.ll_weishiyong:
                chageView("#F75B5C", "#333333", "#333333", View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
                i = 1;
//                if (jiudian1 == null) {
                jiudian1 = new Fragment_Jiudiansecond();
//                }
                addOrShowFragment(getActivity().getSupportFragmentManager().beginTransaction(), jiudian1);
                break;
            case R.id.ll_yishiyong:
                chageView("#333333", "#F75B5C", "#333333", View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
                i = 2;
//                if (jiudian2 == null) {
                jiudian2 = new Fragment_Jiudiansecond();
//                }
                addOrShowFragment(getActivity().getSupportFragmentManager().beginTransaction(), jiudian2);
                break;
            case R.id.ll_yiguoqi:
                chageView("#333333", "#333333", "#F75B5C", View.INVISIBLE, View.INVISIBLE, View.VISIBLE);
                i = 3;
//                if (jiudian3 == null) {
                jiudian3 = new Fragment_Jiudiansecond();
//                }
                addOrShowFragment(getActivity().getSupportFragmentManager().beginTransaction(), jiudian3);
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

    /**
     * 初始化显示的第一个fragment
     */
    private void initTab() {
        //判断进入那个版本
//        if (jiudian1 == null) {
        jiudian1 = new Fragment_Jiudiansecond();
//        }

        if (!jiudian1.isAdded()) {
            // 提交事务
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_linear, jiudian1).commit();
            // 记录当前Fragment
            jiudiantem = jiudian1;
        }
    }

    //fragment优化
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (jiudiantem == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(jiudiantem)
                    .add(R.id.content_linear, fragment).commit();
        } else {
            transaction.hide(jiudiantem).show(fragment).commit();
        }

        jiudiantem = fragment;
    }
}
