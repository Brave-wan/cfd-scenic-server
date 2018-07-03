package view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cfd.business.R;


/**
 * Created by Administrator on 2016/7/22 0022.
 */
public class MyTopBar extends LinearLayout {
    String mCenterTv;                        //中间内容

    boolean mLeftImageVisibility=false;     //左侧图片是否显示
    int mLeftImage;                         //左侧图片
    boolean mRightTextVisibility=false;     //右侧文字是否显示
    String mRightText;                      //右侧文字
    boolean mLeftImageClick=false;          //默认点击事件
    // boolean mRightImageVisibility=false;     //右侧图片是否显示
    //int mRightImage;                         //右侧图片
    TextView right_tv;                          //右侧文字
    TextView center_tv;                         //中间文字
    ImageView left_imageView;                   //左侧图片

    public MyTopBar(Context context) {
        this(context, null);
    }

    public MyTopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getValues(attrs);
        initView(context);

    }

    private void initView(final Context context){
        LayoutInflater.from(context).inflate(R.layout.my_topbar, this);
        left_imageView= (ImageView) findViewById(R.id.im_left_topbar);
        center_tv= (TextView) findViewById(R.id.tv_center);
        right_tv= (TextView) findViewById(R.id.tv_right);

        //左侧图片是否显示
        if (mLeftImageVisibility){
            left_imageView.setVisibility(VISIBLE);
            //left_imageView.setImageResource(mLeftImage);
        }else{
            left_imageView.setVisibility(GONE);
        }

        //中间文字
        center_tv.setText(mCenterTv);

        //右侧文字是否显示
        if (mRightTextVisibility){
            right_tv.setVisibility(VISIBLE);
            right_tv.setText(mRightText);
        }else{
            right_tv.setVisibility(GONE);
        }

        //左侧图片单击事件
        if (mLeftImageClick)
            left_imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getContext() instanceof Activity) {
                        ((Activity) getContext()).finish();
                    }
                }
            });
    }

    private void getValues(AttributeSet attrs){

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.MyTopBar);

        mCenterTv=array.getString(R.styleable.MyTopBar_tb_centerTv);
        mLeftImageVisibility=array.getBoolean(R.styleable.MyTopBar_tb_leftImVisibility, true);
        mLeftImage=array.getResourceId(R.styleable.MyTopBar_tb_leftIm, 0);
        mRightTextVisibility=array.getBoolean(R.styleable.MyTopBar_tb_rightTvVisibility, false);
        mRightText=array.getString(R.styleable.MyTopBar_tb_rightTv);
        mLeftImageClick=array.getBoolean(R.styleable.MyTopBar_tb_leftImClick, false);
        //mRightImageVisibility=array.getBoolean(R.styleable.MyTopBar_tb_rightImVisibility, false);
        //mRightImage=array.getResourceId(R.styleable.MyTopBar_tb_rightIm, 0);
        array.recycle();
    }

    //右侧文字单击事件
    public void setRightTextOnClick(OnClickListener listener){
        right_tv.setOnClickListener(listener);
    }

    //改变右侧文字显示
    public void setRightText(String s){
        right_tv.setText(s);
    }

    //改变右侧文字颜色
    public void setRightTextColor(String color){
        right_tv.setTextColor(Color.parseColor(color));
    }
}
