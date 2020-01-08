package com.dsta.CNYBackend.game;

import java.util.Random;

public class GameState {
    enum QuestionState {
        START,
        END
    }

    enum ProgressState {
        EMPTY,
        WAITING,
        PLAYING,
        END
    }

    final int gameSessionId = getRandom();
    int question;
    QuestionState questionState;
    ProgressState progress;

    public GameState() {
        this.question = 0;
        this.questionState = QuestionState.START;
        this.progress = ProgressState.EMPTY;
    }

    public GameState(int question, QuestionState questionState) {
        this.question = question;
        this.questionState = questionState;
    }

    public void setProgressToWait() {
        this.progress = ProgressState.WAITING;
    }

    public void setProgressToPlaying() {
        this.progress = ProgressState.PLAYING;
    }

    public void endGameAndSetProgressToEnd() {
        this.setQuestion(0);
        this.setQuestionState(GameState.QuestionState.END);
        this.progress = ProgressState.END;
    }

    public void endQuestion() {
        this.questionState = QuestionState.END;
    }

    public void nextQuestion() {
        this.question++;
        this.questionState = QuestionState.START;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public QuestionState getQuestionState() {
        return questionState;
    }

    public void setQuestionState(QuestionState questionState) {
        this.questionState = questionState;
    }

    private int getRandom() {
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(100000);
    }

}


