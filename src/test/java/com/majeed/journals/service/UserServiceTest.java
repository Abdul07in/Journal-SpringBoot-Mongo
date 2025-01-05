package com.majeed.journals.service;

import com.majeed.journals.entity.User;
import com.majeed.journals.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void testFindByUsernameNotNull() {
        User user = userRepository.findByUsername("Majeed");
        assertNotNull(user, "User should not be null for username 'Majeed'");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Majeed", "Aniket", "Dheeraj"})
    public void testFindByUsername(String username) {
        User user = userRepository.findByUsername(username);
        assertNotNull(user, "User should not be null for username: " + username);
        assertEquals(username, user.getUsername(), "Username mismatch for user: " + username);
    }

    @ParameterizedTest
    @CsvSource({"1, 1, 2", "2, 2, 4"})
    public void testParameterized(int a, int b, int expected) {
        assertEquals(expected, a + b, "Sum is incorrect for inputs " + a + " and " + b);
    }

    @Test
    public void testFindByUsernameNotFound() {
        User user = userRepository.findByUsername("nonexistent");
        assertNull(user, "User should be null for username 'nonexistent'");
    }
}
