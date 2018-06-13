package e.ivor.myapplication;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public TimeTableDB DH = null;
    LinearLayout week[] = new LinearLayout[5];
    List courseData[] = new List[5];
    int itemHeight, marTop, marLeft, inputWeek;
    final int DIALOG_ID = 1;
    int[] colorArray = {0xff0099cc, 0xff669900, 0xffff8800, 0xffcc0000};//藍 綠 橘 紅
    int[] colorArray2 = {0xff00ddff, 0xff99cc00, 0xffffbb33, 0xffff4444};
    int colorIndex = 0;
    Course newC;
    List<Course> list1 = new ArrayList<>();
    List<Course> list2 = new ArrayList<>();
    List<Course> list3 = new ArrayList<>();
    List<Course> list4 = new ArrayList<>();
    List<Course> list5 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DH = new TimeTableDB(this);

        itemHeight = getResources().getDimensionPixelSize(R.dimen.weekItemHeight);
        marTop = getResources().getDimensionPixelSize(R.dimen.weekItemMarTop);
        marLeft = getResources().getDimensionPixelSize(R.dimen.weekItemMarLeft);

        getCourceData();

        for (int i = 0; i < week.length; i++) {
            week[i] = findViewById(R.id.week1+i);
            initWeek(week[i], courseData[i]);
        }
    }

    private void add(Course c, int week) {
        SQLiteDatabase db = DH.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Weeks", week);
        values.put("Name", c.getName());
        values.put("Room", c.getClassRoom());
        values.put("Teacher", c.getTeacher());
        values.put("Start", c.getStart());
        values.put("Nums", c.getNums());
        db.insert("TIMETABLE", null, values);
    }
    private void getDB() {
        SQLiteDatabase db = DH.getReadableDatabase();
        //ContentValues values = new ContentValues();

        Cursor cursor = db.query("TIMETABLE", new String []{"_id", "Weeks", "Name", "Room", "Teacher", "Start", "Nums"}, null, null, null, null, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            //add data
            cursor.getString(1); // weeks
            cursor.getString(2); // Name
            cursor.getString(3); // Room
            cursor.getString(4); // Teacher
            cursor.getString(5); // Start
            cursor.getString(6); // Nums
            cursor.moveToNext();
        }
    }

    public void getCourceData() {

        list1.add(new Course("日文(二)","資電107", "陳俊廷", 3, 2));
        list1.add(new Course("行動應用程式開發","資電B19", "薛念林", 8, 1));
        list1.add(new Course("班級活動","資電B03", "陳錫民", 6, 2));
        list1.add(new Course("電子商務安全","資電404", "王銘宏", 11, 3));
        add(new Course("日文(二)","資電107", "陳俊廷", 3, 2), 1);
        courseData[0] = list1;

        list2.add(new Course("機率論","資電404", "游景盛", 1, 2));
        list2.add(new Course("系統程式","忠B01", "黃志銘", 6, 1));
        courseData[1] = list2;

        list3.add(new Course("全民國防軍事教育訓練(二)","學219", "王金輝", 1, 2));
        list3.add(new Course("系統程式","忠B03", "黃志銘", 3, 2));
        list3.add(new Course("系統分析與設計","資電404", "陳華總", 6, 2));
        list3.add(new Course("物件導向","資電404", "陳錫民", 8, 1));
        list3.add(new Course("物件導向實習","紀207", "陳錫民", 9, 2));
        courseData[2] = list3;

        list4.add(new Course("系統分析與設計","資電B22", "陳華總", 1, 2));
        list4.add(new Course("物件導向","資電B22", "陳錫民", 3, 1));
        list4.add(new Course("智慧財產之保護與實務","商402", "蔡宜宏", 11, 2));
        list4.add(new Course("機率論","商304", "游景盛", 4, 1));

        courseData[3] = list4;

        list5.add(new Course("行動應用程式開發","資電B19", "薛念林", 3, 2));
        courseData[4] = list5;
    }

    public void initWeek(LinearLayout lL, List<Course>data){
        if (lL == null || data == null || data.size() < 1) return;
        Course pre = data.get(0);
        for (int i = 0; i < data.size(); i++) {
            Course c = data.get(i);
            TextView tv = new TextView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT ,
                    itemHeight*(c.getNums())+marTop*(c.getNums()-1));
            if (i > 0){
                lp.setMargins(marLeft, (c.getStart()-(pre.getStart()+pre.getNums()))*(itemHeight+marTop)+marTop, 0, 0);
            } else {
                lp.setMargins(marLeft, (c.getStart()-1)*(itemHeight+marTop)+marTop, 0, 0);
            }
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(10);
            tv.setBackgroundColor(0x99ffffff);
            tv.setTextColor(getResources().getColor(R.color.courseTextColor2));
            tv.setText(c.getName()+"\n"+c.getClassRoom()+"\n"+c.getTeacher());
            lL.addView(tv);
            pre = c;
        }
    }

    public void addNewCourse(Course course, int day) {
        switch (day) {
            case 1:
                list1.add(course);
                courseData[0] = list1;
                break;
            case 2:
                list2.add(course);
                courseData[1] = list2;
                break;
            case 3:
                list3.add(course);
                courseData[2] = list3;
                break;
            case 4:
                list4.add(course);
                courseData[3] = list4;
                break;
            case 5:
                list5.add(course);
                courseData[4] = list5;
                break;
            default:
                Toast.makeText(MainActivity.this, "輸入錯誤", Toast.LENGTH_SHORT).show();
                return;
        }
        Toast.makeText(MainActivity.this, "新增課程", Toast.LENGTH_SHORT).show();
        int i = day-1;
        week[i] = findViewById(R.id.week1+i);
        week[i].removeAllViewsInLayout();
        initWeek(week[i], courseData[i]);
    }

    public boolean checkRepeat(Course c, int week) {
        List<Course> list;
        Course tempC;
        switch (week) {
            case 1:
                list = list1;
                break;
            case 2:
                list = list2;
                break;
            case 3:
                list = list3;
                break;
            case 4:
                list = list4;
                break;
            case 5:
                list = list5;
                break;
            default:
                return false;
        }
        for(int i = 0 ; i < list.size(); i++) {
            tempC = list.get(i);
            for(int j = c.getStart(); j < c.getStart()+c.getNums(); j++) {
                for(int k = tempC.getStart(); k < tempC.getStart()+c.getNums(); k++){
                    if(j == k){
                        Toast.makeText(this, "課程重複", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }
        }
        Toast.makeText(this, "新增成功", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id)  //初始化對話方塊，透過 showDialog(ID) 觸發
    {
        Dialog dialog = null;

        switch(id)
        {
            case DIALOG_ID:
                //自訂一個名稱為 content_layout 的介面資源檔
                final View content_layout = LayoutInflater.from(MainActivity.this).inflate(R.layout.newcourse, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("設定課程") //設定標題文字
                        .setView(content_layout) //設定內容外觀
                        .setPositiveButton("OK", new DialogInterface.OnClickListener()
                        { //設定確定按鈕
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                newC = null;
                                inputWeek = -1;
                                //取得 content_layout 介面資源檔中的元件
                                EditText et = content_layout.findViewById(R.id.courseName);
                                EditText et2 = content_layout.findViewById(R.id.courseRoom);
                                EditText et3 = content_layout.findViewById(R.id.courseTeacher);
                                EditText et4 = content_layout.findViewById(R.id.courseStart);
                                EditText et5 = content_layout.findViewById(R.id.courseNum);
                                EditText et6 = content_layout.findViewById(R.id.courseWeek);

                                if (et.getText().toString().matches("") || et2.getText().toString().matches("")
                                        || et3.getText().toString().matches("") || et4.getText().toString().matches("")
                                            || et5.getText().toString().matches("") || et6.getText().toString().matches("")){
                                    Toast.makeText(MainActivity.this, "不可為空!", Toast.LENGTH_SHORT).show();
                                } else {
                                    String name = et.getText().toString();
                                    String room = et2.getText().toString();
                                    String teacher = et3.getText().toString();
                                    int start = Integer.parseInt(et4.getText().toString());
                                    int num = Integer.parseInt(et5.getText().toString());
                                    int week = Integer.parseInt(et6.getText().toString());
                                    if (start > 14 || start <= 0 || num > 3 || num <= 0 || week < 1 || week > 5){
                                        Toast.makeText(MainActivity.this, "輸入錯誤!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        et.setText("");
                                        et2.setText("");
                                        et3.setText("");
                                        et4.setText("");
                                        et5.setText("");
                                        et6.setText("");
                                        newC = new Course(name, room, teacher, start, num);
                                        inputWeek = week;
                                        if(checkRepeat(newC, inputWeek))
                                            addNewCourse(newC, inputWeek);
                                    }
                                }
                            }
                        });
                dialog = builder.create(); //建立對話方塊並存成 dialog
                break;
            default:
                break;
        }
        return dialog;
    }


    public void changeColor(TextView tv, int color) {
        tv.setBackgroundColor(color);
    }

    public void changeWeeklineColor(int color) {
        TextView tv = findViewById(R.id.week_blank);
        changeColor(tv, color);
        tv = findViewById(R.id.week_mon);
        changeColor(tv, color);
        tv = findViewById(R.id.week_tue);
        changeColor(tv, color);
        tv = findViewById(R.id.week_wed);
        changeColor(tv, color);
        tv = findViewById(R.id.week_thu);
        changeColor(tv, color);
        tv = findViewById(R.id.week_fri);
        changeColor(tv, color);
    }

    public void changeNumColor(int color) {
        TextView tv = findViewById(R.id.num_1);
        changeColor(tv, color);
        tv = findViewById(R.id.num_2);
        changeColor(tv, color);
        tv = findViewById(R.id.num_3);
        changeColor(tv, color);
        tv = findViewById(R.id.num_4);
        changeColor(tv, color);
        tv = findViewById(R.id.num_5);
        changeColor(tv, color);
        tv = findViewById(R.id.num_6);
        changeColor(tv, color);
        tv = findViewById(R.id.num_7);
        changeColor(tv, color);
        tv = findViewById(R.id.num_8);
        changeColor(tv, color);
        tv = findViewById(R.id.num_9);
        changeColor(tv, color);
        tv = findViewById(R.id.num_10);
        changeColor(tv, color);
        tv = findViewById(R.id.num_11);
        changeColor(tv, color);
        tv = findViewById(R.id.num_12);
        changeColor(tv, color);
        tv = findViewById(R.id.num_13);
        changeColor(tv, color);
        tv = findViewById(R.id.num_14);
        changeColor(tv, color);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                colorIndex  = (colorIndex + 1)%colorArray.length;
                changeWeeklineColor(colorArray[colorIndex]);
                changeNumColor(colorArray2[colorIndex]);
                break;
            case R.id.action_new:
                showDialog(DIALOG_ID);
                break;
            case R.id.action_quit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
