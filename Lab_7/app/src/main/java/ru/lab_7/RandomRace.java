package ru.lab_7;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class RandomRace extends View {
    private Paint mPaint;
    private Path mPath;
    private Path mTouchPath;
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

    public RandomRace(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    public RandomRace(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RandomRace(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);

        mBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.car1);
        mOffsetX = mBitmap.getWidth() / 2;
        mOffsetY = mBitmap.getHeight();

        mTouchPath = new Path();
        mPath = new Path();

        mPathMeasure = new PathMeasure(mPath, false);
        mPathLength = mPathMeasure.getLength();

        Toast.makeText(getContext(), "Path Length: " + mPathLength,
                Toast.LENGTH_LONG).show();

        mStep = 40;
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
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        if (mPath.isEmpty()) {
            return;
        }

        canvas.drawPath(mPath, mPaint);
        mMatrix.reset();

        if ((mTargetAngle - mCurAngle) > mStepAngle) {
            mCurAngle += mStepAngle;
            mMatrix.postRotate(mCurAngle, mOffsetX, mOffsetY);
            mMatrix.postTranslate(mCurX, mCurY);
            canvas.drawBitmap(mBitmap, mMatrix, null);

            invalidate();
        } else if ((mCurAngle - mTargetAngle) > mStepAngle) {
            mCurAngle -= mStepAngle;
            mMatrix.postRotate(mCurAngle, mOffsetX, mOffsetY);
            mMatrix.postTranslate(mCurX, mCurY);
            canvas.drawBitmap(mBitmap, mMatrix, null);

            invalidate();
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

                invalidate();
            } else {
                // mDistance = 0;
                mMatrix.postRotate(mCurAngle, mOffsetX, mOffsetY);
                mMatrix.postTranslate(mCurX, mCurY);
                canvas.drawBitmap(mBitmap, mMatrix, null);
            }
        }

        // invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mTouchPath.reset();
                mTouchPath.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                mTouchPath.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                mTouchPath.lineTo(event.getX(), event.getY());
                mPath = new Path(mTouchPath);

                mPathMeasure = new PathMeasure(mPath, false);
                mPathLength = mPathMeasure.getLength();

                mStep = 40;
                mDistance = 0;
                mCurX = 0;
                mCurY = 0;

                mStepAngle = 20;
                mCurAngle = 0;
                mTargetAngle = 0;

                invalidate();

                break;
        }

        return true;
    }
}
