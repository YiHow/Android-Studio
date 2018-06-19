package e.ivor.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TimeTableDB extends SQLiteOpenHelper{
    private final static String DATABASE = "DB";  //資料庫
    private final static String TIMETABLE = "TimeTable"; //資料表
    private final static int VS = 1; //版本

    public TimeTableDB(Context context) {
       //super(context, name, factory, version);
        super(context, DATABASE, null, VS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL = "CREATE TABLE IF NOT EXISTS " + TIMETABLE +  " (" +
        "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Weeks" + " CHAR, " +
                "Name" + " CHAR, " +
                "Room" + " CHAR, " +
                "Teacher" + " CHAR, " +
                "Start" + " CHAR, " +
                "Nums" +  " CHAR);";
        db.execSQL(SQL);
    }

    public void cleanTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TIMETABLE);
        this.onCreate(db);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL = "DROP TABLE " + TIMETABLE;
        db.execSQL(SQL);
    }
}
