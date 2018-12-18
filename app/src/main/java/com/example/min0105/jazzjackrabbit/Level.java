package com.example.min0105.jazzjackrabbit;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.min0105.jazzjackrabbit.GameObjects.Block;
import com.example.min0105.jazzjackrabbit.GameObjects.Bullet;
import com.example.min0105.jazzjackrabbit.GameObjects.Enemy;
import com.example.min0105.jazzjackrabbit.GameObjects.GameBlock;
import com.example.min0105.jazzjackrabbit.GameObjects.Turtle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Level {


    private GameSurface gameSurface;

    private ArrayList<GameBlock> blocks = new ArrayList<>();

    private ArrayList<Bullet> bullets = new ArrayList<>();

    private ArrayList<Enemy> enemies = new ArrayList<>();

    private int finishX, finishY;

    public Level(GameSurface gameSurface) {
        // this.background =  background;
        // background.setBounds(0, 0, 1920, 1080);
        this.gameSurface = gameSurface;

    }

    /*public void drawBackground(Canvas canvas){
        if(!backgroundDrawn)
        {
            background.draw(canvas);
            backgroundDrawn = true;
        }
    } */

    public void draw(Canvas canvas, int xDiff, int yDiff) {
        drawBlocks(canvas, xDiff, yDiff);
        drawBullets(canvas, xDiff, yDiff);
        drawEnemies(canvas, xDiff, yDiff);
    }

    private void drawBlocks(Canvas canvas, int xDiff, int yDiff) {
        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).draw(canvas, xDiff, yDiff);
        }
    }

    private void drawBullets(Canvas canvas, int xDiff, int yDiff) {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(canvas, xDiff, yDiff);
        }
    }

    private void drawEnemies(Canvas canvas, int xDiff, int yDiff) {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(canvas, xDiff, yDiff);
        }
    }

    public void update() {

        updateBullets();
        updateEnemies();

    }


    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void addBlock(GameBlock block) {
        blocks.add(block);
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public boolean isPlayerInBlocks(int x, int y) {
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).isInBlock(x, y)) {
                gameSurface.getPlayer().setCollidingBlock(blocks.get(i).getX(), blocks.get(i).getY());
                return true;
            }

        }
        return false;
    }

    public boolean isInBlocks(int x, int y) {
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).isInBlock(x, y)) {
                return true;
            }

        }
        return false;
    }

    public boolean isInFinish(int x, int y) {
        if(x >= finishX &&
                x <= finishX + gameSurface.BLOCK_SIZE &&
                y >= finishY &&
                y <= finishY + gameSurface.BLOCK_SIZE
                ){
            return true;
        }
        else
            return false;
    }

    private boolean isBulletInEnemies(int x, int y, int damage) {
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).touchingEnemy(x, y)) {
                enemies.get(i).hit(damage);
                return true;
            }

        }
        return false;
    }

    public boolean isInEnemies(int x, int y) {
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).touchingEnemy(x, y)) {
                return true;
            }

        }
        return false;
    }

    private void updateBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).destroy)
                bullets.remove(i);
            bullets.get(i).update();
            if (isInBlocks(bullets.get(i).getX(), bullets.get(i).getY()))
                bullets.remove(i);
            if (isBulletInEnemies(bullets.get(i).getX(), bullets.get(i).getY(), bullets.get(i).getDamage()))
                bullets.remove(i);
        }
    }

    private void updateEnemies() {

        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isDead())
                enemies.remove(i);
            enemies.get(i).update();
        }

    }

    public void initLevel(String levelName){

        BufferedReader reader = null;

        try {
            File file;
            if(levelName == "custom.txt")
            {
                file  = new File(gameSurface.context.getFilesDir(), levelName);
                reader = new BufferedReader(new FileReader(file));
            }

            else
                reader = new BufferedReader(
                    new InputStreamReader(gameSurface.context.getAssets().open(levelName), "UTF-8"));


            String line;
            int i = 0;
            int x;
            int y;
            while ((line = reader.readLine()) != null) {
                int j = 0;
                y = i * gameSurface.BLOCK_SIZE;
                String[] parts = line.split(" ");
                for(String item : parts){
                    x = j * gameSurface.BLOCK_SIZE;

                    if(!item.equals("0") && !item.equals("X"))
                    {
                        if(item.equals("finish"))
                        {
                            finishX = x;
                            finishY = y;
                        }
                        if(item.equals("rabbit"))
                            gameSurface.setStartingPosition(x, y);
                        else if(item.charAt(0) != 'E')
                        {

                            addBlock(new Block(BitmapFactory.decodeResource(gameSurface.getResources(),
                                    gameSurface.getResources().getIdentifier(item , "drawable", gameSurface.context.getPackageName())),
                                    x, y, gameSurface.BLOCK_SIZE
                            ));
                        }
                        else
                        {
                            addEnemy(new Turtle(gameSurface,BitmapFactory.decodeResource(gameSurface.getResources(),
                                    gameSurface.getResources().getIdentifier(item.substring(1) , "drawable", gameSurface.context.getPackageName())),
                                    1, 1, x, y, gameSurface.PLAYER_WIDTH*3, gameSurface.PLAYER_HEIGHT
                            ));
                        }
                    }

                    j++;
                }

                i++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
