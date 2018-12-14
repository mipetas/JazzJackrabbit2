package com.example.min0105.jazzjackrabbit;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class Level {

    private Drawable background;

    private GameSurface gameSurface;

    private ArrayList<GameBlock> blocks = new ArrayList<>();

    private boolean backgroundDrawn = false;

    public Level(GameSurface gameSurface){
       // this.background =  background;
       // background.setBounds(0, 0, 1920, 1080);
        this.gameSurface = gameSurface;

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


    public boolean isInBlocks(int x, int y){
        for(int i = 0; i < blocks.size(); i++){
            if(blocks.get(i).isInBlock(x, y))
            {
                gameSurface.getPlayer().setCollidingBlock(blocks.get(i).getX(), blocks.get(i).getY());
                return true;
            }

        }
        return false;
    }

}
