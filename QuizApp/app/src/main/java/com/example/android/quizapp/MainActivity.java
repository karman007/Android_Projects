package com.example.android.quizapp;


import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CheckBox checkboxOne, checkboxTwo, checkboxThree;
    LinearLayout checkbox_layout;
    EditText answer_field;
    TextView questionNumbers, questions;
    RadioButton answerChoiceOne, answerChoiceTwo, answerChoiceThree, answerChoiceFour;
    Button next, reset;
    int count = 1;
    double score = 0;
    int QuestionNumber = -1;

    /* source of the questions and answers http://www.quizfreak.co.uk/can-you-answer-13-trivia-questions-from-tvs-the-chase/results.html#summary*/
    String[] questionsList = {"Mariah Carey and Enrique Iglesias have both had hits with which one word title?",
            "Where is Adeliade located?",
            "A cheap café or restaurant is often referred to as what?",
            "In the 1990s, Sir Alan Sugar was chairman of which football club?",
            "Which of these tennis Grand Slam tournaments is not played on hard courts?",
            "In The Flintstones, who is Barney’s wife?",
            "In what country is the city of Hyderabad?",
            "Which Welsh singer is a judge on The Voice?",
            "Which of these is also St Stephen’s Day?",
            "Named after a Dukes of Hazzard TV character, what are ‘Daisy Dukes’?"};

    String[][] possibelAnswers = {{"Dreamlover", "Hero", "Tonight"}, {"Michael Jackson", "Bryan Adams", "Mick Jagger"},
            {"A greasy fork", "A greasy knife", "A greasy spoon"}, {"Manchester United", "Sunderland", "Tottenham Hotspur"},
            {"Australian Open", "French Open", "US Open"}, {"Betty", "Wilma", "Winnie"},
            {"Iraq", "India", "Israel"}, {"Cerys Matthews", "Kelly Jones", "Tom Jones"},
            {"Boxing Day", "Halloween", "New Year's Eve"}, {"Cowboy Boots", "Denim Shorts", "Giant ring earrings"}};

    String[] answerList = {"2", "1", "3", "3", "2", "1", "2", "3", "1", "2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Edittext view*/
        answer_field = (EditText) findViewById(R.id.name_textfield);

        /*text views*/
        questionNumbers = (TextView) findViewById(R.id.question_number_text_view);
        questions = (TextView) findViewById(R.id.question_text_view);

        /*radio buttons*/
        answerChoiceOne = (RadioButton) findViewById(R.id.radio_1);
        answerChoiceTwo = (RadioButton) findViewById(R.id.radio_2);
        answerChoiceThree = (RadioButton) findViewById(R.id.radio_3);
        answerChoiceFour = (RadioButton) findViewById(R.id.radio_4);

        /*normal buttons*/
        next = (Button) findViewById(R.id.next_button);
        reset = (Button) findViewById(R.id.reset_button);

        /*checkboxes*/
        checkboxOne   = (CheckBox) findViewById(R.id.checkbox_one);
        checkboxTwo   = (CheckBox) findViewById(R.id.checkbox_two);
        checkboxThree = (CheckBox) findViewById(R.id.checkbox_three);


        /*layout*/
        checkbox_layout = (LinearLayout)findViewById(R.id.checkbox_layout);
        welcomeMessage();

    }


    /*set the display to show the welcome message for the game*/
    private void welcomeMessage() {

        answer_field.setVisibility(View.INVISIBLE);
        checkbox_layout.setVisibility(View.INVISIBLE);
        next.setText(getString(R.string.start_button));
        questionNumbers.setVisibility(View.INVISIBLE);

        questions.setText(getString(R.string.welcome_message));
        questions.setTextSize(36);
        questions.setTextColor(Color.parseColor("#D50000"));
        questions.setGravity(Gravity.CENTER_HORIZONTAL);

        answerChoiceOne.setVisibility(View.INVISIBLE);
        answerChoiceTwo.setVisibility(View.INVISIBLE);
        answerChoiceThree.setVisibility(View.INVISIBLE);
        answerChoiceFour.setVisibility(View.INVISIBLE);
    }

    /*changes the display to show the next question*/
    private void showQuestions() {

        if (count <= 10) {
            QuestionNumber++;
        }

        if (QuestionNumber == 0){
            next.setText(getString(R.string.next_button));
            questionNumbers.setVisibility(View.VISIBLE);

            answer_field.setVisibility(View.VISIBLE);
            questions.setText(questionsList[QuestionNumber]);
            questions.setTextSize(32);
            questions.setTextColor(Color.BLACK);
            questions.setGravity(Gravity.CENTER_HORIZONTAL);
        }else if (QuestionNumber == 1){
            checkbox_layout.setVisibility(View.VISIBLE);
            answer_field.setHeight(0);
            next.setText(getString(R.string.next_button));
            questionNumbers.setVisibility(View.VISIBLE);

            questions.setText(questionsList[QuestionNumber]);
            questions.setTextSize(32);
            questions.setTextColor(Color.BLACK);
            questions.setGravity(Gravity.CENTER_HORIZONTAL);

        }else {

            checkbox_layout.setVisibility(View.INVISIBLE);
            next.setText(getString(R.string.next_button));
            questionNumbers.setVisibility(View.VISIBLE);

            questions.setText(questionsList[QuestionNumber]);
            questions.setTextSize(32);
            questions.setTextColor(Color.BLACK);
            questions.setGravity(Gravity.CENTER_HORIZONTAL);


            answerChoiceOne.setVisibility(View.VISIBLE);
            answerChoiceTwo.setVisibility(View.VISIBLE);
            answerChoiceThree.setVisibility(View.VISIBLE);
            answerChoiceFour.setVisibility(View.VISIBLE);

            answerChoiceOne.setText(possibelAnswers[QuestionNumber][0]);
            answerChoiceTwo.setText(possibelAnswers[QuestionNumber][1]);
            answerChoiceThree.setText(possibelAnswers[QuestionNumber][2]);
            answerChoiceFour.setText(getString(R.string.unknown_answer));

            answerChoiceOne.setTypeface(null, Typeface.ITALIC);
            answerChoiceTwo.setTypeface(null, Typeface.ITALIC);
            answerChoiceThree.setTypeface(null, Typeface.ITALIC);
            answerChoiceFour.setTypeface(null, Typeface.ITALIC);

            answerChoiceOne.setTextColor(Color.BLACK);
            answerChoiceTwo.setTextColor(Color.BLACK);
            answerChoiceThree.setTextColor(Color.BLACK);
            answerChoiceFour.setTextColor(Color.BLACK);

            answerChoiceOne.setTextSize(24);
            answerChoiceTwo.setTextSize(24);
            answerChoiceThree.setTextSize(24);
            answerChoiceFour.setTextSize(24);
        }
    }

    /*shows the final score for the quiz*/
    private void showScore() {

        next.setText(getString(R.string.start_button));
        questionNumbers.setVisibility(View.INVISIBLE);

        questions.setText(getString(R.string.score_message, "" + score));
        Toast.makeText(this,"Score: " + score, Toast.LENGTH_SHORT).show();
        questions.setTextSize(36);
        questions.setTextColor(Color.parseColor("#D50000"));
        questions.setGravity(Gravity.CENTER_HORIZONTAL);

        answerChoiceOne.setVisibility(View.INVISIBLE);
        answerChoiceTwo.setVisibility(View.INVISIBLE);
        answerChoiceThree.setVisibility(View.INVISIBLE);
        answerChoiceFour.setVisibility(View.INVISIBLE);

    }

    /*checks the answer the player provided with the correct one*/
    private void check() {

        if (QuestionNumber == 0 && answer_field.getText().toString().equals("Hero")){
            score++;
        }

        if (QuestionNumber == 1){
            if (checkboxTwo.isChecked()){
                score+= 0.5;
            }
            if (checkboxThree.isChecked()){
                score+= 0.5;
            }
        }

        if ((answerList[QuestionNumber].equals("1")) && answerChoiceOne.isChecked()) {
            score++;
        }

        if ((answerList[QuestionNumber].equals("2")) && answerChoiceTwo.isChecked()) {
            score++;
        }

        if ((answerList[QuestionNumber].equals("3")) && answerChoiceThree.isChecked()) {
            score++;
        }

    }


    /*changes questions*/
    public void nextQuestion(View view) {

        if (count <= 10) {

            if (count == 1) {

                showQuestions();

                questionNumbers.setText(getString(R.string.question_number, "" + count));
            } else {
                check();
                showQuestions();
                answer_field.setText("");
                questionNumbers.setText(getString(R.string.question_number, "" + count));

                if (count == 10) {
                    next.setText(getString(R.string.done_button));
                }
            }
            count++;
        } else {
            if (count == 11) {
                check();
                count++;
            }
            showScore();
            Toast.makeText(this, "Press Reset to play again ", Toast.LENGTH_SHORT).show();

        }
        answerChoiceOne.setChecked(false);
        answerChoiceTwo.setChecked(false);
        answerChoiceThree.setChecked(false);
        answerChoiceFour.setChecked(true);

        checkboxOne.setChecked(false);
        checkboxTwo.setChecked(false);
        checkboxThree.setChecked(false);

    }

    /*resets the game*/
    public void reset(View view) {

        welcomeMessage();
        answer_field.setHeight(200);
        score = 0;
        count = 1;
        QuestionNumber = -1;

    }
}
