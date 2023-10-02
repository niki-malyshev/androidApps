package com.example.lab_2;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Spinner;
import android.os.Bundle;
import java.util.ArrayList;

import com.example.lab_2.Adapters.IconSpinnerAdapter;
import com.example.lab_2.AdditionalClasses.IconClass;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<IconClass> iconList = new ArrayList<>();

        iconList.add(new IconClass("Android", "Android Mobile Application", R.drawable.android));
        iconList.add(new IconClass("Google", "Google", R.drawable.google));
        iconList.add(new IconClass("Apple", "Apple", R.drawable.apple));
        iconList.add(new IconClass("Yahoo", "Yahoo", R.drawable.yahoo));

        Spinner spIconBrands = (Spinner) findViewById(R.id.spIconBrands);

        IconSpinnerAdapter spinnerAdapter = new IconSpinnerAdapter(this, iconList);

        spIconBrands.setAdapter(spinnerAdapter);
    }
}