package com.dsta.CNYBackend.shared.security;

import com.dsta.CNYBackend.User.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UsersService usersService;

    private int MAX_AGE_SECONDS = 3600 * 3600;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest, HttpServletResponse response) throws Exception {
        final String correctPassword = "cny@game";
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        if (username == "admin" && !password.equals(correctPassword)) {
            return ResponseEntity.notFound().build();
        }
        final UserDetails userDetails = usersService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        Cookie cookie = createCookie("Authorization", String.format("Bearer%s", token));
        response.addCookie(cookie);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    private Cookie createCookie(String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(MAX_AGE_SECONDS);
        return cookie;
    }
}
