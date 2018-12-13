package com.example.min0105.jazzjackrabbit;

import android.graphics.Bitmap;

public abstract class Enemy extends GameObject implements IDestroyable {


    public Enemy(Bitmap image, int rowCount, int colCount, int x, int y, int width, int height) {
        super(image, rowCount, colCount, x, y, width, height);


    }


}
