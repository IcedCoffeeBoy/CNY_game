package com.dsta.CNYBackend.user;

import com.dsta.CNYBackend.shared.security.JwtResponse;
import com.dsta.CNYBackend.shared.security.JwtTokenUtil;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    UsersService userDetailService;

    private int MAX_AGE_SECONDS = 3600 * 3600;

    @Autowired
    public UserController(UsersService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(
            @JsonView(UserResourceViews.Create.class) @RequestBody UserResource user,
            HttpServletResponse response
    ) {
        String username = user.getUsername();
        if (this.userDetailService.checkExistingUser(username)) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("error", "There is an existing username");
            return ResponseEntity.badRequest().body(map);
        }

        User newUser = this.userDetailService.createUser(username);
        final String token = jwtTokenUtil.generateToken(newUser);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUserUsingGET(
            @RequestParam(value = "username") String username,
            HttpServletResponse response
    ) {
        if (this.userDetailService.checkExistingUser(username)) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "There is an existing username");
            return ResponseEntity.badRequest().body(map);
        }
        User newUser = this.userDetailService.createUser(username);
        final String token = jwtTokenUtil.generateToken(newUser);
        return ResponseEntity.ok(new JwtResponse(token));
    }


    private Cookie createCookie(String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(MAX_AGE_SECONDS);
        return cookie;
    }
}
