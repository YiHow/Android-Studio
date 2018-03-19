package e.ivor.luckynumber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textHello;
    Button button_lucky, button_go;
    int luckyNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textHello = findViewById(R.id.textHello);
        button_lucky = findViewById(R.id.button_lucky);
        button_go = findViewById(R.id.button_go);
    }

    public void func_lucky (View view) {
        Random r = new Random();
        luckyNum = r.nextInt(6) + 1;
        textHello.setText(Integer.toString(luckyNum));
        button_go.setEnabled(true);
    }

    public func_go (View view) {
        Intent intent = new Intent();
    }
}
