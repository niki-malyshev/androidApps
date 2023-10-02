package ru.lab_8;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.View;

public class SpaceInfinity extends View {
    int screenWidth, screenHeight, newWidth, newHeight;// ширина и высота экрана
    int spaceX = 0; //
    int spaceshipX, spaceshipY; // координаты расположения неподвижного объекта
    Bitmap space; // изображение имитирующее движение объекта
    Bitmap spaceship; // неподвижный объект
    Handler handler; //
    Runnable runnable; //
    final long UPDATE_MILLIS=10;

    public SpaceInfinity(Context context) {
        super(context);

        // Переменная space инициализируется изображением космоса
        space = BitmapFactory.decodeResource(getResources(),R.drawable.spacebg);
        // Переменная spaceship - изображением космического корабля
        spaceship = BitmapFactory.decodeResource(getResources(),R.drawable.spaceship);

        // Вычисляется соотношение сторон изображения космоса,
        // чтобы создать новый растровый объект,
        // который подходит для размера экрана
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        // Вычисляется новый размер изображения space,
        // чтобы соответствовать размеру экрана устройства с
        // соотношением сторон для изображения space
        float height = space.getHeight();
        float width = space.getWidth();
        float ratio = width/height;
        newHeight = screenHeight;
        newWidth = (int)(ratio*screenHeight);

        // Новый размер сохранится в space
        space = Bitmap.createScaledBitmap(space,newWidth,newHeight,false);

        // spaceshipХ и spaceshipY используются для
        // указания начальной позиции космического корабля
        spaceshipX = screenWidth/2-200;
        spaceshipY = screenHeight/2;

        // Handler и Runnable используются,
        // чтобы задать период обновления изображения экрана и запустить его
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Переменная spaceX уменьшается на 3 каждый раз,
        // чтобы создать движение изображения космоса
        spaceX -= 3;
        if(spaceX < -newWidth){ // Если фон отображен полностью,
            spaceX = 0;        // то начинаем заново со сдвигом на размер экрана
        }

        canvas.drawBitmap(space,spaceX,0,null);// отобразить изображение фона с текущим сдвигом
        if(spaceX < screenWidth - newWidth){// отобразить копию фона в конце первого фона,
                                            // чтобы получился эффект зацикливания
            canvas.drawBitmap(space, spaceX+newWidth,0,null);
        }

        // Изображение космического корабля выводится на экран
        // handler.postDelayed(runnable, UPDATE_MILLIS) запускает Runnable,
        // который повторно запускает этот метод в течение 30 миллисекунд
        canvas.drawBitmap(spaceship, spaceshipX, spaceshipY, null);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }
}
