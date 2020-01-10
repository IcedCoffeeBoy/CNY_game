package com.dsta.CNYBackend.user.model;

public class UserRank {
    private String username;
    private Integer rank;

    public UserRank(String username, Integer rank) {
        this.username = username;
        this.rank = rank;
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
}
