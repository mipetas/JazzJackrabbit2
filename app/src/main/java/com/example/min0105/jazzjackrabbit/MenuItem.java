package com.example.min0105.jazzjackrabbit;

import android.graphics.Bitmap;

class MenuItem {

    private Bitmap bitmap;

    private String name;

    public MenuItem(Bitmap bitmap, String name)
    {
        this.bitmap = bitmap;
        this.name = name;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public String getString(){
        return name;
    }

}
