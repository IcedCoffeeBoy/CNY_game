package com.dsta.CNYBackend.User;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> createUser(
            @JsonView(UserResourceViews.Create.class) @RequestBody UserResource user
    ) {
        String username = user.getUsername();
        if (this.userService.checkExistingUser(username)) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "There is an existing username");
            return ResponseEntity.badRequest().body(map);
        }

        User newUser = this.userService.createUser(username);
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", newUser.getUsername());
        return ResponseEntity.ok(map);
    }
}
