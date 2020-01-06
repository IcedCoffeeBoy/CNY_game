package com.dsta.CNYBackend.Game;

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

    public Boolean checkExistingGame() {
        GameState gameState = this.gameComponent.getGameState();
        if (gameState.getQuestion() != 0) {
            return true;
        } else {
            return false;
        }
    }

}
