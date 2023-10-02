package ru.startandroid.develop.myapplication;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ViewFlipper;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;// менеджер датчиков
    private Sensor mAccelerometer;// объект датчика
    private ViewFlipper mViewFlipper;// ViewFlipper для смены экранов
    private float mLastX;// последнее значение координаты x

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Создание ViewFlipper и добавление трех макетов
        mViewFlipper = new ViewFlipper(this);
        mViewFlipper.addView(getLayoutInflater().inflate(R.layout.activity_main, null));
        mViewFlipper.addView(getLayoutInflater().inflate(R.layout.activity_right, null));
        mViewFlipper.addView(getLayoutInflater().inflate(R.layout.activity_left, null));

        setContentView(mViewFlipper);

        //Выключить поворот экрана
        lockScreenOrientation();

        //Подключить сенсоры
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //Процедура блокирования поворота экрана
    private void lockScreenOrientation() {
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    //Регистрация обработчика датчиков
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    //Снятие обработчика
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Метод вызывается, если точность датчика изменяется
        // Игнорируем в данном случае
        }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Метод вызывается при изменении показаний датчика
        float x = event.values[0];// Получение координаты x
        //mLastX = x;
        if (x == 0 && mLastX < 0) {
            mViewFlipper.setInAnimation(this, R.anim.slide_in_left);
            mViewFlipper.setOutAnimation(this, R.anim.slide_out_right);
            mViewFlipper.setDisplayedChild(0);

        }
        else if (x == 0 && mLastX > 0) {
            mViewFlipper.setInAnimation(this, R.anim.slide_in_right);
            mViewFlipper.setOutAnimation(this, R.anim.slide_out_left);
            mViewFlipper.setDisplayedChild(0);

        }

        else if (x > 0 && mLastX <= 0) {
            mViewFlipper.setInAnimation(this, R.anim.slide_in_left);
            mViewFlipper.setOutAnimation(this, R.anim.slide_out_right);
            mViewFlipper.setDisplayedChild(2);

        }
        else if (x < 0 && mLastX >= 0) {
            mViewFlipper.setInAnimation(this, R.anim.slide_in_right);
            mViewFlipper.setOutAnimation(this, R.anim.slide_out_left);
            mViewFlipper.setDisplayedChild(1);
        }
        mLastX = x;
        Log.d("T_001", "X=" + Float.toString(mLastX));
    }
}