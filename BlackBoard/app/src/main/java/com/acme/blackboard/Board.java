package com.acme.blackboard;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.HashMap;

public class Board extends View {
    private Bitmap mBitmap = null;
    private Canvas mCanvas = null;
    private Path mPath = null;
    private float mX, mY;
    private static final float TOLERANCE = 4;


    public void setPaintColor (int id) {
        mPaint.setColor(id);
        mPaint.setXfermode(null); //Quitamos si habíamos puesto para borrar.
    }

    private Paint mPaint = null;


    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Board(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        //obtener pantalla
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        mBitmap = Bitmap.createBitmap(point.x,point.y, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPath = new Path();
        //preparamos el pincel
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0XFF00E1FF);
        mPaint.setStrokeWidth(10);
        
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchStart(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x,y);
                invalidate();//Fuerza refresco de la vista para hacer que se ejecute el métod onDraw y actualiza los trazos del objeto PAth
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        return true;
    }


    private void touchStart(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y) { //Se mueve dedo por la pantalla
        //Se actualiza el Path y se guardan las ultimas coordenadas. PAra no sobrecargar maquina
        // se utiliza la tolerance
        if (Math.abs(x - mX)>= TOLERANCE ||Math.abs(y - mY)>= TOLERANCE){
            mPath.quadTo(mX, mY, (x + mX)/2,(y + mY)/2);
            mX = x;
            mY = y;
        }
    }

    private void touchUp() {
        mPath.lineTo(mX, mY);
        mCanvas.drawPath(mPath, mPaint);
        mPath.reset();
    }

    @Override
    protected void onDraw(Canvas canvas){
        //fondo
        canvas.drawColor(0XFFBBBBBB);
        // lo ya pintado
        canvas.drawBitmap(mBitmap, 0,0, null);
        //el trazo actual
        canvas.drawPath(mPath, mPaint);
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void clear(Context context){
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        mBitmap = Bitmap.createBitmap(point.x,point.y, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        invalidate();
    }

    public void setEraser() {
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public void setStrokeWidth(float strokewidht) {
        mPaint.setStrokeWidth(strokewidht);
    }
}
