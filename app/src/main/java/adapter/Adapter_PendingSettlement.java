package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cfd.business.R;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class Adapter_PendingSettlement extends BaseAdapter {
    Context mContext;
    String type;

    public Adapter_PendingSettlement(Context mContext,String type) {
        this.mContext = mContext;
        this.type=type;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (type.equals("1")) {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_jiudian,null);
        } else if (type.equals("2")) {
            convertView=LayoutInflater.from(mContext).inflate(R.layout.adapter_fandian_taocan,null);
        } else if (type.equals("3")) {
            convertView=LayoutInflater.from(mContext).inflate(R.layout.adapter_shangpin,null);
        }else{
            convertView=LayoutInflater.from(mContext).inflate(R.layout.adapter_jiudian,null);
        }
        return convertView;
    }
}
