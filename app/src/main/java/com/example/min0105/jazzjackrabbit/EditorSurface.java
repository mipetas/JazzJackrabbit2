package com.example.min0105.jazzjackrabbit;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class EditorSurface extends SurfaceView implements SurfaceHolder.Callback {

    private EditorThread editorThread;

    public final int BLOCK_SIZE = 100;

    private boolean isRabbit = false;
    private boolean isFinish = false;

    private Joystick joystick;

    private EditorGrid editorGrid;

    private ObjectMenu objectMenu;

    private SaveButton saveButton;

    private final int CAMERA_SPEED = 30;

    private int xDiff = 0;
    private int yDiff = 0;

    Context context;

    public EditorSurface(Context context)  {
        super(context);

        this.context = context;

        this.setFocusable(true);


        this.getHolder().addCallback(this);
    }

    public void update()  {
        this.joystick.update();
        updateCameraPosition();

    }



    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);

        editorGrid.draw(canvas, xDiff, yDiff);
        objectMenu.draw(canvas);
        joystick.draw(canvas);
        saveButton.draw(canvas);
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        this.saveButton = new SaveButton(this,getWidth()*8/9, getHeight()*4/5, getWidth()/10);
        this.joystick = new Joystick(this);
        this.editorGrid = new EditorGrid(this, 200 * BLOCK_SIZE, 200*BLOCK_SIZE, BLOCK_SIZE);
        this.objectMenu = new ObjectMenu(this, 100);
        initMenu();


        this.editorThread = new EditorThread(this,holder);
        this.editorThread.setRunning(true);
        this.editorThread.start();
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
                this.editorThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.editorThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= false;
        }
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        float xDown;
        float yDown;

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    xDown = event.getX();
                    yDown = event.getY();

                    if (saveButton.isInButton(xDown, yDown)){
                        editorGrid.saveToFile(context);
                        Toast.makeText(context, "LEVEL SAVED",
                                Toast.LENGTH_LONG).show();
                    }
                    else if (joystick.isInCircle(xDown, yDown)) {
                        joystick.moveBall(xDown, yDown);
                    }
                    else if(objectMenu.isInObjectMenu(xDown, yDown)){
                        objectMenu.select(xDown);
                    }
                    else{
                        if(objectMenu.getSelectedName().equals("remove"))
                            editorGrid.removeObject((int)Math.floor((xDown+xDiff)/BLOCK_SIZE),(int)Math.floor((yDown+yDiff)/BLOCK_SIZE));
                        else
                            editorGrid.addObject((int)Math.floor((xDown+xDiff)/BLOCK_SIZE),(int)Math.floor((yDown+yDiff)/BLOCK_SIZE),objectMenu.getSelectedName(),BLOCK_SIZE, BLOCK_SIZE);
                    }
                    break;
                }

                case MotionEvent.ACTION_MOVE: {
                    xDown = event.getX();
                    yDown = event.getY();
                    if (joystick.isInCircle(xDown, yDown)) {
                        joystick.moveBall(xDown, yDown);
                    }
                    else if(objectMenu.isInObjectMenu(xDown, yDown)){
                        objectMenu.select(xDown);
                    }
                    else{
                        if(objectMenu.getSelectedName().equals("remove"))
                            editorGrid.removeObject((int)Math.floor((xDown+xDiff)/BLOCK_SIZE),(int)Math.floor((yDown+yDiff)/BLOCK_SIZE));
                        else
                            editorGrid.addObject((int)Math.floor((xDown+xDiff)/BLOCK_SIZE),(int)Math.floor((yDown+yDiff)/BLOCK_SIZE),objectMenu.getSelectedName(),BLOCK_SIZE, BLOCK_SIZE);
                    }
                    break;
                }

                case MotionEvent.ACTION_UP: {
                        joystick.resetBall();
                }

            }

        return true;
    }

    private void initMenu(){
        objectMenu.addItem("ground");
        objectMenu.addItem("grass_l");
        objectMenu.addItem("grass");
        objectMenu.addItem("grass_r");
        objectMenu.addItem("finish");
        objectMenu.addItem("rabbit");
        objectMenu.addItem("Eturtle");

    }

    private void updateCameraPosition(){
        switch(joystick.getDirection()){
            case LEFT:{
                xDiff -= CAMERA_SPEED;
                break;
            }
            case RIGHT:{
                xDiff += CAMERA_SPEED;
                break;
            }
            case UP:{
                yDiff -= CAMERA_SPEED;
                break;
            }
            case DOWN:{
                yDiff += CAMERA_SPEED;
                break;
            }
        }

        if(xDiff < 0)
            xDiff = 0;
        if(yDiff < 0)
            yDiff = 0;

    }


}
