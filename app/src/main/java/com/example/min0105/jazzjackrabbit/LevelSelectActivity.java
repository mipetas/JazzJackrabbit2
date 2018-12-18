package com.example.min0105.jazzjackrabbit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.min0105.jazzjackrabbit.LevelEditor.EditorActivity;

public class LevelSelectActivity extends Activity {

    private Button level1B;
    private Button level2B;
    private Button level3B;
    private Button customLevelB;
    private Button resetButton;
    private TextView levelSelectText;

    SharedPreferences mySharedPref;
    SharedPreferences.Editor mySharedPrefEditor;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_level_select);

        mySharedPref = getSharedPreferences("LEVELS", MODE_PRIVATE);
        mySharedPrefEditor = mySharedPref.edit();
        intent = new Intent(getApplicationContext(), PlayActivity.class);

        level1B = findViewById(R.id.level1button);
        level2B = findViewById(R.id.level2button);
        level3B = findViewById(R.id.level3button);
        customLevelB = findViewById(R.id.customLevelbutton);
        resetButton = findViewById(R.id.resetButton);
        levelSelectText = findViewById(R.id.LevelSelectText);


        level1B.setText("LEVEL 1");
        level2B.setText("LEVEL 2");
        level3B.setText("LEVEL 3");
        customLevelB.setText("CUSTOM LEVEL");
        resetButton.setText("RESET");
        levelSelectText.setText("LEVEL SELECT");
        if(mySharedPref.getInt("level1.txt", 0) == 0)
            level2B.setEnabled(false);
        if(mySharedPref.getInt("level2.txt", 0) == 0)
            level3B.setEnabled(false);

        level1B.setOnClickListener(levelSelectedClickListener);
        level2B.setOnClickListener(levelSelectedClickListener);
        level3B.setOnClickListener(levelSelectedClickListener);
        customLevelB.setOnClickListener(levelSelectedClickListener);
        resetButton.setOnClickListener(resetClickListener);


    }


    Button.OnClickListener levelSelectedClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

                    switch (v.getId()) {

                        case R.id.level1button:
                            Toast.makeText(LevelSelectActivity.this, "Level1",
                                    Toast.LENGTH_LONG).show();
                            mySharedPrefEditor.putString("currLevel", "level1.txt");
                            mySharedPrefEditor.commit();
                            startActivity(intent);
                            break;

                        case R.id.level2button:
                            Toast.makeText(LevelSelectActivity.this, "Level2",
                                    Toast.LENGTH_LONG).show();
                            mySharedPrefEditor.putString("currLevel", "level2.txt");
                            mySharedPrefEditor.commit();
                            startActivity(intent);
                            break;

                        case R.id.level3button:
                            Toast.makeText(LevelSelectActivity.this, "Level3",
                                    Toast.LENGTH_LONG).show();
                            mySharedPrefEditor.putString("currLevel", "level3.txt");
                            mySharedPrefEditor.commit();
                            startActivity(intent);
                            break;

                        case R.id.customLevelbutton:
                            Toast.makeText(LevelSelectActivity.this, "CustomLevel",
                                    Toast.LENGTH_LONG).show();
                            mySharedPrefEditor.putString("currLevel", "custom.txt");
                            mySharedPrefEditor.commit();
                            startActivity(intent);
                            break;

                        default:
                            break;
                    }

        }
    };

    Button.OnClickListener resetClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            mySharedPrefEditor.putInt("level1.txt", 0);
            mySharedPrefEditor.putInt("level2.txt", 0);
            mySharedPrefEditor.commit();

            level2B.setEnabled(false);
            level3B.setEnabled(false);

        }
    };

}