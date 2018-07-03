package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.JsonBean.AuthenticationJson;
import com.cfd.business.R;
import com.google.gson.Gson;

import activity.Activity_Administration;
import activity.Activity_Main;
import activity.Activity_MerchantBalance;
import activity.Activity_News;
import activity.Activity_RefundOrder_Fd;
import activity.Activity_RefundOrder_Jd;
import activity.Activity_RefundOrder_Sp;
import activity.Activity_Set;
import activity.Activity_ShopFd;
import activity.Activity_ShopJd;
import activity.Activity_ShopSp;
import activity.Activity_SjOk;
import activity.Activity_SmOk;
import activity.Activity_Statistics;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import util.SpUtil;
import view.MyLinearLayoutItem;

/**
 * 店铺中心
 * Created by Administrator on 2016/7/27 0027.
 */
public class Fragment_Shop extends Fragment {

    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.ll_shiming)
    LinearLayout llShiming;
    @Bind(R.id.ll_shangjia)
    LinearLayout llShangjia;
    @Bind(R.id.ll_jiesuan)
    LinearLayout llJiesuan;
    @Bind(R.id.view_statistics)
    MyLinearLayoutItem viewStatistics;
    @Bind(R.id.view_news)
    MyLinearLayoutItem viewNews;
    @Bind(R.id.view_shop)
    MyLinearLayoutItem viewStop;
    @Bind(R.id.view_set)
    MyLinearLayoutItem viewSet;
    @Bind(R.id.view_Refund_order)
    MyLinearLayoutItem viewRefundOrder;
    @Bind(R.id.view_Merchant_balance)
    MyLinearLayoutItem viewMerchantBalance;
    Activity_Main activity_main;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, null);
        ButterKnife.bind(this, view);
        activity_main=(Activity_Main)getActivity();
        tvName.setText(activity_main.authenticationJson.getName());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.view_Refund_order,R.id.ll_shiming, R.id.ll_shangjia, R.id.ll_jiesuan, R.id.view_statistics, R.id.view_Merchant_balance, R.id.view_news, R.id.view_shop, R.id.view_set})
    public void onClick(View view) {
        Intent intent = new Intent();
        String type = SpUtil.getString(getContext(), "Login_Type", "1");
        switch (view.getId()) {
            case R.id.ll_shiming:
                intent.setClass(getContext(), Activity_SmOk.class);
                startActivity(intent);
                break;
            case R.id.ll_shangjia:
                intent.setClass(getContext(), Activity_SjOk.class);
                startActivity(intent);
                break;
            case R.id.ll_jiesuan:
                intent.setClass(getContext(), Activity_SjOk.class);
                startActivity(intent);
                break;
            case R.id.view_statistics://数据统计
                intent.setClass(getContext(), Activity_Statistics.class);
                startActivity(intent);
                break;
            case R.id.view_Merchant_balance://账户余额
                intent.setClass(getContext(), Activity_MerchantBalance.class);
                startActivity(intent);
                break;
            case R.id.view_Refund_order:    //退款订单      判断进入那个退款订单
                if (type.equals("1")) {
                    intent.setClass(getContext(), Activity_RefundOrder_Jd.class);
                    startActivity(intent);
                } else if (type.equals("2")) {
                    intent.setClass(getContext(), Activity_RefundOrder_Fd.class);
                    startActivity(intent);
                } else if (type.equals("3")) {
                    intent.setClass(getContext(), Activity_RefundOrder_Sp.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), Activity_RefundOrder_Sp.class);
                    startActivity(intent);
                }
                break;
            case R.id.view_news://消息中心
                intent.setClass(getContext(), Activity_News.class);
                startActivity(intent);
                break;
            case R.id.view_shop://店铺信息
                if (type.equals("1")) {
                    intent.setClass(getContext(), Activity_ShopJd.class);
                    startActivity(intent);
                } else if (type.equals("2")) {
                    intent.setClass(getContext(), Activity_ShopFd.class);
                    startActivity(intent);
                } else if (type.equals("3")) {
                    intent.setClass(getContext(), Activity_ShopSp.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), Activity_ShopSp.class);
                    startActivity(intent);
                }

                break;
            case R.id.view_set:
                intent.setClass(getContext(), Activity_Set.class);
                startActivity(intent);
                break;
        }
    }


}
