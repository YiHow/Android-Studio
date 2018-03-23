package com.example.user.eatwell;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    String [] data = {"台北", "台中", "高雄"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.tv_item, data);
        setListAdapter(adapter);
    }

    protected void onListItemClick(ListView l, View v, int position, long id){
        Toast.makeText(MainActivity.this, data[position], Toast.LENGTH_LONG).show();
    }
}
