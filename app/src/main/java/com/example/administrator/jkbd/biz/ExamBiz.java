package com.example.administrator.jkbd.biz;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.bean.Exam;
import com.example.administrator.jkbd.dao.ExamDao;
import com.example.administrator.jkbd.dao.IExamdao;

import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */

public class ExamBiz implements IExambiz{
    IExamdao dao;
    int examIndex=0;
    List<Exam> examList=null;
    public ExamBiz(){
        this.dao=new ExamDao();
    }
    @Override
    public void beginExam() {
        examIndex=0;
dao.loadExamInfo();
        dao.loadQuestionList();
        examList=ExamApplication.getInstance().getmExamList();
    }

    @Override
    public Exam getExam() {
        if(examList!=null) {
            return examList.get(examIndex);
        }else {
            return null;
        }
    }

    @Override
    public Exam nextQuestion() {
        if(examList!=null&&examIndex<examList.size()-1) {
            examIndex++;
            return examList.get(examIndex);
        }else {
            return null;
        }
    }

    @Override
    public Exam preQuestion() {

    }

    @Override
    public void commitExam() {

    }

    @Override
    public String getExamIndex() {
        return (examIndex+1)+".";
    }
}
