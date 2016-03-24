package com.boxfish.ming.rxjavaretrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.boxfish.ming.rxjavaretrofitdemo.R;
import com.boxfish.ming.rxjavaretrofitdemo.adapter.MovieAdapter;
import com.boxfish.ming.rxjavaretrofitdemo.entity.MovieEntity;
import com.boxfish.ming.rxjavaretrofitdemo.http.HttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivity extends AppCompatActivity implements Callback<MovieEntity> {


    private static final String TAG = "MovieActivity";
    @Bind(R.id.recycler)
    RecyclerView mRecyclerView;


    private MovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        //一定要初始化 一般清情况下,会写一个baseActivity的抽象基类
        ButterKnife.bind(this);
        initViews();
    }

    public void initViews(){

        // 可以在布局文件中设置 LinearLayoutManager 尝试一下
       /* LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        Log.e(TAG, "initViews: "+manager);
        mRecyclerView.setLayoutManager(manager);*/

        mAdapter = new MovieAdapter();
        mRecyclerView.setAdapter(mAdapter);
        Call<MovieEntity> call = HttpUtils.getService().getTopMovie(0,10);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
        mAdapter.addAll(response.body().getSubjects());
    }

    @Override
    public void onFailure(Call<MovieEntity> call, Throwable t) {

        Log.e(TAG, "onFailure: == "+t.toString());

    }
}
