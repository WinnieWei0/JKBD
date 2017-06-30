package com.example.administrator.jkbd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.administrator.jkbd.bean.ExamInfo;
import com.example.administrator.jkbd.utils.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void test(View view){
        OkHttpUtils<ExamInfo> utils=new OkHttpUtils<>(getApplicationContext());
        String uri="http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(uri).targetClass(ExamInfo.class).execute(new OkHttpUtils.OnCompleteListener<ExamInfo>(){
            Log log;
            @Override
            public void onSuccess(ExamInfo result) {
                log.e("main","result="+result);
            }

            @Override
            public void onError(String error) {
log.e("main","error="+error);
            }
        });
        startActivity(new Intent(MainActivity.this,ExamActivity.class));
    }
    public void exit(View view){
finish();
    }
}
