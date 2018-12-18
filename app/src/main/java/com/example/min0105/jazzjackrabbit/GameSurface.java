package com.example.min0105.jazzjackrabbit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.min0105.jazzjackrabbit.GameObjects.Rabbit;


public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    private boolean isRunning = true;

    public final int BLOCK_SIZE = 100;

    public final int PLAYER_WIDTH = 60;
    public final int PLAYER_HEIGHT = 80;

    private int playerStartX;
    private int playerStartY;

    private GameThread gameThread;

    private Rabbit player;

    private Joystick joystick;

    private Level currLevel;

    private ShootButton shootButton;

    public Context context;

    public GameSurface(Context context)  {
        super(context);

        this.context = context;
        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);

        // Sét callback.
        this.getHolder().addCallback(this);
    }

    public void update()  {
        this.joystick.update();
        this.player.setDirection(this.joystick.getDirection());
        this.player.update();
        this.currLevel.update();
    }



    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        this.currLevel.draw(canvas,player.getX() - getWidth()/2+PLAYER_WIDTH/2,
                player.getY() - getHeight()*2/3);
        this.player.draw(canvas);
        this.joystick.draw(canvas);
        this.shootButton.draw(canvas);
        this.player.drawHearts(canvas);
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap playerBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.kralejk_bitmap);
        this.currLevel = new Level(this);
        SharedPreferences sharedPref = context.getSharedPreferences(
                "LEVELS", Context.MODE_PRIVATE);
        String levelName = sharedPref.getString("currLevel", "custom.txt");
        currLevel.initLevel(levelName);

        this.player = new Rabbit(this,playerBitmap,
                playerStartX,playerStartY,
                PLAYER_WIDTH, PLAYER_HEIGHT);
        this.joystick = new Joystick(this);
        this.shootButton = new ShootButton(this,getWidth()*8/9, getHeight()*4/5, getWidth()/10);

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
            retry = false;
        }
    }

    public void gameOver() {

        getContext().startActivity(new Intent(this.getContext(), GameOverActivity.class));
    }

    public void win() {

        getContext().startActivity(new Intent(this.getContext(), WinActivity.class));
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        float xDown[] = new float[2];
        float yDown[] = new float[2];

        int shootingId = 0, movingId = 0;

        int totalPointerCount = event.getPointerCount();
        if(totalPointerCount > 2)
            totalPointerCount = 2;


        for(int i=0;i<totalPointerCount;i++) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN: {
                    xDown[i] = event.getX(i);
                    yDown[i] = event.getY(i);
                    if (joystick.isInCircle(xDown[i], yDown[i])) {
                        joystick.moveBall(xDown[i], yDown[i]);
                        movingId = i;
                    }
                    if (shootButton.isInButton(xDown[i], yDown[i]))
                    {
                        player.startShooting();
                        shootingId = i;
                    }
                    break;
                }

                case MotionEvent.ACTION_MOVE: {
                    xDown[i] = event.getX(i);
                    yDown[i] = event.getY(i);
                    if (joystick.isInCircle(xDown[i], yDown[i])) {
                        joystick.moveBall(xDown[i], yDown[i]);
                    }
                    if (shootButton.isInButton(xDown[i], yDown[i]))
                        player.startShooting();
                    else
                        player.stopShooting();
                    break;
                }

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP: {
                    if(i == movingId)
                        joystick.resetBall();
                    if(i == shootingId)
                        player.stopShooting();
                }

            }
        }

        return true;
    }

    public void setStartingPosition(int x, int y)
    {
        playerStartX = x;
        playerStartY = y;
    }

    public Level getCurrLevel(){
        return currLevel;
    }

    public Rabbit getPlayer(){
        return player;
    }

}
