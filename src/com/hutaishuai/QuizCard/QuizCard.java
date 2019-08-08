package com.hutaishuai.QuizCard;


public class QuizCard {
    String questuon;
    String answer;

    public QuizCard(String q,String a){
        questuon = q;
        answer = a;
    }

    public String getQuestion(){
        return questuon;
    }

    public String getAnswer(){
        return answer;
    }
}
