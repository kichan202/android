package com.example.geoquiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.com.geoquiz.answer_shown";
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    boolean answer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        //get the answer for the current question from the intent that started this activity
        //get the answer from the extra;
        answer = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);

        //inflate
        mAnswerTextView = findViewById(R.id.answer_text_view);
        mShowAnswer = findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(answer){
                    mAnswerTextView.setText(R.string.true_button);
                }else mAnswerTextView.setText(R.string.false_button);

                setAnswerShownResult(true);


            }
        });

    }


    //did the user peep the answer?
    private void setAnswerShownResult(boolean isAnswerShown){
       Intent data = new Intent();
       data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
       setResult(RESULT_OK, data);
       //can decide to finish the activity manually
        //finish();
    }

    //use this intent maker to start this activity
    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        return intent;
    }

    //called by the parent activity to check if the user peeped
    public static boolean wasAnswerShown(Intent intent){
        return intent.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }
}
