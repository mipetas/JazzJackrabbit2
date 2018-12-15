package com.example.min0105.jazzjackrabbit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class ShootButton {

    private int x;
    private int y;

    private GameSurface gameSurface;

    private int size;

    Bitmap image;

    public ShootButton(GameSurface gameSurface, int x, int y, int size){

        this.x = x;
        this.y = y;
        this.size = size;
        this.gameSurface = gameSurface;

        this.image = BitmapFactory.decodeResource(gameSurface.getResources(),
                gameSurface.getResources().getIdentifier("shoot_button" , "drawable",
                        gameSurface.context.getPackageName()
                ));
        this.image = Bitmap.createScaledBitmap(image, size, size, false);


    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y,null);
    }

    public boolean isInButton(float x, float y){

        if(x > this.x && x < this.x + size &&
                y > this.y && y < this.y + size)
            return true;
        return false;
    }

}
