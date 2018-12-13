package com.example.min0105.jazzjackrabbit;

import android.graphics.Bitmap;

public abstract class GameObject {

    protected Bitmap image;

    protected final int rowCount;
    protected final int colCount;

    private final int mapWidth;
    private final int mapHeight;

    private final int imgWidth;
    private final int imgHeight;

    protected final int objWidth;
    protected final int objHeight;



    protected int x;
    protected int y;

    public GameObject(Bitmap image, int rowCount, int colCount, int x, int y, int width, int height)  {

        this.image = image;
        this.rowCount= rowCount;
        this.colCount= colCount;

        this.x= x;
        this.y= y;

        this.mapWidth = image.getWidth();
        this.mapHeight = image.getHeight();

        this.imgWidth = this.mapWidth/ colCount;
        this.imgHeight= this.mapHeight/ rowCount;

        this.objWidth = width;
        this.objHeight = height;

    }


    protected Bitmap createSubImageAt(int row, int col)  {
        // createBitmap(bitmap, x, y, width, height).
        Bitmap subImage = Bitmap.createScaledBitmap(
                Bitmap.createBitmap(image, col* imgWidth, row* imgHeight ,imgWidth, imgHeight)
                , objWidth, objHeight, false
        );
        return subImage;
    }

    public int getX()  {
        return this.x;
    }

    public int getY()  {
        return this.y;
    }


    public int getHeight() {
        return objHeight;
    }

    public int getWidth() {
        return objWidth;
    }

}