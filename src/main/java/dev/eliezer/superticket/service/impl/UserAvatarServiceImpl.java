package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.modules.user.entities.User;
import dev.eliezer.superticket.modules.user.entities.UserPicture;
import dev.eliezer.superticket.modules.user.repositories.UserPictureRepository;
import dev.eliezer.superticket.modules.user.repositories.UserRepository;
import dev.eliezer.superticket.providers.ImageUtil;
import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class UserAvatarServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPictureRepository userPictureRepository;

    public String update(Long id, MultipartFile file) throws IOException {
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



    public String getAvatar (Long id) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        UserPicture userPicture = new UserPicture();

        if (user.getIdPicture() == null) {
            throw new BusinessException("The user does not have an avatar.");
        }

        userPicture = userPictureRepository.findById(user.getIdPicture())
                .orElseThrow(() -> new BusinessException("image not found."));

        byte[] image = ImageUtil.decompressImage(userPicture.getImageData());

        return Base64.getEncoder().encodeToString(image);


    }
}


