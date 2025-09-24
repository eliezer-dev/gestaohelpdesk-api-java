package dev.eliezer.superticket.modules.auth.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import dev.eliezer.superticket.modules.auth.dtos.AuthUserRequestDTO;
import dev.eliezer.superticket.modules.auth.dtos.AuthUserResponseDTO;
import dev.eliezer.superticket.modules.auth.dtos.UserForAuthResponseDTO;
import dev.eliezer.superticket.modules.user.repositories.UserRepository;
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
public class AuthUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.token.secret-user}")
    private String secretKey;

    public AuthUserResponseDTO execute(AuthUserRequestDTO authUserRequestDTO) throws AuthenticationException {
        var user = userRepository.findByEmail(authUserRequestDTO.getEmail())
                .orElseThrow(() ->{
                    throw new UsernameNotFoundException("Usuário/e-mail ou senha incorretos");
                });
        var passwordMatches = this.passwordEncoder
                .matches(authUserRequestDTO.getPassword(), user.getPassword());

        if(!passwordMatches){
            throw new AuthenticationException("Usuário/e-mail ou senha incorretos");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofHours(2));
        var token = JWT.create()
                .withIssuer("SuperTicket")
                .withSubject(user.getId().toString())
                .withClaim("roles", Arrays.asList(user.getUserRole()))
                .withExpiresAt(expiresIn)
                .sign(algorithm);

        var authUserResponse = AuthUserResponseDTO.builder()
                .user(UserForAuthResponseDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .cpf(user.getCpf())
                        .email(user.getEmail())
                        .cep(user.getCep())
                        .address(user.getAddress())
                        .addressNumber(user.getAddressNumber())
                        .state(user.getState())
                        .city(user.getCity())
                        .neighborhood(user.getNeighborhood())
                        .userRole(user.getUserRole())
                        .build())
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();

        return authUserResponse;
    }
}
