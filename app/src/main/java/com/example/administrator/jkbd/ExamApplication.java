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
