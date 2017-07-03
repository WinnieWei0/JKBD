package com.example.administrator.jkbd.biz;

import com.example.administrator.jkbd.dao.ExamDao;
import com.example.administrator.jkbd.dao.IExamdao;

/**
 * Created by Administrator on 2017/7/3.
 */

public class ExamBiz implements IExambiz{
    IExamdao dao;
    public ExamBiz(){
        this.dao=new ExamDao();
    }
    @Override
    public void beginExam() {
dao.loadExamInfo();
        dao.loadQuestionList();
    }

    @Override
    public void nextQuestion() {

    }

    @Override
    public void preQuestion() {

    }

    @Override
    public void commitExam() {

    }
}
