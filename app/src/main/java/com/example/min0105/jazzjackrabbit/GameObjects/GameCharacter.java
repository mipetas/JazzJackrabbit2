package com.example.min0105.jazzjackrabbit.GameObjects;

import android.graphics.Bitmap;

public abstract class GameCharacter extends GameObject implements IDestroyable {

    protected int health;

    public GameCharacter(Bitmap image, int rowCount, int colCount, int x, int y, int width, int height) {
        super(image, rowCount, colCount, x, y, width, height);
    }

    public abstract void update();

}
