package com.example.min0105.jazzjackrabbit.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Bullet extends GameObject {

    protected int damage;

    public boolean destroy = false;

    protected int traveledDistance = 0 ;

    Bitmap currImg;

    public Bullet(Bitmap image, int rowCount, int colCount, int x, int y, int width, int height, int damage) {
        super(image, rowCount, colCount, x, y, width, height);
        this.damage = damage;
    }

    public abstract void update();

    public void draw(Canvas canvas, int xDiff, int yDiff){
        canvas.drawBitmap(currImg, x-xDiff, y-yDiff,null);
    }

    public int getDamage(){
        return damage;
    }

}
