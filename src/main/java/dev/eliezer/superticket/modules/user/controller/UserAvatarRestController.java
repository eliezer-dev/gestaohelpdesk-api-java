package dev.eliezer.superticket.modules.user.controller;

import dev.eliezer.superticket.service.impl.UserAvatarServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import java.io.IOException;

@CrossOrigin
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
    @ResponseBody
    public ResponseEntity<String> getAvatar(@Valid HttpServletRequest request) throws IOException, AuthenticationException {
        Long id = Long.valueOf(request.getAttribute("user_id").toString());
        String avatar = userAvatarService.getAvatar(id);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(avatar);
    }
}
