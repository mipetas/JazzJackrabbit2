package com.example.min0105.jazzjackrabbit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.min0105.jazzjackrabbit.LevelEditor.EditorActivity;

public class MainActivity extends Activity {

    private Button playButton;
    private Button levelEditorButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        playButton = findViewById(R.id.playButton);
        levelEditorButton = findViewById(R.id.levelEditorButton);

        playButton.setOnTouchListener(playTouchListener);
        levelEditorButton.setOnTouchListener(levelEditorTouchListener);
    }


    Button.OnTouchListener playTouchListener = new Button.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    Intent intent = new Intent(getApplicationContext(), LevelSelectActivity.class);
                    startActivity(intent);
                    break;
                }

            }
            return false;
        }
    };
    Button.OnTouchListener levelEditorTouchListener = new Button.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
                    startActivity(intent);
                    break;
                }

            }
            return false;
        }
    };


}