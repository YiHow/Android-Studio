package com.example.user.radiobutton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ImageView img = findViewById(R.id.img_food);
        Intent intent = getIntent();
        int food = intent.getIntExtra("f", 1);
        switch (food) {
            case 1:
                img.setImageResource(R.drawable.chicken);
                break;
            case 2:
                img.setImageResource(R.drawable.milktea);
                break;
            case 3:
                img.setImageResource(R.drawable.ice);
                break;
            case 4:
                img.setImageResource(R.drawable.beanflower);
                break;
            case 5:
                img.setImageResource(R.drawable.luwei);
                break;
            default:
                img.setImageResource(R.drawable.chicken);
        }
    }

    public void func_back(View view) {
        Intent i = new Intent();
        finish();
    }
}
