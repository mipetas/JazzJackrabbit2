package com.example.min0105.jazzjackrabbit.LevelEditor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class SaveButton {

    private int x;
    private int y;

    private EditorSurface editorSurface;

    private int size;

    Bitmap image;

    public SaveButton(EditorSurface editorSurface, int x, int y, int size){

        this.x = x;
        this.y = y;
        this.size = size;
        this.editorSurface = editorSurface;

        this.image = BitmapFactory.decodeResource(editorSurface.getResources(),
                editorSurface.getResources().getIdentifier("save_button" , "drawable",
                        editorSurface.context.getPackageName()
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
