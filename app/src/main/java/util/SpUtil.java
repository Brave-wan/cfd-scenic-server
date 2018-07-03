package util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
//Login type登录类型
public class SpUtil {
    private static SharedPreferences sp;
    private static String xml_name="test";


    public static String state="state";
    public static String token="token";
    public static String userId="userId";
    public static String guidePage="guidePage";//判断引导页
    /**
     * 写入boolean变量至sp中
     * @param ctx   上下文环境
     * @param key   存储节点名称
     * @param value 存储节点的值 boolean
     *
     */
    public static void putBoolean(Context ctx,String key,boolean value){
        //(存储节点文件名称,读写方式)
        if(sp == null){
            sp = ctx.getSharedPreferences(xml_name, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }
    /**
     * 读取boolean标示从sp中
     * @param ctx   上下文环境
     * @param key   存储节点名称
     * @param defValue  没有此节点默认值
     * @return      默认值或者此节点读取到的结果
     */
    public static boolean getBoolean(Context ctx,String key,boolean defValue){
        //(存储节点文件名称,读写方式)
        if(sp == null){
            sp = ctx.getSharedPreferences(xml_name, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    /**
     * 写入String变量至sp中
     * @param ctx   上下文环境
     * @param key   存储节点名称
     * @param value 存储节点的值string
     */
    public static void putString(Context ctx,String key,String value){
        //(存储节点文件名称,读写方式)
        if(sp == null){
            sp = ctx.getSharedPreferences(xml_name, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }
    /**
     * 读取String标示从sp中
     * @param ctx   上下文环境
     * @param key   存储节点名称
     * @param defValue  没有此节点默认值
     * @return      默认值或者此节点读取到的结果
     */
    public static String getString(Context ctx,String key,String defValue){
        //(存储节点文件名称,读写方式)
        if(sp == null){
            sp = ctx.getSharedPreferences(xml_name, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }
    /**
     * 从sp中移除指定节点
     * @param ctx   上下文环境
     * @param key   需要移除节点的名称
     */
    public static void remove(Context ctx, String key) {
        if(sp == null){
            sp = ctx.getSharedPreferences(xml_name, Context.MODE_PRIVATE);
        }
        sp.edit().remove(key).commit();
    }


    /**
     * 写入Int变量至sp中
     * @param ctx   上下文环境
     * @param key   存储节点名称
     * @param value 存储节点的值 boolean
     *
     */
    public static void putInt(Context ctx,String key,int value){
        //(存储节点文件名称,读写方式)
        if(sp == null){
            sp = ctx.getSharedPreferences(xml_name, Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }
    /**
     * 读取Int标示从sp中
     * @param ctx   上下文环境
     * @param key   存储节点名称
     * @param defValue  没有此节点默认值
     * @return      默认值或者此节点读取到的结果
     */
    public static int getInt(Context ctx,String key,int defValue){
        //(存储节点文件名称,读写方式)
        if(sp == null){
            sp = ctx.getSharedPreferences(xml_name, Context.MODE_PRIVATE);
        }
        return sp.getInt(key,defValue);
    }


    /**
     * 写入long变量至sp中
     * @param ctx   上下文环境
     * @param key   存储节点名称
     * @param value 存储节点的值 boolean
     *
     */
    public static void putlong(Context ctx,String key,long value){
        //(存储节点文件名称,读写方式)
        if(sp == null){
            sp = ctx.getSharedPreferences(xml_name, Context.MODE_PRIVATE);
        }
        sp.edit().putLong(key, value).commit();
    }
    /**
     * 读取long标示从sp中
     * @param ctx   上下文环境
     * @param key   存储节点名称
     * @param defValue  没有此节点默认值
     * @return      默认值或者此节点读取到的结果
     */
    public static long getLong(Context ctx,String key,long defValue){
        //(存储节点文件名称,读写方式)
        if(sp == null){
            sp = ctx.getSharedPreferences(xml_name, Context.MODE_PRIVATE);
        }
        return sp.getLong(key,defValue);
    }
}
