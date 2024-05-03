package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.domain.model.User;
import dev.eliezer.superticket.domain.model.UserPicture;
import dev.eliezer.superticket.domain.repository.UserPictureRepository;
import dev.eliezer.superticket.domain.repository.UserRepository;
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

        var userToChange = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        UserPicture userPicture = userPictureRepository.save(UserPicture.builder()
                .filename(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes()))
                .build());

        if (!(userToChange.getIdPicture() == null)) {
            userPictureRepository.deleteById(userToChange.getIdPicture());
        }

        userToChange.setIdPicture(userPicture.getId());

        userRepository.save(userToChange);

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


