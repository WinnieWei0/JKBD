package com.example.administrator.jkbd;

import android.animation.AnimatorSet;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.jkbd.activity.ExamApplication;
import com.example.administrator.jkbd.activity.QuestinAdapter;
import com.example.administrator.jkbd.bean.Exam;
import com.example.administrator.jkbd.bean.ExamInfo;
import com.example.administrator.jkbd.biz.ExamBiz;
import com.example.administrator.jkbd.biz.IExambiz;
import com.example.administrator.jkbd.view.QuestionAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo,tv_examTitle,tv_op1,tv_op2,tv_op3,tv_op4,tv_load,tv_no,tv_time;
    ImageView mimageView;
    CheckBox cb_01,cb_02,cb_03,cb_04;
    CheckBox[] cbs=new CheckBox[4];
    QuestionAdapter mAdapter;
    Gallery mGallery;
    LinearLayout layoutLoading,layout_03,layout_04;
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
        initGallery();
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
        layout_03= (LinearLayout) findViewById(R.id.layout_03);
        layout_04= (LinearLayout) findViewById(R.id.layout_04);
        tvExamInfo= (TextView) findViewById(R.id.tvExamInfo);
        tv_examTitle= (TextView) findViewById(R.id.tv_examTitle);
        tv_op1= (TextView) findViewById(R.id.tv_op1);
        tv_op2= (TextView) findViewById(R.id.tv_op2);
        tv_op3= (TextView) findViewById(R.id.tv_op3);
        tv_op4= (TextView) findViewById(R.id.tv_op4);
        tv_load=(TextView) findViewById(R.id.tv_load);
        tv_no=(TextView) findViewById(R.id.tv_no);
        cb_01= (CheckBox) findViewById(R.id.cb_01);
        cb_02= (CheckBox) findViewById(R.id.cb_02);
        cb_03= (CheckBox) findViewById(R.id.cb_03);
        cb_04= (CheckBox) findViewById(R.id.cb_04);
        cbs[0]=cb_01;
        cbs[2]=cb_02;
        cbs[3]=cb_03;
        cbs[4]=cb_04;
        mimageView= (ImageView) findViewById(R.id.im_examImage);
        dialog=(ProgressBar)findViewById(R.id.dialog);
        layoutLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        cb_01.setOnCheckedChangeListener(listener);
        cb_02.setOnCheckedChangeListener(listener);
        cb_03.setOnCheckedChangeListener(listener);
        cb_04.setOnCheckedChangeListener(listener);
    }
    CompoundButton.OnCheckedChangeListener listener=new CompoundButton.OnCheckedChangeListener(){

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                int userAnswer = 0;
                switch (buttonView.getId()) {
                    case R.id.cb_01:
                        userAnswer = 1;
                        break;
                    case R.id.cb_02:
                        userAnswer = 2;
                        break;
                    case R.id.cb_03:
                        userAnswer = 3;
                        break;
                    case R.id.cb_04:
                        userAnswer = 4;
                        break;
                }
                Log.e("CheckedChanged", "usera=" + userAnswer + ",isChecked=" + isChecked);
                if (userAnswer > 0) {
                    for (CheckBox cb : cbs) {
                        cb.setChecked(false);
                    }
                    cbs[userAnswer - 1].setChecked(true);
                }
            }
        }
    };

    private void initData() {
        if (isLoadExamInfoRecetver && isLoadQuestionRecetver) {
            if (isLoadQuestion && isLoadExamInfo) {
                layoutLoading.setVisibility(View.GONE);
                ExamInfo examInfo = ExamApplication.getInstance().getmExamInfo();
                if (examInfo != null) {
                    showData(examInfo);
                    initTime(examInfo);
                }
                showExam(biz.getExam());
            }else {
layoutLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
tv_load.setText("下载失败，点击重新下载");
            }
        }
    }

    private void initTime(ExamInfo examInfo) {
        int sumTime=examInfo.getLimitTime()*60*1000;
        final long overTime= (int) (sumTime+System.currentTimeMillis());
        final Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long l = overTime - System.currentTimeMillis();
                final int min= (int) (l/1000/60);
                final int sec= (int) (l/1000%60);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_time.setText("剩余时间："+min+"分"+sec+"秒");
                    }
                });
            }
        },0,1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        commxit(null);
                    }
                });
            }
        },sumTime);
    }

    private void showExam(Exam exam) {
        Log.e("showExam","showExam,exam="+exam);
        if(exam!=null){
            tv_no.setText(biz.getExamIndex());
            tv_examTitle.setText(exam.getQuestion());
            tv_op1.setText(exam.getItem1());
            tv_op2.setText(exam.getItem2());
            tv_op3.setText(exam.getItem3());
            tv_op4.setText(exam.getItem4());
            tv_time= (TextView) findViewById(R.id.tv_time);
            if(exam.getUrl()!=null&&!exam.getUrl().equals("")) {
                Picasso.with(ExamActivity.this).load(exam.getUrl()).into(mimageView);
            }else{
                mimageView.setVisibility(View.GONE);
            }
resetOptions();
            String userAnswer = exam.getUserAnswer();
            if(userAnswer!=null&&!userAnswer.equals("")){
                int userCB = Integer.parseInt(userAnswer) - 1;
                cbs[userCB].setChecked(true);
            }
            layout_03.setVisibility(exam.getItem3().equals("")?View.GONE:View.VISIBLE);
            cb_03.setVisibility(exam.getItem3().equals("")?View.GONE:View.VISIBLE);
            layout_04.setVisibility(exam.getItem4().equals("")?View.GONE:View.VISIBLE);
            cb_04.setVisibility(exam.getItem4().equals("")?View.GONE:View.VISIBLE);
        }
    }
    private void resetOptions(){
for(CheckBox cb:cbs){
    cb.setChecked(false);
}
    }
    private void saveUserAnswer(){
        for (int i = 0; i < cbs.length; i++) {
            if(cbs[i].isChecked()){
                biz.getExam().setUserAnswer(String.valueOf(i+1));
                return;
            }
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
    public void initGallery(){
        mAdapter=new QuestinAdapter(this);
        mGallery.setAdapter(mAdapter);
        mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("gallery","gallery item position="+position);
                saveUserAnswer();
                showExam(biz.getExam(position));
            }
        });
    }

    public void nextExam(View view) {
        saveUserAnswer();
        showExam(biz.nextQuestion());
    }

    public void preExam(View view) {
        saveUserAnswer();
        showExam(biz.preQuestion());
    }

    public void commxit(View view) {
        saveUserAnswer();
        int sum=biz.commitExam();
        View inflate = View.inflate(this,R.layout.layout_result, null);
        TextView tv_result=(TextView)inflate.findViewById(R.id.tv_result);
        tv_result.setText("你的分数：\n"+sum+"分！");
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.exam_commit32x32).setTitle("交卷").setView(inflate)
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.create().show();
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
