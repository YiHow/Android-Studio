package com.example.user.radiobutton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rg = findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                switch (checkedId) {
                    case R.id.rbtn1:
                        intent.putExtra("f", 1);
                        break;
                    case R.id.rbtn2:
                        intent.putExtra("f", 2);
                        break;
                    case R.id.rbtn3:
                        intent.putExtra("f", 3);
                        break;
                    case R.id.rbtn4:
                        intent.putExtra("f", 4);
                        break;
                    case R.id.rbtn5:
                        intent.putExtra("f", 5);
                        break;
                }
                startActivity(intent);
            }
        });
    }
}
