package com.example.min0105.jazzjackrabbit.LevelEditor;

import android.graphics.Bitmap;

public class GridObject {

    private Bitmap bitmap;

    private String name;

    public GridObject(Bitmap bitmap, int width, int height, String name)
    {
        if(bitmap != null)
            this.bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        this.name = name;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public String getString(){
        return name;
    }
}
