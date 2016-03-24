package com.boxfish.ming.rxjavaretrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.boxfish.ming.rxjavaretrofitdemo.R;
import com.boxfish.ming.rxjavaretrofitdemo.entity.Subject;
import com.boxfish.ming.rxjavaretrofitdemo.http.HttpMethods;
import com.boxfish.ming.rxjavaretrofitdemo.subscribers.ProgressSubscriber;
import com.boxfish.ming.rxjavaretrofitdemo.subscribers.SubscriberOnNextListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends AppCompatActivity {

    @Bind(R.id.resultDialog)
    TextView mResultDialog;

    private SubscriberOnNextListener getTopMovieOnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);

        getTopMovieOnNext = new SubscriberOnNextListener<List<Subject>>() {
            @Override
            public void onNext(List<Subject> subjects) {
                mResultDialog.setText(subjects.toString());
            }
        };


    }

    @OnClick({R.id.clickLoadDialog})
    public void onClick(View view) {

        getTopMovie();

    }

    private void getTopMovie() {

        HttpMethods.getInstance().getTopMovieHttpResultNo(new ProgressSubscriber(getTopMovieOnNext, this), 0, 10);

    }
}
