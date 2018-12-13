package com.example.min0105.jazzjackrabbit;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Rabbit extends GameObject {

    private static final int ROW_LEFT = 0;
    private static final int ROW_RIGHT = 1;

    private int rowUsing = ROW_RIGHT;

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
    private Bitmap currMoveBitmap;

    // Velocity of game character (pixel/millisecond)
    private static float upVelocity = 0f;
    private static float downVelocity = 0f;
    private static float rightVelocity = 0f;
    private static float leftVelocity = 0f;
    private static final float MAX_Y_VELOCITY = 1.5f;
    private static final float MAX_X_VELOCITY = 1.5f;
    private static final float MAX_X_VECTOR = 3;
    private static final float MAX_Y_VECTOR = 3;

    private Direction direction;
    private Direction facingDirection = Direction.RIGHT;

    private boolean isShooting = false;

    private float movingVectorX = 0;
    private float movingVectorY = 0;

    private long lastDrawNanoTime =-1;

    private GameSurface gameSurface;

    public Rabbit(GameSurface gameSurface, Bitmap image, int x, int y,int width,int height) {
        super(image, 4, 3, x, y, width, height);

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

        currMoveBitmap = standingR;

    }


    public Bitmap getCurrentMoveBitmap()  {
        return currMoveBitmap;
    }

    private void setCurrentMoveBitmap(){
        if(isAirborne()){
            if(isShooting)
                if(facingDirection == Direction.RIGHT)
                    currMoveBitmap = jumpShootR;
                else
                    currMoveBitmap = jumpShootL;
            else
                if(facingDirection == Direction.RIGHT)
                    currMoveBitmap = jumpR;
                else
                    currMoveBitmap = jumpL;
        }
        else{
            if(isShooting)
            {
                if(facingDirection == Direction.LEFT)
                    currMoveBitmap = shootingL;
                else
                    currMoveBitmap = shootingR;
            }
            else
            {
                switch(direction) {
                    case RIGHT: {
                        if(currMoveBitmap != movingR[0])
                            currMoveBitmap = movingR[0];
                        else
                            currMoveBitmap = movingR[1];
                        break;
                    }

                    case LEFT: {
                        if(currMoveBitmap != movingL[0])
                            currMoveBitmap = movingL[0];
                        else
                            currMoveBitmap = movingL[1];
                        break;
                    }

                    case NONE: {
                        if (facingDirection == Direction.LEFT)
                            currMoveBitmap = standingL;
                        else
                            currMoveBitmap = standingR;
                        break;
                    }
                }
            }
        }

    }


    public void update()  {

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
        float yDistance = Math.max(downVelocity,upVelocity) * deltaTime;

        double movingVectorLength = Math.sqrt(movingVectorX* movingVectorX + movingVectorY*movingVectorY);

        // Calculate the new position of the game character.
        this.x = x +  (int)(xDistance* movingVectorX / movingVectorLength);
        this.y = y +  (int)(yDistance* movingVectorY / movingVectorLength);

        int xD = gameSurface.getCurrLevel().isInBlocks(x + getWidth()/2, y + getHeight());


        setCurrentMoveBitmap();

        if(isAirborne())
        {
            fall();
        }
        else
        {
            y=this.gameSurface.getHeight()- objHeight;
            movingVectorY = 0;
            downVelocity = 0;
        }

        changeDirection();

        if(xD !=0)
            y = xD - getHeight();

    }

    public void draw(Canvas canvas)  {
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap,gameSurface.getWidth()/2-getWidth()/2,
                gameSurface.getHeight()*2/3, null);
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

    private void  jump()
    {
        if(downVelocity <= 0 && upVelocity <= 0)
        {
            upVelocity = 1.5f;
            movingVectorY = -3;
        }
    }

    private void changeDirection()
    {
        switch(direction)
        {
            case RIGHT:
            {
                facingDirection = Direction.RIGHT;
                if(leftVelocity > 0)
                    deaccel();
                else
                    accelRight();
                break;
            }

            case LEFT:
            {
                facingDirection = Direction.LEFT;
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

            case UP:
            {
                jump();
                break;
            }
        }
    }

    private boolean isAirborne()
    {
        if(gameSurface.getCurrLevel().isInBlocks(x + getWidth()/2, y + getHeight())== 0)
            return true;

       /* if(y < this.gameSurface.getHeight()- objHeight)
        {
            return true;
        }*/

        return false;
    }

    private void fall()
    {
        if(upVelocity > 0)
            upVelocity -= 0.05f;
        if(movingVectorY <= MAX_Y_VECTOR)
            movingVectorY += 0.1;
        if(movingVectorY > 0)
            if(downVelocity <= MAX_Y_VELOCITY)
                downVelocity += 0.05f;
    }


}
