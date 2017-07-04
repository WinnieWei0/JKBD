package com.example.administrator.jkbd.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jkbd.activity.ExamApplication;
import com.example.administrator.jkbd.bean.Exam;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */

public class QuestionAdapter extends BaseAdapter {
    Context mContext;
    List<Exam> examList;
    public QuestionAdapter(Context context){
        mContext=context;
        examList = ExamApplication.getInstance().getmExamList();

    }
    @Override
    public int getCount() {
        return examList==null? 0:examList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewr=View.inflate(mContext, R.layout.item_question,null);
        TextView tv_num = (TextView)viewr.findViewById(R.id.tv_num);
        ImageView ivQuestion=(ImageView)viewr.findViewById(R.id.iv_question);
        tv_num.setText("第"+(position+1)+"题");
        return viewr;
    }
}
