package e.ivor.luckynumber;

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

        ImageView img = findViewById(R.id.img_flower);

        Intent intent = getIntent();
        int luckyNum = intent.getIntExtra()
    }

    public void func_like(View view) {
        Intent i = new Intent();
        i.putExtra("Like", 1);
        setResult(RESULT_OK, i);
        finish();
    }

    public void func_dislike(View view) {
        Intent i = new Intent();
        i.putExtra("Like", 0);
        setResult(RESULT_OK, i);
        finish();
}
