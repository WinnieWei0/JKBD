package com.example.administrator.jkbd.activity;

import android.app.Application;

import com.example.administrator.jkbd.bean.Exam;
import com.example.administrator.jkbd.bean.ExamInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application {
    public static String LOAD_EXAM_INFO="load_exam_info";
    public static String LOAD_EXAM_QUESTION="load_exam_question";
    public static String LOAD_EXAM_SUCCESS="load_exam_success";
    ExamInfo mExamInfo;
    List<Exam> mExamList;
    private static ExamApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
public static ExamApplication getInstance(){
    return instance;
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
