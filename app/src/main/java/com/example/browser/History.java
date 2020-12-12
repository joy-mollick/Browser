package com.example.browser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class History extends AppCompatActivity {
    private LinearLayout parentLinearLayout;
    ListView lv;
    String []data;
    Data dt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        final MyDBFunctions mf = new MyDBFunctions(getApplicationContext());
        /// receiving database's all datas from method of my_data()
        data = mf.my_data();
        parentLinearLayout=(LinearLayout) findViewById(R.id.parent_layout);
        lv = (ListView) findViewById(R.id.myhistry);
        lv.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.lview, R.id.mytext, data));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String rec_pos=mf.fetch_str(i+1);
                Intent ii=new Intent(History.this,Histry.class);
                ii.putExtra("message_key", rec_pos);
                startActivity(ii);
                finish();
            }
        });
    }
}