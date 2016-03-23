package com.boxfish.ming.rxjavaretrofitdemo.http;

import com.boxfish.ming.rxjavaretrofitdemo.entity.MovieEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

// +----------------------------------------------------------------------
// | CreateTime: 16/3/23 
// +----------------------------------------------------------------------
// | Author:     xab(http://www.zhangkun.net.cn)
// +----------------------------------------------------------------------
// | CopyRight:  http://www.boxfish.cn
// +----------------------------------------------------------------------
public class HttpUtils {

    public interface Service{

        @GET("top250")
        Call<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);
    }

    private static Service service;

    static {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new TypeAdapter<Date>() {
            @Override
            public void write(JsonWriter out, Date value) throws IOException {
                out.value(value.getTime());
            }

            @Override
            public Date read(JsonReader in) throws IOException {
                long l = in.nextLong();
                return new Date(l);
            }
        }).create();
        service = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.douban.com/v2/movie/").build().create(Service.class);
    }

    public static Service getService() {
        return service;
    }
}
