package com.example.user.eatwell2;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ListActivity{

    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

    private SimpleAdapter adapter;

    private static final String[] mPlaces = new String[] {
            "台北市", "新北市", "台南市", "高雄市", "苗粟縣",
            "台北市", "新北市", "台南市", "高雄市", "苗粟縣",
            "台北市", "新北市", "台南市", "高雄市", "苗粟縣",
            "台北市", "新北市", "台南市", "高雄市", "苗粟縣",
            "台北市", "新北市", "台南市", "高雄市", "苗粟縣"
    };

    private static final String[] mFoods = new String[] {
            "大餅包小餅", "蚵仔煎", "東山鴨頭", "臭豆腐", "潤餅",
            "豆花", "青蛙下蛋","豬血糕", "大腸包小腸", "鹹水雞",
            "烤香腸","車輪餅","珍珠奶茶","鹹酥雞","大熱狗",
            "炸雞排","山豬肉","花生冰","剉冰","水果冰",
            "包心粉圓","排骨酥","沙茶魷魚","章魚燒","度小月"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=0; i<mPlaces.length; i++){
            HashMap<String,String> item = new HashMap<String,String>();
            item.put( "food", mFoods[i]);
            item.put( "place",mPlaces[i] );
            list.add( item );
        }

        adapter = new SimpleAdapter(
                this,
                list,
                android.R.layout.simple_list_item_activated_2,
                new String[] { "food","place" },
                new int[] { android.R.id.text1, android.R.id.text2 } );

        setListAdapter(adapter);
    }

    protected void onListItemClick(ListView l, View v, int position, long id){
        //改成按下去顯示圖片
        Toast.makeText(MainActivity.this, "I want to eat " + mFoods[position] + " at " + mPlaces[position], Toast.LENGTH_LONG).show();
    }
}

