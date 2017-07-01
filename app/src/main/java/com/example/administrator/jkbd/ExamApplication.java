package com.example.administrator.jkbd;

import android.app.Application;
import android.util.Log;

import com.example.administrator.jkbd.bean.Exam;
import com.example.administrator.jkbd.bean.ExamInfo;
import com.example.administrator.jkbd.bean.Result;
import com.example.administrator.jkbd.utils.OkHttpUtils;
import com.example.administrator.jkbd.utils.ResultUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application {
    ExamInfo mExamInfo;
    List<Exam> mExamList;
    private static ExamApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;

        initData();
    }
public static ExamApplication getInstance(){
    return instance;
}
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
        OkHttpUtils<ExamInfo> utils=new OkHttpUtils<>(instance);
        String url1="http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(url1).targetClass(ExamInfo.class).execute(new OkHttpUtils.OnCompleteListener<ExamInfo>(){
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

                OkHttpUtils<String> utils1=new OkHttpUtils<>(instance);
                String url2="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
                utils1.url(url2);
                utils1.targetClass(String.class);
                utils1.execute(new OkHttpUtils.OnCompleteListener<String>() {
                    Log log;

                    @Override
                    public void onSuccess(String jsonStr) {
                        Result result=ResultUtils.getListResultxFromJson(jsonStr);
                        if(result!=null&&result.getError_code()==0)
                        {
                            List<Exam> list = result.getResult();
                            if(list!=null&&list.size()>0){
                                mExamList=list;
                            }
                        }
                    }

                    @Override
                    public void onError(String error) {
                        log.e("main", "error=" + error);
                    }
                });
            }
}).start();
    }

    public void setmExamInfo(ExamInfo mExamInfo) {
        this.mExamInfo = mExamInfo;
    }

    public void setmExamList(List<Exam> mExamList) {
        this.mExamList = mExamList;
    }

    public ExamInfo getmExamInfo() {
        return mExamInfo;
    }

    public List<Exam> getmExamList() {
        return mExamList;
    }
}
