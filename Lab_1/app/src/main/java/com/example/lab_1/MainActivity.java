package com.example.lab_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public String[] arr = {String.valueOf(R.id.button1), String.valueOf(R.id.button2), String.valueOf(R.id.button3), String.valueOf(R.id.button4)};
    public int i = 0;
    TextView Count;
   // Button Res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Count = (TextView) findViewById(R.id.textView);
        //Res = (Button) findViewById(R.id.buttonRes);
    }


    public void onClick(View view) {
        View _view = findViewById(Integer.parseInt(arr[i]));
        i++;
        view.setEnabled(false);
        //view.setClickable(false);
        if(i == 4) {
            Count.setVisibility(View.VISIBLE);
            //Res.setVisibility(View.VISIBLE);
        }
    }
    /*public void resClick(View view) {
        View _view = findViewById(Integer.parseInt(arr[i]));
        for(i=0; i<4 ;i++) {
            view.setEnabled(true);
        }
        i = 0;
        Count.setVisibility(View.INVISIBLE);
        Res.setVisibility(View.INVISIBLE);
    }*/
}