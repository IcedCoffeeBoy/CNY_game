package com.dsta.CNYBackend.user.model;

public class UserResponse {
    private Integer question;
    private Long choice;

    public UserResponse() {
    }

    public UserResponse(Integer question, Long choice) {
        this.question = question;
        this.choice = choice;
    }

    public Integer getQuestion() {
        return question;
    }

    public void setQuestion(Integer question) {
        this.question = question;
    }

    public Long getChoice() {
        return choice;
    }

    public void setChoice(Long choice) {
        this.choice = choice;
    }
}
