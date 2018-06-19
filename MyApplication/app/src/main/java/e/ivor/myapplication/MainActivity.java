package e.ivor.myapplication;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {
    public TimeTableDB DH = null;
    
    LinearLayout week[] = new LinearLayout[5];
    List courseData[] = new List[5];

    int itemHeight, marTop, marLeft;

    private final int NEWCOURSE = 1;
    private final int DELCOURSE = 2;

    int colorIndex = 0;
    int[] colorArray = {0xff0099cc, 0xff669900, 0xffff8800, 0xffcc0000};//藍 綠 橘 紅
    int[] colorArray2 = {0xff00ddff, 0xff99cc00, 0xffffbb33, 0xffff4444};

    private final static String TIMETABLE = "TimeTable";
    private final static String COLOR = "PREF_COLOR";

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

        getDB();

        SharedPreferences pref = getSharedPreferences(COLOR, MODE_PRIVATE);
        colorIndex =  pref.getInt(COLOR, 0);
        changeWeeklineColor(colorArray[colorIndex]);
        changeNumColor(colorArray2[colorIndex]);
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
    private int findId(int week, int num) {
        SQLiteDatabase db = DH.getReadableDatabase();
        Cursor cursor = db.query("TIMETABLE", new String []{"_id", "Weeks", "Name", "Room", "Teacher", "Start", "Nums"}, null, null, null, null, null);
        cursor.moveToFirst();
        int dWeek, dStart, dNum;
        for (int i = 0; i < cursor.getCount(); i++) {
            dWeek = Integer.parseInt(cursor.getString(1)); // weeks
            dStart = Integer.parseInt(cursor.getString(5));
            dNum = Integer.parseInt(cursor.getString(6));
            for(int j = dStart; j < dStart+dNum; j++) {
                if(week == dWeek && num == j) {
                    return Integer.parseInt(cursor.getString(0));
                }
            }

            cursor.moveToNext();
        }
        return -1;
    }
    private void del(int week, int num) {
        int id = findId(week, num);
        if(id == -1) {
            Toast.makeText(this, "無此課程", Toast.LENGTH_SHORT).show();
            return;
        }
        SQLiteDatabase db = DH.getWritableDatabase();
        db.delete(TIMETABLE, "_ID" + "=" + id, null);
        Toast.makeText(this, "刪除成功", Toast.LENGTH_SHORT).show();
    }
    private void getDB() {
        SQLiteDatabase db = DH.getReadableDatabase();
        Cursor cursor = db.query("TIMETABLE", new String []{"_id", "Weeks", "Name", "Room", "Teacher", "Start", "Nums"}, null, null, null, null, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            int week = Integer.parseInt(cursor.getString(1)); // weeks

            String name = cursor.getString(2); // Name
            String room = cursor.getString(3); // Room
            String teacher = cursor.getString(4); // Teacher
            int start = Integer.parseInt(cursor.getString(5)); // Start
            int num = Integer.parseInt(cursor.getString(6)); // Nums
            Course c = new Course(name, room, teacher, start, num);
            addNewCourse(c, week);
            cursor.moveToNext();
        }
    }

    public void setCourceData() {
        DH.cleanTable();
        list1.clear();
        list2.clear();
        list3.clear();
        list4.clear();
        list5.clear();
        initCourse(new Course("日文(二)","資電107", "陳俊廷", 3, 2), 1);
        initCourse(new Course("行動應用程式開發","資電B19", "薛念林", 8, 1), 1);
        initCourse(new Course("班級活動","資電B03", "陳錫民", 6, 2), 1);
        initCourse(new Course("電子商務安全","資電404", "王銘宏", 11, 3), 1);

        initCourse(new Course("機率論","資電404", "游景盛", 1, 2), 2);
        initCourse(new Course("系統程式","忠B01", "黃志銘", 6, 1),2);

       initCourse(new Course("全民國防軍事教育訓練(二)","學219", "王金輝", 1, 2), 3);
       initCourse(new Course("系統程式","忠B03", "黃志銘", 3, 2),3);
       initCourse(new Course("系統分析與設計","資電404", "陳華總", 6, 2), 3);
       initCourse(new Course("物件導向","資電404", "陳錫民", 8, 1), 3);

        initCourse(new Course("系統分析與設計","資電B22", "陳華總", 3, 1), 4);
        initCourse(new Course("物件導向","資電B22", "陳錫民", 1, 2), 4);
        initCourse(new Course("智慧財產之保護與實務","商402", "蔡宜宏", 11, 2), 4);
        initCourse(new Course("機率論","商304", "游景盛", 4, 1), 4);

        initCourse(new Course("行動應用程式開發","資電B19", "薛念林", 3, 2), 5);
        for(int i = 0; i < 5; i++) {
            week[i] = findViewById(R.id.week1+i);
            week[i].removeAllViewsInLayout();
            initWeek(week[i], courseData[i]);
        }
    }
    public void initCourse(Course c, int day) {
        if(checkRepeat(c, day)) {
            addNewCourse(c, day);
            add(c, day);
        }
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
        int i = day-1;
        week[i] = findViewById(R.id.week1+i);
        week[i].removeAllViewsInLayout();
        initWeek(week[i], courseData[i]);
    }

    public void delCourse(int day, int Cnum) {
        List<Course> list;
        switch (day) {
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
                return;
        }
        Course tempC;
        for(int i = 0 ; i < list.size(); i++) {
            tempC = list.get(i);
            for (int j = tempC.getStart(); j < tempC.getStart()+tempC.getNums(); j++) {
                if (j == Cnum) {
                    list.remove(i);
                    int d = day - 1;
                    week[d] = findViewById(R.id.week1 + d);
                    week[d].removeAllViewsInLayout();
                    initWeek(week[d], courseData[d]);
                    return;
                }
            }
        }
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
                for(int k = tempC.getStart(); k < tempC.getStart()+tempC.getNums(); k++){
                    if(j == k){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        Dialog dialog = null;

        switch(id)
        {
            case NEWCOURSE:
                final View content_layout = LayoutInflater.from(MainActivity.this).inflate(R.layout.newcourse, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("設定課程")
                        .setView(content_layout)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener()
                        { //設定確定按鈕
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

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
                                        Course newC = new Course(name, room, teacher, start, num);
                                        int inputWeek = week;
                                        if(checkRepeat(newC, inputWeek)){
                                            addNewCourse(newC, inputWeek);
                                            add(newC, inputWeek);
                                            Toast.makeText(MainActivity.this, "新增成功", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(MainActivity.this, "課程重複", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });
                dialog = builder.create();
                break;
            case DELCOURSE:
                final View content_layout2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.delcourse, null);
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this)
                        .setTitle("刪除課程")
                        .setView(content_layout2)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener()
                        { //設定確定按鈕
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                EditText et = content_layout2.findViewById(R.id.delCourseWeek);
                                EditText et2 = content_layout2.findViewById(R.id.delCourseNum);

                                if (et.getText().toString().matches("") || et2.getText().toString().matches("")){
                                    Toast.makeText(MainActivity.this, "不可為空!", Toast.LENGTH_SHORT).show();
                                } else {
                                    int week = Integer.parseInt(et.getText().toString());
                                    int num = Integer.parseInt(et2.getText().toString());
                                    if(week > 7 || week < 1 || num > 14 || num < 1) {
                                        Toast.makeText(MainActivity.this, "輸入錯誤!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        delCourse(week, num);
                                        del(week, num);
                                        et.setText("");
                                        et2.setText("");
                                    }
                                }
                            }
                        });
                dialog = builder2.create();
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
                showDialog(NEWCOURSE);
                break;
            case R.id.action_del:
                showDialog(DELCOURSE);
                break;
            case R.id.action_eqTable:
                setCourceData();
                break;
            case R.id.action_quit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop(){
        super.onStop();
        SharedPreferences pref = getSharedPreferences(COLOR, MODE_PRIVATE);
        SharedPreferences.Editor preEdit = pref.edit();
        preEdit.putInt(COLOR, colorIndex);
        preEdit.commit();
    }
}
