package com.example.min0105.jazzjackrabbit;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class EditorGrid {

    private int width;
    private int height;
    private int blockSize;

    private GridObject[][] objectArray;

    private EditorSurface editorSurface;

    Paint linePaint = new Paint();


    public EditorGrid(EditorSurface editorSurface, int width, int height, int blockSize) {

        this.width = width;
        this.height = height;
        this.blockSize = blockSize;
        this.editorSurface = editorSurface;
        objectArray = new GridObject[width / blockSize][height / blockSize];

        linePaint.setColor(Color.WHITE);

        initItems();

    }

    public void draw(Canvas canvas, int xDiff, int yDiff){
        drawItems(canvas, xDiff, yDiff);
        drawLines(canvas, xDiff, yDiff);
    }

    private void drawLines(Canvas canvas, int xDiff, int yDiff) {
        for (int i = 0; i < width; i += blockSize)
            canvas.drawLine(i - xDiff , 0 - yDiff, i - xDiff, editorSurface.getHeight() + yDiff, linePaint);
        for (int i = 0; i < height; i += blockSize)
            canvas.drawLine(0 - xDiff, i - yDiff, editorSurface.getWidth() + xDiff, i - yDiff , linePaint);
    }

    private void drawItems(Canvas canvas, int xDiff, int yDiff) {

        for(int x = 0; x < objectArray.length; x++)
            for(int y = 0; y < objectArray[x].length; y++){
                if(!objectArray[x][y].getString().equals("0") && !objectArray[x][y].getString().equals("X"))
                    canvas.drawBitmap(objectArray[x][y].getBitmap(), x * editorSurface.BLOCK_SIZE - xDiff, y * editorSurface.BLOCK_SIZE - yDiff, null);
            }

    }

    public void addObject(int x, int y, String object, int width, int height) {

        String objectDrawableName = object;
        if(object.charAt(0) == 'E')
            objectDrawableName = object.substring(1);

        if(object.equals("0")){
            objectArray[x][y] = new GridObject(null, 1, 1, object);
        }
        else
            objectArray[x][y] = new GridObject( BitmapFactory.decodeResource(editorSurface.getResources(),
                    editorSurface.getResources().getIdentifier(objectDrawableName , "drawable", editorSurface.context.getPackageName())),
                    width, height, object);
    }

    private void initItems() {

        for(int x = 0; x < objectArray.length; x++)
            for(int y = 0; y < objectArray[x].length; y++){
                objectArray[x][y] = new GridObject(null, 1, 1, "0");
            }

    }

    public void removeObject(int x, int y){
        objectArray[x][y] = new GridObject(null, 1, 1, "0");
    }

    public void saveToFile(Context context) {

        BufferedWriter writer;
        try {
            File file = new File(context.getFilesDir(), "level1.txt");
            if(!file.exists())
            {
                file.createNewFile();
            }

            writer = new BufferedWriter(new FileWriter(file));

            for(int x = 0; x < objectArray.length; x++)
            {
                for (int y = 0; y < objectArray[x].length; y++)
                {
                    writer.write(objectArray[y][x].getString() + " ");
                }
                writer.newLine();
            }

            writer.close();

        }
        catch(java.io.FileNotFoundException e)
        {
            System.out.println("File Not Found");
            System.exit( 1 );
        }
        catch(java.io.IOException e)
        {
            System.out.println("something messed up");
            System.exit( 1 );
        }


    }

}
