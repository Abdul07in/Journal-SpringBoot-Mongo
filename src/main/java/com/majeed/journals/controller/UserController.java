package com.majeed.journals.controller;

import com.majeed.journals.api.response.WeatherResponse;
import com.majeed.journals.entity.User;
import com.majeed.journals.service.QuotesService;
import com.majeed.journals.service.UserService;
import com.majeed.journals.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final WeatherService weatherService;
    private final QuotesService quotesService;

    public UserController(UserService userService, WeatherService weatherService, QuotesService quotesService) {
        this.userService = userService;
        this.weatherService = weatherService;
        this.quotesService = quotesService;
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User userByUsername = userService.findUserByUsername(authentication.getName());
            if (userByUsername != null) {
                userByUsername.setUsername(user.getUsername());
                userByUsername.setPassword(user.getPassword());
                userService.saveUser(userByUsername);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authUser = userService.findUserByUsername(authentication.getName());
        if (authUser != null) {
            userService.deleteUser(authUser.getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String greeting = "";
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String quotes = quotesService.getQuotes();
        if (weatherResponse != null) {
            greeting = " , Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }
        if (quotes != null) {
            greeting += " , " + quotes;
        }
        return ResponseEntity.ok("Hello, " + authentication.getName() + "! " + greeting);
    }


}
