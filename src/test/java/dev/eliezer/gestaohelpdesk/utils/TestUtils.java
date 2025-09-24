package dev.eliezer.gestaohelpdesk.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

public class TestUtils {
    public static String objectToJson(Object obj) {
        try{
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(Long userID, String secretKey){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofHours(2));
        var token = JWT.create()
                .withIssuer("SuperTicket")
                .withSubject(userID.toString())
                .withClaim("roles", Arrays.asList("user"))
                .withExpiresAt(expiresIn)
                .sign(algorithm);
        return token;

    }

}
