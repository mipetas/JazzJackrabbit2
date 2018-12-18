package com.example.min0105.jazzjackrabbit.GameObjects;

import android.graphics.Bitmap;

import com.example.min0105.jazzjackrabbit.Direction;


public class BasicBullet extends Bullet {

    private int speed = 25;

    private final int MAX_TRAVEL_DISTANCE = 1000;

    Direction direction;

    public BasicBullet(Bitmap image, int x, int y, int width, int height, Direction direction) {
        super(image, 1, 1, x, y, width, height, 1);

        currImg = Bitmap.createScaledBitmap(image, objWidth, objHeight, false);
        this.direction = direction;
    }

    @Override
    public void  update() {

        if(traveledDistance < MAX_TRAVEL_DISTANCE)
        {
            switch (direction){
                case RIGHT:{
                    this.x += speed;
                    break;
                }

                case LEFT:{
                    this.x -= speed;
                    break;
                }
            }
            traveledDistance += speed;
        }
        else
        {
            destroy = true;
        }

    }


}
