package kma.topic2.junit.validation;

import kma.topic2.junit.exceptions.ConstraintViolationException;
import kma.topic2.junit.exceptions.LoginExistsException;
import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @Mock
    UserRepository repository;
    @InjectMocks
    UserValidator userValidator;

    @ParameterizedTest(name = "login [{0}] password [{1}]")
    @MethodSource("dataForOk")
    void validateNewUserOk(final String login, final String password) {userValidator.validateNewUser(
                NewUser.builder()
                        .login(login)
                        .password(password)
                        .build());
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> dataForOk() {
        return Stream.of(Arguments.of("test","test"),
                Arguments.of("test1","sdvs"),
                Arguments.of("test2","testx"), Arguments.of("test3","testxc"), Arguments.of("test4","tesdtc"));
    }

    @ParameterizedTest(name = "login [{0}] password [{1}]")
    @MethodSource("dataForLogin")
    void validateNewUserLogin(final String login, final String password) {
        Mockito.when(repository.isLoginExists("test")).thenReturn(Boolean.TRUE);
        assertThatThrownBy(() -> userValidator.validateNewUser(
                NewUser.builder()
                        .login(login)
                        .password(password)
                        .build())).isInstanceOf(LoginExistsException.class);
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> dataForLogin() {
        return Stream.of(Arguments.of("test","test"),
                Arguments.of("test","sdvs"),
                Arguments.of("test","testx"), Arguments.of("test","testxc"), Arguments.of("test","tesdtc"));
    }

    @ParameterizedTest(name = "login [{0}] password [{1}]")
    @MethodSource("dataForPassword")
    void validateNewUserPassword(final String login, final String password) {
        assertThatThrownBy(() -> userValidator.validateNewUser(
                NewUser.builder()
                        .login(login)
                        .password(password)
                        .build())).isInstanceOf(ConstraintViolationException.class);
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> dataForPassword() {
        return Stream.of(Arguments.of("test","t"),
                Arguments.of("test","sdvsfdgsdfispombsponbspogmbosm"),
                Arguments.of("test","+-./`"));
    }

}