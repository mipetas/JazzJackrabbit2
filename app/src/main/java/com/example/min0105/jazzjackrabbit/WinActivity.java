package com.example.min0105.jazzjackrabbit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WinActivity extends Activity {

    private Button playAgain;
    private Button menu;
    private TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        playAgain = findViewById(R.id.playAgainButton);
        menu = findViewById(R.id.menuButton);
        text = findViewById(R.id.gameOverText);

        playAgain.setText("PLAY AGAIN");
        menu.setText("MENU");
        text.setText("YOU WIN");

        playAgain.setOnTouchListener(playAgainTouchListener);
        menu.setOnTouchListener(menuTouchListener);

        SharedPreferences mySharedPref = getSharedPreferences("LEVELS", MODE_PRIVATE);
        SharedPreferences.Editor mySharedPrefEditor = mySharedPref.edit();
        String currLvl = mySharedPref.getString("currLevel", "level1.txt");
        mySharedPrefEditor.putInt(currLvl, 1);
        mySharedPrefEditor.commit();
    }

    Button.OnTouchListener menuTouchListener = new Button.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    break;
                }

            }
            return false;
        }
    };
    Button.OnTouchListener playAgainTouchListener = new Button.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                    startActivity(intent);
                    break;
                }

            }
            return false;
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


}
