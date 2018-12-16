package com.example.min0105.jazzjackrabbit;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Level {

    //  private Drawable background;

    private GameSurface gameSurface;

    private ArrayList<GameBlock> blocks = new ArrayList<>();

    private ArrayList<Bullet> bullets = new ArrayList<>();

    private ArrayList<Enemy> enemies = new ArrayList<>();

    //private boolean backgroundDrawn = false;

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

    public boolean isInBlocks(int x, int y) {
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).isInBlock(x, y)) {
                gameSurface.getPlayer().setCollidingBlock(blocks.get(i).getX(), blocks.get(i).getY());
                return true;
            }

        }
        return false;
    }

    public boolean isBulletInEnemies(int x, int y, int damage) {
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).touchingEnemy(x, y)) {
                enemies.get(i).hit(damage);
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
}
