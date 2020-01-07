package com.dsta.CNYBackend.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Timer;

@Component
public class GameComponent {
    @Autowired
    GameListener listener;
    GameState gameState;
    Timer scheduleTimer;
    int timer = 60;

    public GameComponent() {
        this.gameState = new GameState();
    }

    public GameState start() {
        this.gameState.startGame();
        this.nextQuestion();
        return this.gameState;
    }

    public GameState end() {
        this.gameState.endGame();
        this.scheduleTimer.cancel();
        this.listener.sendGameState(this.gameState);
        return this.gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void endQuestion() {
        this.gameState.endQuestion();
        this.scheduleTimer.cancel();
        this.listener.sendGameState(this.gameState);
    }

    public void nextQuestion() {
        this.gameState.nextQuestion();
        this.scheduleTimer = new Timer();
        this.scheduleTimer.schedule(new GameTimerTask(this, this.gameState.getQuestion(), this.gameState.getQuestionState()), 5000);
        this.listener.sendGameState(this.gameState);
    }


    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getCurrentGameSessionId() {
        return this.gameState.gameSessionId;
    }
}
