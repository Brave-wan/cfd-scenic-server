package com.application;


import android.app.Application;
import android.graphics.Bitmap;
import android.os.StrictMode;

import com.cfd.business.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zhy.http.okhttp.OkHttpUtils;


import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;


/**
 * Created by Administrator on 2016/5/30 0030.
 */
public class MyApplication extends Application {
    private final static float TARGET_HEAP_UTILIZATION = 0.75f;

    // 在程序onCreate时就可以调用

    public void onCreate() {
        super.onCreate();

        configImageLoader();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(30000L, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        OkHttpUtils.initClient(okHttpClient);
//
    }

    /**
     * 配置ImageLoder
     */
    private void configImageLoader() {


        // 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.mipmap.zhanwei) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.zhanwei) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.zhanwei) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheExtraOptions(480, 800, Bitmap.CompressFormat.PNG, 70,null)//CompressFormat.PNG类型，70质量（0-100）
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(60 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }




}
