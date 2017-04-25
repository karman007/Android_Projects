package com.example.android.courtcounter;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.VectorEnabledTintResources;
import android.view.View;
import android.widget.TextView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity {

    int score_A = 0;
    int score_B = 0;
    TextView A;
    TextView B;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       A = (TextView)findViewById(R.id.score_text_view);
       B = (TextView)findViewById(R.id.score_text_view_2);

    }

    public void threePoints_A(View view){
        score_A = score_A + 3;
        displayScoreA(score_A);
    }

    public void twoPoints_A(View view){
        score_A = score_A + 2;
        displayScoreA(score_A);
    }

    public void freeThrow_A(View view){
        score_A = score_A + 1;
        displayScoreA(score_A);
    }

    public void threePoints_B(View view){
        score_B = score_B + 3;
        displayScoreB(score_B);
    }

    public void twoPoints_B(View view){
        score_B = score_B + 2;
        displayScoreB(score_B);
    }

    public void freeThrow_B(View view){
        score_B = score_B + 1;
        displayScoreB(score_B);
    }

    public void reset(View view){
        score_A = 0;
        score_B = 0;
        displayScoreA(score_A);
        displayScoreB(score_B);
    }

    private void displayScoreA(int score){
        A.setText(String.valueOf(score));
    }

    private void displayScoreB(int score){
        B.setText(String.valueOf(score));
    }
}
