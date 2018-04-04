package e.ivor.timetosleep;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText input_hour, input_minute;
    Button setClock;
    PendingIntent pendingintent;
    AlarmManager am;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_hour = findViewById(R.id.textHour);
        input_minute = findViewById(R.id.textMinute);
        setClock = findViewById(R.id.button_set);
        setClock.setOnClickListener(setAlarm);

        Intent intent = new Intent();
        intent.setClass(MainActivity.this, AlarmReceiver.class);

        pendingintent = PendingIntent.getBroadcast(MainActivity.this, 1, intent, 0);

        am = (AlarmManager)getSystemService(ALARM_SERVICE);
    }

    View.OnClickListener setAlarm = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String hour = input_hour.getText().toString();
            String minute = input_minute.getText().toString();

            check(hour, minute);
        }
    };

    protected void check (String h, String m) {
        int hour = Integer.parseInt(h);
        int minute = Integer.parseInt(m);

        if (hour > 24 || hour < 0 || minute > 60 || minute < 0) {
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
        else {
            startReport(hour, minute);
        }
    }

    private void startReport(int h, int m) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, h);
        c.set(Calendar.MINUTE, m);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingintent);
        Toast.makeText(MainActivity.this, "remind at " + h + " : " + m, Toast.LENGTH_SHORT).show();

    }
}
