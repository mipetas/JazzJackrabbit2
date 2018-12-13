package com.example.min0105.jazzjackrabbit;


import android.graphics.Canvas;
import android.graphics.Paint;


public class Joystick {

    private GameSurface gameSurface;

    private int radius;

    private int centerX;
    private int centerY;
    private int ballX;
    private int ballY;

    private double angle = 0;
    private double power = 0;
    private final int requiredPower = 30;

    private Direction direction;

    private Paint backgroundPaint;
    private Paint ballPaint;

    public Joystick(GameSurface gameSurface)
    {
        this.radius = gameSurface.getWidth()/8;
        this.gameSurface = gameSurface;

        centerY = gameSurface.getHeight()-radius-10;
        centerX = radius+10;
        ballX = centerX;
        ballY = centerY;

        backgroundPaint = new Paint();
        backgroundPaint.setARGB(30,255,255,255);
        backgroundPaint.setStyle(Paint.Style.FILL);

        ballPaint = new Paint();
        ballPaint.setARGB(70,255,255,255);
        ballPaint.setStyle(Paint.Style.FILL);

    }

    public void moveBall(float xDown, float yDown)
    {
        if(isPointInCircle(xDown, yDown))
        {
            ballY = (int) yDown;
            ballX = (int) xDown;
        }
    }

    public void draw(Canvas canvas){

        canvas.drawCircle(centerX, centerY, this.radius,  backgroundPaint);
        canvas.drawCircle(ballX, ballY, this.radius/4, ballPaint);

    }

    public void resetBall()
    {
        ballY = centerY;
        ballX = centerX;
    }

    public boolean isPointInCircle(float x, float y){
        if(Math.sqrt(((x-centerX)*(x-centerX)+(y-centerY)*(y-centerY))) <= radius)
            return true;
        else
            return false;

    }

    public void update(){

        int adjacent, opposite;

        if(ballY == centerY && ballX == centerX){
            angle = 0;
            power = 0;
        }

        else if(ballX > centerX && ballY < centerY){
            adjacent = ballX - centerX;
            opposite = centerY - ballY;
            angle = Math.toDegrees(Math.atan2(opposite, adjacent));
            power = Math.sqrt(opposite*opposite+adjacent*adjacent)/radius*100;
        }

        else if(ballX < centerX && ballY < centerY){
            opposite = centerX - ballX;
            adjacent = centerY - ballY;
            angle = Math.toDegrees(Math.atan2(opposite, adjacent)) + 90;
            power = Math.sqrt(opposite*opposite+adjacent*adjacent)/radius*100;
        }

        else if(ballX < centerX && ballY > centerY){
            adjacent = centerX - ballX;
            opposite = ballY - centerY;
            angle = Math.toDegrees(Math.atan2(opposite, adjacent)) + 180;
            power = Math.sqrt(opposite*opposite+adjacent*adjacent)/radius*100;
        }

        else if(ballX > centerX && ballY > centerY){
            opposite = ballX - centerX;
            adjacent = ballY - centerY;
            angle = Math.toDegrees(Math.atan2(opposite, adjacent)) + 270;
            power = Math.sqrt(opposite*opposite+adjacent*adjacent)/radius*100;
        }

        setDirection(angle, power);

    }

    private void setDirection(double angle, double power){
        if(power > requiredPower){
            if((angle >= 0 && angle <= 50) ||
                    (angle >= 310 && angle <= 360)){
                direction = Direction.RIGHT;
            }
            else if(angle >= 130 && angle <= 220){
                direction = Direction.LEFT;
            }
            else if(angle > 50 && angle < 130){
                direction = Direction.UP;
            }
            else if(angle > 220 && angle < 310){
                direction = Direction.DOWN;
            }
        }
        else
            direction = Direction.NONE;
    }

    public double getAngle(){
        return angle;
    }

    public double getPower(){
        return power;
    }

    public Direction getDirection(){
        return direction;
    }

}
