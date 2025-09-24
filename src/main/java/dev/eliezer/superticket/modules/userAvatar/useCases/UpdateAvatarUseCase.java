package dev.eliezer.superticket.modules.userAvatar.useCases;

import dev.eliezer.superticket.modules.userAvatar.entities.UserPicture;
import dev.eliezer.superticket.modules.userAvatar.repositories.UserPictureRepository;
import dev.eliezer.superticket.modules.user.repositories.UserRepository;
import dev.eliezer.superticket.providers.ImageUtil;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class UpdateAvatarUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPictureRepository userPictureRepository;

    public String execute(Long id, MultipartFile file) throws IOException {
        Long idPictureToDelete = null;
        var userToChange = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        UserPicture userPicture = userPictureRepository.save(UserPicture.builder()
                .filename(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes()))
                .build());

        if (!(userToChange.getIdPicture() == null)) {
            idPictureToDelete = userToChange.getIdPicture();
        }

        userToChange.setIdPicture(userPicture.getId());
        userRepository.save(userToChange);

        if (!(idPictureToDelete == null)) {
            userPictureRepository.deleteById(idPictureToDelete);
        }

        return Base64.getEncoder().encodeToString(file.getBytes());

    }
}
