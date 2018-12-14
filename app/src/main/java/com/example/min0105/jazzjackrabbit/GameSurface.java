package com.example.min0105.jazzjackrabbit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    public final int BLOCK_SIZE = 120;

    public final int PLAYER_WIDTH = 120;
    public final int PLAYER_HEIGHT = 140;

    private int playerStartX;
    private int playerStartY;

    private GameThread gameThread;

    private Rabbit player;

    private Joystick joystick;

    private Level currLevel;

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
        this.currLevel.drawBlocks(canvas, this.player.getX() - playerStartX,
                this.player.getY() - playerStartY);
        this.player.draw(canvas);
        this.joystick.draw(canvas);
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap playerBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.kralejk_bitmap);
        this.player = new Rabbit(this,playerBitmap,
                getWidth()/2-PLAYER_WIDTH/2,getHeight()*2/3,
                PLAYER_WIDTH, PLAYER_HEIGHT);
        playerStartX = getWidth()/2 - PLAYER_WIDTH/2;
        playerStartY = getHeight()*2/3;
        this.joystick = new Joystick(this);
        this.currLevel = new Level(this);

        this.currLevel.addBlock(new Block(BitmapFactory.decodeResource(this.getResources(),R.drawable.grass),7*120, 500, 120));
        this.currLevel.addBlock(new Block(BitmapFactory.decodeResource(this.getResources(),R.drawable.grass),9*120, 650, 120));
        this.currLevel.addBlock(new Block(BitmapFactory.decodeResource(this.getResources(),R.drawable.grass),14*120, 480, 120));
        this.currLevel.addBlock(new Block(BitmapFactory.decodeResource(this.getResources(),R.drawable.grass),14*120, 600, 120));
        this.currLevel.addBlock(new Block(BitmapFactory.decodeResource(this.getResources(),R.drawable.grass),14*120, 720, 120));
        this.currLevel.addBlock(new Block(BitmapFactory.decodeResource(this.getResources(),R.drawable.grass),14*120, 840, 120));
        this.currLevel.addBlock(new Block(BitmapFactory.decodeResource(this.getResources(),R.drawable.grass),0, 840, 120));
        this.currLevel.addBlock(new Block(BitmapFactory.decodeResource(this.getResources(),R.drawable.ground),0, 960, 120));
        for(int i=1; i<14; i++)
        {
            this.currLevel.addBlock(new Block(BitmapFactory.decodeResource(this.getResources(),R.drawable.grass),i*120, 960, 120));
        }
        this.currLevel.addBlock(new Block(BitmapFactory.decodeResource(this.getResources(),R.drawable.ground),14*120, 960, 120));

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

    public Level getCurrLevel(){
        return currLevel;
    }

    public Rabbit getPlayer(){
        return player;
    }

}
