package ru.lab_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.StackView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private StackView stackView;
    private Button buttonPrevious;
    private Button buttonNext;
    int count = 1;
    private final String[] IMAGE_NAMES= {"image1","image2", "image3", "image4","image5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1000);
        animationDrawable.start();

        this.stackView = (StackView) findViewById(R.id.stackView);
        this.buttonNext =(Button) findViewById(R.id.button_next);
        this.buttonPrevious= (Button) findViewById(R.id.button_previous);

        List<StackItem> items = new ArrayList<StackItem>();

        for(String imageName: IMAGE_NAMES) {
            items.add(new StackItem(imageName));
        }

        StackAdapter adapt = new StackAdapter(this, R.layout.stack_item, items);
        stackView.setAdapter(adapt);

        buttonNext.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                count++;
                stackView.showNext();
                if (count == 5) {
                    buttonNext.setEnabled(false);
                    buttonPrevious.setEnabled(true);
                }
                else if (count == 2)
                    buttonPrevious.setEnabled(true);
                else if (count == 1)
                    buttonPrevious.setEnabled(false);
            }
        });

        buttonPrevious.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                count--;
                stackView.showPrevious();
                if (count == 1) {
                    buttonPrevious.setEnabled(false);
                    buttonNext.setEnabled(true);
                }
                else if (count == 4)
                    buttonNext.setEnabled(true);
                else if (count == 5)
                    buttonNext.setEnabled(false);
            }
        });
    }
}