package com.dsta.CNYBackend.Game;

import java.util.TimerTask;

public class GameTimerTask extends TimerTask {
    GameComponent gameComponent;
    int question;
    GameState.State state;


    public GameTimerTask(GameComponent gameComponent, int question, GameState.State state) {
        this.gameComponent = gameComponent;
        this.question = question;
        this.state = state;
    }

    @Override
    public void run() {
        if (this.gameComponent.getGameState().question == this.question) {
            this.gameComponent.endQuestion();
        }
    }
}
