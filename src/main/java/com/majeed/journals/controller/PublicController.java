package com.majeed.journals.controller;

import com.majeed.journals.entity.User;
import com.majeed.journals.service.UserService;
import com.majeed.journals.service.UserServiceDetailsImpl;
import com.majeed.journals.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    private final UserServiceDetailsImpl userServiceDetailsImpl;
    private final JwtUtils jwtUtils;

    public PublicController(UserService userService, AuthenticationManager authenticationManager, UserServiceDetailsImpl userServiceDetailsImpl, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userServiceDetailsImpl = userServiceDetailsImpl;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            user.setRoles(List.of("USER"));
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = userServiceDetailsImpl.loadUserByUsername(user.getUsername());
            String jwtToken = jwtUtils.generateToken(userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
