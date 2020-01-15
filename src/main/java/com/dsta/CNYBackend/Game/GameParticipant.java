package com.dsta.CNYBackend.game;

import com.dsta.CNYBackend.game.model.Participant;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Keep tracks of all the participants and score in the game
 */
@Component
@ApplicationScope
public class GameParticipant {
    private List<Participant> participants;
    private List<Participant> waitingParticipants;

    public GameParticipant() {
        this.participants = new ArrayList<>();
        this.waitingParticipants = new ArrayList<>();
    }

    @EventListener(SessionSubscribeEvent.class)
    public void handleSubscribe(SessionSubscribeEvent event) {
        System.out.println("Subscribed " + event.getUser().getName());
        if ("admin".equals(event.getUser().getName()) || event.getUser() == null) {
            return;
        }
        this.addParticipant(event.getUser().getName());
    }

    @EventListener(SessionDisconnectEvent.class)
    public void handleWebSocketDisconnect(SessionDisconnectEvent event) {
        if ("admin".equals(event.getUser().getName()) || event.getUser() == null) {
            return;
        }
        this.removeParticipant(event.getUser().getName());
    }

    public void reset() {
        this.participants = new ArrayList<>();
        this.waitingParticipants = new ArrayList<>();
    }

    public Boolean checkParticipant(String username) {
        for (Participant participant : this.participants) {
            if (participant.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private void addParticipant(String username) {
        Participant newParticipant = new Participant(username);
        this.participants.add(newParticipant);
        this.waitingParticipants.add(newParticipant);
    }

    private void removeParticipant(String username) {
        this.participants.removeIf(participant -> participant.getUsername().equals(username));
        this.waitingParticipants.removeIf(participant -> participant.getUsername().equals(username));
    }

    public void resetWaiting() {
        this.waitingParticipants = new ArrayList<>(this.participants);
    }

    public void removeFromWaiting(String username) {
        this.waitingParticipants.removeIf(participant -> participant.getUsername().equals(username));
    }

    public List<Participant> getParticipants() {
        return this.participants;
    }

    public List<Participant> getWaiting() {
        return this.waitingParticipants;
    }


}
