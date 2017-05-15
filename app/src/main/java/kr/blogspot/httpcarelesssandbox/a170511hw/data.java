package kr.blogspot.httpcarelesssandbox.a170511hw;

/**
 * Created by 윤현하 on 2017-05-11.
 */

public class data {
    String memo;
    String date;

    public void datastore(String date, String memo){
        this.memo=memo;
        this.date=date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
