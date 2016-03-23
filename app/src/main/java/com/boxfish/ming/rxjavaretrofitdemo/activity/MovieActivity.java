package com.boxfish.ming.rxjavaretrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.boxfish.ming.rxjavaretrofitdemo.R;
import com.boxfish.ming.rxjavaretrofitdemo.adapter.MovieAdapter;
import com.boxfish.ming.rxjavaretrofitdemo.entity.MovieEntity;
import com.boxfish.ming.rxjavaretrofitdemo.http.HttpUtils;

import butterknife.Bind;
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
        initViews();
    }

    public void initViews(){

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

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
