package kma.topic2.junit.service;

import kma.topic2.junit.exceptions.UserNotFoundException;
import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.model.User;
import kma.topic2.junit.repository.UserRepository;
import kma.topic2.junit.validation.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @SpyBean
    private UserValidator userValidator;


    @ParameterizedTest(name = "login [{0}] password [{1}] fullName [{2}]")
    @MethodSource("dataForCreate")
    void createNewUserTest(final String login, final String password, final String fullName) {
        userService.createNewUser(NewUser.builder()
                .login(login).password(password).fullName(fullName).build());
        assertThat(userRepository.isLoginExists(login)).isTrue();
        Mockito.verify(userValidator).validateNewUser(ArgumentMatchers.any());
    }
    private static Stream<org.junit.jupiter.params.provider.Arguments> dataForCreate() {
        return Stream.of(Arguments.of("Login10", "Passwo", "FullName"),
                Arguments.of("Login20", "Passwo", "FullName"),
                Arguments.of("Login30", "Passwo", "FullName"));
    }

    @ParameterizedTest(name = "login [{0}]")
    @MethodSource("dataForGet")
    void getUserByLoginTest(final String login, final String password, final String fullName) {
        userService.createNewUser(NewUser.builder()
                .login(login).password(password).fullName(fullName).build());
        userRepository.getUserByLogin(login);
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> dataForGet() {
        return Stream.of(Arguments.of("Login1", "Passwo", "FullName"),
                Arguments.of("Login2", "Passwo", "FullName"),
                Arguments.of("Login3", "Passwo", "FullName"));
    }

    @Test
    void getUserByLoginTestErr() {
        assertThatThrownBy(() ->userService.getUserByLogin("login"))
                .isInstanceOf(UserNotFoundException.class);
    }

}