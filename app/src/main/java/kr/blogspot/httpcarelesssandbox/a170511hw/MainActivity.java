package kr.blogspot.httpcarelesssandbox.a170511hw;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int count=0, key=0, number=0;
    ListView listView;
    ArrayList<String> memobook=new ArrayList<String>();
    ArrayList<String> datebook=new ArrayList<String>();
    ArrayList<String> titlebook=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    LinearLayout addPage, listPage;
    TextView memocounter;
    EditText memogetter;
    DatePicker dategetter;
    Button btnsave,btncancel,btn1;
    String tempmemo="",tempdate="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnsave=(Button)findViewById(R.id.btnsave);
        btncancel=(Button)findViewById(R.id.btncancel);
        btn1=(Button)findViewById(R.id.btn1);

        addPage=(LinearLayout)findViewById(R.id.linear2);
        listPage=(LinearLayout)findViewById(R.id.linear1);

        memocounter=(TextView)findViewById(R.id.tvCount);

        memogetter=(EditText)findViewById(R.id.memogetter);

        dategetter=(DatePicker)findViewById(R.id.dategetter);
        dategetter.setMinDate(01/01/2000);

        listView=(ListView)findViewById(R.id.listview);

        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,titlebook);

        listView.setAdapter(adapter);
        setListView();
    }

    public void setListView(){
        number=titlebook.size();
        memocounter.setText("등록된 메모의 개수 : "+number+" 개");
        //어뎁터 만들기


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("삭제확인");
                dlg.setMessage("선택한 메모가 삭제됩니다.");
                dlg.setNegativeButton("취소", null);
                dlg.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        datebook.remove(key);
                        memobook.remove(key);
                        titlebook.remove(key);
                        number=titlebook.size();
                        memocounter.setText("등록된 메모의 개수 : "+number+" 개");
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();
                return true;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addPage.setVisibility(View.VISIBLE);
                listPage.setVisibility(View.GONE);
                btnsave.setText("수정");
                String original=titlebook.get(position);

                tempdate=original.substring(0,9);

                int year=Integer.parseInt(20+original.substring(0,2));
                int month=Integer.parseInt(original.substring(3,5));
                int day=Integer.parseInt(original.substring(6,8));

                Toast.makeText(getApplicationContext(), tempdate, Toast.LENGTH_LONG).show();
                tempmemo=memobook.get(memofinder(tempdate));

                dategetter.init(year, month-1, day, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    }
                });
                memogetter.setText(tempmemo);
            }
        });

    }
    public void onClick(View v){
        //메모추가
        if(v.getId()==R.id.btn1)
        {
            btnsave.setText("저장");
            memogetter.setText("");
            memogetter.setHint("이 부분에 메모를 입력하세요");
            addPage.setVisibility(View.VISIBLE);
            listPage.setVisibility(View.GONE);
        }

        //저장
        else if(v.getId()==R.id.btnsave)
        {
            if(btnsave.getText().toString().equals("저장")) {
                data databox = new data();
                //일단 추가기능만 구현
                String date = dategetter.getYear() + "년" + (dategetter.getMonth() + 1) + "월" + dategetter.getDayOfMonth() + "일";
                date = SirSlicer(date);
                Toast.makeText(getApplicationContext(), date, Toast.LENGTH_LONG).show();
                String memo = memogetter.getText().toString();

                if(memochecker(date)){
                    int clue=memofinder(date);
                    Toast.makeText(getApplicationContext(), "해당 날짜에는 이미 메모가 저장되어 있습니다.", Toast.LENGTH_SHORT).show();
                    btnsave.setText("수정");
                    String findmemo=memobook.get(clue);
                    memogetter.setText(findmemo);
                }

                else{
                    datebook.add(count,date);
                    memobook.add(count,memo);
                    titlebook.add(count,date + "memo");
                    count++;
                    adapter.notifyDataSetChanged();

                    number=titlebook.size();
                    memocounter.setText("등록된 메모의 개수 : "+number+" 개");
                    btnsave.setText("저장");
                    memogetter.setText("");
                    memogetter.setHint("이 부분에 메모를 입력하세요");

                    addPage.setVisibility(View.GONE);
                    listPage.setVisibility(View.VISIBLE);
                }
            }
            else if(btnsave.getText().toString().equals("수정"))
            {

                data databox = new data();
                //일단 추가기능만 구현
                String date = dategetter.getYear() + "년" + (dategetter.getMonth() + 1) + "월" + dategetter.getDayOfMonth() + "일";
                date = SirSlicer(date);
                Toast.makeText(getApplicationContext(), date + "이겁니다", Toast.LENGTH_LONG).show();
                String memo = memogetter.getText().toString();

                databox.datastore(date, memo);
                datebook.add(count,date);
                memobook.add(count,memo);
                titlebook.add(count,date + "memo");


                datebook.remove(key);
                memobook.remove(key);
                titlebook.remove(key);

                number=titlebook.size();
                memocounter.setText("등록된 메모의 개수 : "+number+" 개");
                adapter.notifyDataSetChanged();

                btnsave.setText("저장");
                memogetter.setText("");
                memogetter.setHint("이 부분에 메모를 입력하세요");
                addPage.setVisibility(View.GONE);
                listPage.setVisibility(View.VISIBLE);
            }
        }

        //취소
        else if(v.getId()==R.id.btncancel)
        {
            btnsave.setText("저장");
            memogetter.setText("");
            memogetter.setHint("이 부분에 메모를 입력하세요");
            addPage.setVisibility(View.GONE);
            listPage.setVisibility(View.VISIBLE);
        }
    }

    public int memofinder(String date){
        for(key=0;key<datebook.size();key++)
        {
            if(date.equals(datebook.get(key))){
                return key;
            }
        }
        return 0;
    }

    public boolean memochecker(String date){
        for(key=0;key<datebook.size();key++)
        {
            if(date.equals(datebook.get(key))){
                return true;
            }
        }
        return false;
    }

    public String SirSlicer(String victim){
        //yy-mm-dd.형식 만들기

        int ym=victim.indexOf('년');
        int md=victim.indexOf('월');
        int dend=victim.indexOf('일');

        int year=Integer.parseInt(victim.substring(2,ym));
        int month=Integer.parseInt(victim.substring(ym+1,md));
        int day=Integer.parseInt(victim.substring(md+1,dend));

        String syear="";
        String smonth="";
        String sday="";

        if(year<10)
        {
            syear="0"+year;
        }
        else
            syear=year+"";

        if(month<10)
        {
            smonth="0"+month;
        }
        else
            smonth=month+"";

        if(day<10)
        {
            sday="0"+day;
        }
        else
            sday=day+"";

        String result=syear+"-"+smonth+"-"+sday+".";
        return result;
    }


}
