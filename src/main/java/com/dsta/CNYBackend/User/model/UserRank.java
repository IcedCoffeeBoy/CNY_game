package com.dsta.CNYBackend.user.model;

public class UserRank {
    private String username;
    private Integer rank;
    private Integer score;

    public UserRank(String username, Integer rank, Integer score) {
        this.username = username;
        this.rank = rank;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
