package com.dsta.CNYBackend.game;

import java.util.TimerTask;

public class GameTimerTask extends TimerTask {
    GameComponent gameComponent;
    int question;
    GameState.QuestionState questionState;


    public GameTimerTask(GameComponent gameComponent, int question, GameState.QuestionState questionState) {
        this.gameComponent = gameComponent;
        this.question = question;
        this.questionState = questionState;
    }

    @Override
    public void run() {
        if (this.gameComponent.getGameState().getQuestion() == this.question) {
            this.gameComponent.endQuestion();
        }
    }
}
