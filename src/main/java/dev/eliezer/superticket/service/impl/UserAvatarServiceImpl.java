package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.domain.model.User;
import dev.eliezer.superticket.domain.repository.UserRepository;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static dev.eliezer.superticket.config.Upload.UPLOAD_FOLDER;

@Service
public class UserAvatarServiceImpl {

    @Autowired
    private UserRepository userRepository;

    public User update(Long id, MultipartFile file) throws IOException {

        var userToChange = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        try {
            byte[] bytes = file.getBytes();
            byte[] hash = RandomUtils.nextBytes(20);
            String filename = hash + "-" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_FOLDER + filename);
            userToChange.setAvatar(filename);
            Files.write(path, bytes);

        }catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Não foi possível salvar o arquivo");
        }

        return userRepository.save(userToChange);

    }

    public ByteArrayResource getAvatar(Long id) throws IOException {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        try {
            String filename = user.getAvatar();

            var file = new File(UPLOAD_FOLDER + filename);

            var path = Paths.get(file.getAbsolutePath());

            return new ByteArrayResource(Files.readAllBytes(path));

        }catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Não foi possível carregar o arquivo do usuário.");

        }

    }

}


