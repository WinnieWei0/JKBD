package com.example.administrator.jkbd.biz;

import com.example.administrator.jkbd.bean.Exam;

/**
 * Created by Administrator on 2017/7/2.
 */

public interface IExambiz {
    void beginExam();
    Exam getExam();
    Exam nextQuestion();
    Exam preQuestion();
    void commitExam();
    String getExamIndex();
}
