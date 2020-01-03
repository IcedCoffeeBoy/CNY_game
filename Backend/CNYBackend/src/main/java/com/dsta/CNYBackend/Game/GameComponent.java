package com.dsta.CNYBackend.Game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameComponent {
    @Autowired
    GameListener listener;
    GameState gameState;

    public GameComponent() {
        this.gameState = new GameState(0, GameState.State.END);
    }

    public GameState start() {
        this.gameState = new GameState(1, GameState.State.START);
        this.listener.sendGameState(this.gameState);
        return this.gameState;
    }

    public GameState end() {
        this.gameState = new GameState(0, GameState.State.END);
        this.listener.sendGameState(this.gameState);
        return this.gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void endQuestion() {
        this.gameState.endQuestion();
        this.listener.sendGameState(this.gameState);
    }

    public void nextQuestion() {
        this.gameState.nextQuestion();
        this.listener.sendGameState(this.gameState);
    }


}
