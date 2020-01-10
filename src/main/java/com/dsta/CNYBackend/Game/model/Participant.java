package com.dsta.CNYBackend.game.model;

public class Participant {
    private String username;

    public Participant() {
    }

    public Participant(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
