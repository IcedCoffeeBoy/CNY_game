package com.dsta.CNYBackend.game;

import com.dsta.CNYBackend.game.model.Participant;
import com.dsta.CNYBackend.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Keep tracks of all the participants and score in the game
 */
@Component
public class GameParticipant {
    private UsersService usersService;
    private List<Participant> participants;
    private List<Participant> waitingParticipants;

    @Autowired
    public GameParticipant(UsersService usersService) {
        this.usersService = usersService;
        this.getAllParticipant();
    }

    // Execute when the game starts
    public void getAllParticipant() {
        this.participants = this.usersService.getAllParticipants();
        this.participants.removeIf(participant -> participant.getUsername().equals("admin"));
        this.resetWaiting();
    }

    public void resetWaiting() {
        this.waitingParticipants =  new ArrayList<>(this.participants);
    }

    public void removeFromWaiting(String username) {
        this.waitingParticipants.removeIf(participant -> participant.getUsername().equals(username));
    }

    public List<Participant> getWaiting(){
        return this.waitingParticipants;
    }
}
