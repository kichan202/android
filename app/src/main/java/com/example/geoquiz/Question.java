package com.example.geoquiz;

class Question {
    private final int mTextResId;
    private final boolean mAnswerTrue;



    public int getTextResId() {
        return mTextResId;
    }
/*
    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }*/

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

/*    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }*/


    public Question(int textResId, boolean answerTrue){
        mAnswerTrue = answerTrue;
        mTextResId = textResId;
    }
}
