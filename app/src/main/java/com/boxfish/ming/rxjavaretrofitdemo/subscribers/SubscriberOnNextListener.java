package com.boxfish.ming.rxjavaretrofitdemo.subscribers;

// +----------------------------------------------------------------------
// | CreateTime: 16/3/24 
// +----------------------------------------------------------------------
// | Author:     xab(http://www.zhangkun.net.cn)
// +----------------------------------------------------------------------
// | CopyRight:  http://www.boxfish.cn
// +----------------------------------------------------------------------
public interface SubscriberOnNextListener<T> {

    void onNext(T t);
}
