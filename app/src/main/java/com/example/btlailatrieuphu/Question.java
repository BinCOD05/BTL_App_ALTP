package com.example.btlailatrieuphu;

import java.util.List;

public class Question {
    private String questionText ;
    private List<String> answer;

    private int correctAns ;
    public Question( String questionText, List<String>answer,int correctAns) {
        this.answer = answer;
        this.questionText = questionText;
        this.correctAns = correctAns;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public int getCorrectAns() {
        return correctAns;
    }
}
