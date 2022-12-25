package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.user.dto.UserDto;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@JsonTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDtoJsonTest {
    private final JacksonTester<UserDto> userDtoJson;
    private static UserDto userDto;

    @BeforeAll
    public static void setUp() {
        userDto = UserDto.builder()
                .name("test name")
                .email("test@test.ru")
                .build();
    }

    @Test
    public void testCreateUserDto() throws IOException {
        //given
        JsonContent<UserDto> incorrectDtoJson = userDtoJson.write(userDto);
        //when
        UserDto mappedUserDto = userDtoJson.parseObject(incorrectDtoJson.getJson());
        //then
        assertThat(mappedUserDto).usingRecursiveComparison().isEqualTo(userDto);
    }
}
