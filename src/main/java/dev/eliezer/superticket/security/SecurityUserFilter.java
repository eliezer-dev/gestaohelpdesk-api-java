package dev.eliezer.superticket.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.eliezer.superticket.providers.JWTUserProvider;
import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.exception.NotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static java.time.LocalDateTime.now;

@Component
public class SecurityUserFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUserProvider jwtUserProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");


            if(header != null && header != ""){

                var token = this.jwtUserProvider.validateToken(header);

                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getOutputStream().print("O token fornecido expirou.");
                    return;
                }
                var roles = token.getClaim("roles").asList(Object.class);
//        if (request.getRequestURI().startsWith("/candidate")){
//                request.setAttribute("candidate_id", token.getSubject());
//                var roles = token.getClaim("roles").asList(Object.class);
//                var grants = roles.stream()
//                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())
//                        ).toList();
//        }
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(),
                        null,
                        Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);
                request.setAttribute("user_id",auth.getPrincipal());
                request.setAttribute("user_role", roles.get(0));
            }

        filterChain.doFilter(request,response);
    }
}

