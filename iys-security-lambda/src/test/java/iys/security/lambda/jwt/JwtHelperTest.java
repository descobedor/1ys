package iys.security.lambda.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JwtHelperTest {

    JwtHelper jwtHelper;

    @BeforeEach
    void setUp() {
        jwtHelper = new JwtHelper();
    }

    @Test
    void getJsonWebToken() {

        //Given


        //When
        String json = jwtHelper.getJsonWebToken("clientId", "scope", Duration.ofHours(1));


        //Then
        assertThat(json).isNotNull();
        System.out.println(json);

    }
}