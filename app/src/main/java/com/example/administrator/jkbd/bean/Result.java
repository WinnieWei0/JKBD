package com.example.administrator.jkbd.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class Result {

    /**
     * error_code : 0
     * reason : ok
     * result : [{"id":2,"question":"这个标志是何含义？","answer":"1","item1":"线形诱导标志","item2":"合流诱导标志","item3":"分流诱导标志","item4":"转弯诱导标志","explains":"线型诱导标线型诱导标志，用于引导车辆驾驶人改变行驶方向，促使安全运行。视需要设于易肇事之弯道路段，小半径匝道曲线或中央隔离设施及渠化设施的端部。线形诱导标的颜色规定为：指示性线形诱导标一般道路为蓝底白图案,高速公路为绿底白图案，用以提供一般性行驶指示;警告性线形诱导标为红底白图案，可使车辆驾驶人提高警觉，并准备防范应变之措施。","url":"http://images.juheapi.com/jztk/c1c2subject1/2.jpg"},{"id":5,"question":"这个标志是何含义？","answer":"3","item1":"停车让行","item2":"单行路","item3":"会车先行","item4":"对向先行","explains":"表示会车先行，此标志设在车道以前适当位置。","url":"http://images.juheapi.com/jztk/c1c2subject1/5.jpg"},{"id":6,"question":"这个标志是何含义？","answer":"4","item1":"右转车道","item2":"掉头车道","item3":"左转车道","item4":"直行车道","explains":"表示只准一切车辆直行。此标志设在直行的路口以前适当位置。","url":"http://images.juheapi.com/jztk/c1c2subject1/6.jpg"}]
     */

    private int error_code;
    private String reason;
    private List<Exam> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Exam> getResult() {
        return result;
    }

    public void setResult(List<Exam> result) {
        this.result = result;
    }


}
