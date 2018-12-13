package com.example.min0105.jazzjackrabbit;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public abstract class GameBlock extends GameObject {

    Bitmap scaledImg;

    public GameBlock(Bitmap image, int x, int y, int size) {
        super(image, 1, 1, x, y, size, size);

        scaledImg = Bitmap.createScaledBitmap(image, objWidth, objHeight, false);

    }

    public void draw(Canvas canvas, int xDiff, int yDiff){
        canvas.drawBitmap(scaledImg, x-xDiff, y-yDiff,null);
    }

    public boolean isInBlock(int x, int y)
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

}
