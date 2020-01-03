package com.dsta.CNYBackend.User;

import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotNull;

public class UserResource {

    @JsonView({UserResourceViews.Create.class})
    private Long id;

    @NotNull(groups = {UserResourceViews.Create.class})
    @JsonView({UserResourceViews.Create.class})
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
