package com.mytools;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class MyToast {
     public  static void  SHow(Context context,String Text){
         Toast.makeText(context,Text,Toast.LENGTH_SHORT).show();

     }

    public  static void  SHownetworkField(Context context){
        Toast.makeText(context,"连接网络失败",Toast.LENGTH_SHORT).show();

    }
}
