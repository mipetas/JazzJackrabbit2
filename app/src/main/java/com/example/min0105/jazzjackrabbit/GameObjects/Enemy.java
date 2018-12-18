package com.example.min0105.jazzjackrabbit.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.min0105.jazzjackrabbit.GameSurface;

public abstract class Enemy extends GameCharacter implements IDestroyable {


    protected boolean isDead = false;

    GameSurface gameSurface;

    Bitmap scaledImg;

    public Enemy(GameSurface gameSurface, Bitmap image, int rowCount, int colCount, int x, int y, int width, int height) {
        super(image, rowCount, colCount, x, y, width, height);
        this.gameSurface = gameSurface;

        scaledImg = Bitmap.createScaledBitmap(image, objWidth, objHeight, false);

    }

    public boolean isDead() {
        return isDead;
    }

    public boolean touchingEnemy(int x, int y)
    {
        if(x >= this.x &&
                x <= this.x + getWidth() &&
                y >= this.y &&
                y <= this.y + getHeight()
                ){
            return true;
        }
        else
            return false;
    }

    public void draw(Canvas canvas, int xDiff, int yDiff) {
        canvas.drawBitmap(scaledImg, x-xDiff, y-yDiff,null);
    }

}
