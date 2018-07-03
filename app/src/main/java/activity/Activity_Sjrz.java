package activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.JsonBean.AuthenticationJson;
import com.JsonBean.LoginBean;
import com.JsonBean.UpfileJson;
import com.application.URL;
import com.cfd.business.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.mytools.MyToast;
import com.mytools.SelectImagePopupWindow;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.http.protocol.HTTP;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import permission.PermissionsActivity;
import permission.PermissionsChecker;
import util.SpUtil;
import util.ToastUtil;
import view.DialogProgressbar;
import view.DialogPrompt;
import view.MyRadioButton;

/**
 * 商家认证
 * Created by Administrator on 2016/7/27 0027.
 */
public class Activity_Sjrz extends Activity implements SelectImagePopupWindow.OnCompleteListener {

    @Bind(R.id.tv_stop_type)
    TextView tvStopType;
    @Bind(R.id.tv_account_type)
    TextView tvAccountType;
    @Bind(R.id.bt_Next)
    Button btNext;
    @Bind(R.id.ll_stop_type)
    LinearLayout llStopType;
    @Bind(R.id.ll_account_type)
    LinearLayout llAccountType;
    @Bind(R.id.view_true)
    MyRadioButton viewTrue;
    @Bind(R.id.view_false)
    MyRadioButton viewFalse;
    @Bind(R.id.ll_yingyezhizhao)
    LinearLayout llYingyezhizhao;
    @Bind(R.id.et_shangjia)
    EditText etShangjia;
    @Bind(R.id.et_chanpin)
    EditText etChanpin;
    @Bind(R.id.et_zhanghu)
    EditText etZhanghu;
    @Bind(R.id.et_yinhang)
    EditText etYinhang;
    @Bind(R.id.et_kaihuhang)
    EditText etKaihuhang;
    @Bind(R.id.iv_businesslicense)
    ImageView iv_businesslicense;
    @Bind(R.id.iv_orhter1)
    ImageView iv_orhter1;
    @Bind(R.id.iv_orhter2)
    ImageView iv_orhter2;

    DialogProgressbar dialogProgressbar;
    int shop_Stye = 1;
    int accout_Stye = 1;
    boolean inputState = false;//是否全部输入完毕
    private PopupWindow mPopWindow;
    private ImageView im_fandian, im_jiudian, im_xiaochi, im_techan, im_geren, im_duigong;

    int int_businesslicense = 1231;
    int int_orhter1 = 1232;
    int int_orhter2 = 1233;
    int int_tem = 0;

    PermissionsChecker mPermissionsChecker;
    private SelectImagePopupWindow SelectphotoPPW;
    AuthenticationJson authenticationJson = new AuthenticationJson();

    // 所需权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public static int REQUEST_CODE = 123456;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjrz);
        ButterKnife.bind(this);

        mPermissionsChecker=new PermissionsChecker(this);
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
            if (resultCode == PermissionsActivity.PERMISSIONS_DENIED)
                finish();
        } else
        SelectphotoPPW.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mPermissionsChecker.getAndroidSDKVersion() >= 23) {
            if (mPermissionsChecker.lacksPermissions(PERMISSIONS))
                startPermissionsActivity();
        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }


    private void init() {
        SelectphotoPPW = new SelectImagePopupWindow(this);
        SelectphotoPPW.addOnCompleteListener(this);
        Activity_Authentication.activityHashMap.put("Activity_Sjrz", Activity_Sjrz.this);
        show_accout(accout_Stye);
        show_shop(shop_Stye);
        pathlist.add(SpUtil.getString(getApplication(), "pathlist1", ""));
        pathlist.add(SpUtil.getString(getApplication(), "pathlist2", ""));
        pathlist.add(SpUtil.getString(getApplication(), "pathlist3", ""));
        pathlist.add(SpUtil.getString(getApplication(), "pathlist4", ""));
        pathlist.add(SpUtil.getString(getApplication(), "pathlist5", ""));
        pathlist.add(SpUtil.getString(getApplication(), "pathlist6", ""));

        if (!SpUtil.getString(getApplication(), "pathlist4", "").equals("")) {
            ImageLoader.getInstance().displayImage(geturi(SpUtil.getString(getApplication(), "pathlist4", "")), iv_businesslicense);
//            pathlist.set(0,SpUtil.getString(getApplication(), "pathlist1", ""));
        }
        if (!SpUtil.getString(getApplication(), "pathlist5", "").equals("")) {
            ImageLoader.getInstance().displayImage(geturi(SpUtil.getString(getApplication(), "pathlist5", "")), iv_orhter1);
//            pathlist.set(1,SpUtil.getString(getApplication(), "pathlist2", ""));
        }
        if (!SpUtil.getString(getApplication(), "pathlist6", "").equals("")) {
            ImageLoader.getInstance().displayImage(geturi(SpUtil.getString(getApplication(), "pathlist6", "")), iv_orhter2);
//            pathlist.set(2,SpUtil.getString(getApplication(), "pathlist3", ""));
        }

        if (!SpUtil.getString(getApplication(), "authenticationJson", "").equals("")) {
            authenticationJson = new Gson().fromJson(SpUtil.getString(getApplication(), "authenticationJson", ""), AuthenticationJson.class);

            //回显
            etShangjia.setText(authenticationJson.getName());
            etShangjia.setSelection(etShangjia.getText().length());
            if (authenticationJson.getShopId().equals("1")) {
                shop_Stye = 1;
                tvStopType.setText("酒店");
            } else if (authenticationJson.getShopId().equals("2")) {
                shop_Stye = 2;
                tvStopType.setText("饭店");
            } else if (authenticationJson.getShopId().equals("3")) {
                shop_Stye = 3;
                tvStopType.setText("特产");
            } else if (authenticationJson.getShopId().equals("4")) {
                shop_Stye = 4;
                tvStopType.setText("小吃");
            } else {
                shop_Stye = 1;
                tvStopType.setText("");
            }
//        tvStopType.setText(SpUtil.getString(getApplication(),"Store_type",""));
            etChanpin.setText(authenticationJson.getBusinessScope());
            if (authenticationJson.getAccountType() == null || authenticationJson.getAccountType().equals("")) {
                tvAccountType.setText("");
            } else if (authenticationJson.getAccountType().equals("1")) {
                accout_Stye = 1;
                tvAccountType.setText("个人");
                im_geren.setVisibility(View.VISIBLE);
                im_duigong.setVisibility(View.INVISIBLE);
            } else if (authenticationJson.getAccountType().equals("2")) {
                accout_Stye = 2;
                tvAccountType.setText("对公");
                im_duigong.setVisibility(View.VISIBLE);
                im_geren.setVisibility(View.INVISIBLE);
            }

            etZhanghu.setText(authenticationJson.getAccountName());
            etYinhang.setText(authenticationJson.getBankCard());
            etKaihuhang.setText(authenticationJson.getBankCard());

            if (authenticationJson.getIsLicense().equals(2)) {
                viewFalse.setStatus(true);
                viewTrue.setStatus(false);
                llYingyezhizhao.setVisibility(View.GONE);
            } else {
                viewTrue.setStatus(true);
                viewFalse.setStatus(false);
                llYingyezhizhao.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick({R.id.ll_stop_type, R.id.ll_account_type, R.id.bt_Next, R.id.view_true, R.id.view_false, R.id.iv_businesslicense, R.id.iv_orhter1, R.id.iv_orhter2})
    public void onClick(View view) {
        InputMethodManager inputMethodManager;
        View rootview;
        switch (view.getId()) {
            case R.id.ll_stop_type:
                inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(etShangjia.getWindowToken(), 0);

                show_shop(shop_Stye);
                //显示PopupWindow
                rootview = LayoutInflater.from(Activity_Sjrz.this).inflate(R.layout.popup_shop_type, null);
                mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ll_account_type:
                inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(etShangjia.getWindowToken(), 0);
                show_accout(accout_Stye);
                //显示PopupWindow
                rootview = LayoutInflater.from(Activity_Sjrz.this).inflate(R.layout.popup_accout_type, null);
                mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.bt_Next:
                if (etShangjia.getText().toString().equals("")) {
                    ToastUtil.show(getApplicationContext(), "商家名称不能为空");
                    return;
                }
                if (tvStopType.getText().toString().equals("")) {
                    ToastUtil.show(getApplicationContext(), "店铺类型不能为空");
                    return;
                }
                if (etChanpin.getText().toString().equals("")) {
                    ToastUtil.show(getApplicationContext(), "经营产品不能为空");
                    return;
                }
                if (tvAccountType.getText().toString().equals("")) {
                    ToastUtil.show(getApplicationContext(), "账户类型不能为空");
                    return;
                }
                if (etZhanghu.getText().toString().equals("")) {
                    ToastUtil.show(getApplicationContext(), "账户名称不能为空");
                    return;
                }
                if (etYinhang.getText().toString().equals("")) {
                    ToastUtil.show(getApplicationContext(), "银行账户不能为空");
                    return;
                }
                if (etKaihuhang.getText().toString().equals("")) {
                    ToastUtil.show(getApplicationContext(), "开户行不能为空");
                    return;
                }
                if (viewTrue.isDisplay_Status() && pathlist.get(3).equals("")) {
                    ToastUtil.show(getApplicationContext(), "请上传营业执照");
                    return;
                }
                if (pathlist.get(4).equals("") || pathlist.get(5).equals("")) {
                    ToastUtil.show(getApplicationContext(), "请上传其他证书");
                    return;
                }

                dialogProgressbar=new DialogProgressbar(Activity_Sjrz.this,R.style.AlertDialogStyle);
                dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
                dialogProgressbar.show();
                UploadFile();
//                inputState=true;
//                delete_AllSp();
//                DialogPrompt dialogPrompt = new DialogPrompt(Activity_Sjrz.this, R.style.AlertDialogStyle);
//                dialogPrompt.show();
                break;
            case R.id.view_true:
                viewTrue.setStatus(true);
                viewFalse.setStatus(false);
                llYingyezhizhao.setVisibility(View.VISIBLE);
                break;
            case R.id.view_false:
                viewFalse.setStatus(true);
                viewTrue.setStatus(false);
                llYingyezhizhao.setVisibility(View.GONE);
                break;
            case R.id.iv_businesslicense:
                SelectphotoPPW.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                int_tem = int_businesslicense;

                break;
            case R.id.iv_orhter1:
                SelectphotoPPW.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                int_tem = int_orhter1;

                break;
            case R.id.iv_orhter2:
                SelectphotoPPW.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                int_tem = int_orhter2;

                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (inputState) {
            delete_AllSp();
        } else {
            put_SpString();
            etShangjia.setFocusable(true);
            etShangjia.setFocusableInTouchMode(true);
            etShangjia.clearFocus();
        }
    }


    //保存信息  用来回显
    private void put_SpString() {
        authenticationJson.setName(etShangjia.getText().toString());
        authenticationJson.setShopId(shop_Stye + "");
        authenticationJson.setBusinessScope(etChanpin.getText().toString());
        authenticationJson.setAccountType(accout_Stye + "");
        authenticationJson.setAccountName(etZhanghu.getText().toString());
        authenticationJson.setBankCard(etYinhang.getText().toString());
        authenticationJson.setAccountBank(etKaihuhang.getText().toString());
        SpUtil.putString(getApplication(), "pathlist4", pathlist.get(3));
        SpUtil.putString(getApplication(), "pathlist5", pathlist.get(4));
        SpUtil.putString(getApplication(), "pathlist6", pathlist.get(5));

//        SpUtil.putString(getApplication(),"Merchant_name",etShangjia.getText().toString());//商家名称
//        SpUtil.putString(getApplication(),"Store_type",tvStopType.getText().toString());//店铺类型
//        SpUtil.putString(getApplication(),"Operating_products",etChanpin.getText().toString());//经营产品
//        SpUtil.putString(getApplication(),"Account_type",tvAccountType.getText().toString());//账户类型
//        SpUtil.putString(getApplication(),"Account_name",etZhanghu.getText().toString());//账户名称
//        SpUtil.putString(getApplication(), "Bank_card", etYinhang.getText().toString());//银行账户
//        SpUtil.putString(getApplication(), "Bank_account", etKaihuhang.getText().toString());//开户行
        if (viewTrue.isDisplay_Status()) {
            authenticationJson.setIsLicense("1");//营业执照 1有
        } else {
            authenticationJson.setIsLicense("2");//营业执照 2没有
        }
        SpUtil.putString(getApplication(), "authenticationJson", new Gson().toJson(authenticationJson));//开户行
    }

    //清除数据
    private void delete_AllSp() {
        SpUtil.remove(getApplicationContext(), "authenticationJson");//商家名称
        SpUtil.remove(getApplicationContext(), "pathlist1");//商家名称
        SpUtil.remove(getApplicationContext(), "pathlist2");//商家名称
        SpUtil.remove(getApplicationContext(), "pathlist3");//商家名称
        SpUtil.remove(getApplicationContext(), "pathlist4");//商家名称
        SpUtil.remove(getApplicationContext(), "pathlist5");//商家名称
        SpUtil.remove(getApplicationContext(), "pathlist6");//商家名称

        SpUtil.remove(getApplicationContext(),"token");
//        SpUtil.remove(getApplication(), "Store_type");//店铺类型
//        SpUtil.remove(getApplication(), "Operating_products");//经营产品
//        SpUtil.remove(getApplication(), "Account_type");//账户类型
//        SpUtil.remove(getApplication(), "Account_name");//账户名称
//        SpUtil.remove(getApplication(), "Bank_card");//银行账户
//        SpUtil.remove(getApplication(), "Bank_account");//开户行
//        SpUtil.remove(getApplication(),"zhizhao");//营业执照
//        SpUtil.remove(getApplication(), "ShopkeeperName");//店主姓名
//        SpUtil.remove(getApplication(), "Gender");//性别
//        SpUtil.remove(getApplication(), "ID");//身份证号
    }


    //显示popuwindow 店铺类型
    private void show_shop(int i) {
        //设置contentView
        View contentView = LayoutInflater.from(Activity_Sjrz.this).inflate(R.layout.popup_shop_type, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopWindow.setContentView(contentView);

        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);

        im_fandian = (ImageView) contentView.findViewById(R.id.im_fandian);
        im_jiudian = (ImageView) contentView.findViewById(R.id.im_jiudian);
        im_xiaochi = (ImageView) contentView.findViewById(R.id.im_xiaochi);
        im_techan = (ImageView) contentView.findViewById(R.id.im_techan);
        if (i == 1) {
            setVisibility(View.VISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
        } else if (i == 2) {
            setVisibility(View.INVISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
        } else if (i == 3) {
            setVisibility(View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
        } else if (i == 4) {
            setVisibility(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.VISIBLE);
        }


        contentView.findViewById(R.id.ll_fandian).setOnClickListener(new Click());
        contentView.findViewById(R.id.ll_jiudian).setOnClickListener(new Click());
        contentView.findViewById(R.id.ll_techan).setOnClickListener(new Click());
        contentView.findViewById(R.id.ll_xiaochi).setOnClickListener(new Click());
        contentView.findViewById(R.id.ll_popup).setOnClickListener(new Click());

    }

    //显示popuwindow 账户类型
    private void show_accout(int i) {
        //设置contentView
        View contentView = LayoutInflater.from(Activity_Sjrz.this).inflate(R.layout.popup_accout_type, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopWindow.setContentView(contentView);

        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);

        im_geren = (ImageView) contentView.findViewById(R.id.im_geren);
        im_duigong = (ImageView) contentView.findViewById(R.id.im_duigong);
        if (i == 1) {
            im_geren.setVisibility(View.VISIBLE);
            im_duigong.setVisibility(View.INVISIBLE);
        } else if (i == 2) {
            im_duigong.setVisibility(View.VISIBLE);
            im_geren.setVisibility(View.INVISIBLE);
        }

        contentView.findViewById(R.id.ll_geren).setOnClickListener(new Click());
        contentView.findViewById(R.id.ll_duigong).setOnClickListener(new Click());
        contentView.findViewById(R.id.ll_popup).setOnClickListener(new Click());

    }

    List<String> pathlist = new ArrayList<>();

    @Override
    public void onSelectImage(List<Uri> uris) {
        SelectphotoPPW.toCropPhoto(uris.get(0));
    }

    @Override
    public void onCropImage(List<String> urlist) {
        for (int i = 0; i < urlist.size(); i++) {

            try {
//                Bitmap      image = ImageHelper.getImage(this, urlist.get(i));
//                setUri(uri);
//                Bitmap image = FileTools.getbitmap1(urlist.get(i));
//                setUri(uri)
                if (int_tem == int_businesslicense) {
//                    iv_holdercard.setImageBitmap(image);
                    ImageLoader.getInstance().displayImage(geturi(urlist.get(i)), iv_businesslicense);
//                    pathlist.set(0, FileTools.getRealFilePath(this, urlist.get(i)) + ".jpg");
//                    listbitmap.set(0, image);
                    pathlist.set(3, urlist.get(i));
                } else if (int_tem == int_orhter1) {

//                    iv_cardfront.setImageBitmap(image);
//                    listbitmap.set(1, image);
                    ImageLoader.getInstance().displayImage(geturi(urlist.get(i)), iv_orhter1);
                    pathlist.set(4, urlist.get(i));
                } else if (int_tem == int_orhter2) {
                    ImageLoader.getInstance().displayImage(geturi(urlist.get(i)), iv_orhter2);
//                    listbitmap.set(2, image);
                    pathlist.set(5, urlist.get(i));
                }

            } catch (Exception e) {

            }
        }

    }

    @Override
    public void onCaptureImage(Uri uri) {
        SelectphotoPPW.toCropPhoto(uri);

    }

    @Override
    public void onCaptureVidio(Uri uri) {

    }


    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_popup:
                    mPopWindow.dismiss();
                    break;
                case R.id.ll_fandian:
                    shop_Stye = 2;
                    tvStopType.setText("饭店");
                    setVisibility(View.INVISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
                    mPopWindow.dismiss();
                    break;
                case R.id.ll_jiudian:
                    shop_Stye = 1;
                    tvStopType.setText("酒店");
                    setVisibility(View.VISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
                    mPopWindow.dismiss();
                    break;
                case R.id.ll_xiaochi:
                    shop_Stye = 4;
                    tvStopType.setText("小吃");
                    setVisibility(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.VISIBLE);
                    mPopWindow.dismiss();
                    break;
                case R.id.ll_techan:
                    shop_Stye = 3;
                    tvStopType.setText("特产");
                    setVisibility(View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
                    mPopWindow.dismiss();
                    break;
                case R.id.ll_duigong:
                    accout_Stye = 2;
                    tvAccountType.setText("对公");
                    im_duigong.setVisibility(View.VISIBLE);
                    im_geren.setVisibility(View.INVISIBLE);
                    mPopWindow.dismiss();
                    break;
                case R.id.ll_geren:
                    accout_Stye = 1;
                    tvAccountType.setText("个人");
                    im_geren.setVisibility(View.VISIBLE);
                    im_duigong.setVisibility(View.INVISIBLE);
                    mPopWindow.dismiss();
                    break;
            }
        }
    }

    private void setVisibility(int i1, int i2, int i3, int i4) {
        im_jiudian.setVisibility(i1);
        im_fandian.setVisibility(i2);
        im_techan.setVisibility(i3);
        im_xiaochi.setVisibility(i4);

    }


    public String geturi(String path) {

        Uri uri = null;

        path = path.trim();
        if (path != null) {
            path = Uri.decode(path);
            path = path.trim();
            ContentResolver cr = this.getContentResolver();
            StringBuffer buff = new StringBuffer();
            buff.append("(")
                    .append(MediaStore.Images.ImageColumns.DATA)
                    .append("=")
                    .append("'" + path + "'")
                    .append(")");
            Cursor cur = cr.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.ImageColumns._ID},
                    buff.toString(), null, null);
            int index = 0;
            for (cur.moveToFirst(); !cur.isAfterLast(); cur
                    .moveToNext()) {
                index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                // set _id value
                index = cur.getInt(index);
            }
            if (index == 0) {
                //do nothing
                uri = SelectphotoPPW.getUri();
            } else {
                Uri uri_temp = Uri
                        .parse("content://media/external/images/media/"
                                + index);
                if (uri_temp != null) {
                    uri = uri_temp;
                }
            }
        }
        return uri + "";

    }


    /**
     * 上传文件
     */
    UpfileJson upfileJson;

    public void UploadFile() {


        PostFormBuilder getBuilder = OkHttpUtils.post();
        if (authenticationJson.getIsLicense().equals("1"))
            for (int i = 0; i < pathlist.size(); i++) {
//                params.addBodyParameter("file", new File(pathlist.get(i)), "image" + i + ".jpg", "image/png");
//            else if(i!=4) params.addBodyParameter("file", new File(pathlist.get(i)),"image"+i+".jpg","image/png");
                getBuilder.addFile("file", "image" + i + ".jpg", new File(pathlist.get(i)));
            }
        else
            for (int i = 0; i < pathlist.size(); i++) {
                if (i != 4)
                    getBuilder.addFile("file", "image" + i + ".jpg", new File(pathlist.get(i)));
//            else if(i!=4) params.addBodyParameter("file", new File(pathlist.get(i)),"image"+i+".jpg","image/png");
            }

        getBuilder
                .url(URL.UploadFile)
                .addHeader("Authorization", SpUtil.getString(getApplication(), SpUtil.token, ""))
                .addParams("type", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        dialogProgressbar.dismiss();
                        MyToast.SHownetworkField(Activity_Sjrz.this);
                    }



                    @Override
                    public void onResponse(String s, int i) {
                         dialogProgressbar.dismiss();
                        try {
                            upfileJson = new Gson().fromJson(s, UpfileJson.class);
                            if (upfileJson.getHeader().getStatus() == 0) {
                                Request();
                            } else
                                MyToast.SHow(Activity_Sjrz.this, upfileJson.getHeader().getMsg());

                        } catch (Exception e) {
                            MyToast.SHow(Activity_Sjrz.this, "图片上传失败");
                        }

                    }
                });

    }


    /**
     * 上传数据
     */
    public void Request() {
        if (authenticationJson.getIsLicense().equals("1")) {
            authenticationJson.setHoldCardImg(upfileJson.getData().get(0));
            authenticationJson.setFaceCardImg(upfileJson.getData().get(1));
            authenticationJson.setBackCardImg(upfileJson.getData().get(2));
            authenticationJson.setLicenseImg(upfileJson.getData().get(3));
            authenticationJson.setOtherImg1(upfileJson.getData().get(4));
            authenticationJson.setOtherImg2(upfileJson.getData().get(5));
        } else {

            authenticationJson.setHoldCardImg(upfileJson.getData().get(0));
            authenticationJson.setFaceCardImg(upfileJson.getData().get(1));
            authenticationJson.setBackCardImg(upfileJson.getData().get(2));
            authenticationJson.setOtherImg1(upfileJson.getData().get(3));
            authenticationJson.setOtherImg2(upfileJson.getData().get(4));
        }


        if (tvStopType.getText().toString().equals("酒店")) {
            authenticationJson.setShopId("1");

        } else if (tvStopType.getText().toString().equals("饭店")) {
            authenticationJson.setShopId("2");
        } else if (tvStopType.getText().toString().equals("特产")) {
            authenticationJson.setShopId("3");
        } else if (tvStopType.getText().toString().equals("小吃")) {
            authenticationJson.setShopId("4");
        }


        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpUtil.token, ""));
        params.addQueryStringParameter("realName", authenticationJson.getRealName());
        params.addQueryStringParameter("idCard", authenticationJson.getIdCard());
        params.addQueryStringParameter("sex", authenticationJson.getSex());
        params.addQueryStringParameter("age", authenticationJson.getAge());
        params.addQueryStringParameter("name", authenticationJson.getName());
        params.addQueryStringParameter("shopId", authenticationJson.getShopId());
        params.addQueryStringParameter("businessScope", authenticationJson.getBusinessScope());
        params.addQueryStringParameter("accountType", authenticationJson.getAccountType());
        params.addQueryStringParameter("accountName", authenticationJson.getAccountName());
        params.addQueryStringParameter("bankCard", authenticationJson.getBankCard());
        params.addQueryStringParameter("accountBank", authenticationJson.getAccountBank());
        params.addQueryStringParameter("isLicense", authenticationJson.getIsLicense());
        params.addQueryStringParameter("holdCardImg", authenticationJson.getHoldCardImg());
        params.addQueryStringParameter("faceCardImg", authenticationJson.getFaceCardImg());
        params.addQueryStringParameter("backCardImg", authenticationJson.getBackCardImg());
        params.addQueryStringParameter("licenseImg", authenticationJson.getLicenseImg());
        params.addQueryStringParameter("otherImg1", authenticationJson.getOtherImg1());
        params.addQueryStringParameter("otherImg2", authenticationJson.getOtherImg2());
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET,
                URL.auditMessage,
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            LoginBean loginBean = new Gson().fromJson(responseInfo.result, LoginBean.class);
                            if (loginBean.getHeader().getStatus() == 0) {
                                //                inputState=true;
                                inputState = true;
                                pathlist.clear();
                                pathlist.addAll(upfileJson.getData());
                                DialogPrompt dialogPrompt = new DialogPrompt(Activity_Sjrz.this, R.style.AlertDialogStyle);
                                dialogPrompt.show();
                            } else MyToast.SHow(Activity_Sjrz.this, loginBean.getHeader().getMsg());

                        } catch (Exception e) {
                            MyToast.SHow(Activity_Sjrz.this, "上传失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        MyToast.SHownetworkField(Activity_Sjrz.this);
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Activity_Authentication.activityHashMap.get("Activity_Sjrz") != null)
            Activity_Authentication.activityHashMap.get("Activity_Sjrz").finish();
    }
}
