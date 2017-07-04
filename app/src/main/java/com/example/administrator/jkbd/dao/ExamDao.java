package com.example.administrator.jkbd.dao;

import android.content.Intent;
import android.util.Log;

import com.example.administrator.jkbd.activity.ExamApplication;
import com.example.administrator.jkbd.bean.Exam;
import com.example.administrator.jkbd.bean.ExamInfo;
import com.example.administrator.jkbd.bean.Result;
import com.example.administrator.jkbd.utils.OkHttpUtils;
import com.example.administrator.jkbd.utils.ResultUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */

public class ExamDao implements IExamdao {
    @Override
    public void loadExamInfo() {
        OkHttpUtils<ExamInfo> utils=new OkHttpUtils<>(ExamApplication.getInstance());
        String url1="http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(url1).targetClass(ExamInfo.class).execute(new OkHttpUtils.OnCompleteListener<ExamInfo>(){
            Log log;
            @Override
            public void onSuccess(ExamInfo result) {
                log.e("main","result="+result);
                ExamApplication.getInstance().setmExamInfo(result);
                ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_INFO)
                        .putExtra(ExamApplication.LOAD_EXAM_SUCCESS,true));
            }

            @Override
            public void onError(String error) {
                log.e("main","error="+error);
                ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_INFO)
                        .putExtra(ExamApplication.LOAD_EXAM_SUCCESS,false));
            }
        });
    }

    @Override
    public void loadQuestionList() {
        OkHttpUtils utils1=new OkHttpUtils<>(ExamApplication.getInstance());
        String url2="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
        utils1.url(url2);
        utils1.targetClass(String.class);
        utils1.execute(new OkHttpUtils.OnCompleteListener<String>() {
            Log log;

            @Override
            public void onSuccess(String jsonStr) {
                boolean success=false;
                Result result= ResultUtils.getListResultxFromJson(jsonStr);
                if(result!=null&&result.getError_code()==0)
                {
                    List<Exam> list = result.getResult();
                    if(list!=null&&list.size()>0){
                        ExamApplication.getInstance().setmExamList(list);
                        success=true;
                        }
                }
                ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_QUESTION)
                        .putExtra(ExamApplication.LOAD_EXAM_SUCCESS,success));
            }

            @Override
            public void onError(String error) {
                log.e("main", "error=" + error);
                ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_QUESTION)
                        .putExtra(ExamApplication.LOAD_EXAM_SUCCESS,false));
            }
        });
    }
}
