package com.example.min0105.jazzjackrabbit;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class Level {

    private Drawable background;

    private ArrayList<GameBlock> blocks = new ArrayList<>();

    private boolean backgroundDrawn = false;

    public Level(){
       // this.background =  background;
       // background.setBounds(0, 0, 1920, 1080);

    }

    public void drawBackground(Canvas canvas){
        if(!backgroundDrawn)
        {
            background.draw(canvas);
            backgroundDrawn = true;
        }
    }

    public void drawBlocks(Canvas canvas, int xDiff, int yDiff){
        for(int i = 0; i < blocks.size(); i++){
            blocks.get(i).draw(canvas, xDiff, yDiff);
        }
    }

    public void addBlock(GameBlock block){
        blocks.add(block);
    }


    public int isInBlocks(int x, int y){
        for(int i = 0; i < blocks.size(); i++){
            if(blocks.get(i).isInBlock(x, y))
                return blocks.get(i).getY();
        }
        return 0;
    }

}
