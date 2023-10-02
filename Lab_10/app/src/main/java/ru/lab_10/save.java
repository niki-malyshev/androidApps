package ru.lab_10;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class save {
    public class MainActivity extends AppCompatActivity {
        private static final String PREFS_NAME = "MyPrefsFile";
        private static final String KEY_HORIZONTAL_POSITION = "horizontal_position";
        private static final String KEY_VERTICAL_POSITION = "vertical_position";
        private ImageView mBall;
        private SeekBar mHorizontalSeekBar;
        private SeekBar mVerticalSeekBar;
        private Button mRememberButton;
        private Button mPlayButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mBall = findViewById(R.id.ball);
            mHorizontalSeekBar = findViewById(R.id.horizontal_seek_bar);
            mVerticalSeekBar = findViewById(R.id.vertical_seek_bar);
            mRememberButton = findViewById(R.id.remember_button);
            mPlayButton = findViewById(R.id.play_button);
            loadSeekBarPositionsFromPreferences();
            updateBallPosition();
            mHorizontalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    updateBallPosition();
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    saveSeekBarPositionsToPreferences();
                }
            });
            mVerticalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    updateBallPosition();
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    saveSeekBarPositionsToPreferences();
                }
            });
        }

        private void updateBallPosition() {
            int x = mHorizontalSeekBar.getProgress();
            int y = mVerticalSeekBar.getMax() - mVerticalSeekBar.getProgress();
            setBallPosition(new Point(x, y));
        }
        private Point getBallPosition() {
            int x = mHorizontalSeekBar.getProgress();
            int y = mVerticalSeekBar.getMax() - mVerticalSeekBar.getProgress();
            return new Point(x, y);
        }
        private void setBallPosition(Point position) {
            mBall.setX(position.x);
            mBall.setY(position.y);
        }
        private void loadSeekBarPositionsFromPreferences() {
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            int horizontalPosition = prefs.getInt(KEY_HORIZONTAL_POSITION, 0);
            int verticalPosition = prefs.getInt(KEY_VERTICAL_POSITION, mVerticalSeekBar.getMax() / 2);
            mHorizontalSeekBar.setProgress(horizontalPosition);
            mVerticalSeekBar.setProgress(verticalPosition);
        }

        private void saveSeekBarPositionsToPreferences() {
            SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
            editor.putInt(KEY_HORIZONTAL_POSITION, mHorizontalSeekBar.getProgress());
            editor.putInt(KEY_VERTICAL_POSITION, mVerticalSeekBar.getProgress());
            editor.apply();
        }
    }
}
