package com.example.administrator.jkbd;

import android.animation.AnimatorSet;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.jkbd.bean.Exam;
import com.example.administrator.jkbd.bean.ExamInfo;
import com.example.administrator.jkbd.biz.ExamBiz;
import com.example.administrator.jkbd.biz.IExambiz;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo,tv_examTitle,tv_op1,tv_op2,tv_op3,tv_op4,tv_load;
    ImageView mimageView;
    LinearLayout layoutLoading;
    IExambiz biz;
    ProgressBar dialog;
    boolean isLoadExamInfo=false;
    boolean isLoadQuestion=false;
    boolean isLoadExamInfoRecetver=false;
    boolean isLoadQuestionRecetver=false;

    loadExamBraodcast mloadExamBraodcast;
    loadQuestionBroadcast mloadQuestionBroadcast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.exam);
        mloadExamBraodcast=new loadExamBraodcast();
        mloadQuestionBroadcast=new loadQuestionBroadcast();
        setListener();
        initView();
        loadData();
        biz=new ExamBiz();
    }

    private void setListener() {
        registerReceiver(mloadExamBraodcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mloadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        layoutLoading.setEnabled(false);
        dialog.setVisibility(View.VISIBLE);
        tv_load.setText("下载数据...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }

    private void initView() {
        layoutLoading= (LinearLayout) findViewById(R.id.layout_loading);
        tvExamInfo= (TextView) findViewById(R.id.tvExamInfo);
        tv_examTitle= (TextView) findViewById(R.id.tv_examTitle);
        tv_op1= (TextView) findViewById(R.id.tv_op1);
        tv_op2= (TextView) findViewById(R.id.tv_op2);
        tv_op3= (TextView) findViewById(R.id.tv_op3);
        tv_op4= (TextView) findViewById(R.id.tv_op4);
        tv_load=(TextView) findViewById(R.id.tv_load);
        mimageView= (ImageView) findViewById(R.id.im_examImage);
        dialog=(ProgressBar)findViewById(R.id.dialog);
        layoutLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void initData() {
        if (isLoadExamInfoRecetver && isLoadQuestionRecetver) {
            if (isLoadQuestion && isLoadExamInfo) {
                layoutLoading.setVisibility(View.GONE);
                ExamInfo examInfo = ExamApplication.getInstance().getmExamInfo();
                if (examInfo != null) {
                    showData(examInfo);
                }
                List<Exam> examList = ExamApplication.getInstance().getmExamList();
                if (examList != null) {
                    showExam(examList);
                }
            }else {
layoutLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
tv_load.setText("下载失败，点击重新下载");
            }
        }
    }

    private void showExam(List<Exam> examList) {
        Exam exam = examList.get(0);
        if(exam!=null){
            tv_examTitle.setText(exam.getQuestion());
            tv_op1.setText(exam.getItem1());
            tv_op2.setText(exam.getItem2());
            tv_op3.setText(exam.getItem3());
            tv_op4.setText(exam.getItem4());
            Picasso.with(ExamActivity.this).load(exam.getUrl()).into(mimageView);
        }
    }
    private void showData(ExamInfo examInfo) {
tvExamInfo.setText(examInfo.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mloadExamBraodcast!=null){
            unregisterReceiver(mloadExamBraodcast);
        }
        if (mloadQuestionBroadcast!=null){
            unregisterReceiver(mloadQuestionBroadcast);
        }
    }

    class loadExamBraodcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_EXAM_SUCCESS, false);
            Log.e("loadExamBroadcast","loadExamBroadcast,isSuccess="+isSuccess);
            if(isSuccess){
                isLoadExamInfo=true;
            }
            isLoadExamInfoRecetver=true;
            initData();
        }
    }
    class loadQuestionBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_EXAM_SUCCESS, false);

            Log.e("loadQuestionBroadcast","loadQuestionBroadcast,isSuccess="+isSuccess);
            if(isSuccess){
                isLoadQuestion=true;
            }
            isLoadQuestionRecetver=true;
            initData();
        }
    }
}
