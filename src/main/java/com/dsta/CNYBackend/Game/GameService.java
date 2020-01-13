package com.dsta.CNYBackend.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GameService {
    private GameComponent gameComponent;
    private GameParticipant gameParticipant;
    private EntityManager em;

    @Autowired
    public GameService(GameComponent gameComponent, GameParticipant gameParticipant, EntityManager em) {
        this.gameComponent = gameComponent;
        this.gameParticipant = gameParticipant;
        this.em = em;
    }

    public void startGame() {
        this.gameComponent.start();
        this.gameParticipant.getAllParticipant();
    }

    public void endGame() {
        this.gameComponent.end();
    }

    public void nextQuestionState() {
        if (this.gameComponent.getGameState().questionState.equals(GameState.QuestionState.START)) {
            this.gameComponent.endQuestion();
        } else {
            this.gameComponent.nextQuestion();
            this.gameParticipant.resetWaiting();
        }
    }

    public void resetGame() {
        Query q1 = this.em.createQuery("DELETE FROM Answer a");
        Query q2 = this.em.createQuery("DELETE FROM PollSummary p ");
        Query q3 = this.em.createQuery("DELETE FROM Poll p");
        Query q4 = this.em.createQuery("DELETE FROM User u WHERE u.username!='admin'");

        q1.executeUpdate();
        q2.executeUpdate();
        q3.executeUpdate();
        q4.executeUpdate();

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

    public List<String> getWaitingParticipants() {
        return this.gameParticipant.getWaiting().stream().map(participant -> participant.getUsername()).collect(Collectors.toUnmodifiableList());
    }

    public void setGameTimer(int timer) {
        this.gameComponent.setTimer(timer);
    }

    public GameState getGameState() {
        return this.gameComponent.getGameState();
    }

}
