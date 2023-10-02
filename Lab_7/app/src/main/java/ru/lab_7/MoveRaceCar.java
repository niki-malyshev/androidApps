package ru.lab_7;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class MoveRaceCar extends View {
    private Paint mPaint;
    private Path mPath;
    private Bitmap mBitmap;
    private PathMeasure mPathMeasure;
    private Matrix mMatrix;
    private int mOffsetX, mOffsetY;
    private float mPathLength;
    private float mStep; // distance each step
    private float mDistance; // distance moved
    private float[] mPosition;
    private float[] mTan;
    private float mCurX, mCurY;
    private float mCurAngle; // current angle
    private float mTargetAngle; // target angle
    private float mStepAngle; // angle each step

    public MoveRaceCar(Context context) {
        super(context);
        init();
    }

    public MoveRaceCar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MoveRaceCar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.DKGRAY);
        mPaint.setStrokeWidth(100);
        mPaint.setStyle(Paint.Style.STROKE);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car1);

        mOffsetX = mBitmap.getWidth() / 2;
        mOffsetY = mBitmap.getHeight() / 2;

        mPath = new Path();
        float radius = 40.0f;
        CornerPathEffect mCornerPathEffect = new CornerPathEffect(radius);
        mPaint.setPathEffect(mCornerPathEffect);

        mPath.moveTo(100, 100);
        mPath.lineTo(2100, 100);
        mPath.lineTo(100, 900);
        mPath.lineTo(2100, 900);

        mPath.close();

        mPathMeasure = new PathMeasure(mPath, false);
        mPathLength = mPathMeasure.getLength();

        mStep = 0;
        mDistance = 0;
        mCurX = 0;
        mCurY = 0;

        mStepAngle = 20;
        mCurAngle = 0;
        mTargetAngle = 0;

        mPosition = new float[2];
        mTan = new float[2];

        mMatrix = new Matrix();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPath, mPaint);
        mMatrix.reset();

        if ((mTargetAngle - mCurAngle) > mStepAngle) {
            mCurAngle += mStepAngle;
            mMatrix.postRotate(mCurAngle, mOffsetX, mOffsetY);
            mMatrix.postTranslate(mCurX, mCurY);
            canvas.drawBitmap(mBitmap, mMatrix, null);


        } else if ((mCurAngle - mTargetAngle) > mStepAngle) {
            mCurAngle -= mStepAngle;
            mMatrix.postRotate(mCurAngle, mOffsetX, mOffsetY);
            mMatrix.postTranslate(mCurX, mCurY);
            canvas.drawBitmap(mBitmap, mMatrix, null);


        } else {
            mCurAngle = mTargetAngle;
            if (mDistance < mPathLength) {
                mPathMeasure.getPosTan(mDistance, mPosition, mTan);

                mTargetAngle = (float) (Math.atan2(mTan[1], mTan[0]) * 180.0 / Math.PI);
                mMatrix.postRotate(mCurAngle, mOffsetX, mOffsetY);

                mCurX = mPosition[0] - mOffsetX;
                mCurY = mPosition[1] - mOffsetY;
                mMatrix.postTranslate(mCurX, mCurY);

                canvas.drawBitmap(mBitmap, mMatrix, null);

                mDistance += mStep;
            } else {
                mDistance = 0;
            }
        }

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                if (mStep == 40){
                    mStep = 0;
                } else if (mStep == 0) {
                    mStep = 40;
                }

                break;
        }
        return true;
    }
}
