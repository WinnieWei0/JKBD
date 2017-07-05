package com.example.administrator.jkbd.activity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jkbd.ExamActivity;
import com.example.administrator.jkbd.R;
import com.example.administrator.jkbd.view.QuestionAdapter;

/**
 * Created by Administrator on 2017/7/5.
 */

public class QuestinAdapter extends QuestionAdapter {
    public QuestinAdapter(ExamActivity examActivity) {
        super();
    }
    public View getView(int position, View convertView, ViewGroup parent){
        View view= View.inflate(mContext, R.layout.item_question,null);
        TextView tvNo=(TextView) view.findViewById(R.id.tv_num);
        ImageView ivQuestion=(ImageView) view.findViewById(R.id.iv_question);
        Log.e("adpter"+"examList.get(position)="+examList.get(position));
        if(examList.get(position).getUserAnswer()!=null&&examList.get(position).getUserAnswer().equals("")) {
            ivQuestion.setImageResource(R.mipmap.answer24x24);
        }else {
            ivQuestion.setImageResource(R.mipmap.ques24x24);
        }
        tvNo.setText("第"+(position+1)+"题");
        return view;
    }
}
