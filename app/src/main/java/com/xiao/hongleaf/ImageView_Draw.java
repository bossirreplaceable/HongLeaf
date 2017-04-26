package com.xiao.hongleaf;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by YoBo on 2017/3/23.
 */
public class ImageView_Draw extends android.support.v7.widget.AppCompatImageView {

    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private final Paint paint=new Paint();
    private float mX;
    private float mY;
    private final Path mPath = new Path();
    public ImageView_Draw(Context context, AttributeSet attributes){
        super(context,attributes);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                startX=event.getX();
                startY=event.getY();

                //绘制线条时使用
                //touchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                endX=event.getX();
                endY=event.getY();

                //绘制线条时使用
                //touchMove();
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        //绘制矩形
        canvas.drawRect(startX,startY,endX,endY,paint);
        //绘制线条
//        canvas.drawPath(mPath, paint);
    }

    //手指点下屏幕时调用
    private void touchDown(MotionEvent event)
    {
        //重置绘制路线，即隐藏之前绘制的轨迹
        mPath.reset();
        float x = event.getX();
        float y = event.getY();
        mX = x;
        mY = y;
        //mPath绘制的绘制起点
        mPath.moveTo(x, y);
    }

    //手指在屏幕上滑动时调用
    private void touchMove(MotionEvent event)
    {
        final float x = event.getX();
        final float y = event.getY();

        final float previousX = mX;
        final float previousY = mY;

        final float dx = Math.abs(x - previousX);
        final float dy = Math.abs(y - previousY);

        //两点之间的距离大于等于3时，生成贝塞尔绘制曲线
        if (dx >= 3 || dy >= 3)
        {
            //设置贝塞尔曲线的操作点为起点和终点的一半
            float cX = (x + previousX) / 2;
            float cY = (y + previousY) / 2;

            //二次贝塞尔，实现平滑曲线；previousX, previousY为操作点，cX, cY为终点
            mPath.quadTo(previousX, previousY, cX, cY);

            //第二次执行时，第一次结束调用的坐标值将作为第二次调用的初始坐标值
            mX = x;
            mY = y;
        }
    }

}

