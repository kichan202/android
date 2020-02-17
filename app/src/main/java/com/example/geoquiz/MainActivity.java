package com.example.geoquiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String ARRAY_INDEX = "arrayindex";
    private static final int REQUEST_CODE_CHEAT = 0;


    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;


    private final Question[] mQuestionBank = new Question[]  {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true)
    };
    private ArrayList<Integer> mAnsweredArray = new ArrayList<>();
    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    private int mNumberOfCorrectAnswers = 0;
    private int mNumberOfWrongAnswers = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mAnsweredArray = savedInstanceState.getIntegerArrayList(ARRAY_INDEX);
        }



        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrueButton.setClickable(false);
                mFalseButton.setClickable(false);
                checkAnswer(true);
                mAnsweredArray.add(mCurrentIndex);
            }
        });

        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrueButton.setClickable(false);
                mFalseButton.setClickable(false);
                checkAnswer(false);
                mAnsweredArray.add(mCurrentIndex);

            }
        });

        //set up question text view
        mQuestionTextView = findViewById(R.id.question_text_view);

        //next button
        ImageButton nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
               updateQuestion();
            }
        });

        //previous button
        ImageButton previousButton = findViewById(R.id.previous_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = ((mCurrentIndex - 1) + mQuestionBank.length) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion();
            }
        });

        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(MainActivity.this,answerIsTrue);
               startActivity(intent);
            }
        });
        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode != Activity.RESULT_OK) {
            //first check to make sure we got a go result
            return;
        }



        if(requestCode == REQUEST_CODE_CHEAT){
            if(data == null){
                return;
            }
            if(resultCode == RESULT_OK){
                mIsCheater = CheatActivity.wasAnswerShown(data);

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPaused() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"onSaveInstanceState()");
        outState.putInt(KEY_INDEX,mCurrentIndex);
        outState.putIntegerArrayList(ARRAY_INDEX, mAnsweredArray);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answer = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int msgResId;

        if(mIsCheater) {
            msgResId = R.string.judgement_toast;
        }else{


            if(answer == userPressedTrue){
                mNumberOfCorrectAnswers += 1;
                msgResId = R.string.correct_toast;

            }else{
                mNumberOfWrongAnswers += 1;
                msgResId = R.string.incorrect_toast;
            }


        }

        Toast.makeText(this,msgResId,Toast.LENGTH_SHORT).show();

        if(mNumberOfCorrectAnswers + mNumberOfWrongAnswers == mQuestionBank.length){
            mTrueButton.setClickable(false);
            mFalseButton.setClickable(false);

            double percentage = ((double)mNumberOfCorrectAnswers / (double)mQuestionBank.length) * 100;

            Toast.makeText(MainActivity.this,getString(R.string.number_of_correct_answers) + mNumberOfCorrectAnswers + "\n" +
                    getString(R.string.percentage_of_answers, percentage),Toast.LENGTH_LONG).show();

        }

    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        if(mAnsweredArray.contains(mCurrentIndex)){
            mTrueButton.setClickable(false);
            mFalseButton.setClickable(false);
        }else{
            mFalseButton.setClickable(true);
            mTrueButton.setClickable(true);
        }
    }


}
