package com.boxfish.ming.rxjavaretrofitdemo;

import android.app.Application;
import android.graphics.Bitmap;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;

// +----------------------------------------------------------------------
// | CreateTime: 16/3/24 
// +----------------------------------------------------------------------
// | Author:     xab(http://www.zhangkun.net.cn)
// +----------------------------------------------------------------------
// | CopyRight:  http://www.boxfish.cn
// +----------------------------------------------------------------------
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setBitmapMemoryCacheParamsSupplier(new Supplier<MemoryCacheParams>() {
                    @Override
                    public MemoryCacheParams get() {
                        return new MemoryCacheParams(20 << 20,
                                100,
                                Integer.MAX_VALUE,
                                Integer.MAX_VALUE,
                                Integer.MAX_VALUE
                        );
                    }
                })
                .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(this)
                        .setMaxCacheSize(50 << 20)
                        .setBaseDirectoryPath(getCacheDir())
                        .setBaseDirectoryName("fresco")
                        .build())
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .build();
        Fresco.initialize(this, config);
    }
}
