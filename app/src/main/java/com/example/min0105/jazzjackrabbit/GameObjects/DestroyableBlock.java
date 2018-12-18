package com.example.min0105.jazzjackrabbit.GameObjects;

import android.graphics.Bitmap;

public class DestroyableBlock extends GameBlock implements IDestroyable {

    private int health;

    private boolean isDestroyed = false;

    public DestroyableBlock(Bitmap image, int x, int y, int size, int health) {
        super(image, x, y, size);

        this.health = health;

    }

    @Override
    public void hit(int damage) {

        health -= damage;

        if(health <= 0)
            isDestroyed = true;
    }


}
