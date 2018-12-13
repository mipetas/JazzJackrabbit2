package com.example.min0105.jazzjackrabbit;

import android.graphics.Bitmap;

public class DestroyableBlock extends GameBlock implements IDestroyable {

    private int health;

    public DestroyableBlock(Bitmap image, int x, int y, int size, int health) {
        super(image, x, y, size);

        this.health = health;

    }

    @Override
    public void hit(int damage) {

        health -= damage;

        if(health <= 0)
            destroy();

    }

    @Override
    public void destroy() {

    }
}
