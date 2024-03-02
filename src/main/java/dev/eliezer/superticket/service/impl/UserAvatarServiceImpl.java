package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.domain.model.User;
import dev.eliezer.superticket.domain.repository.UserRepository;
import dev.eliezer.superticket.service.exception.NotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static dev.eliezer.superticket.providers.DiskStorage.getFiles;
import static dev.eliezer.superticket.providers.DiskStorage.saveFiles;

@Service
public class UserAvatarServiceImpl {

    @Autowired
    private UserRepository userRepository;

    public User update(Long id, MultipartFile file) throws IOException {

        var userToChange = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        try {
            String filenameSaved = saveFiles(file);

            if (filenameSaved != "" || filenameSaved != null) {
                userToChange.setAvatar(filenameSaved);
            }
            return userRepository.save(userToChange);
        }catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Não foi possível salvar a imagem");
        }

    }

    public String getAvatar (Long id) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        try {
            return getFiles(user.getAvatar());
        }catch (IOException e ) {
            e.printStackTrace();
            throw  new IOException("Não foi possível carregar a imagem.");
        }

    }
}


