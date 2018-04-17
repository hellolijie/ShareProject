package com.example.test.net;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.test.commonlibrary.net.ApiCreator;

/**
 * Created by lijie on 2017/11/10.
 */

public class RetrofitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApiCreator.create(ApiWrapper.class, "http://www.baidu.com/").testBaidu();
    }
}
