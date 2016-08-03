package com.hilon.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hilon.okhttpdemo.model.Git;
import com.hilon.okhttpdemo.okhttp.OkHttpManager;
import com.hilon.okhttpdemo.okhttp.ResultCallBack;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView tvResponse;
    private Button btnGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvResponse = (TextView) findViewById(R.id.tvResponse);
        btnGet = (Button) findViewById(R.id.btn_get);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getText();
            }
        });
    }

    private void getText() {
        OkHttpManager okHttpManager = OkHttpManager.getInstance();
       /* okHttpManager.get("http://publicobject.com/helloworld.txt", new ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "onSuccess");
                tvResponse.setText(response);
            }

            @Override
            public void onError(int errCode, String errMassage) {
                Log.d(TAG, "onError>>" + errCode + ", " + errMassage);
            }

            @Override
            public void onBefore() {
                Log.d(TAG, "onBefore");
            }

            @Override
            public void onAfter() {
                Log.d(TAG, "onAfter");
            }
        });*/

        // 404
        //https://api.github.com/gists/c2a7c39532239ff261b
        okHttpManager.get("https://api.github.com/gists/c2a7c39532239ff261be", new ResultCallBack<Git>() {
            @Override
            public void onSuccess(Git response) {
                Log.d(TAG, "git:" + response.commentsUrl);
                tvResponse.setText(response.commentsUrl);
            }

            @Override
            public void onError(int errCode, String errMassage) {
                Log.d(TAG, "onError>>" + errCode + ", " + errMassage);
                tvResponse.setText("Error: " + errCode + ", " + errMassage);
            }

            @Override
            public void onBefore() {
                Log.d(TAG, "onBefore");
            }

            @Override
            public void onAfter() {
                Log.d(TAG, "onAfter");
            }
        });
    }
}
