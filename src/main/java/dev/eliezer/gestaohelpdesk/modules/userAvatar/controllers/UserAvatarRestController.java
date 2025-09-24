package dev.eliezer.gestaohelpdesk.modules.userAvatar.controllers;

import dev.eliezer.gestaohelpdesk.modules.userAvatar.useCases.GetAvatarUseCase;
import dev.eliezer.gestaohelpdesk.modules.userAvatar.useCases.UpdateAvatarUseCase;
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
public record UserAvatarRestController(GetAvatarUseCase getAvatarUseCase, UpdateAvatarUseCase updateAvatarUseCase) {

    @PutMapping
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> update(@Valid @RequestParam("avatar") MultipartFile file, HttpServletRequest request) throws IOException, AuthenticationException {
        Long id = Long.valueOf(request.getAttribute("user_id").toString());
        var userUpdated = updateAvatarUseCase.execute(id, file);
        return ResponseEntity.ok().body(userUpdated);

    }

    @GetMapping
    @SecurityRequirement(name = "jwt_auth")
    @ResponseBody
    public ResponseEntity<String> getAvatar(@Valid HttpServletRequest request) throws IOException, AuthenticationException {
        Long id = Long.valueOf(request.getAttribute("user_id").toString());
        String avatar = getAvatarUseCase.execute(id);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(avatar);
    }
}
