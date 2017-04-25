package com.example.android.scorekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends AppCompatActivity {

    int score_Team_A = 0;
    int score_Team_B = 0;
    int out_Team_A = 0;
    int out_Team_B = 0;
    TextView team_A_runs;
    TextView team_B_runs;
    TextView team_A_outs;
    TextView team_B_outs;
    EditText teamARuns;
    EditText teamBRuns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        team_A_runs = (TextView)findViewById(R.id.score_text_view_A);
        team_B_runs = (TextView)findViewById(R.id.score_text_view_B);
        team_A_outs = (TextView)findViewById(R.id.outs_text_view_A);
        team_B_outs = (TextView)findViewById(R.id.outs_text_view_B);

        teamARuns = (EditText)findViewById(R.id.runs_taken_A);
        teamBRuns = (EditText)findViewById(R.id.runs_taken_B);

    }

    public void sixRuns_A(View view){
        if (out_Team_A <= 9){
            score_Team_A = score_Team_A + 6;
            displayScoreA(score_Team_A);
        }
    }

    public void fourRuns_A(View view){
        if (out_Team_A <= 9){
            score_Team_A = score_Team_A + 4;
            displayScoreA(score_Team_A);
        }
    }

    public void runs_A(View view){
        if (out_Team_A <= 9){
            score_Team_A = score_Team_A + Integer.parseInt(teamARuns.getText().toString());
            displayScoreA(score_Team_A);
            teamARuns.setText("0");
        }
    }

    public void sixRuns_B(View view){
        if (out_Team_B <= 9){
            score_Team_B = score_Team_B + 6;
            displayScoreB(score_Team_B);
        }
    }

    public void fourRuns_B(View view){
        if (out_Team_B <= 9){
            score_Team_B = score_Team_B + 4;
            displayScoreB(score_Team_B);
        }
    }

    public void runs_B(View view){
        if (out_Team_B <= 9){
            score_Team_B = score_Team_B + Integer.parseInt(teamBRuns.getText().toString());
            displayScoreB(score_Team_B);
            teamBRuns.setText("0");
        }
    }

    public void out_A(View view){
        if (out_Team_A < 9) {
            displayOutA(++out_Team_A);
        } else {
            out_Team_A++;
            Toast.makeText(this,"Team A Out",Toast.LENGTH_SHORT).show();
        }
    }

    public void out_B(View view){
        if (out_Team_B < 9) {
            displayOutB(++out_Team_B);
        } else {
            out_Team_B++;
            Toast.makeText(this,"Team B Out",Toast.LENGTH_SHORT).show();
        }
    }

    public void reset(View view){
        score_Team_A = 0;
        score_Team_B = 0;
        out_Team_A = 0;
        out_Team_B = 0;
        displayScoreA(score_Team_A);
        displayScoreB(score_Team_B);
        displayOutB(out_Team_B);
        displayOutA(out_Team_A);
        Toast.makeText(this,"Scores reset",Toast.LENGTH_SHORT).show();
    }

    private void displayOutA(int out){
        team_A_outs.setText(String.valueOf(out));
    }

    private void displayOutB(int out){
        team_B_outs.setText(String.valueOf(out));
    }

    private void displayScoreA(int score){
        team_A_runs.setText(String.valueOf(score));
    }

    private void displayScoreB(int score){
        team_B_runs.setText(String.valueOf(score));
    }
}
