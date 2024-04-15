package dev.eliezer.superticket.controller;

import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.impl.UserAvatarServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import static dev.eliezer.superticket.config.Upload.UPLOAD_FOLDER;

@RestController
@RequestMapping("/users/avatar")
@Tag(name = "Avatar", description = "RESTful API for managing users avatars.")
public record UserAvatarRestController(UserAvatarServiceImpl userAvatarService) {

    @PutMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> update(@Valid @PathVariable Long id, @RequestParam("avatar") MultipartFile file ) throws IOException, AuthenticationException {
        //Long id = Long.valueOf(request.getAttribute("user_id").toString());
        var userUpdated = userAvatarService.update(id, file);
        return ResponseEntity.ok().body(userUpdated);

    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    @ResponseBody
    public ResponseEntity<String> getAvatar(@Valid @PathVariable Long id) throws IOException, AuthenticationException {
        //Long id = Long.valueOf(request.getAttribute("user_id").toString());
        String avatar = userAvatarService.getAvatar(id);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(avatar);
    }
}
