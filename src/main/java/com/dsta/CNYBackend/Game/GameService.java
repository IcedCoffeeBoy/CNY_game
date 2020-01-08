package com.dsta.CNYBackend.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    @Autowired
    GameComponent gameComponent;

    public void startGame() {
        this.gameComponent.start();
    }

    public void endGame() {
        this.gameComponent.end();
    }

    public void nextQuestion() {
        this.gameComponent.nextQuestion();
    }

    public void resetGame(){this.gameComponent.reset();}

    public void openGame(){this.gameComponent.open();}

    public Boolean checkExistingGame() {
        GameState gameState = this.gameComponent.getGameState();
        if (gameState.getQuestion() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public void setGameTimer(int timer) {
        this.gameComponent.setTimer(timer);
    }

    public GameState getGameState() {
        return this.gameComponent.getGameState();
    }

}
