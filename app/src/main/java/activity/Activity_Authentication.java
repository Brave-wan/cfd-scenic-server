package activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
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
import com.cfd.business.R;
import com.google.gson.Gson;
import com.mytools.FileTools;
import com.mytools.SelectImagePopupWindow;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permission.PermissionsActivity;
import permission.PermissionsChecker;
import util.SpUtil;
import util.StringUtil;
import util.ToastUtil;

/**
 * 注册--实名认证
 * Created by Administrator on 2016/7/27 0027.
 */
public class Activity_Authentication extends Activity implements SelectImagePopupWindow.OnCompleteListener {


    @Bind(R.id.ll_Gender)
    LinearLayout llGender;
    @Bind(R.id.bt_authentication)
    Button btAuthentication;
    @Bind(R.id.tv_Gender)
    TextView tvGender;
    @Bind(R.id.et_dianzhuxingming)
    EditText etDianzhuxingming;
    @Bind(R.id.et_shenfenzheng)
    EditText etShenfenzheng;
    @Bind(R.id.tv_shenfenzheng)
    TextView tvShenfenzheng;
    @Bind(R.id.iv_holdercard)
    ImageView iv_holdercard;
    @Bind(R.id.iv_cardfront)
    ImageView iv_cardfront;
    @Bind(R.id.iv_cardback)
    ImageView iv_cardback;

    private PopupWindow mPopWindow;

    ImageView im_male;
    ImageView im_female;

    int int_holdercard = 111;
    int int_cardfront = 112;
    int int_cardback = 113;
    int int_cardtem = 0;


    public static HashMap<String, Activity> activityHashMap = new HashMap<>();

    AuthenticationJson authenticationJson=new AuthenticationJson();


    PermissionsChecker mPermissionsChecker;
    private SelectImagePopupWindow SelectphotoPPW;
    // 所需权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public static int REQUEST_CODE = 123456;

     int flag=0; //1，审核失败
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        ButterKnife.bind(this);

        mPermissionsChecker=new PermissionsChecker(this);

        SelectphotoPPW = new SelectImagePopupWindow(this);
        SelectphotoPPW.addOnCompleteListener(this);
        activityHashMap.put("Activity_Authentication", Activity_Authentication.this);
        pathlist.add(SpUtil.getString(getApplication(), "pathlist1", ""));
        pathlist.add(SpUtil.getString(getApplication(), "pathlist2", ""));
        pathlist.add(SpUtil.getString(getApplication(), "pathlist3", ""));
        if (!SpUtil.getString(getApplication(), "pathlist1", "").equals("")) {
            ImageLoader.getInstance().displayImage(geturi(SpUtil.getString(getApplication(), "pathlist1", "")), iv_holdercard);
//            pathlist.set(0,SpUtil.getString(getApplication(), "pathlist1", ""));
        }
        if (!SpUtil.getString(getApplication(), "pathlist2", "").equals("")) {
            ImageLoader.getInstance().displayImage(geturi(SpUtil.getString(getApplication(), "pathlist2", "")), iv_cardfront);
//            pathlist.set(1,SpUtil.getString(getApplication(), "pathlist2", ""));
        }
        if (!SpUtil.getString(getApplication(), "pathlist3", "").equals("")) {
            ImageLoader.getInstance().displayImage(geturi(SpUtil.getString(getApplication(), "pathlist3", "")), iv_cardback);
//            pathlist.set(2,SpUtil.getString(getApplication(), "pathlist3", ""));
        }
        show_gender();

        if(!SpUtil.getString(getApplication(), "authenticationJson", "").equals("")){
         authenticationJson=new Gson().fromJson(SpUtil.getString(getApplication(), "authenticationJson", ""),AuthenticationJson.class);
        etDianzhuxingming.setText(authenticationJson.getRealName());
        etDianzhuxingming.setSelection(etDianzhuxingming.getText().length());
        if (authenticationJson.getSex().equals("0")) {
            tvGender.setText("男");
            im_male.setVisibility(View.VISIBLE);
            im_female.setVisibility(View.GONE);
            tvGender.setText("男");
            tvGender.setTextColor(Color.parseColor("#000000"));
        } else if (authenticationJson.getSex().equals("1")) {
            tvGender.setText("女");
            im_female.setVisibility(View.VISIBLE);
            im_male.setVisibility(View.GONE);
            tvGender.setText("女");
            tvGender.setTextColor(Color.parseColor("#000000"));
        } else if (authenticationJson.getSex().equals("")) {
            tvGender.setText("");
        }


        etShenfenzheng.setText(authenticationJson.getIdCard());
        }
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

    @OnClick({R.id.ll_Gender, R.id.bt_authentication, R.id.iv_holdercard, R.id.iv_cardfront, R.id.iv_cardback})
    public void onClick(View view) {
        boolean idboolean = false;//判断身份证格式是否正确
        switch (view.getId()) {
            case R.id.ll_Gender:
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(etDianzhuxingming.getWindowToken(), 0);

                //显示PopupWindow
                View rootview = LayoutInflater.from(Activity_Authentication.this).inflate(R.layout.popup_gender, null);
                mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.bt_authentication:
                if (etDianzhuxingming.getText().toString().equals("")) {
                    ToastUtil.show(getApplicationContext(), "姓名不能为空");
                    return;
                }
                if (tvGender.getText().toString().equals("")) {
                    ToastUtil.show(getApplicationContext(), "性别不能为空");
                    return;
                }
                if (etShenfenzheng.getText().toString().equals("")) {
                    ToastUtil.show(getApplicationContext(), "身份证不能为空");
                    return;
                }
                try {
                    idboolean = StringUtil.IDCardValidate(etShenfenzheng.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (!idboolean) {
                    ToastUtil.show(getApplicationContext(), "身份证号格式错误");
                    return;
                }
                if (pathlist.get(0).equals("")) {
                    ToastUtil.show(getApplicationContext(), "请上传手持身份证照");
                    return;
                }
                if (pathlist.get(1).equals("")) {
                    ToastUtil.show(getApplicationContext(), "请上传身份证正面照");
                    return;
                }
                if (pathlist.get(1).equals("")) {
                    ToastUtil.show(getApplicationContext(), "请上传身份证反面照");
                    return;
                }

                put_SpString();
                Intent intent = new Intent(getApplicationContext(), Activity_Sjrz.class);
                startActivity(intent);
                break;

            case R.id.iv_holdercard:
                SelectphotoPPW.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                int_cardtem = int_holdercard;
                break;
            case R.id.iv_cardfront:
                SelectphotoPPW.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                int_cardtem = int_cardfront;
                break;
            case R.id.iv_cardback:
                SelectphotoPPW.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                int_cardtem = int_cardback;
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        put_SpString();
        etDianzhuxingming.setFocusable(true);
        etDianzhuxingming.setFocusableInTouchMode(true);
        etDianzhuxingming.clearFocus();
    }


    private void put_SpString() {
           authenticationJson.setRealName(etDianzhuxingming.getText().toString());
        if(tvGender.getText().toString().equals("女"))
            authenticationJson.setSex("1");
          else  authenticationJson.setSex("0");
        authenticationJson.setIdCard(etShenfenzheng.getText().toString());
        SpUtil.putString(getApplication(),"authenticationJson",new Gson().toJson(authenticationJson));
//        SpUtil.putString(getApplication(), "ShopkeeperName", etDianzhuxingming.getText().toString());//店主姓名
//        SpUtil.putString(getApplication(), "Gender", tvGender.getText().toString());//性别
//        SpUtil.putString(getApplication(), "ID", etShenfenzheng.getText().toString());//身份证号
        SpUtil.putString(getApplication(), "pathlist1", pathlist.get(0));
        SpUtil.putString(getApplication(), "pathlist2", pathlist.get(1));
        SpUtil.putString(getApplication(), "pathlist3", pathlist.get(2));
    }

    //显示popuwindow 性别
    private void show_gender() {
        //设置contentView
        View contentView = LayoutInflater.from(Activity_Authentication.this).inflate(R.layout.popup_gender, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopWindow.setContentView(contentView);

        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);

        im_male = (ImageView) contentView.findViewById(R.id.im_male);
        im_female = (ImageView) contentView.findViewById(R.id.im_female);
        contentView.findViewById(R.id.ll_male).setOnClickListener(new Click());
        contentView.findViewById(R.id.ll_female).setOnClickListener(new Click());
        contentView.findViewById(R.id.ll_popup).setOnClickListener(new Click());

    }

    @Override
    public void onSelectImage(List<Uri> uris) {
        SelectphotoPPW.toCropPhoto(uris.get(0));
    }

    List<String> pathlist = new ArrayList<>();

    @Override
    public void onCropImage(List<String> urlist) {
        for (int i = 0; i < urlist.size(); i++) {

            try {
//                Bitmap      image = ImageHelper.getImage(this, urlist.get(i));
//                setUri(uri);
//                Bitmap image = FileTools.getbitmap1(urlist.get(i));
//                setUri(uri)
                if (int_cardtem == int_holdercard) {
//                    iv_holdercard.setImageBitmap(image);
                    ImageLoader.getInstance().displayImage(geturi(urlist.get(i)), iv_holdercard);
//                    pathlist.set(0, FileTools.getRealFilePath(this, urlist.get(i)) + ".jpg");
//                    listbitmap.set(0, image);
                    pathlist.set(0, urlist.get(i));
                } else if (int_cardtem == int_cardfront) {

//                    iv_cardfront.setImageBitmap(image);
//                    listbitmap.set(1, image);
                    ImageLoader.getInstance().displayImage(geturi(urlist.get(i)), iv_cardfront);
                    pathlist.set(1, urlist.get(i));
                } else if (int_cardtem == int_cardback) {
                    ImageLoader.getInstance().displayImage(geturi(urlist.get(i)), iv_cardback);
//                    listbitmap.set(2, image);
                    pathlist.set(2, urlist.get(i));
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

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SelectphotoPPW.onActivityResult(requestCode, resultCode, data);
    }*/

    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_popup:
                    mPopWindow.dismiss();
                    break;
                case R.id.ll_male:
                    im_male.setVisibility(View.VISIBLE);
                    im_female.setVisibility(View.GONE);
                    tvGender.setText("男");
                    tvGender.setTextColor(Color.parseColor("#000000"));
                    mPopWindow.dismiss();
                    break;
                case R.id.ll_female:    //女
                    im_female.setVisibility(View.VISIBLE);
                    im_male.setVisibility(View.GONE);
                    tvGender.setText("女");
                    tvGender.setTextColor(Color.parseColor("#000000"));
                    mPopWindow.dismiss();
                    break;
            }
        }
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

    @Override
    public  void onDestroy(){
        super.onDestroy();
        if( Activity_Authentication. activityHashMap.get("Activity_Authentication")!=null)
            Activity_Authentication. activityHashMap.get("Activity_Authentication").finish();
    }
}
