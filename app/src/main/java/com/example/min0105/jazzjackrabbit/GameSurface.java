package com.example.min0105.jazzjackrabbit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {


    private GameThread gameThread;

    private Rabbit player;

    private Joystick joystick;

    private float xDown, yDown;

    public GameSurface(Context context)  {
        super(context);

        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);

        // Sét callback.
        this.getHolder().addCallback(this);
    }

    public void update()  {
        this.joystick.update();
        this.player.setDirection(this.joystick.getDirection());
        this.player.update();
    }



    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);

        this.player.draw(canvas);
        this.joystick.draw(canvas);
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap playerBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.kralejk_bitmap);
        this.player = new Rabbit(this,playerBitmap,20,10);

        this.joystick = new Joystick(this);
        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                xDown = event.getX();
                yDown = event.getY();
                joystick.moveBall(xDown,yDown);
                break;
            }

            case MotionEvent.ACTION_MOVE:
            {
                xDown = event.getX();
                yDown = event.getY();
                joystick.moveBall(xDown,yDown);
                break;
            }

            case MotionEvent.ACTION_UP:
            {
                joystick.resetBall();
            }

        }
        return true;
    }

}
