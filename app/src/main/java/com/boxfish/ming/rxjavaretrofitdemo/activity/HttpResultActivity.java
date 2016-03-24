package com.boxfish.ming.rxjavaretrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.boxfish.ming.rxjavaretrofitdemo.R;
import com.boxfish.ming.rxjavaretrofitdemo.entity.HttpResult;
import com.boxfish.ming.rxjavaretrofitdemo.entity.Subject;
import com.boxfish.ming.rxjavaretrofitdemo.http.HttpMethods;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class HttpResultActivity extends AppCompatActivity {


    @Bind(R.id.result)
    TextView mResult;

    private Subscriber mSubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_result);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.clickLoad)
    public void onClick(View view){
        getMovie();
    }

    private void getMovie(){

        mSubscriber = new Subscriber<HttpResult<Subject>>(){

            @Override
            public void onCompleted() {

                Toast.makeText(HttpResultActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable e) {

                mResult.setText(e.getMessage());

            }

            @Override
            public void onNext(HttpResult<Subject> subjectHttpResult) {
                mResult.setText(subjectHttpResult.toString());
            }
        };

        HttpMethods.getInstance().getTopMovieHttpResult(mSubscriber,0,10);

    }
}
