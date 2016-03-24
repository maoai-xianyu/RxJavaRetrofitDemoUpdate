package com.boxfish.ming.rxjavaretrofitdemo.subscribers;

import android.content.Context;
import android.widget.Toast;

import com.boxfish.ming.rxjavaretrofitdemo.progress.ProgressCancelListener;
import com.boxfish.ming.rxjavaretrofitdemo.progress.ProgressDialogHandler;

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
 * <p/>
 * <p/>
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 *
 * @param <T>
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private Context mContext;

    // 添加消息处理
    private ProgressDialogHandler mProgressDialogHandler;

    public ProgressSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context) {
        mSubscriberOnNextListener = subscriberOnNextListener;
        mContext = context;
        mProgressDialogHandler = new ProgressDialogHandler(mContext, this, true);
    }


    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * onStart方法我们用来启动一个ProgressDialog 展示
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * onComplated方法里面停止ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
        Toast.makeText(mContext, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();

    }

    /**
     * onError方法我们集中处理错误，同时也停止ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        Toast.makeText(mContext, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNext(T t) {
        mSubscriberOnNextListener.onNext(t);
    }

    /**
     * 取消订阅
     */
    @Override
    public void onCancelProgress() {

        if (!this.isUnsubscribed()){
            this.unsubscribe();
        }
    }
}
