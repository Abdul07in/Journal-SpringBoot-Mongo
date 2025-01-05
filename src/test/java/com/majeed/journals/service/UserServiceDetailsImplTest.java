package com.majeed.journals.service;

import com.majeed.journals.entity.User;
import com.majeed.journals.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.mockito.Mockito.when;

public class UserServiceDetailsImplTest {

    @InjectMocks
    private UserServiceDetailsImpl userServiceDetails;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadUserByName() {
        when(userRepository.findByUsername("testUser"))
                .thenReturn(
                        User
                                .builder()
                                .username("testUser")
                                .password("test123")
                                .roles(List.of("ADMIN"))
                                .build()
                );

        UserDetails userDetails = userServiceDetails.loadUserByUsername("testUser");
        Assertions.assertNotNull(userDetails);
    }

    @Test
    public void testLoadUserByNameNotFound() {
        when(userRepository.findByUsername("testUser1"))
                .thenReturn(
                        User
                                .builder()
                                .username("testUser")
                                .password("test123")
                                .roles(List.of("ADMIN"))
                                .build()
                );

        UserDetails userDetails = userServiceDetails.loadUserByUsername("testUser");
        Assertions.assertNull(userDetails);
    }
}
