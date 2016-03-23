package com.boxfish.ming.rxjavaretrofitdemo.http;

import com.boxfish.ming.rxjavaretrofitdemo.entity.MovieEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// +----------------------------------------------------------------------
// | CreateTime: 16/3/23 
// +----------------------------------------------------------------------
// | Author:     xab(http://www.zhangkun.net.cn)
// +----------------------------------------------------------------------
// | CopyRight:  http://www.boxfish.cn
// +----------------------------------------------------------------------
public interface MovieService {

    @GET("top250")
    Call<MovieEntity> getTopMovie(@Query("start") int start,@Query("count") int count);
}
