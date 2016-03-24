package com.boxfish.ming.rxjavaretrofitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.boxfish.ming.rxjavaretrofitdemo.activity.DialogActivity;
import com.boxfish.ming.rxjavaretrofitdemo.activity.HttpResultActivity;
import com.boxfish.ming.rxjavaretrofitdemo.activity.MovieActivity;
import com.boxfish.ming.rxjavaretrofitdemo.entity.MovieEntity;
import com.boxfish.ming.rxjavaretrofitdemo.http.HttpMethods;
import com.boxfish.ming.rxjavaretrofitdemo.http.MovieService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Bind(R.id.click_me_BN)
    Button clickMeBN;

    @Bind(R.id.click_me_try)
    Button clickMeTry;

    @Bind(R.id.click_me_RxJavaNo)
    Button clickMeRxJavaNo;

    @Bind(R.id.click_me_RxJavaYes)
    Button clickMeRxJavaYes;

    @Bind(R.id.click_me_Jump)
    Button clickMeJump;

    @Bind(R.id.click_me_JumpEntity)
    Button clickMeJumpEntity;

    @Bind(R.id.click_me_JumpHttp)
    Button clickMeJumpHttp;

    @Bind(R.id.result_TV)
    TextView resultTV;

    private Subscriber<MovieEntity> mSubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.click_me_BN, R.id.click_me_try, R.id.click_me_Jump, R.id.click_me_RxJavaNo, R.id.click_me_RxJavaYes,R.id.click_me_JumpEntity,R.id.click_me_JumpHttp})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.click_me_BN:
                Log.e(TAG, "onClick: 点击");
                getMovie();
                break;
            case R.id.click_me_try:
                Log.e(TAG, "onClick: 用于测试多个 button 的点击事件");
                getMovie();
                break;

            case R.id.click_me_Jump:
                Log.e(TAG, "onClick: 跳转movie 页面");

                startActivity(new Intent(this, MovieActivity.class));
                break;

            case R.id.click_me_RxJavaNo:
                getMovieRxJavaNo();
                break;

            case R.id.click_me_RxJavaYes:
                getMovieRxJavaYes();
                break;

            case R.id.click_me_JumpEntity:

                startActivity(new Intent(this, HttpResultActivity.class));
                break;

            case R.id.click_me_JumpHttp:
                startActivity(new Intent(this, DialogActivity.class));

                break;
            default:
                break;
        }
    }

    // 使用 RxJava 进行请求 进行封装
    private void getMovieRxJavaYes() {

        mSubscriber = new Subscriber<MovieEntity>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

                resultTV.setText(e.getMessage());

            }

            @Override
            public void onNext(MovieEntity entity) {
                resultTV.setText(entity.toString());
            }
        };

        HttpMethods.getInstance().getTopMovie(mSubscriber, 0, 10);

    }


    // 使用 RxJava 进行请求 没有进行封装
    private void getMovieRxJavaNo() {

        String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);

        movieService.getTopMovieRxJava(0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieEntity>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        resultTV.setText(e.getMessage());
                    }

                    @Override
                    public void onNext(MovieEntity movieEntity) {
                        resultTV.setText(movieEntity.toString());
                    }
                });

    }


    /**
     * 进行网络请求
     */
    private void getMovie() {

        String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);

        Call<MovieEntity> call = movieService.getTopMovie(0, 10);

        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                // 这边显示数据可以用adapter 整个页面,先这样
                resultTV.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                resultTV.setText(t.getMessage());
            }
        });


    }


}
