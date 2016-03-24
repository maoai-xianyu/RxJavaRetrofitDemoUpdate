package com.boxfish.ming.rxjavaretrofitdemo.http;

// +----------------------------------------------------------------------
// | CreateTime: 16/3/24 
// +----------------------------------------------------------------------
// | Author:     xab(http://www.zhangkun.net.cn)
// +----------------------------------------------------------------------
// | CopyRight:  http://www.boxfish.cn
// +----------------------------------------------------------------------

import com.boxfish.ming.rxjavaretrofitdemo.entity.HttpResult;
import com.boxfish.ming.rxjavaretrofitdemo.entity.MovieEntity;
import com.boxfish.ming.rxjavaretrofitdemo.entity.Subject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 请求封装 使用的是 retrofit 和 RxJava
 */
public class HttpMethods {

    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit mRetrofit;

    private MovieService mService;

    //构造方法私有


    private HttpMethods() {

        // 手动创建一个 OkHttpClient 并设置超时时间
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // 设置超时时间
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        mService = mRetrofit.create(MovieService.class);

    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }


    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * RxJava 封装
     *
     * @param subscriber
     * @param start
     * @param count
     */
    public void getTopMovie(Subscriber<MovieEntity> subscriber, int start, int count) {
        mService.getTopMovieRxJava(start, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * HttpResult 封装
     *
     * @param subscriber
     * @param start
     * @param count
     */
    public void getTopMovieHttpResult(Subscriber<HttpResult<List<Subject>>> subscriber, int start, int count) {
        mService.getTopMovieHttpResult(start, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * HttpResult 封装 同时添加 错误信息
     *
     * @param subscriber
     * @param start
     * @param count
     */
    public void getTopMovieHttpResultNo(Subscriber<List<Subject>> subscriber, int start, int count) {
        mService.getTopMovieHttpResult(start, count)
                .flatMap(new Func1<HttpResult<List<Subject>>, Observable<List<Subject>>>() {
                    @Override
                    public Observable<List<Subject>> call(HttpResult<List<Subject>> httpResult) {
                        return flatResult(httpResult);
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    static <T> Observable<T> flatResult(final HttpResult<T> result) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {

                if (result.getCount() == 0) {
                    subscriber.onError(new ApiException(ApiException.USER_NOT_EXIST));
                } else {
                    subscriber.onNext(result.getSubjects());
                }
                subscriber.onCompleted();
            }
        });
    }


    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getCount() == 0) {
                throw new ApiException(100);
            }
            return httpResult.getSubjects();
        }
    }


    //-------------------------------------------------------------------------------------------------------

    /**
     * 网络请求的优化
     */
    public void getTopMovieHttpFinally(Subscriber<List<Subject>> subscriber, int start, int count) {

        Observable observable = mService.getTopMovieHttpResult(start, count)
                .map(new HttpResultFunc<List<Subject>>());

        toSubscribe(observable, subscriber);
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

}
