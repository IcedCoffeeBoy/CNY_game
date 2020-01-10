package com.dsta.CNYBackend.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Service
@Transactional
public class GameService {
    private GameComponent gameComponent;
    private EntityManager em;

    @Autowired
    public GameService(GameComponent gameComponent, EntityManager entityManager) {
        this.gameComponent = gameComponent;
        this.em = entityManager;
    }

    public void startGame() {
        this.gameComponent.start();
    }

    public void endGame() {
        this.gameComponent.end();
    }

    public void nextQuestion() {
        this.gameComponent.nextQuestion();
    }

    public void resetGame() {
        Query q1 = this.em.createQuery("DELETE FROM Answer a");
        Query q2 = this.em.createQuery("DELETE FROM Poll p");
        Query q3 = this.em.createQuery("UPDATE User u SET u.score  = 0 ");

        q1.executeUpdate();
        q2.executeUpdate();
        q3.executeUpdate();

        this.gameComponent.reset();
    }

    public void openGame() {
        this.gameComponent.open();
    }

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
