package com.dsta.CNYBackend.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Timer;


/**
 * Manages the game
 */
@Component
public class GameComponent {
    @Autowired
    GameListener listener;
    @Autowired
    GameUtil gameUtil;
    GameState gameState;
    Timer scheduleTimer;
    int timer = 60;
    int questionSize;

    public GameComponent() {
        this.gameState = new GameState();
    }

    public GameState open() {
        this.gameState.setProgressToWait();
        this.listener.sendGameState(this.gameState);
        return this.gameState;
    }

    public GameState start() {
        this.gameState.setProgressToPlaying();
        this.questionSize = this.gameUtil.getTotalNumberOfQuestions();
        this.nextQuestion();
        return this.gameState;
    }

    public GameState end() {
        this.gameState.endGameAndSetProgressToEnd();
        this.scheduleTimer.cancel();
        this.listener.sendGameState(this.gameState);
        return this.gameState;
    }

    public GameState reset() {
        this.gameState = new GameState();
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
        this.gameUtil.addPointsToWinner(this.gameState.getQuestion());
        this.listener.sendGameState(this.gameState);
    }

    public void nextQuestion() {
        if (this.isLastQuestion()) {
            this.end();
            return;
        }
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

    private Boolean isLastQuestion() {
        int position = this.gameState.getQuestion();
        if (position == this.questionSize) {
            return true;
        } else {
            return false;
        }
    }
}
