package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.cfd.business.R;

import activity.Activity_OrderDetails_Fddp;
import activity.Activity_OrderDetails_Jd;
import activity.Activity_OrderDetails_Sp;
import adapter.Adapter_JiuDian;
import adapter.Adapter_ShangPin;
import adapter.Adapter_TaoCan;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.SpUtil;
import view.MyListView;

/**
 * 累计待结算金额
 * Created by Administrator on 2016/7/28 0028.
 */
public class Fragment_PendingSettlement extends Fragment {

    @Bind(R.id.listView)
    MyListView listView;

    String type;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_settlement, null);
        ButterKnife.bind(this, view);
        type= SpUtil.getString(getActivity(), "Login_Type", "1");


        if (type.equals("1")) {
//            listView.setAdapter(new Adapter_JiuDian(getContext(),1));
        } else if (type.equals("2")) {
//            listView.setAdapter(new Adapter_TaoCan(getContext(),1));
        } else if (type.equals("3")) {
            //listView.setAdapter(new Adapter_ShangPin(getContext(),1));
        } else {
//            listView.setAdapter(new Adapter_JiuDian(getContext(),1));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                if (type.equals("1")) {
                    intent.setClass(getActivity(), Activity_OrderDetails_Jd.class);
                } else if (type.equals("2")) {
                    intent.setClass(getActivity(), Activity_OrderDetails_Fddp.class);
                } else if (type.equals("3")) {
                    intent.setClass(getActivity(), Activity_OrderDetails_Sp.class);
                } else {
                    intent.setClass(getActivity(), Activity_OrderDetails_Jd.class);
                }
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
