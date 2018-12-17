package com.example.min0105.jazzjackrabbit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class ObjectMenu {

    private ArrayList<MenuItem> menuItems;

    private EditorSurface editorSurface;

    private int itemSize;

    private Paint notSelectedPaint;

    private Paint selectedPaint;

    private int selected = 0;

    private int xStart = 0;


    public ObjectMenu(EditorSurface editorSurface, int itemSize){

        this.editorSurface = editorSurface;
        this.menuItems = new ArrayList<>();
        this.itemSize = itemSize;

        this.notSelectedPaint = new Paint();
        notSelectedPaint.setColor(Color.GRAY);

        this.selectedPaint = new Paint();
        selectedPaint.setColor(Color.RED);

    }

    public void addItem(String object) {

        String objectDrawableName = object;
        if(object.charAt(0) == 'E')
            objectDrawableName = object.substring(1);

        menuItems.add(new MenuItem( Bitmap.createScaledBitmap(BitmapFactory.decodeResource(editorSurface.getResources(),
                editorSurface.getResources().getIdentifier(objectDrawableName , "drawable", editorSurface.context.getPackageName())),
                itemSize-2, itemSize-2, false),
                object));
    }

    public void draw(Canvas canvas){
        xStart = (editorSurface.getWidth() - ((menuItems.size() + 1 ) * itemSize))/2;

        for(int i = 0; i < menuItems.size() + 1; i++){
            if(i == selected)
                canvas.drawRect(i*itemSize + xStart, 0, i*itemSize + xStart + itemSize, itemSize, selectedPaint);
            else
                canvas.drawRect(i*itemSize + xStart, 0, i*itemSize + xStart + itemSize, itemSize, notSelectedPaint);
            if(i == menuItems.size()){
                canvas.drawLine(i*itemSize + xStart ,0, i*itemSize + xStart + itemSize, itemSize, selectedPaint);
                canvas.drawLine(i*itemSize + xStart + itemSize ,0, i*itemSize + xStart, itemSize, selectedPaint);
            }
            else
                canvas.drawBitmap(menuItems.get(i).getBitmap(),i*itemSize + xStart + 1, 1, null);
        }

    }

    public String getSelectedName(){
        if(selected == menuItems.size())
            return "remove";
        return menuItems.get(selected).getString();
    }

    public boolean isInObjectMenu(float xDown, float yDown) {

        if(xDown > xStart && xDown < xStart * itemSize * menuItems.size() &&
                yDown > 0 && yDown < itemSize)
            return true;
        else
            return false;
    }

    public void select(float xDown) {
        float x = xDown - xStart;
        for(selected = 0; x-itemSize>0 ;x -= itemSize)
            selected++;
    }
}
