package com.example.administrator.jkbd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.jkbd.R;

/**
 * Created by Administrator on 2017/6/28.
 */

public class splashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mdownTimer.start();
    }
    CountDownTimer mdownTimer=new CountDownTimer(2000,1000){
        public void onTick(long millisUntilFinished){

        }

        @Override
        public void onFinish() {
            Intent intent=new Intent(splashActivity.this,MainActivity.class);
            startActivity(intent);
            splashActivity.this.finish();
        }
    };
}
