package dev.eliezer.superticket.controller;

import dev.eliezer.superticket.domain.model.User;
import dev.eliezer.superticket.dto.UserForUpdateRequestDTO;
import dev.eliezer.superticket.dto.UserResponseDTO;
import dev.eliezer.superticket.service.impl.UserAvatarServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@RestController
@RequestMapping("/users/avatar")
@Tag(name = "Avatar", description = "RESTful API for managing users avatars.")
public record UserAvatarRestController(UserAvatarServiceImpl userAvatarService) {

    @PutMapping
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> update(@Valid @RequestParam("avatar") MultipartFile file, HttpServletRequest request) throws IOException, AuthenticationException {
        Long id = Long.valueOf(request.getAttribute("user_id").toString());
        var userUpdated = userAvatarService.update(id, file);
        return ResponseEntity.ok().body(userUpdated);
    }

    @GetMapping
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<ByteArrayResource> getAvatar(@Valid HttpServletRequest request) throws IOException, AuthenticationException {
        Long id = Long.valueOf(request.getAttribute("user_id").toString());
        ByteArrayResource avatar = userAvatarService.getAvatar(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(avatar);
    }
}
