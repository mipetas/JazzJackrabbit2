package com.example.min0105.jazzjackrabbit;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Rabbit extends GameObject {

    private static final int ROW_LEFT = 0;
    private static final int ROW_RIGHT = 1;

    // Row index of Image are being used.
    private int rowUsing = ROW_LEFT;

    private int colUsing;
    private Bitmap standingL;
    private Bitmap standingR;
    private Bitmap shootingL;
    private Bitmap shootingR;
    private Bitmap jumpL;
    private Bitmap jumpShootL;
    private Bitmap jumpR;
    private Bitmap jumpShootR;
    private Bitmap[] movingL;
    private Bitmap[] movingR;

    // Velocity of game character (pixel/millisecond)
    public static float yVelocity = 0f;
    public static float xVelocity = 0f;
    public static float rightVelocity = 0f;
    public static float leftVelocity = 0f;
    public static final float MAX_Y_VELOCITY = 1.5f;
    public static final float MAX_X_VELOCITY = 1.5f;
    public static final float MAX_X_VECTOR = 3;
    public static final float MAX_Y_VECTOR = 3;

    Direction direction;

    private float movingVectorX = 0;
    private float movingVectorY = 0;

    private long lastDrawNanoTime =-1;

    private GameSurface gameSurface;

    public Rabbit(GameSurface gameSurface, Bitmap image, int x, int y) {
        super(image, 4, 3, x, y, 120, 140);

        this.gameSurface= gameSurface;

        this.movingL = new Bitmap[2];
        this.movingR = new Bitmap[2];
        for(int col = 0; col< 2; col++ ) {
            this.movingL[col] = this.createSubImageAt(ROW_LEFT, col+1);
            this.movingR[col]  = this.createSubImageAt(ROW_RIGHT, col+1);
        }

        this.standingL = this.createSubImageAt(0,0);
        this.standingR = this.createSubImageAt(1,0);
        this.shootingL = this.createSubImageAt(2,0);
        this.shootingR = this.createSubImageAt(3,0);
        this.jumpL = this.createSubImageAt(2,1);
        this.jumpShootL = this.createSubImageAt(2,2);
        this.jumpR = this.createSubImageAt(3,1);
        this.jumpShootR = this.createSubImageAt(3,2);


    }

    public Bitmap[] getMoveBitmaps()  {
        switch (rowUsing)  {
            case ROW_LEFT:
                return  this.movingL;
            case ROW_RIGHT:
                return this.movingR;
            default:
                return null;

        }
    }

    public Bitmap getCurrentMoveBitmap()  {
        Bitmap[] bitmaps = this.getMoveBitmaps();
        return bitmaps[this.colUsing];
    }


    public void update()  {

        // Rabbit above ground
        if(y < this.gameSurface.getHeight()- objHeight)
        {
            if(movingVectorY <= MAX_Y_VECTOR)
                movingVectorY += 0.1;
            movingVectorY = 1;
            if(yVelocity <= MAX_Y_VELOCITY)
                yVelocity += 0.05f;
        }
        else
        {
                movingVectorY = 0;
                yVelocity = 0;
        }

        switch(direction)
        {
            case RIGHT:
            {
                if(leftVelocity > 0)
                    deaccel();
                else
                    accelRight();
                break;
            }

            case LEFT:
            {
                if(rightVelocity > 0)
                    deaccel();
                else
                    accelLeft();
                break;
            }
            case NONE:
            {
                deaccel();
                break;
            }
        }

        this.colUsing++;
        if(colUsing >= 2)  {
            this.colUsing =0;
        }
        // Current time in nanoseconds
        long now = System.nanoTime();

        // Never once did draw.
        if(lastDrawNanoTime==-1) {
            lastDrawNanoTime= now;
        }
        // Change nanoseconds to milliseconds (1 nanosecond = 1000000 milliseconds).
        int deltaTime = (int) ((now - lastDrawNanoTime)/ 1000000 );

        // Distance moves

        float xDistance = Math.max(rightVelocity,leftVelocity) * deltaTime;
        float yDistance = yVelocity * deltaTime;

        double movingVectorLength = Math.sqrt(movingVectorX* movingVectorX + movingVectorY*movingVectorY);

        // Calculate the new position of the game character.
        this.x = x +  (int)(xDistance* movingVectorX / movingVectorLength);
        this.y = y +  (int)(yDistance* movingVectorY / movingVectorLength);

        // When the game's character touches the edge of the screen, then change direction
/*
        if(this.x < 0 )  {
            this.x = 0;
            this.movingVectorX = - this.movingVectorX;
        } else if(this.x > this.gameSurface.getWidth() -objWidth)  {
            this.x= this.gameSurface.getWidth()-objWidth;
            this.movingVectorX = - this.movingVectorX;
        }

        if(this.y < 0 )  {
            this.y = 0;
            this.movingVectorY = - this.movingVectorY;
        } else if(this.y > this.gameSurface.getHeight()- objHeight)  {
            this.y= this.gameSurface.getHeight()- objHeight;
            this.movingVectorY = - this.movingVectorY ;
        }

        // rowUsing
        if( movingVectorX > 0 )  {
            if(movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.rowUsing = ROW_LEFT;
            }else if(movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.rowUsing = ROW_LEFT;
            }else  {
                this.rowUsing = ROW_RIGHT;
            }
        } else {
            if(movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.rowUsing = ROW_LEFT;
            }else if(movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.rowUsing = ROW_LEFT;
            }else  {
                this.rowUsing = ROW_LEFT;
            }
        }*/
    }

    public void draw(Canvas canvas)  {
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap,x, y, null);
        // Last draw time.
        this.lastDrawNanoTime= System.nanoTime();
    }

    public void setMovingVector(int movingVectorX, int movingVectorY)  {
        this.movingVectorX= movingVectorX;
        this.movingVectorY = movingVectorY;
    }

    public void setDirection(Direction d){
        this.direction = d;
    }

    private void accelRight()
    {
        if(movingVectorX <= MAX_X_VECTOR)
            movingVectorX += 0.1;

        if(rightVelocity <= MAX_X_VELOCITY)
            rightVelocity += 0.05f;
    }

    private void deaccel()
    {
        if(rightVelocity > 0)
            rightVelocity -= 0.05f;
        if(leftVelocity > 0)
            leftVelocity -= 0.05f;
        if(movingVectorX > 0)
            movingVectorX -= 0.1;
        if(movingVectorX < 0)
            movingVectorX += 0.1;
    }

    private void accelLeft()
    {
        if(movingVectorX >= -MAX_X_VECTOR)
            movingVectorX -= 0.1;

        if(leftVelocity <= MAX_X_VELOCITY)
            leftVelocity += 0.05f;
    }
}
