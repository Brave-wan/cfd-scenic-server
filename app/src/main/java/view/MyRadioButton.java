package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cfd.business.R;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class MyRadioButton extends LinearLayout {
    private ImageView imageView;        //图片
    private TextView textView;          //文字

    String mContext;                        //内容
    int mTrueImage;                         //选中图片
    int mFalseImage;                         //未选中图片

    private boolean Display_Status=false;   //显示状态   是选中还是不选中



    public MyRadioButton(Context context) {
        this(context, null);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getValues(attrs);
        initView(context);
    }

    private void initView(final Context context){
        LayoutInflater.from(context).inflate(R.layout.my_radio_button, this);
        imageView= (ImageView) findViewById(R.id.im_MyRadioButton);
        textView= (TextView) findViewById(R.id.tv_MyRadioButton);
        if (Display_Status){
            imageView.setImageResource(mTrueImage);
        }else{
            imageView.setImageResource(mFalseImage);
        }

        textView.setText(mContext);
    }

    private void getValues(AttributeSet attrs){
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.MyRadioButton);
        mContext=array.getString(R.styleable.MyRadioButton_rb_TextView);
        mTrueImage=array.getResourceId(R.styleable.MyRadioButton_rb_True_ImageView, 0);
        mFalseImage=array.getResourceId(R.styleable.MyRadioButton_rb_false_ImageView, 0);
        Display_Status=array.getBoolean(R.styleable.MyRadioButton_rb_Display_Status, false);
    }

    public void setStatus(boolean b){
        Display_Status=b;
        if (Display_Status){
            imageView.setImageResource(mTrueImage);
        }else{
            imageView.setImageResource(mFalseImage);
        }
    }

    public boolean isDisplay_Status() {
        return Display_Status;
    }

}
