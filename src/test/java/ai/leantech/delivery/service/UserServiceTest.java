package ai.leantech.delivery.service;

import ai.leantech.delivery.config.jwt.JwtProvider;
import ai.leantech.delivery.controller.model.user.UserDtoConverter;
import ai.leantech.delivery.entity.User;
import ai.leantech.delivery.repository.RoleRepository;
import ai.leantech.delivery.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private UserDtoConverter userDtoConverter;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService service;

    @Test
    void classIsMockedAndPublicMethodIsCallable() {
        User mockUser = mock(User.class);
        when(userRepository.findByLogin("Glad")).thenReturn(mockUser);
        assertEquals(mockUser, service.findByLogin("Glad"));
    }

}
