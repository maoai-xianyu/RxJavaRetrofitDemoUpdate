package com.boxfish.ming.rxjavaretrofitdemo.http;

import com.boxfish.ming.rxjavaretrofitdemo.entity.MovieEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

// +----------------------------------------------------------------------
// | CreateTime: 16/3/23 
// +----------------------------------------------------------------------
// | Author:     xab(http://www.zhangkun.net.cn)
// +----------------------------------------------------------------------
// | CopyRight:  http://www.boxfish.cn
// +----------------------------------------------------------------------
public interface MovieService {

    /**
     * retrofit2 的请求
     * @param start
     * @param count
     * @return
     */
    @GET("top250")
    Call<MovieEntity> getTopMovie(@Query("start") int start,@Query("count") int count);


    /**
     * 使用 RxJava 创建请求方法
     * @param start
     * @param count
     * @return
     */
    @GET("top250")
    Observable<MovieEntity> getTopMovieRxJava(@Query("start") int start, @Query("count") int count);
}
