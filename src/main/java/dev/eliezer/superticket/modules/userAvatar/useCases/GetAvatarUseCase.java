package dev.eliezer.superticket.modules.userAvatar.useCases;

import dev.eliezer.superticket.modules.user.entities.User;
import dev.eliezer.superticket.modules.userAvatar.entities.UserPicture;
import dev.eliezer.superticket.modules.userAvatar.repositories.UserPictureRepository;
import dev.eliezer.superticket.modules.user.repositories.UserRepository;
import dev.eliezer.superticket.providers.ImageUtil;
import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
public class GetAvatarUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPictureRepository userPictureRepository;


    public String execute (Long id) throws IOException {
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
