package com.dsta.CNYBackend.Game;

public class GameState {
    enum State {
        START,
        END
    }

    int question;
    State state;

    public GameState() {
        this.question = 0;
        this.state = State.START;
    }

    public GameState(int question, State state) {
        this.question = question;
        this.state = state;
    }

    public void endQuestion() {
        this.state = State.END;
    }

    public void nextQuestion() {
        this.question++;
        this.state = State.START;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}


