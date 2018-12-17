package com.example.min0105.jazzjackrabbit;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Turtle extends Enemy {

    private int speed = 5;

    private int gravity = 5;

    public Turtle(GameSurface gameSurface, Bitmap image, int rowCount, int colCount, int x, int y, int width, int height) {
        super(gameSurface, image, rowCount, colCount, x, y, width, height);

        this.health = 5;

    }

    @Override
    public void hit(int damage) {
        health -= 1;
        if(health <= 0)
            isDead = true;
    }

    @Override
    public void update() {

        int newX = x + speed;
        int newY = y + gravity;

        if(speed > 0){
            if(gameSurface.getCurrLevel().isInBlocks(newX + getWidth(), y + getHeight())){
                speed = -speed;
            }
            else
                x = newX;
        }
        else{
            if(gameSurface.getCurrLevel().isInBlocks(newX, y + getHeight())){
                speed = -speed;
            }
            else
                x = newX;
        }

        if(!gameSurface.getCurrLevel().isInBlocks(x + getWidth()/2, newY + getHeight()))
            y = newY;

    }

}
