package ru.lab_4;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button button;
    ImageView packagePlace, background;
    SeekBar packageRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.ButtonChangeImage);
        packagePlace = findViewById(R.id.imageView);
        packageRange = findViewById(R.id.seekBar);
        background = findViewById(R.id.BackGround);

        packageRange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress == 0)
                    packagePlace.setImageResource(R.drawable.gora1);
                else if(progress == 1)
                    packagePlace.setImageResource(R.drawable.gora2);
                else if(progress == 2)
                    packagePlace.setImageResource(R.drawable.gora3);
                else if(progress == 3)
                    packagePlace.setImageResource(R.drawable.gora4);
                else if(progress == 4)
                    packagePlace.setImageResource(R.drawable.gora5);
                else if(progress == 5)
                    packagePlace.setImageResource(R.drawable.gora6);
                else if(progress == 6)
                    packagePlace.setImageResource(R.drawable.gora7);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = packagePlace.getDrawable();
                background.setImageDrawable(drawable);

            }
        });

    }
}