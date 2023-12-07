package dev.eliezer.superticket.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import dev.eliezer.superticket.domain.repository.UserRepository;
import dev.eliezer.superticket.dto.AuthUserRequestDTO;
import dev.eliezer.superticket.dto.AuthUserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthUserServiceImpl {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.token.secret-user}")
    private String secretKey;


    public AuthUserResponseDTO execute(AuthUserRequestDTO authUserRequestDTO) throws AuthenticationException {
        var user = this.userRepository.findByEmail(authUserRequestDTO.email())
                .orElseThrow(() ->{
                    throw new UsernameNotFoundException("email/password incorrect");
                });
        var passwordMatches = this.passwordEncoder
                .matches(authUserRequestDTO.password(), user.getPassword());

        if(!passwordMatches){
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofHours(2));
        var token = JWT.create()
                .withIssuer("SuperTicket")
                .withSubject(user.getId().toString())
                .withClaim("roles", Arrays.asList("user"))
                .withExpiresAt(expiresIn)
                .sign(algorithm);

        var authUserResponse = AuthUserResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();

        return authUserResponse;
    }

}
