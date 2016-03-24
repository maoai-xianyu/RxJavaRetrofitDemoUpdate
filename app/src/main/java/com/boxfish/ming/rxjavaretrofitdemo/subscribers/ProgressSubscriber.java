package com.boxfish.ming.rxjavaretrofitdemo.subscribers;

import android.content.Context;
import android.widget.Toast;

import rx.Subscriber;

// +----------------------------------------------------------------------
// | CreateTime: 16/3/24 
// +----------------------------------------------------------------------
// | Author:     xab(http://www.zhangkun.net.cn)
// +----------------------------------------------------------------------
// | CopyRight:  http://www.boxfish.cn
// +----------------------------------------------------------------------

/**
 * 如何取消一个Http请求 -- 观察者之间的对决  Observer VS Subscriber
 * <p/>
 * Observer本身是一个接口，他的特性是不管你怎么用，都不会解绑 Observer没有unsubscribe方法
 * 同时没有onStart方法 这个一会聊 所以使用 Subscriber
 * Subscriber给我们提供了onStart、onNext、onError、onCompleted四个方法
 *
 * @param <T>
 */
public class ProgressSubscriber<T> extends Subscriber<T> {

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private Context mContext;

    public ProgressSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context) {
        mSubscriberOnNextListener = subscriberOnNextListener;
        mContext = context;
    }

    /**
     * onStart方法我们用来启动一个ProgressDialog
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * onComplated方法里面停止ProgressDialog
     */
    @Override
    public void onCompleted() {

        Toast.makeText(mContext, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();

    }

    /**
     * onError方法我们集中处理错误，同时也停止ProgressDialog
     * @param e
     */
    @Override
    public void onError(Throwable e) {

        Toast.makeText(mContext, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNext(T t) {

        mSubscriberOnNextListener.onNext(t);
    }
}
